package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class WanderingSpiritPieceEffect extends EmptyPieceEffect {
	public static final int ID = 75;

	public WanderingSpiritPieceEffect() {
		this.hasOnSelfDestroyed = true;
	}

	public boolean conditionOnSelfDestroyed(Game g, Piece p) {
		return p.getOwner().getGraveCount() > 0;
	}

	public void effectOnSelfDestroyed(Game g, Piece p) {
		int randomIndex = (int) (Math.random() * p.getOwner().getGraveCount());
		g.simulateSendFromGraveToHand(p.getOwner(),
				p.getOwner().getGraveCard(randomIndex).getCardBase().getId());
	}

}
