package game;

import board.Piece;
import card.Card;

public class HexEffector extends Effector {

	public HexEffector(Game g) {
		super(g);
	}

	public void destroy(Piece p) {
		g.simulateDestroy(p);
	}

	public void draw(Player p) {
		p.drawCard(g);
	}

	public void drawAndReveal(Player p) {
		Card c = p.drawCard(g);
		if (c != null) {
			g.getIface().revealCard(p, c);
		}
	}

	public void damage(Player p, int amount) {
		g.simulateAnyDamage(p, amount);
	}

	public void increasePieceAttacks(Piece p) {
		p.incrAttacks();
	}

	public void increaseAllPiecesAttacks(Player p) {
		for (Piece piece : g.getBoard().getPlayersPieces(p)) {
			increasePieceAttacks(piece);
		}
	}

	public void endTurn() {
		g.endTurn();
	}
}
