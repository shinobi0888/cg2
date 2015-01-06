package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import board.Piece;
import card.Card;
import card.hexes.effects.HexEffect;

public class StrayBoltHexEffect implements HexEffect {
	public static final int ID = 59;
	public static final int MONUMENT_ID = 64;
	private static final int BURN_DAMAGE = 4;
	private static final int MONUMENT_BURN_DAMAGE = 4;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		g.getHexEffector().damage(g.getEnemy(owningPlayer), BURN_DAMAGE);
		// Check for lightning monuments
		for (Piece p : g.getBoard().find(owningPlayer, MONUMENT_ID)) {
			g.getHexEffector().damage(g.getEnemy(owningPlayer), MONUMENT_BURN_DAMAGE);
			g.getHexEffector().sendFromBoardToTopOfDeck(owningPlayer, p);
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

}
