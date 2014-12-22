package card.pieces.pieceeffects.set1;

import game.Game;

import java.awt.Point;

import board.Board;
import board.Piece;
import card.PieceCardBase.CardClass;
import card.pieces.effects.EmptyPieceEffect;

public class WhiteChimaeraPieceEffect extends EmptyPieceEffect {
	public static final int ID = 26;

	public WhiteChimaeraPieceEffect() {
		this.hasOnPiecePlayed = true;
	}

	public boolean conditionOnPiecePlayed(Game g, Piece p, Piece source) {
		return p.getOwner().equals(source.getOwner())
				&& p.getCardBase().getCardClass().equals(CardClass.SPIRIT)
				&& g.getBoard()
						.getSquaresInPattern(source.getX(), source.getY(),
								Board.RADIUS_1_PATTERN).contains(new Point(p.getX(), p.getY()));
	}

	public void effectOnPiecePlayed(Game g, Piece p, Piece source) {
		g.simulateGivePlayerPiecePlays(source.getOwner(), 1);
	}

}
