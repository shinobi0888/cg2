package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import board.Piece;
import card.Card;
import card.hexes.effects.HexEffect;

public class ShadowOfRagnarokHexEffect implements HexEffect {
	public static final int ID = 77;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		while (owningPlayer.getDeckCount() > 0) {
			g.getHexEffector().mill(owningPlayer, 0);
		}
		for (Piece p : g.getBoard().getAllPiecesInPlayerOrder(g.turnPlayer())) {
			g.getHexEffector().destroy(p);
		}
		g.getHexEffector().endTurn();
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

}
