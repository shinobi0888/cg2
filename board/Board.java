package board;

import game.Player;
import gridmap.GridMap;

import java.awt.Point;
import java.util.ArrayList;

import card.PieceCardBase;

public class Board {
	// Patterns
	public static final int NO_LOC_PATTERN = 0;
	public static final int REG_PATTERN = 1;
	public static final int ADJACENT_2_PATTERN = 2;

	public static final int WIDTH = 9;
	public static final int HEIGHT = 9;
	private GridMap<Piece> board;
	private BoardListener listener;
	private Player[] players;

	public Board(Player p1, Player p2, BoardListener listener) {
		board = new GridMap<Piece>(WIDTH, HEIGHT);
		this.listener = listener;
		players = new Player[2];
		players[0] = p1;
		players[1] = p2;
	}

	public ArrayList<Point> getOpenEndZones(Player p) {
		ArrayList<Point> results = new ArrayList<Point>();
		int yIndex = (p.equals(players[0]) ? 0 : HEIGHT - 1);
		for (int i = 0; i < WIDTH; i++) {
			if (!board.occupied(i, yIndex)) {
				results.add(new Point(i, yIndex));
			}
		}
		return results;
	}

	public void calculateAllAllowedActions(Player turnPlayer) {
		for (Piece p : getAllPiecesInPlayerOrder(turnPlayer)) {
			if (p.getOwner().equals(turnPlayer)) {
				p.calculatePossibleActions(this);
			}
		}
	}

	public ArrayList<Piece> getAllPiecesInPlayerOrder(Player turnPlayer) {
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		boolean p1 = turnPlayer.equals(players[0]);
		for (int y = p1 ? 0 : (HEIGHT - 1); y != (p1 ? (HEIGHT) : -1); y += p1 ? 1
				: -1) {
			for (int x = p1 ? 0 : (WIDTH - 1); x != (p1 ? (WIDTH) : -1); x += p1 ? 1
					: -1) {
				Piece p = board.get(x, y);
				if (p != null) {
					pieces.add(p);
				}
			}
		}
		return pieces;
	}

	public boolean pieceInEnemyEndZone(Piece p) {
		return p.getY() == (p.getOwner() == players[1] ? 0 : HEIGHT - 1);
	}

	public void playNewPiece(Piece p, int x, int y) {
		board.put(x, y, p);
		p.setAttacks(((PieceCardBase) p.getSourceCard().getCardBase())
				.getNumAttacks());
		p.setMoves(0);
		listener.piecePlayed(p, x, y);
	}

	// Adding pieces is NOT playing pieces.
	// Playing pieces implies playing from hand.
	public void addNewPiece(Piece p, int x, int y) {
		// TODO: implement
	}

	public void initNewTurnMovesAndAttacks(Player p) {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				Piece piece = board.get(x, y);
				if (piece != null && piece.getOwner().equals(p)) {
					piece
							.setAttacks(((PieceCardBase) piece.getSourceCard().getCardBase())
									.getNumAttacks());
					piece.setMoves(((PieceCardBase) piece.getSourceCard().getCardBase())
							.getNumMoves());
				}
			}
		}
	}

	public ArrayList<Point> getSquaresInPattern(int x, int y, int pattern) {
		ArrayList<Point> result = new ArrayList<Point>();
		if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
			switch (pattern) {
			case NO_LOC_PATTERN:
				break;
			case REG_PATTERN:
				tryAddSquareToPattern(x - 1, y, result);
				tryAddSquareToPattern(x + 1, y, result);
				tryAddSquareToPattern(x, y - 1, result);
				tryAddSquareToPattern(x, y + 1, result);
				break;
			case ADJACENT_2_PATTERN:
				tryAddSquareToPattern(x - 1, y, result);
				tryAddSquareToPattern(x + 1, y, result);
				tryAddSquareToPattern(x, y - 1, result);
				tryAddSquareToPattern(x, y + 1, result);
				tryAddSquareToPattern(x - 2, y, result);
				tryAddSquareToPattern(x + 2, y, result);
				tryAddSquareToPattern(x, y - 2, result);
				tryAddSquareToPattern(x, y + 2, result);
				break;
			}
		}
		return result;
	}

	public Piece getPiece(int x, int y) {
		if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
			return board.get(x, y);
		}
		return null;
	}

	public ArrayList<Piece> getAllPieces() {
		return board.getAll();
	}

	public ArrayList<Piece> find(Player owner, PieceCardBase.CardClass cardClass) {
		ArrayList<Piece> result = new ArrayList<Piece>();
		for (Piece piece : board.getAll()) {
			if ((owner == null || piece.getOwner().equals(owner))
					&& (cardClass == null || piece.getCardBase().getCardClass()
							.equals(cardClass))) {
				result.add(piece);
			}
		}
		return result;
	}

	public ArrayList<Piece> getPlayersPieces(Player p) {
		ArrayList<Piece> result = new ArrayList<Piece>();
		for (Piece piece : board.getAll()) {
			if (piece.getOwner().equals(p)) {
				result.add(piece);
			}
		}
		return result;
	}

	public void movePiece(Piece p, int tX, int tY) {
		int oldX = p.getX();
		int oldY = p.getY();
		board.move(p, tX, tY);
		listener.pieceMoved(p, oldX, oldY, tX, tY);
	}

	public void removePiece(int x, int y) {
		if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
			Piece p = board.get(x, y);
			board.remove(x, y);
			listener.pieceRemoved(p, x, y);
		}
	}

	private void tryAddSquareToPattern(int x, int y, ArrayList<Point> points) {
		if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
			points.add(new Point(x, y));
		}
	}

	public interface BoardListener {
		public void piecePlayed(Piece p, int x, int y);

		public void pieceMoved(Piece p, int oldX, int oldY, int newX, int newY);

		public void pieceRemoved(Piece p, int oldX, int oldY);
	}
}
