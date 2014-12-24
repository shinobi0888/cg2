package card.pieces.effects;

import game.Game;
import game.Player;

import java.awt.Point;
import java.util.ArrayList;

import board.Piece;
import board.PieceSnapshot;
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

	// On Release Play
	protected boolean hasOnReleasePlay;

	public boolean hasOnReleasePlay() {
		return hasOnReleasePlay;
	}

	public abstract boolean conditionOnReleasePlay(Game g, Piece playedPiece,
			ArrayList<PieceSnapshot> released);

	public abstract void effectOnReleasePlay(Game g, Piece playedPiece,
			ArrayList<PieceSnapshot> released);

	// On Kill
	protected boolean hasOnKill;

	public boolean hasOnKill() {
		return hasOnKill;
	}

	public abstract boolean conditionOnKill(Game g, Piece p, PieceSnapshot killed);

	public abstract void effectOnKill(Game g, Piece p, PieceSnapshot killed);

	// On Turn Start (Either player turn start)
	protected boolean hasOnTurnStart;

	public boolean hasOnTurnStart() {
		return hasOnTurnStart;
	}

	public abstract boolean conditionOnTurnStart(Game g, Piece p);

	public abstract void effectOnTurnStart(Game g, Piece p);

	// On Turn End (Either player turn end)
	protected boolean hasOnTurnEnd;

	public boolean hasOnTurnEnd() {
		return hasOnTurnEnd;
	}

	public abstract boolean conditionOnTurnEnd(Game g, Piece p);

	public abstract void effectOnTurnEnd(Game g, Piece p);

	// After Main Draw (Either player draw)

	protected boolean hasAfterMainDraw;

	public boolean hasAfterMainDraw() {
		return hasAfterMainDraw;
	}

	public abstract boolean conditionAfterMainDraw(Game g, Piece p, Card drawn);

	public abstract void effectAfterMainDraw(Game g, Piece p, Card drawn);

	// On Self Destroyed

	protected boolean hasOnSelfDestroyed;

	public boolean hasOnSelfDestroyed() {
		return hasOnSelfDestroyed;
	}

	public abstract boolean conditionOnSelfDestroyed(Game g, Piece p);

	public abstract void effectOnSelfDestroyed(Game g, Piece p);

	// On Piece Played (any piece)

	protected boolean hasOnPiecePlayed;

	public boolean hasOnPiecePlayed() {
		return hasOnPiecePlayed;
	}

	public abstract boolean conditionOnPiecePlayed(Game g, Piece played,
			Piece source);

	public abstract void effectOnPiecePlayed(Game g, Piece played, Piece source);

	// Prevents Overturn
	protected boolean isPreventOverturn;

	public boolean isPreventOverturn() {
		return isPreventOverturn;
	}

	// Prevent Start of Turn Draw
	protected boolean hasPreventStartOfTurnDraw;

	public boolean hasPreventStartOfTurnDraw() {
		return hasPreventStartOfTurnDraw;
	}

	public abstract boolean conditionPreventStartOfTurnDraw(Game g, Player drawing);

	// Condition for piece being able to move
	public abstract boolean conditionCanMove(Game g, Piece p, Point targetLocation);

	// On Piece Manually Moved
	protected boolean hasOnManuallyMoved;

	public boolean hasOnManuallyMoved() {
		return hasOnManuallyMoved;
	}

	public abstract boolean conditionOnManuallyMoved(Game g, Piece p, Point old,
			Point target);

	public abstract void effectOnManuallyMoved(Game g, Piece p, Point old,
			Point target);

	// On Piece attack
	protected boolean hasOnAttack;

	public boolean hasOnAttack() {
		return hasOnAttack;
	}

	public abstract boolean conditionOnAttack(Game g, Piece p,
			PieceSnapshot attacked);

	public abstract void effectOnAttack(Game g, Piece p, PieceSnapshot attacked);
}
