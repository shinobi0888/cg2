package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import board.Piece;
import card.Card;
import card.hexes.effects.HexEffect;

public class ReplacementOfTheDeadHexEffect implements HexEffect {
	public static final int ID = 71;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		g.simulateSendAllGraveToDeck(owningPlayer);
		for (Piece p : g.getBoard().getPlayersPieces(owningPlayer)) {
			g.simulateDestroy(p);
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

}
