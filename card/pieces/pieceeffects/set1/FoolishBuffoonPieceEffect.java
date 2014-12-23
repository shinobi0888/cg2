package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class FoolishBuffoonPieceEffect extends EmptyPieceEffect {
	public static final int ID = 72;

	public FoolishBuffoonPieceEffect() {
		this.hasOnPlay = true;
		this.hasActive = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return playedPiece.getOwner().getCardInDeckCount(
				FoolishJesterPieceEffect.ID) > 0;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		if (g.getIface().requestYesNo(
				"Would you like to add \"Foolish Jester\" to the hand?", false)) {
			g.simulateAddFromDeckToHand(playedPiece.getOwner(),
					FoolishJesterPieceEffect.ID);
		}
	}

	public boolean conditionActive(Game g, Piece p) {
		return true;
	}

	public void effectActive(Game g, Piece p) {
		g.simulateDestroy(p);
		g.simulateEffectDraw(p.getOwner());
	}
}
