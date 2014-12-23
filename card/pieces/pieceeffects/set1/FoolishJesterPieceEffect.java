package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class FoolishJesterPieceEffect extends EmptyPieceEffect {
	public static final int ID = 71;

	public FoolishJesterPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return true;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		g.simulateEffectDraw(playedPiece.getOwner());
	}
}
