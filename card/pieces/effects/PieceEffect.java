package card.pieces.effects;

import game.Game;
import board.Piece;
import card.Card;

public abstract class PieceEffect {
	// Active
	protected boolean hasActive;

	public boolean hasActive() {
		return hasActive;
	}

	public abstract boolean conditionActive(Game g, Piece p);

	public abstract void effectActive(Game g, Piece p);

	// On Play
	protected boolean hasOnPlay;

	public boolean hasOnPlay() {
		return hasOnPlay;
	}

	public abstract boolean conditionOnPlay(Game g, Piece playedPiece);

	public abstract void effectOnPlay(Game g, Piece playedPiece);

	// On Kill
	protected boolean hasOnKill;

	public boolean hasOnKill() {
		return hasOnKill;
	}

	public abstract boolean conditionOnKill(Game g, Piece p);

	public abstract void effectOnKill(Game g, Piece p);

	// On Turn Start (Either player turn start)
	protected boolean hasOnTurnStart;

	public boolean hasOnTurnStart() {
		return hasOnTurnStart;
	}

	public abstract boolean conditionOnTurnStart(Game g, Piece p);

	public abstract void effectOnTurnStart(Game g, Piece p);

	// After Main Draw (Either player draw)

	protected boolean hasAfterMainDraw;

	public boolean hasAfterMainDraw() {
		return hasAfterMainDraw;
	}

	public abstract boolean conditionAfterMainDraw(Game g, Piece p, Card drawn);

	public abstract void effectAfterMainDraw(Game g, Piece p, Card drawn);
}
