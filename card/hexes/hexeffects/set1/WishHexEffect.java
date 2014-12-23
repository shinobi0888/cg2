package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;

import java.util.ArrayList;

import card.Card;
import card.hexes.effects.HexEffect;

public class WishHexEffect implements HexEffect {
	public static final int ID = 76;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		ArrayList<Card> hexes = owningPlayer.getHexesInDeck();
		if (hexes.size() > 0) {
			int randomIndex = (int) (Math.random() * hexes.size());
			g.simulateAddFromDeckToHand(owningPlayer, hexes.get(randomIndex)
					.getCardBase().getId());
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

}
