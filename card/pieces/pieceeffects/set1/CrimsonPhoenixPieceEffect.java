package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import buffs.Aura;
import buffs.PieceBuff;
import card.Card;
import card.pieces.effects.EmptyPieceEffect;

public class CrimsonPhoenixPieceEffect extends EmptyPieceEffect {
	public static final int ID = 23;

	public CrimsonPhoenixPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return true;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		playedPiece.setAura(new CrimsonPhoenixAura(playedPiece));
	}

	public class CrimsonPhoenixAura extends Aura {

		public CrimsonPhoenixAura(Piece src) {
			source = src;
		}

		public boolean shouldApplyAura(Game g, Piece p) {
			return p.getOwner().equals(source.getOwner());
		}

		public PieceBuff getNewBuff(Game g, Piece p) {
			return new CrimsonPhoenixBuff(p, source.getSourceCard(), this);
		}
	}

	public class CrimsonPhoenixBuff extends PieceBuff {
		public CrimsonPhoenixBuff(Piece target, Card source, Aura sourceAura) {
			this.target = target;
			this.source = source;
			this.sourceAura = sourceAura;
			this.stacks = 0;
			this.isRemoveAtEndOfTurn = false;
		}

		public int getAttackBuff(Game g) {
			return 2;
		}
	}
}