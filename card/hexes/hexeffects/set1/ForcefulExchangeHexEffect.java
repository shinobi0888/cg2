package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import buffs.PlayerBuff;
import card.Card;
import card.hexes.effects.HexEffect;

public class ForcefulExchangeHexEffect implements HexEffect {
	public static final int ID = 29;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		Player enemy = g.getEnemy(owningPlayer);
		g.simulateEffectDraw(enemy);
		g.simulateEffectDraw(enemy);
		g.simulateGivePlayerBuff(enemy, new ForcefulExchangeBuff(source));
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

	public class ForcefulExchangeBuff extends PlayerBuff {
		public ForcefulExchangeBuff(Card source) {
			super(source);
			isRemoveAtEndOfOwnTurn = true;
			isPreventFromPlayingPieces = true;
		}
	}
}
