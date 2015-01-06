package card.pieces.pieceeffects.set1;

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
			g.getPieceEffector()
					.discard(p.getOwner(), p.getOwner().getRandomInHand());
		}
		if (g.getEnemy(p.getOwner()).getHandCount() != 0) {
			g.getPieceEffector().discard(g.getEnemy(p.getOwner()),
					g.getEnemy(p.getOwner()).getRandomInHand());
		}
	}
}
