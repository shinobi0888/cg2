package card.pieces.pieceeffects.set1;

import game.Game;
import game.Player;
import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class WedjatFalconPieceEffect extends EmptyPieceEffect {
	public static final int ID = 86;
	private static final int BURN_DAMAGE = 4;

	public WedjatFalconPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return g.getEnemy(playedPiece.getOwner()).getHandCount() > 0;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		Player enemy = g.getEnemy(playedPiece.getOwner());
		int handCount = enemy.getHandCount();
		for (int i = 0; i < handCount; i++) {
			g.getPieceEffector().revealCard(enemy, enemy.getHandCard(i));
		}
		g.getPieceEffector()
				.damage(playedPiece.getOwner(), BURN_DAMAGE * handCount);
	}
}
