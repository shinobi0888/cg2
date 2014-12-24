package card.pieces.pieceeffects.set1;

import game.Game;
import game.Player;
import board.Piece;
import buffs.PieceBuff;
import card.Card;
import card.pieces.effects.EmptyPieceEffect;

public class GuardianAngelPieceEffect extends EmptyPieceEffect {
	public static final int ID = 100;
	private static final int DEF_COST = 2;
	private static final int HEAL_AMOUNT = 3;

	public GuardianAngelPieceEffect() {
		this.hasActive = true;
		this.hasOnPlay = true;
		this.hasOnTurnStart = true;
	}

	public boolean conditionOnTurnStart(Game g, Piece p) {
		return g.turnPlayer().equals(p.getOwner())
				&& p.getOwner().getHealth() < Player.MAX_HEALTH;
	}

	public void effectOnTurnStart(Game g, Piece p) {
		PieceBuff buff = p.findBuff(p.getSourceCard());
		if (buff != null) {
			g.simulateEffectHeal(p.getOwner(), HEAL_AMOUNT);
			buff.incrStacks(1);
			if (p.getDefense() == 0) {
				g.simulateDestroy(p);
			}
		}
	}

	public boolean conditionOnPlay(Game g, Piece p) {
		return true;
	}

	public void effectOnPlay(Game g, Piece p) {
		g.simulateGivePieceBuff(p, new GuardianAngelBuff(p, p.getSourceCard()));
	}

	public boolean conditionActive(Game g, Piece p) {
		return true;
	}

	public void effectActive(Game g, Piece p) {
		int defense = p.getDefense();
		Player owner = p.getOwner();
		g.simulateDestroy(p);
		g.simulateEffectHeal(owner, defense);
	}

	public class GuardianAngelBuff extends PieceBuff {
		public GuardianAngelBuff(Piece target, Card source) {
			this.target = target;
			this.source = source;
			this.stacks = 0;
		}

		public int getDefenseBuff(Game g) {
			return -1 * DEF_COST * stacks;
		}
	}
}