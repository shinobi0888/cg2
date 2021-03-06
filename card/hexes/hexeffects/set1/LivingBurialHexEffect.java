package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import card.Card;
import card.hexes.effects.HexEffect;

public class LivingBurialHexEffect implements HexEffect {
	public static final int ID = 88;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		Player enemy = g.getEnemy(owningPlayer);
		Card c = enemy.getDeckCard(0);
		g.getHexEffector().revealCard(enemy, c);
		g.getHexEffector().reorderDeck(enemy, c, enemy.getDeckCount());
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return g.getEnemy(owningPlayer).getDeckCount() > 0;
	}

}
