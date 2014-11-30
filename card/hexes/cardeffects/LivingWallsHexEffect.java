package card.hexes.cardeffects;

import game.Game;
import game.Player;

import java.awt.Point;
import java.util.ArrayList;

import board.Board;
import board.Piece;
import card.Card;
import card.PieceCardBase.CardClass;
import card.hexes.effects.HexEffect;

public class LivingWallsHexEffect implements HexEffect {
	public static final int ID = 9;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		for (Piece p : g.getBoard().find(owningPlayer, CardClass.PILLAR)) {
			ArrayList<Point> positions = g.getBoard().getSquaresInPattern(p.getX(),
					p.getY(), Board.REG_PATTERN);
			for (int i = 0; i < positions.size(); i++) {
				Point curPosition = positions.get(i);
				if (g.getBoard().getPiece(curPosition.x, curPosition.y) != null) {
					positions.remove(i);
					i--;
				}
			}
			if (positions.size() > 0) {
				Point chosenPosition = g.getIface().requestBoardPos(
						"Select a new position for " + p + ": ", positions);
				g.simulateShift(p, chosenPosition.x, chosenPosition.y);
			}
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return g.getBoard().find(owningPlayer, CardClass.PILLAR).size() > 0;
	}
}
