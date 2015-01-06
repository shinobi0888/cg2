package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import card.PieceCardBase.CardClass;
import card.pieces.effects.EmptyPieceEffect;

public class ChampionGerynPieceEffect extends EmptyPieceEffect {
	public static final int ID = 18;

	public ChampionGerynPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return true;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		g.getPieceEffector().heal(playedPiece.getOwner(),
				g.getBoard().find(playedPiece.getOwner(), CardClass.FAIRY).size() * 2);
	}
}
