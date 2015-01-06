package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import buffs.PieceBuff;
import card.Card;
import card.pieces.effects.EmptyPieceEffect;

public class ManiacalConsciencePieceEffect extends EmptyPieceEffect {
	public static final int ID = 47;

	public ManiacalConsciencePieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece p) {
		return g.getBoard().getPlayersPieces(p.getOwner()).size() > 0;
	}

	public void effectOnPlay(Game g, Piece p) {
		Piece target = g.getIface().requestBoardPiece(
				"Select a piece to apply buff to:",
				g.getBoard().getPlayersPieces(p.getOwner()));
		g.getPieceEffector().givePieceBuff(target,
				new ManiacalConscienceBuff(target, p.getSourceCard()));
	}

	public class ManiacalConscienceBuff extends PieceBuff {
		public ManiacalConscienceBuff(Piece target, Card source) {
			this.target = target;
			this.source = source;
			this.stacks = 0;
			this.isRemoveAtEndOfTurn = false;
		}

		public int getAttackBuff(Game g) {
			return 3;
		}

		public int getDefenseBuff(Game g) {
			return -3;
		}
	}
}
