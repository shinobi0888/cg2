package card.pieces.pieceeffects;

import game.Game;

import java.awt.Point;
import java.util.ArrayList;

import board.Board;
import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class MercurialWispPieceEffect extends EmptyPieceEffect {
	public static final int ID = 52;
	private static final int DEF_LIMIT = 10;

	public MercurialWispPieceEffect() {
		this.hasOnTurnEnd = true;
	}

	public boolean conditionOnTurnEnd(Game g, Piece p) {
		for (Point pt : g.getBoard().getSquaresInPattern(p.getX(), p.getY(),
				Board.REG_PATTERN)) {
			if (g.getBoard().getPiece(pt.x, pt.y) == null) {
				return true;
			}
		}
		return false;
	}

	public void effectOnTurnEnd(Game g, Piece p) {
		// First move randomly
		ArrayList<Point> emptyAdjacents = new ArrayList<Point>();
		for (Point pt : g.getBoard().getSquaresInPattern(p.getX(), p.getY(),
				Board.REG_PATTERN)) {
			if (g.getBoard().getPiece(pt.x, pt.y) == null) {
				emptyAdjacents.add(pt);
			}
		}
		int randomIndex = (int) (Math.random() * emptyAdjacents.size());
		Point moveTarget = emptyAdjacents.get(randomIndex);
		g.getBoard().movePiece(p, moveTarget.x, moveTarget.y);
		// Then detonate
		boolean hasAdjacentEnemy = false;
		ArrayList<Piece> detonateTargets = new ArrayList<Piece>();
		for (Point pt : g.getBoard().getSquaresInPattern(p.getX(), p.getY(),
				Board.REG_PATTERN)) {
			Piece targetPiece = g.getBoard().getPiece(pt.x, pt.y);
			if (targetPiece != null && !targetPiece.getOwner().equals(p.getOwner())) {
				hasAdjacentEnemy = true;
			}
			// Kills friendly pieces too
			if (targetPiece != null && targetPiece.getDefense() <= DEF_LIMIT) {
				detonateTargets.add(targetPiece);
			}
		}
		if (hasAdjacentEnemy) {
			g.simulateDestroy(p);
			for (Piece target : detonateTargets) {
				g.simulateDestroy(target);
			}
		}
	}

}
