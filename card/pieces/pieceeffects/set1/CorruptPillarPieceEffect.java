package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import buffs.PieceBuff;
import card.Card;
import card.pieces.effects.EmptyPieceEffect;

public class CorruptPillarPieceEffect extends EmptyPieceEffect {
	public static final int ID = 4;

	public CorruptPillarPieceEffect() {
		this.hasActive = false;
		this.hasOnKill = false;
		this.hasOnPlay = true;
		this.hasOnTurnStart = true;
	}

	public boolean conditionOnTurnStart(Game g, Piece p) {
		return g.turnPlayer().equals(p.getOwner());
	}

	public void effectOnTurnStart(Game g, Piece p) {
		p.findBuff(p.getSourceCard()).incrStacks(1);
		if (p.getDefense() < 2) {
			g.simulateDestroy(p);
		}
	}

	public boolean conditionOnPlay(Game g, Piece p) {
		return true;
	}

	public void effectOnPlay(Game g, Piece p) {
		g.simulateGivePieceBuff(p, new CorruptPillarBuff(p, p.getSourceCard()));
	}

	public class CorruptPillarBuff extends PieceBuff {
		public CorruptPillarBuff(Piece target, Card source) {
			this.target = target;
			this.source = source;
			this.stacks = 0;
			this.isRemoveAtEndOfTurn = false;
		}

		public int getDefenseBuff(Game g) {
			return -2 * stacks;
		}
	}
}
