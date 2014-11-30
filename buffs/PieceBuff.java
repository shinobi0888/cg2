package buffs;

import game.Game;
import board.Piece;
import card.Card;

public class PieceBuff {
	protected Piece target;
	protected Card source;
	protected Aura sourceAura;
	protected int stacks;
	protected boolean isRemoveAtEndOfTurn;

	public int getAttackBuff(Game g) {
		return 0;
	}

	public int getDefenseBuff(Game g) {
		return 0;
	}

	public Piece getTarget() {
		return target;
	}

	public Card getSource() {
		return source;
	}

	public Aura getSourceAura() {
		return sourceAura;
	}

	public boolean isRemoveAtEndOfTurn() {
		return isRemoveAtEndOfTurn;
	}

	public void setStacks(int newStacks) {
		stacks = newStacks;
	}

	public int getStacks() {
		return stacks;
	}

	public void incrStacks(int amount) {
		stacks += amount;
	}

	public void decrStacks(int amount) {
		stacks -= amount;
	}
}
