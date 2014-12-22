package card.hexes.cardeffects;

import game.Game;
import game.Player;

import java.util.ArrayList;

import board.Piece;
import card.Card;
import card.hexes.effects.HexEffect;

public class AltarOfHeartlessHexEffect implements HexEffect {
	public static final int ID = 46;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		ArrayList<Piece> p1Pieces = g.getBoard().getPlayersPieces(owningPlayer);
		ArrayList<Piece> p2Pieces = g.getBoard().getPlayersPieces(
				g.getEnemy(owningPlayer));
		if (p1Pieces.size() > 0) {
			int randomIndex = (int) (Math.random() * p1Pieces.size());
			g.simulateSendFromBoardToBottomOfDeck(p1Pieces.get(randomIndex));
		}
		if (p2Pieces.size() > 0) {
			int randomIndex = (int) (Math.random() * p2Pieces.size());
			g.simulateSendFromBoardToBottomOfDeck(p2Pieces.get(randomIndex));
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

}
