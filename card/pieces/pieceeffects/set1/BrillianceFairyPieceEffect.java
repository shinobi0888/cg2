package card.pieces.pieceeffects.set1;

import game.Game;

import java.util.ArrayList;

import board.Piece;
import card.PieceCardBase.CardClass;
import card.pieces.effects.EmptyPieceEffect;

public class BrillianceFairyPieceEffect extends EmptyPieceEffect {
	public static final int ID = 16;
	private static final int BURN_DAMAGE = 2;

	public BrillianceFairyPieceEffect() {
		this.hasOnSelfDestroyed = true;
	}

	public boolean conditionOnSelfDestroyed(Game g, Piece p) {
		return true;
	}

	public void effectOnSelfDestroyed(Game g, Piece p) {
		g.getPieceEffector().damage(p.getOwner(), BURN_DAMAGE);
		ArrayList<Integer> fairyIds = p.getOwner().getCardsIdsInDeckOfClass(
				CardClass.FAIRY);
		if (fairyIds.size() > 0) {
			int chosenId = fairyIds.get((int) (Math.random() * fairyIds.size()));
			g.getPieceEffector().addFromDeckToHand(p.getOwner(), chosenId);
		}
	}

}
