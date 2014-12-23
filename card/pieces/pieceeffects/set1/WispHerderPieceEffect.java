package card.pieces.pieceeffects.set1;

import game.Game;

import java.awt.Point;

import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class WispHerderPieceEffect extends EmptyPieceEffect {
	public static final int ID = 67;

	public WispHerderPieceEffect() {
	}

	public boolean conditionCanMove(Game g, Piece p, Point targetLocation) {
		return g.getBoard().pieceInOwnEndZone(p) && targetLocation.y == p.getY();
	}
}
