package card.pieces.effects;

import card.Card;
import game.Game;
import board.Piece;

public class EmptyPieceEffect extends PieceEffect {
	public EmptyPieceEffect() {
		this.hasActive = false;
		this.hasOnKill = false;
		this.hasOnPlay = false;
		this.hasOnTurnStart = false;
		this.hasAfterMainDraw = false;
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
}
