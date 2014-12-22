package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import board.Piece;
import card.Card;
import card.hexes.effects.HexEffect;
import card.pieces.pieceeffects.set1.BoreasPieceEffect;

public class TheDivineWindsHexEffect implements HexEffect {
	public static final int ID = 41;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		if (g.getBoard().find(owningPlayer, BoreasPieceEffect.ID).size() > 0
				&& g.getBoard().find(owningPlayer, BoreasPieceEffect.EURUS_ID).size() > 0
				&& g.getBoard().find(owningPlayer, BoreasPieceEffect.NOTUS_ID).size() > 0
				&& g.getBoard().find(owningPlayer, BoreasPieceEffect.ZEPHYRUS_ID)
						.size() > 0) {
			for (Piece p : g.getBoard().find(g.getEnemy(owningPlayer), null)) {
				g.simulateSendFromBoardToHand(p);
			}
		} else {
			Piece p = g.getIface().requestBoardPiece(
					"Select a piece to return to the hand:", g.getAllPieces());
			g.simulateSendFromBoardToHand(p);
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return g.getAllPieces().size() > 0;
	}

}
