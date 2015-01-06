package card.hexes.hexeffects.debug;

import game.Game;
import game.Player;
import card.Card;
import card.hexes.effects.HexEffect;

public class ExcessOfHarvestHexEffect implements HexEffect {
	public static final int ID = 1003;
	private static final int DRAW_COUNT = 5;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		for (int i = 0; i < DRAW_COUNT; i++) {
			g.getHexEffector().draw(owningPlayer);
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}
}
