package card.pieces.effects;

import java.awt.Point;

import card.Card;
import game.Game;
import game.Player;
import board.Piece;

public class EmptyPieceEffect extends PieceEffect {
	public EmptyPieceEffect() {
		this.hasActive = false;
		this.hasOnKill = false;
		this.hasOnPlay = false;
		this.hasOnTurnStart = false;
		this.hasOnTurnEnd = false;
		this.hasAfterMainDraw = false;
		this.hasOnSelfDestroyed = false;
		this.hasOnPiecePlayed = false;
		this.isPreventOverturn = false;
		this.hasPreventStartOfTurnDraw = false;
	}

	public boolean conditionActive(Game g, Piece p) {
		return false;
	}

	public void effectActive(Game g, Piece p) {

	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return false;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {

	}

	public boolean conditionOnKill(Game g, Piece p) {
		return false;
	}

	public void effectOnKill(Game g, Piece p) {

	}

	public boolean conditionOnTurnStart(Game g, Piece p) {
		return false;
	}

	public void effectOnTurnStart(Game g, Piece p) {

	}

	public boolean conditionAfterMainDraw(Game g, Piece p, Card drawn) {
		return false;
	}

	public void effectAfterMainDraw(Game g, Piece p, Card drawn) {

	}

	public boolean conditionOnSelfDestroyed(Game g, Piece p) {
		return false;
	}

	public void effectOnSelfDestroyed(Game g, Piece p) {

	}

	public boolean conditionOnPiecePlayed(Game g, Piece played, Piece source) {
		return false;
	}

	public void effectOnPiecePlayed(Game g, Piece played, Piece source) {

	}

	public boolean conditionPreventStartOfTurnDraw(Game g, Player drawing) {
		return false;
	}

	public boolean conditionOnTurnEnd(Game g, Piece p) {
		return false;
	}

	public void effectOnTurnEnd(Game g, Piece p) {

	}

	public boolean conditionCanMove(Game g, Piece p, Point targetLocation) {
		return true;
	}
}
