package game;

import java.awt.Point;
import java.util.ArrayList;

import board.Board;
import board.Piece;
import board.PieceSnapshot;
import buffs.PieceBuff;
import buffs.PlayerBuff;
import card.Card;
import card.HexCardBase;
import card.PieceCardBase;
import card.pieces.effects.PieceEffect;

public class Game {
	private Player[] players;
	private Board board;
	private int turn;
	private GameInterface iface;
	private HexEffector hexE;
	private PieceEffector pieceE;

	public class GameBoardListener implements Board.BoardListener {
		private GameInterface iface;
		private Game game;

		public GameBoardListener(Game game, GameInterface iface) {
			this.iface = iface;
			this.game = game;
		}

		public void piecePlayed(Piece p, int x, int y) {
			iface.piecePlayed(p, x, y);
			// Handle onPlay of a piece if effect supports
			if (p.getCardBase().getEffect().hasOnPlay()
					&& p.getCardBase().getEffect().conditionOnPlay(game, p)) {
				iface.pieceEffectActivated(p);
				p.getCardBase().getEffect().effectOnPlay(game, p);
			}
			// Handle onPiecePlayed of all pieces on the board
			for (Piece piece : board.getAllPiecesInPlayerOrder(turnPlayer())) {
				if (!piece.equals(p)
						&& piece.getCardBase().getEffect().hasOnPiecePlayed()
						&& piece.getCardBase().getEffect()
								.conditionOnPiecePlayed(game, p, piece)) {
					piece.getCardBase().getEffect().effectOnPiecePlayed(game, p, piece);
				}
			}
			game.applyAuras();
		}

		public void pieceMoved(Piece p, int oldX, int oldY, int newX, int newY) {
			iface.pieceMoved(p, oldX, oldY, newX, newY);
			game.applyAuras();
		}

		public void pieceRemoved(Piece p, int oldX, int oldY) {
			iface.pieceRemoved(p, oldX, oldY);
			if (p.getAura() != null && p.getAura().isRemoveOnLeaveField()) {
				removePieceAuras(p);
			}
			game.applyAuras();
		}
	}

	public Game(Player p1, Player p2, GameInterface iface) {
		players = new Player[2];
		board = new Board(p1, p2, new GameBoardListener(this, iface));
		players[0] = p1;
		players[1] = p2;
		this.iface = iface;
		hexE = new HexEffector(this);
		pieceE = new PieceEffector(this);
	}

	public void startNewGame() {
		turn = -1;
		players[0].resetHealth();
		players[1].resetHealth();
		players[0].resetCards();
		players[1].resetCards();
		drawHand(players[0]);
		drawHand(players[1]);
	}

	public void startNewGameDebug() {
		turn = -1;
		players[0].resetHealth();
		players[1].resetHealth();
		drawHand(players[0]);
		drawHand(players[1]);
	}

	public Player getEnemy(Player p) {
		if (p.equals(players[0])) {
			return players[1];
		} else if (p.equals(players[1])) {
			return players[0];
		}
		return null;
	}

	public int playerBoardDirection(Player p) {
		return players[0].equals(p) ? 1 : -1;
	}

	public Player turnPlayer() {
		return players[turn % 2];
	}

	public Player offTurnPlayer() {
		return players[(turn + 1) % 2];
	}

	public Player otherPlayer(Player p) {
		return players[0].equals(p) ? players[1] : players[0];
	}

	public boolean isGameOver() {
		return players[0].lost() || players[1].lost();
	}

	public void beginTurn() {
		turn++;
		turnPlayer().resetPiecePlays();
		// Start of turn effects
		simulateOnTurnStartEffects();
		board.initNewTurnMovesAndAttacks(turnPlayer());
		board.calculateAllAllowedActions(turnPlayer());
		// Check for any preventative effects on drawing
		boolean canDraw = true;
		for (Piece p : board.getAllPieces()) {
			PieceEffect e = p.getEffect();
			if (e != null && e.hasPreventStartOfTurnDraw()
					&& e.conditionPreventStartOfTurnDraw(this, turnPlayer())) {
				iface.playerPreventedFromDrawing(turnPlayer(), p.getSourceCard());
				canDraw = false;
				break;
			}
		}
		if (canDraw) {
			Card c = turnPlayer().drawCard(this);
			if (c != null) {
				simulateAfterDrawEffects(c);
			}
		}
	}

	public void endTurn() {
		// First simulate the end of turn events
		simulateOnTurnEndEffects();
		// Then ascend everything
		Piece needAscend = board.getPiece(Board.WIDTH / 2,
				turnPlayer().equals(players[1]) ? 0 : Board.HEIGHT);
		if (needAscend != null && needAscend.getOwner().equals(turnPlayer())) {
			iface.pieceForceAscended(needAscend);
			simulateAscend(needAscend);
		}
		// Also end of turn buff removal
		removeEndOfTurnBuffs();
	}

	private boolean canPlayIndex(int index) {
		// Represents if the card in the turn player's hand can be played.
		Card c = turnPlayer().getHandCard(index);
		if (c.getCardBase() instanceof PieceCardBase) {
			PieceCardBase cardBase = (PieceCardBase) c.getCardBase();
			return turnPlayer().getPiecePlays() > 0
					&& !turnPlayer().isUnableToPlayPieces()
					&& board.getOpenEndZones(turnPlayer()).size() > 0
					&& (cardBase.getRelease() == null || cardBase.getRelease()
							.canRelease(this, turnPlayer()));
		} else if (c.getCardBase() instanceof HexCardBase) {
			return ((HexCardBase) (c.getCardBase())).canActivate(this, turnPlayer(),
					c);
		}
		return false;
	}

	/* Actions taken during turn begin */

	public boolean playIndex(int index) {
		if (!canPlayIndex(index)) {
			return false;
		}
		Card c = turnPlayer().getHandCard(index);
		if (c.getCardBase() instanceof PieceCardBase) {
			PieceCardBase cardBase = (PieceCardBase) c.getCardBase();
			ArrayList<Point> playableSpots;
			if (cardBase.getRelease() != null) {
				playableSpots = cardBase.getRelease().getPlayableSpots(this,
						turnPlayer());
			} else {
				playableSpots = board.getOpenEndZones(turnPlayer());
			}
			Point location = iface.requestBoardPos("Select which location to play "
					+ c.getCardBase().getName() + ":", playableSpots);
			// Handle cancel play
			if (location == null) {
				return false;
			}
			// For release snapshots
			ArrayList<PieceSnapshot> released = new ArrayList<PieceSnapshot>();
			if (cardBase.getRelease() != null) {
				ArrayList<Point> releaseTargets = cardBase.getRelease()
						.getReleaseTargets(this, turnPlayer(), location, iface);
				if (releaseTargets.size() != 0) {
					// Create snapshots for a release effect
					for (Point releaseTarget : releaseTargets) {
						released.add(new PieceSnapshot(board.getPiece(releaseTarget.x,
								releaseTarget.y)));
					}
					for (Point releaseTarget : releaseTargets) {
						releasePiece(releaseTarget.x, releaseTarget.y);
					}
				} else {
					// Bad release
					return false;
				}
			}
			turnPlayer().playFromHandToField(index);
			Piece newPiece = new Piece(c, this);
			board.playNewPiece(newPiece, location.x, location.y);
			// Check for effects of release
			if (newPiece.getEffect() != null
					&& newPiece.getEffect().hasOnReleasePlay()
					&& newPiece.getEffect().conditionOnReleasePlay(this, newPiece,
							released)) {
				newPiece.getEffect().effectOnReleasePlay(this, newPiece, released);
			}
			turnPlayer().decrPiecePlays();
			board.calculateAllAllowedActions(turnPlayer());
			return true;
		} else if (c.getCardBase() instanceof HexCardBase) {
			turnPlayer().playFromHandToField(index);
			HexCardBase hexCardBase = (HexCardBase) c.getCardBase();
			iface.hexEffectActivated(c);
			hexCardBase.activateEffect(this, turnPlayer(), c);
			turnPlayer().removePlayedCard(c);
			board.calculateAllAllowedActions(turnPlayer());
			return true;
		}
		return false;
	}

	private void releasePiece(int x, int y) {
		Piece p = board.getPiece(x, y);
		iface.pieceReleased(p);
		simulateRemove(p);
	}

	public boolean attackPiece(int pieceX, int pieceY, int targetX, int targetY) {
		Piece p = board.getPiece(pieceX, pieceY);
		if (p != null && p.canAttack(new Point(targetX, targetY))) {
			Piece target = board.getPiece(targetX, targetY);
			Piece destroyed = simulateAttack(p, target);
			PieceSnapshot s1 = new PieceSnapshot(p);
			PieceSnapshot s2 = new PieceSnapshot(target);
			if (!destroyed.equals(p) && p.getEffect() != null
					&& p.getEffect().hasOnAttack()
					&& p.getEffect().conditionOnAttack(this, p, s2)) {
				// Attacked effect
				p.getEffect().effectOnAttack(this, p, s2);
				if (destroyed.equals(target) && p.getEffect().hasOnKill()
						&& p.getEffect().conditionOnKill(this, p, s2)) {
					// On kill effect
					p.getEffect().effectOnKill(this, p, s2);
				}
			}
			// Receive attack effect
			if (!destroyed.equals(target) && target.getEffect() != null
					&& target.getEffect().hasOnReceiveAttack()
					&& p.getEffect().conditionOnReceiveAttack(this, p, s1)) {
				target.getEffect().effectOnReceiveAttack(this, target, s1);
			}
			board.calculateAllAllowedActions(turnPlayer());
			return true;
		}
		return false;
	}

	public boolean movePiece(int pieceX, int pieceY, int targetX, int targetY) {
		Piece p = board.getPiece(pieceX, pieceY);
		Point oldPoint = new Point(pieceX, pieceY);
		Point targetPoint = new Point(targetX, targetY);
		if (p != null && p.canMove(targetPoint)) {
			board.movePiece(p, targetX, targetY);
			p.decrMoves();
			PieceEffect effect = p.getEffect();
			if (effect != null && effect.hasOnManuallyMoved()
					&& effect.conditionOnManuallyMoved(this, p, oldPoint, targetPoint)) {
				effect.effectOnManuallyMoved(this, p, oldPoint, targetPoint);
			}
			board.calculateAllAllowedActions(turnPlayer());
			return true;
		}
		return false;
	}

	public boolean activatePiece(int pieceX, int pieceY) {
		Piece p = board.getPiece(pieceX, pieceY);
		if (p != null && p.getOwner().equals(turnPlayer())) {
			PieceEffect e = p.getCardBase().getEffect();
			if (e.hasActive() && e.conditionActive(this, p)) {
				e.effectActive(this, p);
				return true;
			}
		}
		return false;
	}

	public boolean ascendPiece(int pieceX, int pieceY) {
		Piece p = board.getPiece(pieceX, pieceY);
		if (p != null && p.canAscend() && p.getOwner().canAscendPieces()) {
			simulateAscend(p);
			board.calculateAllAllowedActions(turnPlayer());
			return true;
		}
		return false;
	}

	/* Actions taken during turn end */

	public void simulateAscend(Piece target) {
		iface.pieceAscended(target);
		simulateDirectDamage(getEnemy(target.getOwner()), target.getAttack());
		simulateRemove(target);
	}

	public void removeEndOfTurnBuffs() {
		turnPlayer().removeEndOfOwnTurnBuffs();
		turnPlayer().removeEndOfTurnBuffs();
		offTurnPlayer().removeEndOfTurnBuffs();
		for (Piece p : board.getAllPieces()) {
			p.removeEndOfTurnBuffs();
		}
	}

	// Simulates damage through ascension
	public void simulateDirectDamage(Player p, int amount) {
		simulateAnyDamage(p, amount);
	}

	// Simulates any damage, regardless of attacks or effects, etc
	public void simulateAnyDamage(Player p, int amount) {
		p.lowerHealth(amount);
		// Check loss condition
		if (p.lost()) {
			iface.playerLost(p);
		}
	}

	public void simulateAnyHeal(Player p, int amount) {
		p.raiseHealth(amount);
		// Check loss condition
		if (p.lost()) {
			iface.playerLost(p);
		}
	}

	public Piece simulateAttack(Piece attacker, Piece target) {
		iface.pieceAttacked(attacker, target);
		if (attacker.getAttack() >= target.getDefense()) {
			simulateDestroyByBattle(target);
			return target;
		} else if (target.getAttack() >= attacker.getDefense()) {
			simulateDestroyByBattle(attacker);
			return attacker;
		} else {
			iface.attackNothingHappened();
			return null;
		}
	}

	public void simulateDestroyByBattle(Piece target) {
		simulateDestroy(target);
	}

	public void simulateDestroy(Piece target) {
		PieceSnapshot snap = new PieceSnapshot(target);
		simulateRemove(target);
		simulateOnPieceSelfDestroyedEffects(snap);
		simulateOnPieceDestroyedEffects(snap);
	}

	public void simulateRemove(Piece target) {
		board.removePiece(target.getX(), target.getY());
		target.getOwner().removePlayedCard(target.getSourceCard());
	}

	// Applies auras globally, removing unfit auras

	public void applyAuras() {
		for (Piece p : board.getAllPieces()) {
			if (p.getAura() != null) {
				p.getAura().applyBuffs(this);
			}
		}
	}

	public void removePieceAuras(Piece piece) {
		for (Piece p : board.getAllPieces()) {
			PieceBuff b = p.findBuff(piece.getSourceCard());
			if (b != null) {
				p.removeBuff(b);
			}
		}
	}

	// Simulate on turn start effects

	public void simulateOnTurnStartEffects() {
		// Player buff turn starts have precedence, starting from turn player
		for (PlayerBuff b : turnPlayer().getBuffs()) {
			if (b.hasOnTurnStart() && b.conditionOnTurnStart(this, turnPlayer())) {
				b.effectOnTurnStart(this, turnPlayer());
			}
		}
		for (PlayerBuff b : offTurnPlayer().getBuffs()) {
			if (b.hasOnTurnStart() && b.conditionOnTurnStart(this, offTurnPlayer())) {
				b.effectOnTurnStart(this, offTurnPlayer());
			}
		}
		for (Piece p : board.getAllPiecesInPlayerOrder(turnPlayer())) {
			PieceEffect e = p.getCardBase().getEffect();
			if (e.hasOnTurnStart() && e.conditionOnTurnStart(this, p)) {
				iface.pieceEffectActivated(p);
				e.effectOnTurnStart(this, p);
			}
		}
	}

	public void simulateOnTurnEndEffects() {
		// Player buff turn starts have precedence, starting from turn player
		for (PlayerBuff b : turnPlayer().getBuffs()) {
			if (b.hasOnTurnEnd() && b.conditionOnTurnEnd(this, turnPlayer())) {
				b.effectOnTurnEnd(this, turnPlayer());
			}
		}
		for (PlayerBuff b : offTurnPlayer().getBuffs()) {
			if (b.hasOnTurnEnd() && b.conditionOnTurnEnd(this, offTurnPlayer())) {
				b.effectOnTurnEnd(this, offTurnPlayer());
			}
		}
		// Then piece effects
		for (Piece p : board.getAllPiecesInPlayerOrder(turnPlayer())) {
			if (p.getEffect().hasOnTurnEnd()
					&& p.getEffect().conditionOnTurnEnd(this, p)) {
				p.getEffect().effectOnTurnEnd(this, p);
			}
		}
	}

	public void simulateAfterDrawEffects(Card drawn) {
		for (Piece p : board.getAllPiecesInPlayerOrder(turnPlayer())) {
			PieceEffect e = p.getCardBase().getEffect();
			if (e.hasAfterMainDraw() && e.conditionAfterMainDraw(this, p, drawn)) {
				iface.pieceEffectActivated(p);
				e.effectAfterMainDraw(this, p, drawn);
			}
		}
	}

	public void simulateOnPieceDestroyedEffects(PieceSnapshot snap) {
		for (PlayerBuff b : turnPlayer().getBuffs()) {
			if (b.hasOnPieceDestroyed()
					&& b.conditionOnPieceDestroyed(this, turnPlayer(), snap)) {
				b.effectOnPieceDestroyed(this, turnPlayer(), snap);
			}
		}
		for (PlayerBuff b : offTurnPlayer().getBuffs()) {
			if (b.hasOnPieceDestroyed()
					&& b.conditionOnPieceDestroyed(this, offTurnPlayer(), snap)) {
				b.effectOnPieceDestroyed(this, offTurnPlayer(), snap);
			}
		}
	}

	public void simulateOnPieceSelfDestroyedEffects(PieceSnapshot snap) {
		PieceEffect e = snap.getPiece().getCardBase().getEffect();
		if (e != null && e.hasOnSelfDestroyed()
				&& e.conditionOnSelfDestroyed(this, snap.getPiece())) {
			e.effectOnSelfDestroyed(this, snap.getPiece());
		}
	}

	public ArrayList<Piece> getAllPieces() {
		return board.getAllPieces();
	}

	public Board getBoard() {
		return board;
	}

	public GameInterface getIface() {
		return iface;
	}

	public HexEffector getHexEffector() {
		return hexE;
	}

	public PieceEffector getPieceEffector() {
		return pieceE;
	}

	private void drawHand(Player p) {
		for (int i = 0; i < 6; i++) {
			p.drawCard(this);
		}
	}

	public interface GameInterface extends Board.BoardListener {
		// Effect driven
		public Point requestBoardPos(String prompt, ArrayList<Point> valid);

		public Piece requestBoardPiece(String prompt, ArrayList<Piece> valid);

		public boolean requestYesNo(String prompt, boolean defaultAnswer);

		public Card requestHandCard(String prompt, Player p);

		public Card requestCard(String prompt, ArrayList<Card> options);

		public void revealCard(Player p, Card c);

		// Event driven
		public void pieceMoved(Piece target, int oldX, int oldY, int newX, int newY);

		public void pieceAttacked(Piece attacker, Piece target);

		public void attackNothingHappened();

		public void pieceDestroyed(Piece target, int x, int y);

		public void pieceAscended(Piece target);

		public void pieceForceAscended(Piece target);

		public void pieceReleased(Piece target);

		public void playerLost(Player p);

		public void pieceEffectActivated(Piece p);

		public void hexEffectActivated(Card c);

		public void playerGainedPiecePlays(Player p, int count);

		public void playerGainedBuff(Player p, PlayerBuff b);

		public void playerLostBuff(Player p, PlayerBuff b);

		public void pieceGainedBuff(Piece p, PieceBuff b);

		public void pieceLostBuff(Piece p, PieceBuff b);

		public void piecesSwapped(Piece p1, Piece p2);

		public void playerPreventedFromDrawing(Player p, Card source);
	}
}
