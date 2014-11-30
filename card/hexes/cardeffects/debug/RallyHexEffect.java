package card.hexes.cardeffects.debug;

import game.Game;
import game.Player;
import board.Piece;
import buffs.PieceBuff;
import card.Card;
import card.hexes.effects.HexEffect;

public class RallyHexEffect implements HexEffect {
	public static final int ID = 1004;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		for (Piece p : g.getBoard().getAllPieces()) {
			g.simulateGivePieceBuff(p, new RallyBuff(source, p));
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

	public class RallyBuff extends PieceBuff {
		public RallyBuff(Card source, Piece target) {
			this.source = source;
			this.target = target;
			this.isRemoveAtEndOfTurn = true;
		}

		public int getAttackBuff(Game g) {
			return 1;
		}

		public int getDefenseBuff(Game g) {
			return -1;
		}
	}
}
