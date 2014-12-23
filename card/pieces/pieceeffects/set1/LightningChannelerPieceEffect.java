package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import card.hexes.hexeffects.set1.StrayBoltHexEffect;
import card.pieces.effects.EmptyPieceEffect;

public class LightningChannelerPieceEffect extends EmptyPieceEffect {
	public static final int ID = 61;

	public LightningChannelerPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return playedPiece.getOwner().getCardInDeckCount(StrayBoltHexEffect.ID) > 0;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		g.simulateAddFromDeckToHand(playedPiece.getOwner(), StrayBoltHexEffect.ID);
	}
}
