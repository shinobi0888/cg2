package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import card.Card;
import card.hexes.effects.HexEffect;

public class ThreecycleHexEffect implements HexEffect {
	public static final int ID = 87;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		Player enemy = g.getEnemy(owningPlayer);
		for (int i = 0; i < 2 && enemy.getHandCount() > 0; i++) {
			g.getHexEffector().discard(enemy, enemy.getRandomInHand());
		}
		for (int i = 0; i < 2 && enemy.getGraveCount() > 0; i++) {
			int randomIndex = (int) (Math.random() * enemy.getGraveCount());
			g.getHexEffector().sendFromGraveToDeck(enemy,
					enemy.getGraveCard(randomIndex), enemy.getDeckCount());
		}
		for (int i = 0; i < 2; i++) {
			g.getHexEffector().drawAndReveal(enemy);
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

}
