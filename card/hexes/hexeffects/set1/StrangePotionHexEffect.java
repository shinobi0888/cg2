package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import board.Piece;
import buffs.PieceBuff;
import card.Card;
import card.hexes.effects.HexEffect;

public class StrangePotionHexEffect implements HexEffect {
	public static final int ID = 36;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		for (Piece p : g.getBoard().getAllPieces()) {
			g.getHexEffector().givePieceBuff(p, new StrangePotionBuff(source, p));
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

	public class StrangePotionBuff extends PieceBuff {
		public StrangePotionBuff(Card source, Piece target) {
			this.source = source;
			this.target = target;
			this.isRemoveAtEndOfTurn = true;
		}
	}
}
