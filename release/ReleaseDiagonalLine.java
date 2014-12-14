package release;

import game.Game;
import game.Player;

import java.awt.Point;
import java.util.ArrayList;

import board.Piece;

public class ReleaseDiagonalLine implements Release {
	public static final int ID = 2;

	public ArrayList<Point> getReleaseTargets(Game g, Player p, Point center,
			Game.GameInterface iface) {
		ArrayList<Point> result = new ArrayList<Point>();
		result.add(center);
		int x = center.x;
		int y = center.y;
		ArrayList<Point> possibleSelections = new ArrayList<Point>();
		Point d1p = new Point(x - 1, y - 1);
		Piece d1 = g.getBoard().getPiece(d1p.x, d1p.y);
		Point d2p = new Point(x + 1, y + 1);
		Piece d2 = g.getBoard().getPiece(d2p.x, d2p.y);
		Point d3p = new Point(x + 1, y - 1);
		Piece d3 = g.getBoard().getPiece(d3p.x, d3p.y);
		Point d4p = new Point(x - 1, y + 1);
		Piece d4 = g.getBoard().getPiece(d4p.x, d4p.y);
		if (d1 != null && d1.getOwner().equals(p) && d2 != null
				&& d2.getOwner().equals(p)) {
			possibleSelections.add(d1p);
			possibleSelections.add(d2p);
		}
		if (d3 != null && d3.getOwner().equals(p) && d4 != null
				&& d4.getOwner().equals(p)) {
			possibleSelections.add(d3p);
			possibleSelections.add(d4p);
		}
		// If no ambiguities, don't even ask
		if (possibleSelections.size() == 2) {
			possibleSelections.add(center);
			return possibleSelections;
		}
		// Request only if ambiguities exist
		Point s1 = iface.requestBoardPos(
				"Select one of the corner release targets:", possibleSelections);
		result.add(s1);
		if(s1.equals(d1p)) {
			result.add(d2p);
		} else if(s1.equals(d2p)) {
			result.add(d1p);
		} else if(s1.equals(d3p)) {
			result.add(d4p);
		} else if(s1.equals(d4p)) {
			result.add(d3p);
		}
		return result;
	}

	public boolean canReleaseAtCenter(Game g, Player p, Point center) {
		Piece release = g.getBoard().getPiece(center.x, center.y);
		int x = release.getX();
		int y = release.getY();
		Piece d1 = g.getBoard().getPiece(x - 1, y - 1);
		Piece d2 = g.getBoard().getPiece(x + 1, y + 1);
		Piece d3 = g.getBoard().getPiece(x + 1, y - 1);
		Piece d4 = g.getBoard().getPiece(x - 1, y + 1);
		return (d1 != null && d1.getOwner().equals(p) && d2 != null && d2
				.getOwner().equals(p))
				|| (d3 != null && d3.getOwner().equals(p) && d4 != null && d4
						.getOwner().equals(p));
	}

	public boolean canRelease(Game g, Player p) {
		return getPlayableSpots(g, p).size() > 0;
	}

	public ArrayList<Point> getPlayableSpots(Game g, Player p) {
		ArrayList<Point> playableSpots = new ArrayList<Point>();
		for (Piece piece : g.getBoard().getAllPieces()) {
			if (piece.getOwner().equals(p)
					&& canReleaseAtCenter(g, p, new Point(piece.getX(), piece.getY()))) {
				playableSpots.add(new Point(piece.getX(), piece.getY()));
			}
		}
		return playableSpots;
	}
}