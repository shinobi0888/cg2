package card;

import game.Game;
import game.Player;
import card.hexes.cardeffects.InheritanceOfWillHexEffect;
import card.hexes.cardeffects.LivingWallsHexEffect;
import card.hexes.cardeffects.PillarTransfigurationHexEffect;
import card.hexes.cardeffects.ReappropriationHexEffect;
import card.hexes.cardeffects.debug.ExcessOfHarvestHexEffect;
import card.hexes.cardeffects.debug.FireOfHeliosHexEffect;
import card.hexes.cardeffects.debug.RallyHexEffect;
import card.hexes.cardeffects.debug.SmiteHexEffect;
import card.hexes.effects.HexEffect;

public class HexCardBase extends CardBase {
	public static HexCardBase parseCard(CardBase base) {
		HexCardBase result = new HexCardBase();
		result.setBase(base);
		result.effect = getEffectById(base.cardId);
		return result;
	}

	private static HexEffect getEffectById(int id) {
		switch (id) {
		case ReappropriationHexEffect.ID:
			return new ReappropriationHexEffect();
		case PillarTransfigurationHexEffect.ID:
			return new PillarTransfigurationHexEffect();
		case LivingWallsHexEffect.ID:
			return new LivingWallsHexEffect();
		case InheritanceOfWillHexEffect.ID:
			return new InheritanceOfWillHexEffect();
		}
		// Debug cards
		switch (id) {
		case FireOfHeliosHexEffect.ID:
			return new FireOfHeliosHexEffect();
		case ExcessOfHarvestHexEffect.ID:
			return new ExcessOfHarvestHexEffect();
		case RallyHexEffect.ID:
			return new RallyHexEffect();
		case SmiteHexEffect.ID:
			return new SmiteHexEffect();
		default:
			return null;
		}
	}

	private HexEffect effect;

	public boolean canActivate(Game g, Player owningPlayer, Card source) {
		return effect.canActivateEffect(g, owningPlayer, source);
	}

	public void activateEffect(Game g, Player owningPlayer, Card source) {
		effect.simulateEffect(g, owningPlayer, source);
	}

	public String toString() {
		return name + " (" + cardId + ")";
	}

	public String getDetails() {
		return name + " (" + cardId + ")" + "\nEffect: " + lore;
	}
}
