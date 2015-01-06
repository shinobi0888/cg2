package card.pieces.pieceeffects.set1;

import game.Game;

import java.awt.Point;
import java.util.ArrayList;

import board.Board;
import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class SwifttailSwallowPieceEffect extends EmptyPieceEffect {
	public static final int ID = 66;

	public SwifttailSwallowPieceEffect() {
		this.hasActive = true;
	}

	public boolean conditionActive(Game g, Piece p) {
		return !p.activeUsedThisTurn();
	}

	public void effectActive(Game g, Piece p) {
		ArrayList<Point> moveable = new ArrayList<Point>();
		int xLeft = p.getX() - 1, xRight = p.getX() + 1, yUp = p.getY() - 1, yDown = p
				.getY() + 1;
		while (g.getBoard().getPiece(xLeft, p.getY()) == null && xLeft >= 0) {
			xLeft--;
		}
		g.getBoard();
		while (g.getBoard().getPiece(xRight, p.getY()) == null
				&& xRight < Board.WIDTH) {
			xRight++;
		}
		while (g.getBoard().getPiece(p.getX(), yUp) == null && yUp >= 0) {
			yUp--;
		}
		g.getBoard();
		while (g.getBoard().getPiece(p.getX(), yDown) == null
				&& yDown < Board.HEIGHT) {
			yDown++;
		}
		Point left = new Point(xLeft + 1, p.getY());
		Point right = new Point(xRight - 1, p.getY());
		Point up = new Point(p.getX(), yUp + 1);
		Point down = new Point(p.getX(), yDown - 1);
		moveable.add(left);
		if (!moveable.contains(right)) {
			moveable.add(right);
		}
		if (!moveable.contains(up)) {
			moveable.add(up);
		}
		if (!moveable.contains(down)) {
			moveable.add(down);
		}
		Point target = g.getIface().requestBoardPos(
				"Select a location to move piece to:", moveable);
		g.getPieceEffector().shiftPiece(p, target.x, target.y);
	}
}
