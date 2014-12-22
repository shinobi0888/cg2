package card.pieces.pieceeffects.set1;

import game.Game;

import java.util.ArrayList;

import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class FirespringWellPieceEffect extends EmptyPieceEffect {
	public static final int ID = 53;
	public static final int FIRESPRING_KEEPER_ID = 56;

	public FirespringWellPieceEffect() {
		this.hasOnTurnEnd = true;
	}

	public boolean conditionOnTurnEnd(Game g, Piece p) {
		return g.turnPlayer().equals(p.getOwner());
	}

	public void effectOnTurnEnd(Game g, Piece p) {
		ArrayList<Piece> allPieces = g.getAllPieces();
		int randomIndex = (int) (Math.random() * allPieces.size());
		Piece randomPiece = allPieces.get(randomIndex);
		boolean activateKeeper = randomPiece.getCardBase().getId() == FIRESPRING_KEEPER_ID
				&& randomPiece.getOwner().equals(p.getOwner());
		g.simulateDestroy(allPieces.get(randomIndex));
		// Effect of keeper implemented in here as well
		if (activateKeeper) {
			ArrayList<Piece> enemyPieces = g.getBoard().getPlayersPieces(
					g.getEnemy(p.getOwner()));
			for (int i = 0; i < 2 && enemyPieces.size() > 0; i++) {
				randomIndex = (int) (Math.random() * enemyPieces.size());
				randomPiece = enemyPieces.get(randomIndex);
				g.simulateDestroy(randomPiece);
				enemyPieces.remove(randomIndex);
			}
		}
	}
}
