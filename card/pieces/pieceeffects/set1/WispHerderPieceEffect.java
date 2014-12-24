package card.pieces.pieceeffects.set1;

import game.Game;

import java.awt.Point;

import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class WispHerderPieceEffect extends EmptyPieceEffect {
	public static final int ID = 67;

	public WispHerderPieceEffect() {
		hasActive = true;
	}

	public boolean conditionCanMove(Game g, Piece p, Point targetLocation) {
		return g.getBoard().pieceInOwnEndZone(p) && targetLocation.y == p.getY();
	}

	public boolean conditionActive(Game g, Piece p) {
		return true;
	}

	public void effectActive(Game g, Piece p) {
		g.simulateDestroy(p);
		Piece target = g.getIface().requestBoardPiece("Select a piece to move:",
				g.getBoard().getPlayersPieces(p.getOwner()));
		g.simulateShift(target, p.getX(), p.getY());
	}
}
