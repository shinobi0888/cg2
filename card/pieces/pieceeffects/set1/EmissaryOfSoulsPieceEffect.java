package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import buffs.PieceBuff;
import card.Card;
import card.pieces.effects.EmptyPieceEffect;

public class EmissaryOfSoulsPieceEffect extends EmptyPieceEffect {
	public static final int ID = 94;

	public EmissaryOfSoulsPieceEffect() {
		this.hasOnPlay = true;
		this.hasOnKill = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return true;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		g.simulateGivePieceBuff(playedPiece, new EmissaryBuff(playedPiece,
				playedPiece.getSourceCard()));
	}

	public boolean conditionOnKill(Game g, Piece p, Piece killed) {
		return true;
	}

	public void effectOnKill(Game g, Piece p, Piece killed) {
		PieceBuff buff = p.findBuff(p.getSourceCard());
		if (buff != null) {
			buff.incrStacks(1);
		}
	}

	public class EmissaryBuff extends PieceBuff {
		public EmissaryBuff(Piece target, Card source) {
			this.target = target;
			this.source = source;
			this.stacks = 0;
			this.isRemoveAtEndOfTurn = false;
		}

		public int getAttackBuff(Game g) {
			return stacks;
		}
	}
}
