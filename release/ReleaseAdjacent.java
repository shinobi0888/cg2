package release;

import game.Game;
import game.Player;

import java.awt.Point;
import java.util.ArrayList;

import board.Piece;

public class ReleaseAdjacent implements Release {
	public static final int ID = 3;

	public ArrayList<Point> getReleaseTargets(Game g, Player p, Point center,
			Game.GameInterface iface) {
		ArrayList<Point> result = new ArrayList<Point>();
		result.add(center);
		int x = center.x;
		int y = center.y;
		ArrayList<Point> possibleSelections = new ArrayList<Point>();
		Point d1p = new Point(x - 1, y);
		Piece d1 = g.getBoard().getPiece(d1p.x, d1p.y);
		Point d2p = new Point(x, y - 1);
		Piece d2 = g.getBoard().getPiece(d2p.x, d2p.y);
		Point d3p = new Point(x + 1, y);
		Piece d3 = g.getBoard().getPiece(d3p.x, d3p.y);
		Point d4p = new Point(x, y + 1);
		Piece d4 = g.getBoard().getPiece(d4p.x, d4p.y);
		if (d1 != null && d1.getOwner().equals(p)) {
			possibleSelections.add(d1p);
		}
		if (d2 != null && d2.getOwner().equals(p)) {
			possibleSelections.add(d2p);
		}
		if (d3 != null && d3.getOwner().equals(p)) {
			possibleSelections.add(d3p);
		}
		if (d4 != null && d4.getOwner().equals(p)) {
			possibleSelections.add(d4p);
		}
		// If no ambiguities, don't even ask
		if (possibleSelections.size() == 1) {
			possibleSelections.add(center);
			return possibleSelections;
		}
		// Request only if ambiguities exist
		Point s1 = iface.requestBoardPos("Select the outer release target:",
				possibleSelections);
		result.add(s1);
		return result;
	}

	public boolean canReleaseAtCenter(Game g, Player p, Point center) {
		Piece release = g.getBoard().getPiece(center.x, center.y);
		int x = release.getX();
		int y = release.getY();
		Piece d1 = g.getBoard().getPiece(x - 1, y);
		Piece d2 = g.getBoard().getPiece(x, y - 1);
		Piece d3 = g.getBoard().getPiece(x + 1, y);
		Piece d4 = g.getBoard().getPiece(x, y + 1);
		return d1 != null && d1.getOwner().equals(p) || d2 != null
				&& d2.getOwner().equals(p) || d3 != null && d3.getOwner().equals(p)
				|| d4 != null && d4.getOwner().equals(p);
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