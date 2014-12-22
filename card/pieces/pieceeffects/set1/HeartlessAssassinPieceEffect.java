package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class HeartlessAssassinPieceEffect extends EmptyPieceEffect {
	public static final int ID = 50;

	public HeartlessAssassinPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return g.getBoard()
				.find(playedPiece.getOwner(), HeartlessMercenaryPieceEffect.ID).size() > 0;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		Piece p = g.getIface().requestBoardPiece("Select a piece to destroy:",
				g.getAllPieces());
		g.simulateDestroy(p);
	}
}
