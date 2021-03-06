package board;

import game.Game;
import game.Player;
import gridmap.GridMapEntity;

import java.awt.Point;
import java.util.ArrayList;

import buffs.Aura;
import buffs.PieceBuff;
import card.Card;
import card.PieceCardBase;
import card.hexes.hexeffects.set1.StrangePotionHexEffect;
import card.pieces.effects.PieceEffect;

public class Piece extends GridMapEntity {
	private PieceCardBase source;
	private Card sourceCard;
	private Game g;
	private Player owner;
	private int attacks;
	private int moves;
	private ArrayList<PieceBuff> buffs;
	private Aura aura;
	private boolean activeUsedThisTurn;

	// Each piece should calculate its own attackable targets and moveable
	// targets after each turn event. It should also check for ascension status
	private ArrayList<Point> attackable;
	private ArrayList<Point> moveable;
	private boolean ascendable;

	// Calculates all possible actions the unit can take
	public void calculatePossibleActions(Board b) {
		calculateAttackable(b);
		calculateMoveable(b);
		calculateAscendable(b);
	}

	private void calculateAttackable(Board b) {
		attackable.clear();
		if (this.getAttacks() > 0) {
			for (Point p : b.getSquaresInPattern(x, y, source.getAttackPattern())) {
				Piece target = b.getPiece(p.x, p.y);
				if (target != null && !target.getOwner().equals(owner)
						&& target.canBeAttacked()) {
					attackable.add(p);
				}
			}
		}
	}

	private void calculateMoveable(Board b) {
		moveable.clear();
		if (this.getMoves() > 0) {
			for (Point p : b.getSquaresInPattern(x, y, source.getMovePattern())) {
				if (b.getPiece(p.x, p.y) == null
						&& (getEffect() == null || getEffect().conditionCanMove(g, this, p))) {
					moveable.add(p);
				}
			}
		}
	}

	private void calculateAscendable(Board b) {
		ascendable = b.pieceInEnemyEndZone(this);
	}

	public boolean canAttack(Point p) {
		return attackable.contains(p);
	}

	public ArrayList<Point> getAttackable() {
		return attackable;
	}

	public boolean canMove(Point p) {
		return moveable.contains(p);
	}

	public ArrayList<Point> getMoveable() {
		return moveable;
	}

	public boolean canAscend() {
		return ascendable;
	}

	public PieceEffect getEffect() {
		return source.getEffect();
	}

	public boolean activeUsedThisTurn() {
		return activeUsedThisTurn;
	}

	public void resetActive() {
		activeUsedThisTurn = false;
	}

	public void recordActiveUsed() {
		activeUsedThisTurn = true;
	}

	public Piece(Card sourceCard, Game g) {
		source = (PieceCardBase) sourceCard.getCardBase();
		owner = sourceCard.getOwner();
		this.sourceCard = sourceCard;
		this.g = g;
		attackable = new ArrayList<Point>();
		moveable = new ArrayList<Point>();
		buffs = new ArrayList<PieceBuff>();
	}

	// Aura and buff code

	public void setAura(Aura a) {
		aura = a;
	}

	public Aura getAura() {
		return aura;
	}

	public void addBuff(PieceBuff b) {
		buffs.add(b);
	}

	public PieceBuff findBuff(Card c) {
		for (PieceBuff b : buffs) {
			if (b.getSource().equals(c)) {
				return b;
			}
		}
		return null;
	}

	public PieceBuff findBuff(Aura a) {
		for (PieceBuff b : buffs) {
			if (b.getSourceAura() != null && b.getSourceAura().equals(a)) {
				return b;
			}
		}
		return null;
	}

	public void removeBuff(PieceBuff b) {
		buffs.remove(b);
	}

	public void removeBuffsFromPiece(Piece p) {
		ArrayList<PieceBuff> newBuffs = new ArrayList<PieceBuff>();
		for (PieceBuff buff : buffs) {
			if (!buff.getSource().equals(p.getSourceCard())) {
				newBuffs.add(buff);
			}
		}
		buffs = newBuffs;
	}

	public void removeEndOfTurnBuffs() {
		ArrayList<PieceBuff> newBuffs = new ArrayList<PieceBuff>();
		for (PieceBuff buff : buffs) {
			if (!buff.isRemoveAtEndOfTurn()) {
				newBuffs.add(buff);
			}
		}
		buffs = newBuffs;
	}

	// Base information

	public Card getSourceCard() {
		return sourceCard;
	}

	public PieceCardBase getCardBase() {
		return (PieceCardBase) sourceCard.getCardBase();
	}

	public String getName() {
		return source.getName();
	}

	// Stats

	public int getAttack() {
		boolean needSwap = false;
		for (PieceBuff b : buffs) {
			if (b.isSwapStats()) {
				needSwap = true;
				break;
			}
		}
		if (needSwap) {
			return getUnswappedDefense();
		} else {
			return getUnswappedAttack();
		}
	}

	private int getUnswappedAttack() {
		int attack = source.getAttack();
		int additionalAttack = 0;
		// TODO: implement all the attack increase decrease buffs
		for (PieceBuff b : buffs) {
			additionalAttack += b.getAttackBuff(g);
		}
		// Effect of Strange Potion
		boolean hasStrangePotion = false;
		for (PieceBuff b : buffs) {
			if (b instanceof StrangePotionHexEffect.StrangePotionBuff) {
				hasStrangePotion = true;
				break;
			}
		}
		if (hasStrangePotion) {
			attack -= additionalAttack;
		} else {
			attack += additionalAttack;
		}
		return Math.max(0, attack);
	}

	public int getDefense() {
		boolean needSwap = false;
		for (PieceBuff b : buffs) {
			if (b.isSwapStats()) {
				needSwap = true;
				break;
			}
		}
		if (needSwap) {
			return getUnswappedAttack();
		} else {
			return getUnswappedDefense();
		}
	}

	private int getUnswappedDefense() {
		int defense = source.getDefense();
		int additionalDefense = 0;
		// TODO: implement all the defense increase decrease buffs
		for (PieceBuff b : buffs) {
			additionalDefense += b.getDefenseBuff(g);
		}
		// Effect of Reappropriate
		boolean hasStrangePotion = false;
		for (PieceBuff b : buffs) {
			if (b instanceof StrangePotionHexEffect.StrangePotionBuff) {
				hasStrangePotion = true;
				break;
			}
		}
		if (hasStrangePotion) {
			defense -= additionalDefense;
		} else {
			defense += additionalDefense;
		}
		return Math.max(0, defense);
	}

	public boolean canBeAttacked() {
		for (PieceBuff b : buffs) {
			if (!b.canBeAttacked(g)) {
				return false;
			}
		}
		return true;
	}

	public Player getOwner() {
		return owner;
	}

	public void setAttacks(int attacks) {
		this.attacks = attacks;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	public void decrAttacks() {
		attacks--;
	}

	public void incrAttacks() {
		attacks++;
	}

	public void decrMoves() {
		moves--;
	}

	public int getAttacks() {
		return attacks;
	}

	public int getMoves() {
		return moves;
	}

	public String toString() {
		return getName() + " [" + getAttack() + ", " + getDefense() + "]";
	}

	public String toDetailedString() {
		String result = getName() + " [" + getAttack() + ", " + getDefense() + "]";
		if (aura != null) {
			result += "\tAura: " + aura.getClass().getSimpleName();
		}
		if (buffs.size() != 0) {
			result += "\tBuffs: ";
			for (PieceBuff b : buffs) {
				result += b.getClass().getSimpleName() + "(" + b.getStacks() + "), ";
			}
			result = result.substring(0, result.length() - 2);
		}
		return result;
	}
}
