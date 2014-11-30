package release;

import game.Game;
import game.Player;

import java.awt.Point;
import java.util.ArrayList;

import board.Piece;

/**
 * Release pattern where the summoned monster just sits on top of the released
 * piece.
 * 
 * @author Kevin
 */
public class ReleaseSingleCenter implements Release {
	public static final int ID = 1;

	public ArrayList<Point> getReleaseTargets(Game g, Player p, Point center,
			Game.GameInterface iface) {
		ArrayList<Point> result = new ArrayList<Point>();
		result.add(center);
		return result;
	}

	public boolean canReleaseAtCenter(Game g, Player p, Point center) {
		Piece release = g.getBoard().getPiece(center.x, center.y);
		return (release != null && release.getOwner().equals(p));
	}

	public boolean canRelease(Game g, Player p) {
		for (Piece piece : g.getBoard().getAllPieces()) {
			if (piece.getOwner().equals(p)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Point> getPlayableSpots(Game g, Player p) {
		ArrayList<Point> playableSpots = new ArrayList<Point>();
		for (Piece piece : g.getBoard().getAllPieces()) {
			if (piece.getOwner().equals(p)) {
				playableSpots.add(new Point(piece.getX(), piece.getY()));
			}
		}
		return playableSpots;
	}
}
