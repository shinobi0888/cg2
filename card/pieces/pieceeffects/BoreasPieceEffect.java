package card.pieces.pieceeffects;

import game.Game;

import java.util.HashSet;

import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class BoreasPieceEffect extends EmptyPieceEffect {
	public static final int ID = 37;
	public static final int EURUS_ID = 38;
	public static final int ZEPHYRUS_ID = 39;
	public static final int NOTUS_ID = 40;

	public BoreasPieceEffect() {
		this.hasActive = true;
	}

	public boolean conditionActive(Game g, Piece p) {
		HashSet<Integer> cornerIds = new HashSet<Integer>();
		HashSet<Integer> winIds = new HashSet<Integer>();
		winIds.add(ID);
		winIds.add(EURUS_ID);
		winIds.add(ZEPHYRUS_ID);
		winIds.add(NOTUS_ID);
		Piece c1 = g.getBoard().getPiece(0, 0);
		Piece c2 = g.getBoard().getPiece(0, 8);
		Piece c3 = g.getBoard().getPiece(8, 0);
		Piece c4 = g.getBoard().getPiece(8, 8);
		if (c1 != null && c1.getOwner() == p.getOwner()) {
			cornerIds.add(c1.getCardBase().getId());
		}
		if (c2 != null && c2.getOwner() == p.getOwner()) {
			cornerIds.add(c2.getCardBase().getId());
		}
		if (c3 != null && c3.getOwner() == p.getOwner()) {
			cornerIds.add(c3.getCardBase().getId());
		}
		if (c4 != null && c4.getOwner() == p.getOwner()) {
			cornerIds.add(c4.getCardBase().getId());
		}
		return cornerIds.equals(winIds);
	}

	public void effectActive(Game g, Piece p) {
		g.simulateSetHealth(g.getEnemy(p.getOwner()), 0);
	}
}
