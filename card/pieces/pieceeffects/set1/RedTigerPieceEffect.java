package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import buffs.Aura;
import buffs.PieceBuff;
import card.Card;
import card.PieceCardBase;
import card.PieceCardBase.CardClass;
import card.pieces.effects.EmptyPieceEffect;

public class RedTigerPieceEffect extends EmptyPieceEffect {
	public static final int ID = 20;

	public RedTigerPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return true;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		playedPiece.setAura(new RedTigerAura(playedPiece));
	}

	public class RedTigerAura extends Aura {

		public RedTigerAura(Piece src) {
			source = src;
		}

		public boolean shouldApplyAura(Game g, Piece p) {
			return p.getCardBase().getCardClass().equals(CardClass.SPIRIT)
					&& !p.equals(source) && p.getOwner().equals(source.getOwner())
					&& Math.abs(p.getX() - source.getX()) <= 1
					&& Math.abs(p.getY() - source.getY()) <= 1;
		}

		public PieceBuff getNewBuff(Game g, Piece p) {
			return new RedTigerBuff(p, source.getSourceCard(), this);
		}
	}

	public class RedTigerBuff extends PieceBuff {
		public RedTigerBuff(Piece target, Card source, Aura sourceAura) {
			this.target = target;
			this.source = source;
			this.sourceAura = sourceAura;
			this.stacks = 0;
			this.isRemoveAtEndOfTurn = false;
		}

		public int getAttackBuff(Game g) {
			return target.getCardBase().getCardClass()
					.equals(PieceCardBase.CardClass.SPIRIT) ? 2 : 0;
		}
	}
}
