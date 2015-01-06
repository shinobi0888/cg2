package card.pieces.pieceeffects.set1;

import game.Game;

import java.awt.Point;

import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class WanderingHealerPieceEffect extends EmptyPieceEffect {
	public static final int ID = 84;
	private static final int HEAL_AMOUNT = 1;

	public WanderingHealerPieceEffect() {
		this.hasOnManuallyMoved = true;
	}

	public boolean conditionOnManuallyMoved(Game g, Piece p, Point old,
			Point target) {
		return target.x == old.x
				&& (target.y - old.y) == g.playerBoardDirection(p.getOwner());
	}

	public void effectOnManuallyMoved(Game g, Piece p, Point old, Point target) {
		g.getPieceEffector().heal(p.getOwner(), HEAL_AMOUNT);
	}
}
