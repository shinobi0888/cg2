package card.pieces.pieceeffects.set1;

import game.Game;

import java.util.ArrayList;

import board.Piece;
import buffs.PieceBuff;
import card.Card;
import card.pieces.effects.EmptyPieceEffect;

public class HeartlessMercenaryPieceEffect extends EmptyPieceEffect {
	public static final int ID = 49;

	public HeartlessMercenaryPieceEffect() {
		this.hasOnPiecePlayed = true;
	}

	public boolean conditionOnPiecePlayed(Game g, Piece p, Piece source) {
		return p.getOwner().equals(source.getOwner()) && !p.equals(source);
	}

	public void effectOnPiecePlayed(Game g, Piece p, Piece source) {
		g.getPieceEffector().givePlays(source.getOwner(), 1);
	}

	public class HeartlessMercenaryBuff extends PieceBuff {
		public HeartlessMercenaryBuff(Piece target, Card source) {
			this.target = target;
			this.source = source;
			this.isRemoveAtEndOfTurn = true;
			this.hasOnTurnEnd = true;
		}

		public boolean conditionOnTurnEnd(Game g) {
			return true;
		}

		public void effectOnTurnEnd(Game g) {
			g.simulateDestroy(target);
			ArrayList<Piece> enemyPieces = g.getBoard().getPlayersPieces(
					source.getOwner());
			if (enemyPieces.size() > 0) {
				int randomIndex = (int) (Math.random() * enemyPieces.size());
				g.getPieceEffector().destroy(enemyPieces.get(randomIndex));
			}
		}
	}
}
