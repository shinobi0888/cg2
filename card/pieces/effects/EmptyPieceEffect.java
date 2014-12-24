package card.pieces.effects;

import game.Game;
import game.Player;

import java.awt.Point;
import java.util.ArrayList;

import board.Piece;
import board.PieceSnapshot;
import card.Card;

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
		this.hasOnManuallyMoved = false;
		this.hasOnAttack = false;
		this.hasOnReleasePlay = false;
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

	public boolean conditionOnKill(Game g, Piece p, PieceSnapshot killed) {
		return false;
	}

	public void effectOnKill(Game g, Piece p, PieceSnapshot killed) {

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

	public boolean conditionOnManuallyMoved(Game g, Piece p, Point old,
			Point target) {
		return false;
	}

	public void effectOnManuallyMoved(Game g, Piece p, Point old, Point target) {

	}

	public boolean conditionOnAttack(Game g, Piece p, PieceSnapshot attacked) {
		return false;
	}

	public void effectOnAttack(Game g, Piece p, PieceSnapshot attacked) {

	}

	public boolean conditionOnReleasePlay(Game g, Piece playedPiece,
			ArrayList<PieceSnapshot> released) {
		return false;
	}

	public void effectOnReleasePlay(Game g, Piece playedPiece,
			ArrayList<PieceSnapshot> released) {

	}
}
