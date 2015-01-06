package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;

import java.util.ArrayList;

import board.Piece;
import buffs.PieceBuff;
import card.Card;
import card.hexes.effects.HexEffect;

public class UnityHexEffect implements HexEffect {
	public static final int ID = 81;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		Piece destroy = g.getIface().requestBoardPiece(
				"Select a piece to destroy:",
				g.getBoard().getPlayersPieces(owningPlayer));
		int attack = destroy.getAttack();
		int defense = destroy.getDefense();
		g.getHexEffector().destroy(destroy);
		ArrayList<Piece> remainingPieces = g.getBoard().getPlayersPieces(
				owningPlayer);
		if (remainingPieces.size() > 0) {
			Piece target = g.getIface().requestBoardPiece(
					"Select a piece to give buff:", remainingPieces);
			g.getHexEffector().givePieceBuff(target, new UnityBuff(source, target, attack,
					defense));
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return g.getBoard().getPlayersPieces(owningPlayer).size() >= 2;
	}

	public class UnityBuff extends PieceBuff {
		private int attack, defense;

		public UnityBuff(Card source, Piece target, int attack, int defense) {
			this.source = source;
			this.target = target;
			this.attack = attack;
			this.defense = defense;
			this.isRemoveAtEndOfTurn = false;
		}

		public int getAttackBuff(Game g) {
			return attack;
		}

		public int getDefenseBuff(Game g) {
			return defense;
		}
	}
}
