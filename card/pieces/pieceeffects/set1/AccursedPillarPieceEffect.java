package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class AccursedPillarPieceEffect extends EmptyPieceEffect {
	public static final int ID = 5;

	public AccursedPillarPieceEffect() {
		this.hasActive = false;
		this.hasOnKill = false;
		this.hasOnPlay = false;
		this.hasOnTurnStart = true;
	}

	public boolean conditionOnTurnStart(Game g, Piece p) {
		return true;
	}

	public void effectOnTurnStart(Game g, Piece p) {
		g.simulateEffectDamage(g.turnPlayer(), 1);
	}
}
