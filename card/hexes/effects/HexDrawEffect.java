package card.hexes.effects;

import game.Game;
import game.Player;
import card.Card;

public abstract class HexDrawEffect implements HexEffect {
	private int drawCount;

	public HexDrawEffect(int amount) {
		drawCount = amount;
	}

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		for(int i = 0; i < drawCount; i++) {
			g.simulateEffectDraw(owningPlayer);
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}
}
