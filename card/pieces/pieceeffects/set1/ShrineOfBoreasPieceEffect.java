package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class ShrineOfBoreasPieceEffect extends EmptyPieceEffect {
	public static final int ID = 42;

	public ShrineOfBoreasPieceEffect() {
		this.hasOnSelfDestroyed = true;
	}

	public boolean conditionOnSelfDestroyed(Game g, Piece p) {
		return p.getOwner().getCardInDeckCount(BoreasPieceEffect.ID) > 0;
	}

	public void effectOnSelfDestroyed(Game g, Piece p) {
		g.getPieceEffector().reorderDeck(p.getOwner(),
				p.getOwner().findInDeck(BoreasPieceEffect.ID), 0);
	}

}
