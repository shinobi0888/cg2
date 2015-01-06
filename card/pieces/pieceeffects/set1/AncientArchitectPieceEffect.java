package card.pieces.pieceeffects.set1;

import game.Game;

import java.util.ArrayList;

import board.Piece;
import card.PieceCardBase;
import card.pieces.effects.EmptyPieceEffect;

public class AncientArchitectPieceEffect extends EmptyPieceEffect {
	public static final int ID = 7;

	public AncientArchitectPieceEffect() {
		this.hasActive = true;
		this.hasOnKill = false;
		this.hasOnPlay = false;
		this.hasOnTurnStart = false;
	}

	public boolean conditionActive(Game g, Piece p) {
		for (Piece playerP : g.getBoard().getPlayersPieces(p.getOwner())) {
			if (playerP.getCardBase().getCardClass()
					.equals(PieceCardBase.CardClass.PILLAR)) {
				return true;
			}
		}
		return false;
	}

	public void effectActive(Game g, Piece p) {
		ArrayList<Piece> playerPillars = new ArrayList<Piece>();
		for (Piece playerP : g.getBoard().getPlayersPieces(p.getOwner())) {
			if (playerP.getCardBase().getCardClass()
					.equals(PieceCardBase.CardClass.PILLAR)) {
				playerPillars.add(playerP);
			}
		}
		int x = p.getX();
		int y = p.getY();
		g.getPieceEffector().destroy(p);
		g.getPieceEffector().shiftPiece(
				playerPillars.get((int) (Math.random() * playerPillars.size())), x, y);
	}
}
