package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import card.hexes.hexeffects.set1.StrayBoltHexEffect;
import card.pieces.effects.EmptyPieceEffect;

public class LightningDeityPieceEffect extends EmptyPieceEffect {
	public static final int ID = 63;

	public LightningDeityPieceEffect() {
		this.hasOnTurnEnd = true;
	}

	public boolean conditionOnTurnEnd(Game g, Piece p) {
		return g.turnPlayer().equals(p.getOwner())
				&& g.turnPlayer().findInGrave(StrayBoltHexEffect.ID) != null;
	}

	public void effectOnTurnEnd(Game g, Piece p) {
		g.simulateSendFromGraveToHand(p.getOwner(), StrayBoltHexEffect.ID);
		g.simulateMillFromDeck(p.getOwner(), 0);
	}
}
