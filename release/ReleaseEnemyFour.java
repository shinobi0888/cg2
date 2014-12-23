package release;

import game.Game;
import game.Game.GameInterface;
import game.Player;

import java.awt.Point;
import java.util.ArrayList;

import board.Board;
import board.Piece;

public class ReleaseEnemyFour implements Release {

	public ArrayList<Point> getReleaseTargets(Game g, Player p, Point center,
			GameInterface iface) {
		ArrayList<Point> potentialTargets = new ArrayList<Point>();
		for (int i = center.x - 1; i < center.x + 1; i++) {
			for (int j = center.y - 1; i < center.y + 1; j++) {
				Piece piece = g.getBoard().getPiece(i, j);
				if (piece != null && piece.getOwner().equals(g.getEnemy(p))) {
					potentialTargets.add(new Point(i, j));
				}
			}
		}
		if (potentialTargets.size() == 4) {
			return potentialTargets;
		} else {
			ArrayList<Point> targets = new ArrayList<Point>();
			for (int i = 0; i < 4; i++) {
				Point pt = g.getIface().requestBoardPos(
						"Select the location of a release target:", potentialTargets);
				targets.add(pt);
				potentialTargets.remove(pt);
			}
			return targets;
		}
	}

	public ArrayList<Point> getPlayableSpots(Game g, Player p) {
		ArrayList<Point> result = new ArrayList<Point>();
		for (int i = 1; i < Board.WIDTH - 1; i++) {
			for (int j = 1; j < Board.WIDTH - 1; j++) {
				Point pt = new Point(i, j);
				if (canReleaseAtCenter(g, p, pt)) {
					result.add(pt);
				}
			}
		}
		return result;
	}

	public boolean canReleaseAtCenter(Game g, Player p, Point center) {
		int counter = 0;
		for (int i = center.x - 1; i < center.x + 1; i++) {
			for (int j = center.y - 1; i < center.y + 1; j++) {
				Piece piece = g.getBoard().getPiece(i, j);
				if (piece != null && piece.getOwner().equals(g.getEnemy(p))) {
					counter++;
					if (counter == 4) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean canRelease(Game g, Player p) {
		return getPlayableSpots(g, p).size() > 0;
	}

}
