package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class ThunderspringWellPieceEffect extends EmptyPieceEffect {
	public static final int ID = 54;
	public static final int THUNDERSPRING_KEEPER_ID = 57;
	private static final int BURN_CAP = 5;

	public ThunderspringWellPieceEffect() {
		this.hasOnTurnEnd = true;
	}

	public boolean conditionOnTurnEnd(Game g, Piece p) {
		return g.turnPlayer().equals(p.getOwner());
	}

	public void effectOnTurnEnd(Game g, Piece p) {
		int randomDamage = (int) (Math.random() * BURN_CAP) + 1;
		g.simulateEffectDamage(g.getEnemy(p.getOwner()), randomDamage);
		// Check for thunderspring keeper
		if (g.getBoard().find(p.getOwner(), THUNDERSPRING_KEEPER_ID).size() == 0) {
			g.simulateEffectDamage(p.getOwner(), randomDamage);
		}
	}
}
