package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import board.PieceSnapshot;
import buffs.PieceBuff;
import card.Card;
import card.pieces.effects.EmptyPieceEffect;

public class FlamewingPieceEffect extends EmptyPieceEffect {
	public static final int ID = 98;

	public FlamewingPieceEffect() {
		this.hasOnPlay = true;
		this.hasOnReceiveAttack = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return true;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		g.getPieceEffector().givePieceBuff(playedPiece, new FlamewingBuff(playedPiece,
				playedPiece.getSourceCard()));
	}

	public boolean conditionOnReceiveAttack(Game g, Piece p,
			PieceSnapshot attacker) {
		return true;
	}

	public void effectOnReceiveAttack(Game g, Piece p, PieceSnapshot attacker) {
		PieceBuff buff = p.findBuff(p.getSourceCard());
		if (buff != null) {
			buff.incrStacks(attacker.getAttack());
			if (p.getDefense() == 0) {
				g.getPieceEffector().destroy(p);
			}
		}
	}

	public class FlamewingBuff extends PieceBuff {
		public FlamewingBuff(Piece target, Card source) {
			this.target = target;
			this.source = source;
			this.stacks = 0;
			this.isRemoveAtEndOfTurn = false;
		}

		public int getDefenseBuff(Game g) {
			return -stacks;
		}
	}
}