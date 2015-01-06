package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class UnboundSoulPieceEffect extends EmptyPieceEffect {
	public static final int ID = 95;

	public UnboundSoulPieceEffect() {
		hasOnAttack = true;
	}

	public boolean conditionOnAttack(Game g, Piece p, Piece attacked) {
		return true;
	}

	public void effectOnAttack(Game g, Piece p, Piece attacked) {
		g.getPieceEffector().destroy(p);
	}
}
