package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class AgileFairyPieceEffect extends EmptyPieceEffect {
	public static final int ID = 17;

	public AgileFairyPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return true;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		g.getPieceEffector().givePlays(playedPiece.getOwner(), 1);
	}
}
