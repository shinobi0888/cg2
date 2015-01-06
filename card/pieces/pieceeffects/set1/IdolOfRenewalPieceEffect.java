package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import card.Card;
import card.PieceCardBase;
import card.pieces.effects.EmptyPieceEffect;

public class IdolOfRenewalPieceEffect extends EmptyPieceEffect {
	public static final int ID = 10;

	public IdolOfRenewalPieceEffect() {
		this.hasAfterMainDraw = true;
	}

	public boolean conditionAfterMainDraw(Game g, Piece p, Card drawn) {
		return g.turnPlayer().equals(p.getOwner());
	}

	public void effectAfterMainDraw(Game g, Piece p, Card drawn) {
		g.getIface().revealCard(p.getOwner(), drawn);
		if (drawn.getCardBase() instanceof PieceCardBase
				&& ((PieceCardBase) (drawn.getCardBase())).getAttack() < 4) {
			g.getPieceEffector().draw(p.getOwner());
		}
	}
}
