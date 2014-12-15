package card.hexes.cardeffects;

import game.Game;
import game.Player;
import board.Piece;
import buffs.PieceBuff;
import card.Card;
import card.hexes.effects.HexEffect;

public class ReappropriationHexEffect implements HexEffect {
	public static final int ID = 2;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		for (Piece p : g.getAllPieces()) {
			g.simulateGivePieceBuff(p, new ReappropriationBuff(source, p));
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

	public class ReappropriationBuff extends PieceBuff {
		public ReappropriationBuff(Card source, Piece target) {
			this.source = source;
			this.target = target;
			this.isRemoveAtEndOfTurn = true;
			this.isSwapStats = true;
		}
	}

}
