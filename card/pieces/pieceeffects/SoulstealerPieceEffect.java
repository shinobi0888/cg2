package card.pieces.pieceeffects;

import java.util.ArrayList;

import game.Game;
import board.Piece;
import buffs.PieceBuff;
import card.Card;
import card.pieces.effects.EmptyPieceEffect;

public class SoulstealerPieceEffect extends EmptyPieceEffect {
	public static final int ID = 12;

	public SoulstealerPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return true;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		ArrayList<Piece> allyPieces = g.getBoard().getPlayersPieces(
				playedPiece.getOwner());
		allyPieces.remove(playedPiece);
		g.simulateGivePieceBuff(playedPiece, new SoulstealerBuff(playedPiece,
				playedPiece.getSourceCard(), allyPieces.size()));
	}

	public class SoulstealerBuff extends PieceBuff {
		public SoulstealerBuff(Piece target, Card source, int numAllies) {
			this.target = target;
			this.source = source;
			this.stacks = numAllies;
			this.isRemoveAtEndOfTurn = false;
		}

		public int getAttackBuff(Game g) {
			return stacks;
		}

		public int getDefenseBuff(Game g) {
			return stacks;
		}
	}
}
