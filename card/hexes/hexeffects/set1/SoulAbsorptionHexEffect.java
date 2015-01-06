package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import board.Piece;
import card.Card;
import card.hexes.effects.HexEffect;

public class SoulAbsorptionHexEffect implements HexEffect {
	public static final int ID = 80;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		int maxAttack = -1;
		Piece strongest = null;
		for (Piece p : g.getAllPieces()) {
			int attack = p.getAttack();
			if (attack == maxAttack) {
				strongest = Math.random() > .5 ? strongest : p;
			} else if (attack > maxAttack) {
				strongest = p;
				maxAttack = attack;
			}
		}
		g.getHexEffector().destroy(strongest);
		g.getHexEffector().damage(owningPlayer, maxAttack);
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return g.getAllPieces().size() > 0;
	}

}
