package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import card.hexes.hexeffects.set1.StrayBoltHexEffect;
import card.pieces.effects.EmptyPieceEffect;

public class LightningConjurerPieceEffect extends EmptyPieceEffect {
	public static final int ID = 62;
	public static final int BURN_DAMAGE = 4;

	public LightningConjurerPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return playedPiece.getOwner().findInGrave(StrayBoltHexEffect.ID) != null;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		g.getPieceEffector().sendFromGraveToDeck(playedPiece.getOwner(),
				playedPiece.getOwner().findInGrave(StrayBoltHexEffect.ID), 0);
		g.getPieceEffector().shuffle(playedPiece.getOwner());
		g.getPieceEffector()
				.damage(g.getEnemy(playedPiece.getOwner()), BURN_DAMAGE);
	}
}
