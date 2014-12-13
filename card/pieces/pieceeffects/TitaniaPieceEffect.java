package card.pieces.pieceeffects;

import game.Game;
import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class TitaniaPieceEffect extends EmptyPieceEffect {
	public static final int ID = 14;
	private static final int BURN_DAMAGE = 2;

	public TitaniaPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return playedPiece.getOwner().getCardInDeckCount(OberonPieceEffect.ID) > 0;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		if (g.getIface().requestYesNo(
				"Would you like to add 1 \"Oberon, King of Fairies\" to the hand?",
				false)) {
			playedPiece.getOwner().addFromDeckToHand(OberonPieceEffect.ID);
			g.simulateEffectDamage(playedPiece.getOwner(), BURN_DAMAGE);
		}
	}
}
