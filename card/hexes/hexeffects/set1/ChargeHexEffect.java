package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import card.Card;
import card.hexes.effects.HexEffect;

public class ChargeHexEffect implements HexEffect {
	public static final int ID = 69;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		g.getHexEffector().increaseAllPiecesAttacks(owningPlayer);
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

}
