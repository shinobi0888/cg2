package card.pieces.pieceeffects;

import game.Game;

import java.awt.Point;

import board.Board;
import board.Piece;
import buffs.PieceBuff;
import card.Card;
import card.pieces.effects.EmptyPieceEffect;

public class BlueHeronPieceEffect extends EmptyPieceEffect {
	public static final int ID = 22;

	public BlueHeronPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return true;
	}

	public void effectOnPlay(Game g, Piece p) {
		g.simulateGivePieceBuff(p, new BlueHeronBuff(p, p.getSourceCard()));
	}

	public class BlueHeronBuff extends PieceBuff {
		public BlueHeronBuff(Piece target, Card source) {
			this.target = target;
			this.source = source;
			this.stacks = 0;
			this.isRemoveAtEndOfTurn = false;
		}

		public boolean canBeAttacked(Game g) {
			for (Point p : g.getBoard().getSquaresInPattern(target.getX(),
					target.getY(), Board.REG_PATTERN)) {
				Piece piece = g.getBoard().getPiece(p.x, p.y);
				if (piece != null && piece.getOwner().equals(target.getOwner())) {
					return false;
				}
			}
			return true;
		}
	}
}
