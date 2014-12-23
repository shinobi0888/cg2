package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import card.Card;
import card.hexes.effects.HexEffect;

public class OpenRiftHexEffect implements HexEffect {
	public static final int ID = 70;
	private static final int MILL_COUNT = 3;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		owningPlayer.shuffleDeck();
		g.simulateHexDraw(owningPlayer);
		g.simulateHexDraw(owningPlayer);
		for (int i = 0; i < MILL_COUNT && owningPlayer.getDeckCount() > 0; i++) {
			g.simulateMillFromDeck(owningPlayer, owningPlayer.getDeckCount());
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

}
