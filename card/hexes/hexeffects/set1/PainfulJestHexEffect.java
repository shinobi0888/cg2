package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import board.Piece;
import card.Card;
import card.hexes.effects.HexEffect;

public class PainfulJestHexEffect implements HexEffect {
	public static final int ID = 83;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		int totalAttack = 0;
		for (Piece p : g.getBoard().getPiecesInEndZone(owningPlayer)) {
			if (!p.getOwner().equals(owningPlayer)) {
				totalAttack += p.getAttack();
				g.getHexEffector().returnToHand(owningPlayer, p);
			}
		}
		g.getHexEffector().damage(owningPlayer, totalAttack);
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		for (Piece p : g.getBoard().getPiecesInEndZone(owningPlayer)) {
			if (!p.getOwner().equals(owningPlayer)) {
				return true;
			}
		}
		return false;
	}
}