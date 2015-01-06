package card.hexes.hexeffects.debug;

import game.Game;
import game.Player;
import card.Card;
import card.hexes.effects.HexEffect;

public class FireOfHeliosHexEffect implements HexEffect {
	public static final int ID = 1002;
	private static final int BASE_DAMAGE = 15;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		g.getHexEffector().damage(g.otherPlayer(owningPlayer), BASE_DAMAGE);
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}
}
