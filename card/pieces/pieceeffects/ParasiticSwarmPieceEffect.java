package card.pieces.pieceeffects;

import game.Game;
import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class ParasiticSwarmPieceEffect extends EmptyPieceEffect {
	public static final int ID = 33;

	public ParasiticSwarmPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		int numPiecesInPlay1 = g.getBoard().find(g.turnPlayer(), null).size();
		int numPiecesInPlay2 = g.getBoard().find(g.offTurnPlayer(), null).size();
		int deck1 = g.turnPlayer().getDeckCount();
		int deck2 = g.offTurnPlayer().getDeckCount();
		return (numPiecesInPlay1 > 0 && deck1 > 0)
				|| (numPiecesInPlay2 > 0 && deck2 > 0);
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		int discardRequirement1 = g.getBoard().find(g.turnPlayer(), null).size();
		int discardRequirement2 = g.getBoard().find(g.offTurnPlayer(), null).size();
		int deck1 = g.turnPlayer().getDeckCount();
		int deck2 = g.offTurnPlayer().getDeckCount();
		while (discardRequirement1 > 0 && deck1 > 0) {
			g.simulateMillFromDeck(g.turnPlayer(), 0);
			discardRequirement1--;
			deck1 = g.turnPlayer().getDeckCount();
		}
		while (discardRequirement2 > 0 && deck2 > 0) {
			g.simulateMillFromDeck(g.offTurnPlayer(), 0);
			discardRequirement2--;
			deck2 = g.offTurnPlayer().getDeckCount();
		}
	}
}
