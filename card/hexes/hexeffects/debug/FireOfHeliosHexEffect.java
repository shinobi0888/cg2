package card.hexes.hexeffects.debug;

import card.hexes.effects.HexEnemyDamageEffect;

public class FireOfHeliosHexEffect extends HexEnemyDamageEffect {
	private static final int BURN_DAMAGE = 15;
	public static final int ID = 1002;

	public FireOfHeliosHexEffect() {
		super(BURN_DAMAGE);
	}
}
