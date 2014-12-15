package card.pieces.pieceeffects;

import game.Game;
import board.Piece;
import buffs.Aura;
import buffs.PieceBuff;
import card.Card;
import card.pieces.effects.EmptyPieceEffect;

public class EmeraldSerpentPieceEffect extends EmptyPieceEffect {
	public static final int ID = 24;

	public EmeraldSerpentPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return true;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		playedPiece.setAura(new EmeraldSerpentAura(playedPiece));
	}

	public class EmeraldSerpentAura extends Aura {

		public EmeraldSerpentAura(Piece src) {
			source = src;
		}

		public boolean shouldApplyAura(Game g, Piece p) {
			return p.getOwner().equals(source.getOwner());
		}

		public PieceBuff getNewBuff(Game g, Piece p) {
			return new EmeraldSerpentBuff(p, source.getSourceCard(), this);
		}
	}

	public class EmeraldSerpentBuff extends PieceBuff {
		public EmeraldSerpentBuff(Piece target, Card source, Aura sourceAura) {
			this.target = target;
			this.source = source;
			this.sourceAura = sourceAura;
			this.stacks = 0;
			this.isRemoveAtEndOfTurn = false;
		}

		public int getDefenseBuff(Game g) {
			return 2;
		}
	}
}