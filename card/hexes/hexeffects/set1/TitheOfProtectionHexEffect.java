package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import card.Card;
import card.hexes.effects.HexEffect;

public class TitheOfProtectionHexEffect implements HexEffect {
	public static final int ID = 28;
	private static final int BURN_DAMAGE = 8;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		g.getHexEffector().damage(owningPlayer, BURN_DAMAGE);
		for (int i = 0; i < 2; i++) {
			int enemyHandCount = g.getEnemy(owningPlayer).getHandCount();
			if (enemyHandCount == 0) {
				break;
			}
			g.getHexEffector().sendFromHandToDeck(g.getEnemy(owningPlayer),
					g.getEnemy(owningPlayer).getRandomInHand(), 0);
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

}
