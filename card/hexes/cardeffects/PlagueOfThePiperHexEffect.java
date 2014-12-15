package card.hexes.cardeffects;

import game.Game;
import game.Player;
import buffs.PlayerBuff;
import card.Card;
import card.hexes.effects.HexEffect;

public class PlagueOfThePiperHexEffect implements HexEffect {
	public static final int ID = 30;
	private static final int PLAYS = 2;
	private static final int BURN_DAMAGE = 2;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		Player enemy = g.getEnemy(owningPlayer);
		g.simulateGivePlayerBuff(enemy, new PlagueOfThePiperBuff(source));
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

	public class PlagueOfThePiperBuff extends PlayerBuff {
		public PlagueOfThePiperBuff(Card source) {
			super(source);
			isRemoveAtEndOfOwnTurn = true;
			hasOnTurnStart = true;
			hasOnTurnEnd = true;
		}

		public boolean conditionOnTurnStart(Game g, Player player) {
			return g.turnPlayer().equals(player);
		}

		public void effectOnTurnStart(Game g, Player player) {
			g.simulateGivePlayerPiecePlays(player, PLAYS);
		}

		public boolean conditionOnTurnEnd(Game g, Player player) {
			return g.turnPlayer().equals(player);
		}

		public void effectOnTurnEnd(Game g, Player player) {
			int totalHand = player.getHandCount();
			for (int i = 0; i < totalHand; i++) {
				g.simulateMillPlayerHand(player, 0);
				g.simulateHexDamage(player, BURN_DAMAGE);
			}
		}

	}
}