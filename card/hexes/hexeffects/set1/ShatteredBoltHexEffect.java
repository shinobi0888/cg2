package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import card.Card;
import card.hexes.effects.HexEffect;

public class ShatteredBoltHexEffect implements HexEffect {
	public static final int ID = 60;
	public static final int BURN_DAMAGE = 4;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		g.getHexEffector().damage(g.getEnemy(owningPlayer), BURN_DAMAGE);
		g.getHexEffector().damage(owningPlayer, BURN_DAMAGE);
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

}
