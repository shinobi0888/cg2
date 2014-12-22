package card.pieces.pieceeffects.set1;

import game.Game;

import java.awt.Point;
import java.util.ArrayList;

import board.Board;
import board.Piece;
import buffs.PieceBuff;
import card.Card;
import card.pieces.effects.EmptyPieceEffect;

public class HeartlessSentryPieceEffect extends EmptyPieceEffect {
	public static final int ID = 51;

	public HeartlessSentryPieceEffect() {
		this.hasOnTurnEnd = true;
	}

	public boolean conditionOnTurnEnd(Game g, Piece p) {
		// Only on players turn
		if(!g.turnPlayer().equals(p.getOwner())) {
			return false;
		}
		for (Point pt : g.getBoard().getSquaresInPattern(p.getX(), p.getY(),
				Board.REG_PATTERN)) {
			if (!g.getBoard().getPiece(pt.x, pt.y).getOwner().equals(p.getOwner())) {
				return true;
			}
		}
		return false;
	}

	public void effectOnTurnEnd(Game g, Piece p) {
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		for (Point pt : g.getBoard().getSquaresInPattern(p.getX(), p.getY(),
				Board.REG_PATTERN)) {
			if (!g.getBoard().getPiece(pt.x, pt.y).getOwner().equals(p.getOwner())) {
				pieces.add(g.getBoard().getPiece(pt.x, pt.y));
			}
		}
		int randomIndex = (int) (Math.random() * pieces.size());
		Piece target = pieces.get(randomIndex);
		g.simulateGivePieceBuff(target,
				new HeartlessSentryBuff(target, p.getSourceCard()));
		if (target.getAttack() == 0 || target.getDefense() == 0) {
			g.simulateDestroy(target);
		}
	}

	public class HeartlessSentryBuff extends PieceBuff {
		public HeartlessSentryBuff(Piece target, Card source) {
			this.target = target;
			this.source = source;
			this.isRemoveAtEndOfTurn = false;
		}

		public int getDefenseBuff(Game g) {
			return -3;
		}

		public int getAttackBuff(Game g) {
			return -3;
		}
	}
}
