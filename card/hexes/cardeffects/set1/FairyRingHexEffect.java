package card.hexes.cardeffects.set1;

import game.Game;
import game.Player;

import java.util.ArrayList;

import card.Card;
import card.PieceCardBase.CardClass;
import card.hexes.effects.HexEffect;

public class FairyRingHexEffect implements HexEffect {
	public static final int ID = 19;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		ArrayList<Integer> fairyIds = owningPlayer
				.getCardsIdsInDeckOfClass(CardClass.FAIRY);
		if (fairyIds.size() > 0) {
			int chosenId = fairyIds.get((int) (Math.random() * fairyIds.size()));
			owningPlayer.addFromDeckToHand(chosenId);
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

}
