package card.hexes.effects;

import game.Game;
import game.Player;

import java.util.ArrayList;

import card.Card;

public abstract class HexCompoundEffect implements HexEffect {
	private ArrayList<HexEffect> effects;

	public HexCompoundEffect(ArrayList<HexEffect> effects) {
		this.effects = effects;
	}

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		for (HexEffect e : effects) {
			e.simulateEffect(g, owningPlayer, source);
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		for (HexEffect e : effects) {
			if (!e.canActivateEffect(g, owningPlayer, source)) {
				return false;
			}
		}
		return true;
	}
}
