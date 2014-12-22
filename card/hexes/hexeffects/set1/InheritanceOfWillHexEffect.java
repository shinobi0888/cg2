package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import board.PieceSnapshot;
import buffs.PlayerBuff;
import card.Card;
import card.hexes.effects.HexEffect;

public class InheritanceOfWillHexEffect implements HexEffect {
	public static final int ID = 11;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		g.simulateGivePlayerBuff(owningPlayer, new InheritanceOfWillBuff(source));
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

	public class InheritanceOfWillBuff extends PlayerBuff {
		public InheritanceOfWillBuff(Card source) {
			super(source);
			isRemoveAtEndOfTurn = true;
			hasOnPieceDestroyed = true;
		}

		public boolean conditionOnPieceDestroyed(Game g, Player player,
				PieceSnapshot p) {
			return p.getPiece().getOwner().equals(player) && p.getAttack() < 4;
		}

		public void effectOnPieceDestroyed(Game g, Player player, PieceSnapshot p) {
			g.simulateGivePlayerPiecePlays(player, 1);
		}
	}
}
