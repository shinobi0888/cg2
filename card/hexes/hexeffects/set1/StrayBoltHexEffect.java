package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import card.Card;
import card.hexes.effects.HexEffect;

public class StrayBoltHexEffect implements HexEffect {
	public static final int ID = 59;
	public static final int BURN_DAMAGE = 4;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		g.simulateHexDamage(g.getEnemy(owningPlayer), BURN_DAMAGE);
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

}
