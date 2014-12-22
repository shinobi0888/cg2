
package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class ShrineOfEurusPieceEffect extends EmptyPieceEffect {
	public static final int ID = 43;

	public ShrineOfEurusPieceEffect() {
		this.hasOnSelfDestroyed = true;
	}

	public boolean conditionOnSelfDestroyed(Game g, Piece p) {
		return p.getOwner().getCardInDeckCount(BoreasPieceEffect.EURUS_ID) > 0;
	}

	public void effectOnSelfDestroyed(Game g, Piece p) {
		g.simulateMoveToTopOfDeck(p.getOwner(), BoreasPieceEffect.EURUS_ID);
	}

}
