package card.pieces.pieceeffects.set1;

import game.Game;

import java.awt.Point;

import board.Board;
import board.Piece;
import buffs.PieceBuff;
import card.Card;
import card.PieceCardBase.CardClass;
import card.pieces.effects.EmptyPieceEffect;

public class GreyKirinPieceEffect extends EmptyPieceEffect {
	public static final int ID = 25;

	public GreyKirinPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return true;
	}

	public void effectOnPlay(Game g, Piece p) {
		g.getPieceEffector().givePieceBuff(p, new GreyKirinBuff(p, p.getSourceCard()));
	}

	public class GreyKirinBuff extends PieceBuff {
		public GreyKirinBuff(Piece target, Card source) {
			this.target = target;
			this.source = source;
			this.stacks = 0;
			this.isRemoveAtEndOfTurn = false;
		}

		public int getAttackBuff(Game g) {
			int count = 0;
			for (Point p : g.getBoard().getSquaresInPattern(target.getX(),
					target.getY(), Board.RADIUS_1_PATTERN)) {
				Piece piece = g.getBoard().getPiece(p.x, p.y);
				if (piece != null && piece.getOwner().equals(target.getOwner())
						&& piece.getCardBase().getCardClass().equals(CardClass.SPIRIT)) {
					count++;
				}
			}
			return count;
		}

		public int getDefenseBuff(Game g) {
			int count = 0;
			for (Point p : g.getBoard().getSquaresInPattern(target.getX(),
					target.getY(), Board.RADIUS_1_PATTERN)) {
				Piece piece = g.getBoard().getPiece(p.x, p.y);
				if (piece != null && piece.getOwner().equals(target.getOwner())
						&& piece.getCardBase().getCardClass().equals(CardClass.SPIRIT)) {
					count++;
				}
			}
			return count;
		}
	}
}
