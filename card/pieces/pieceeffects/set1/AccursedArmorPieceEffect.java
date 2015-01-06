package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import buffs.PieceBuff;
import card.Card;
import card.pieces.effects.EmptyPieceEffect;

public class AccursedArmorPieceEffect extends EmptyPieceEffect {
	public static final int ID = 97;

	public AccursedArmorPieceEffect() {
		this.hasOnPlay = true;
		this.hasOnKill = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return true;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		g.getPieceEffector().givePieceBuff(playedPiece, new AccursedArmorBuff(playedPiece,
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

	public class AccursedArmorBuff extends PieceBuff {
		public AccursedArmorBuff(Piece target, Card source) {
			this.target = target;
			this.source = source;
			this.stacks = 0;
			this.isRemoveAtEndOfTurn = false;
		}

		public int getDefenseBuff(Game g) {
			return stacks;
		}
	}
}
