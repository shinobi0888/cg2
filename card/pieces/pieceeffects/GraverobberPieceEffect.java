package card.pieces.pieceeffects;

import game.Game;
import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class GraverobberPieceEffect extends EmptyPieceEffect {
	public static final int ID = 48;

	public GraverobberPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece p) {
		return true;
	}

	public void effectOnPlay(Game g, Piece p) {
		if (p.getOwner().getHandCount() != 0) {
			int randomIndex = (int) (Math.random() * p.getOwner().getHandCount());
			g.simulateDiscardFromHand(p.getOwner(), randomIndex);
		}
		if (g.getEnemy(p.getOwner()).getHandCount() != 0) {
			int randomIndex = (int) (Math.random() * g.getEnemy(p.getOwner())
					.getHandCount());
			g.simulateDiscardFromHand(g.getEnemy(p.getOwner()), randomIndex);
		}
	}
}
