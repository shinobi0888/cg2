package card.hexes.effects;

import game.Game;
import game.Player;
import card.Card;

public interface HexEffect {
	public void simulateEffect(Game g, Player owningPlayer, Card source);

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source);
}
