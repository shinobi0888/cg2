package tests.cardtests;

import static org.junit.Assert.fail;
import game.Game;
import game.Player;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import board.Piece;
import buffs.PieceBuff;
import buffs.PlayerBuff;
import card.Card;

public abstract class BasicCardTest {
	protected Game game;

	@Before
	public void setUp() throws Exception {
		Player[] players = new Player[] { new Player("Player 1"),
				new Player("Player 2") };
		setPlayers(players);
		game = new Game(players[0], players[1], new CardTestGameInterface(this));
		game.startNewGameDebug();
	}

	public abstract void setPlayers(Player[] players);

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	protected void setDeck(Player p, int[] deck) {
		String result = "";
		for (int i = 0; i < deck.length; i += 2) {
			for (int j = 0; j < deck[i + 1]; j++) {
				result += (deck[i] + ",");
			}
		}
		p.loadDeck(result);
	}

	protected void setDummyDeck(Player p) {
		String result = "";
		for (int i = 0; i < 24; i++) {
			result += "1001,";
		}
		p.loadDeck(result);
	}

	// Game actions
	public ArrayList<String> instructionQueue = new ArrayList<String>();

	protected void actionPlay(int index) {
		game.playIndex(index);
	}

	protected void actionPlay(int index, int x, int y) {		
		instructionQueue.add(x + "," + y);
		game.playIndex(index);
	}
	
	protected void actionCycleTurn() {
		game.endTurn();
		game.beginTurn();
	}

	protected class CardTestPlayerListener implements Player.PlayerListener {
		BasicCardTest test;

		public CardTestPlayerListener(BasicCardTest test) {
			this.test = test;
		}

		public void cardDrawn(Card card) {

		}

		public void health(int oldHealth, int newHealth) {

		}

		public void onDamage(int amount) {

		}

		public void cardRemovedFromPlayed(Card card) {

		}

		public void overturned(int numCards) {

		}

		public void onDamageFromNoDraw() {

		}

		public void onPlayToField(Card card) {

		}

		public void cardSentToGrave(Card card) {

		}

	}

	protected class CardTestGameInterface implements Game.GameInterface {
		BasicCardTest test;

		public CardTestGameInterface(BasicCardTest test) {
			this.test = test;
		}

		public void piecePlayed(Piece p, int x, int y) {
		}

		public void pieceRemoved(Piece p, int oldX, int oldY) {

		}

		public Point requestBoardPos(String prompt, ArrayList<Point> valid) {
			Point pos = tryParsePos();
			return pos;
		}

		private Point tryParsePos() {
			if (test.instructionQueue.size() == 0) {
				return null;
			}
			String top = test.instructionQueue.get(test.instructionQueue.size() - 1)
					.trim();
			String[] pieces = top.split(",");
			if (pieces.length == 2) {
				try {
					return new Point(Integer.parseInt(pieces[0].trim()),
							Integer.parseInt(pieces[1].trim()));
				} catch (Exception e) {
					return null;
				}
			}
			return null;
		}

		public Piece requestBoardPiece(String prompt, ArrayList<Piece> valid) {

			return null;
		}

		public void revealCard(Player p, Card c) {

		}

		public void pieceMoved(Piece target, int oldX, int oldY, int newX, int newY) {

		}

		public void pieceAttacked(Piece attacker, Piece target) {

		}

		public void attackNothingHappened() {

		}

		public void pieceDestroyed(Piece target, int x, int y) {

		}

		public void pieceAscended(Piece target) {

		}

		public void pieceForceAscended(Piece target) {

		}

		public void pieceReleased(Piece target) {

		}

		public void playerLost(Player p) {

		}

		public void pieceEffectActivated(Piece p) {

		}

		public void hexEffectActivated(Card c) {

		}

		public void playerGainedPiecePlays(Player p, int count) {

		}

		public void playerGainedBuff(Player p, PlayerBuff b) {

		}

		public void playerLostBuff(Player p, PlayerBuff b) {

		}

		public void pieceGainedBuff(Piece p, PieceBuff b) {

		}

		public void pieceLostBuff(Piece p, PieceBuff b) {

		}

	}

}
