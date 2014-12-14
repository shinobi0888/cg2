package card;

import release.Release;
import release.ReleaseDiagonalLine;
import release.ReleaseSingleCenter;
import card.pieces.effects.EmptyPieceEffect;
import card.pieces.effects.PieceEffect;
import card.pieces.pieceeffects.AccursedPillarPieceEffect;
import card.pieces.pieceeffects.AgileFairyPieceEffect;
import card.pieces.pieceeffects.AncientArchitectPieceEffect;
import card.pieces.pieceeffects.AncientPillarPieceEffect;
import card.pieces.pieceeffects.BlueHeronPieceEffect;
import card.pieces.pieceeffects.BrillianceFairyPieceEffect;
import card.pieces.pieceeffects.CelebrationalPillarPieceEffect;
import card.pieces.pieceeffects.ChampionGerynPieceEffect;
import card.pieces.pieceeffects.CorruptPillarPieceEffect;
import card.pieces.pieceeffects.GreenWolfPieceEffect;
import card.pieces.pieceeffects.IdolOfRenewalPieceEffect;
import card.pieces.pieceeffects.OberonPieceEffect;
import card.pieces.pieceeffects.RedTigerPieceEffect;
import card.pieces.pieceeffects.SoulstealerPieceEffect;
import card.pieces.pieceeffects.TitaniaPieceEffect;
import card.pieces.pieceeffects.WinglessFairyPieceEffect;

/*
 * Adding a new card:
 * 1) Add to cards.txt
 * 2) Create appropriate PieceEffect and PieceBuffs
 * 3) Tie effect here
 */

public class PieceCardBase extends CardBase {
	private int attack, defense;
	private Release releasePattern;
	private CardClass cardClass;
	private PieceEffect effect;
	private int attackPattern;
	private int movePattern;
	private int numAttacks;
	private int numMoves;

	public int getAttack() {
		return attack;
	}

	public int getDefense() {
		return defense;
	}

	public int getAttackPattern() {
		return attackPattern;
	}

	public int getMovePattern() {
		return movePattern;
	}

	public int getNumAttacks() {
		return numAttacks;
	}

	public int getNumMoves() {
		return numMoves;
	}

	public Release getRelease() {
		return releasePattern;
	}

	public CardClass getCardClass() {
		return cardClass;
	}

	public PieceEffect getEffect() {
		return effect;
	}

	public String toString() {
		return name + " (" + cardId + ") [" + attack + "/" + defense + "]";
	}

	public String getDetails() {
		return name + " (" + cardId + ") [" + attack + "/" + defense + "]"
				+ "\nLore: " + lore + "\nClass: " + cardClass.name
				+ "\nAttacks and Moves: " + numAttacks + ", " + numMoves;
	}

	public static PieceCardBase parseCard(CardBase base, String additionalData) {
		PieceCardBase result = new PieceCardBase();
		result.setBase(base);
		String[] dataPieces = additionalData.split("::", -1);
		result.attack = Integer.parseInt(dataPieces[0]);
		result.defense = Integer.parseInt(dataPieces[1]);
		result.releasePattern = getReleaseById(Integer.parseInt(dataPieces[2]));
		result.cardClass = dataPieces[3].equals("") ? CardClass.NONE : CardClass
				.getCardClassFromString(dataPieces[3]);
		result.attackPattern = Integer.parseInt(dataPieces[4]);
		result.movePattern = Integer.parseInt(dataPieces[5]);
		result.numAttacks = Integer.parseInt(dataPieces[6]);
		result.numMoves = Integer.parseInt(dataPieces[7]);
		result.effect = getEffectById(result.cardId);
		return result;
	}

	private static Release getReleaseById(int id) {
		switch (id) {
		case ReleaseSingleCenter.ID:
			return new ReleaseSingleCenter();
		case ReleaseDiagonalLine.ID:
			return new ReleaseDiagonalLine();
		default:
			return null;
		}
	}

	private static PieceEffect getEffectById(int id) {
		switch (id) {
		case AncientPillarPieceEffect.ID:
			return new AncientPillarPieceEffect();
		case CorruptPillarPieceEffect.ID:
			return new CorruptPillarPieceEffect();
		case AccursedPillarPieceEffect.ID:
			return new AccursedPillarPieceEffect();
		case CelebrationalPillarPieceEffect.ID:
			return new CelebrationalPillarPieceEffect();
		case AncientArchitectPieceEffect.ID:
			return new AncientArchitectPieceEffect();
		case IdolOfRenewalPieceEffect.ID:
			return new IdolOfRenewalPieceEffect();
		case SoulstealerPieceEffect.ID:
			return new SoulstealerPieceEffect();
		case OberonPieceEffect.ID:
			return new OberonPieceEffect();
		case TitaniaPieceEffect.ID:
			return new TitaniaPieceEffect();
		case WinglessFairyPieceEffect.ID:
			return new WinglessFairyPieceEffect();
		case BrillianceFairyPieceEffect.ID:
			return new BrillianceFairyPieceEffect();
		case AgileFairyPieceEffect.ID:
			return new AgileFairyPieceEffect();
		case ChampionGerynPieceEffect.ID:
			return new ChampionGerynPieceEffect();
		case RedTigerPieceEffect.ID:
			return new RedTigerPieceEffect();
		case GreenWolfPieceEffect.ID:
			return new GreenWolfPieceEffect();
		case BlueHeronPieceEffect.ID:
			return new BlueHeronPieceEffect();
		default:
			return new EmptyPieceEffect();
		}
	}

	public enum CardClass {
		NONE("None"), PILLAR("Pillar"), FAIRY("Fairy"), SPIRIT("Spirit");

		private final String name;

		private CardClass(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public static CardClass getCardClassFromString(String str) {
			for (CardClass c : CardClass.values()) {
				if (c.getName().equals(str)) {
					return c;
				}
			}
			return CardClass.NONE;
		}
	}
}
