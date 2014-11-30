package card.hexes.effects;

import game.Game;
import game.Player;
import card.Card;

public abstract class HexEnemyDamageEffect implements HexEffect {
	private int baseDamage;

	public HexEnemyDamageEffect(int amount) {
		baseDamage = amount;
	}

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		// TODO: handle any spell damage +1 or anything
		g.simulateHexDamage(g.otherPlayer(owningPlayer), baseDamage);
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}
}
