package card.pieces.pieceeffects;

import game.Game;
import board.Piece;
import buffs.PieceBuff;
import card.Card;
import card.pieces.effects.EmptyPieceEffect;

public class CelebrationalPillarPieceEffect extends EmptyPieceEffect {
	public static final int ID = 6;

	public CelebrationalPillarPieceEffect() {
		this.hasActive = false;
		this.hasOnKill = false;
		this.hasOnPlay = true;
		this.hasOnTurnStart = false;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return true;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		g.simulateGivePieceBuff(playedPiece, new CelebrationalPillarBuff(
				playedPiece, playedPiece.getSourceCard()));
	}

	public class CelebrationalPillarBuff extends PieceBuff {
		public CelebrationalPillarBuff(Piece target, Card source) {
			this.target = target;
			this.source = source;
			this.isRemoveAtEndOfTurn = false;
		}

		public int getDefenseBuff(Game g) {
			return g.getBoard().getPlayersPieces(g.getEnemy(target.getOwner()))
					.size();
		}
	}
}
