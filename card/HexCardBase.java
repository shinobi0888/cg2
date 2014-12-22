package card;

import game.Game;
import game.Player;
import card.hexes.effects.HexEffect;
import card.hexes.hexeffects.debug.ExcessOfHarvestHexEffect;
import card.hexes.hexeffects.debug.FireOfHeliosHexEffect;
import card.hexes.hexeffects.debug.RallyHexEffect;
import card.hexes.hexeffects.debug.SmiteHexEffect;
import card.hexes.hexeffects.set1.FairyRingHexEffect;
import card.hexes.hexeffects.set1.ForcefulExchangeHexEffect;
import card.hexes.hexeffects.set1.InheritanceOfWillHexEffect;
import card.hexes.hexeffects.set1.LivingWallsHexEffect;
import card.hexes.hexeffects.set1.PillarTransfigurationHexEffect;
import card.hexes.hexeffects.set1.PlagueOfThePiperHexEffect;
import card.hexes.hexeffects.set1.PrismaticPromotionHexEffect;
import card.hexes.hexeffects.set1.ReappropriationHexEffect;
import card.hexes.hexeffects.set1.StrangePotionHexEffect;
import card.hexes.hexeffects.set1.TheDivineWindsHexEffect;
import card.hexes.hexeffects.set1.TitheOfProtectionHexEffect;

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
		case FairyRingHexEffect.ID:
			return new FairyRingHexEffect();
		case PrismaticPromotionHexEffect.ID:
			return new PrismaticPromotionHexEffect();
		case TitheOfProtectionHexEffect.ID:
			return new TitheOfProtectionHexEffect();
		case ForcefulExchangeHexEffect.ID:
			return new ForcefulExchangeHexEffect();
		case PlagueOfThePiperHexEffect.ID:
			return new PlagueOfThePiperHexEffect();
		case StrangePotionHexEffect.ID:
			return new StrangePotionHexEffect();
		case TheDivineWindsHexEffect.ID:
			return new TheDivineWindsHexEffect();
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
