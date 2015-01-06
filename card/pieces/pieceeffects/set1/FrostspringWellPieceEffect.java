package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class FrostspringWellPieceEffect extends EmptyPieceEffect {
	public static final int ID = 55;
	public static final int FROSTSPRING_KEEPER_ID = 58;

	public FrostspringWellPieceEffect() {
		this.hasOnTurnEnd = true;
	}

	public boolean conditionOnTurnEnd(Game g, Piece p) {
		return g.turnPlayer().equals(p.getOwner());
	}

	public void effectOnTurnEnd(Game g, Piece p) {
		g.getPieceEffector().draw(p.getOwner());
		g.getPieceEffector().draw(g.getEnemy(p.getOwner()));
		// Effect of frostspring here as well
		if (g.getBoard().find(p.getOwner(), FROSTSPRING_KEEPER_ID).size() == 0) {
			g.getPieceEffector()
					.discard(p.getOwner(), p.getOwner().getRandomInHand());
		}
		g.getPieceEffector().discard(g.getEnemy(p.getOwner()),
				g.getEnemy(p.getOwner()).getRandomInHand());
	}

}
