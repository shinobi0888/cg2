package card.pieces.pieceeffects;

import game.Game;
import board.Piece;
import buffs.Aura;
import buffs.PieceBuff;
import card.Card;
import card.PieceCardBase;
import card.pieces.effects.EmptyPieceEffect;

public class AncientPillarPieceEffect extends EmptyPieceEffect {
	public static final int ID = 3;

	public AncientPillarPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return true;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		playedPiece.setAura(new AncientPillarAura(playedPiece));
	}

	public class AncientPillarAura extends Aura {

		public AncientPillarAura(Piece src) {
			source = src;
		}

		public boolean shouldApplyAura(Game g, Piece p) {
			return p.getCardBase().getCardClass()
					.equals(PieceCardBase.CardClass.PILLAR)
					&& p.getOwner().equals(source.getOwner());
		}

		public PieceBuff getNewBuff(Game g, Piece p) {
			return new AncientPillarBuff(p, source.getSourceCard(), this);
		}
	}

	public class AncientPillarBuff extends PieceBuff {
		public AncientPillarBuff(Piece target, Card source, Aura sourceAura) {
			this.target = target;
			this.source = source;
			this.sourceAura = sourceAura;
			this.stacks = 0;
			this.isRemoveAtEndOfTurn = false;
		}

		public int getDefenseBuff(Game g) {
			return target.getCardBase().getCardClass()
					.equals(PieceCardBase.CardClass.PILLAR) ? 2 : 0;
		}
	}
}
