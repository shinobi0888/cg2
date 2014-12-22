package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;

import java.util.ArrayList;

import board.Piece;
import card.Card;
import card.PieceCardBase.CardClass;
import card.hexes.effects.HexEffect;

public class PrismaticPromotionHexEffect implements HexEffect {
	public static final int ID = 27;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		Piece target = g.getIface().requestBoardPiece(
				"Select a [Spirit] to destroy:",
				g.getBoard().find(owningPlayer, CardClass.SPIRIT));
		int x = target.getX();
		int y = target.getY();
		g.simulateDestroy(target);
		ArrayList<Card> spirits = owningPlayer
				.getCardsInDeckOfClass(CardClass.SPIRIT);
		if (spirits.size() > 0) {
			Card chosen = spirits.get((int) (Math.random() * spirits.size()));
			if (g.getBoard().getPiece(x, y) == null) {
				g.simulatePlayFromDeck(owningPlayer, chosen, x, y);
			}
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return g.getBoard().find(owningPlayer, CardClass.SPIRIT).size() > 0;
	}

}
