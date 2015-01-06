package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import board.Piece;
import card.Card;
import card.hexes.effects.HexEffect;

public class AltarOfHeartlessHexEffect implements HexEffect {
	public static final int ID = 46;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		Piece p1Random = g.getBoard().getPlayersRandomPiece(owningPlayer);
		Piece p2Random = g.getBoard().getPlayersRandomPiece(
				g.getEnemy(owningPlayer));
		if (p1Random != null) {
			g.getHexEffector().sendFromBoardToBottomOfDeck(owningPlayer, p1Random);
		}
		if (p2Random != null && g.getBoard().isPieceOnBoard(p2Random)) {
			g.getHexEffector().sendFromBoardToBottomOfDeck(owningPlayer, p2Random);
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return true;
	}

}
