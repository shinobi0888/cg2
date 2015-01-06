package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;

import java.util.ArrayList;

import board.Piece;
import card.Card;
import card.PieceCardBase;
import card.hexes.effects.HexEffect;

public class SoulCannonHexEffect implements HexEffect {
	public static final int ID = 79;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		ArrayList<Card> handPieces = new ArrayList<Card>();
		int handSize = owningPlayer.getHandCount();
		for (int i = 0; i < handSize; i++) {
			if (owningPlayer.getHandCard(i).getCardBase() instanceof PieceCardBase) {
				handPieces.add(owningPlayer.getHandCard(i));
			}
		}
		Card discard = g.getIface().requestCard("Select a piece to discard:",
				handPieces);
		int attack = ((PieceCardBase) discard.getCardBase()).getAttack();
		g.getHexEffector().discard(owningPlayer, discard);
		Piece target = g.getIface().requestBoardPiece("Select a piece to target:",
				g.getAllPieces());
		if (target.getAttack() < attack || target.getDefense() < attack) {
			g.getHexEffector().destroy(target);
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		if (g.getAllPieces().size() == 0) {
			return false;
		}
		int handSize = owningPlayer.getHandCount();
		for (int i = 0; i < handSize; i++) {
			if (owningPlayer.getHandCard(i).getCardBase() instanceof PieceCardBase) {
				return true;
			}
		}
		return false;
	}

}
