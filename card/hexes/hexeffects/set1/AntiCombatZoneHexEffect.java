package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import buffs.PlayerBuff;
import card.Card;
import card.hexes.effects.HexEffect;

public class AntiCombatZoneHexEffect implements HexEffect {
	public static final int ID = 82;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		Player enemy = g.getEnemy(owningPlayer);
		g.getHexEffector().givePlayerBuff(owningPlayer,
				new AntiCombatZoneBuff(source));
		g.getHexEffector().givePlayerBuff(enemy, new AntiCombatZoneBuff(source));
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

	public class AntiCombatZoneBuff extends PlayerBuff {
		public AntiCombatZoneBuff(Card source) {
			super(source);
			isRemoveAtEndOfOwnTurn = true;
			isPreventAscends = true;
		}
	}
}