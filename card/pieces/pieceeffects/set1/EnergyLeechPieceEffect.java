package card.pieces.pieceeffects.set1;

import game.Game;
import board.Piece;
import buffs.PieceBuff;
import card.Card;
import card.pieces.effects.EmptyPieceEffect;

public class EnergyLeechPieceEffect extends EmptyPieceEffect {
	public static final int ID = 32;

	public EnergyLeechPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return g.turnPlayer().getHandCount() > 3
				|| g.offTurnPlayer().getHandCount() > 3;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		int stacks = 0;
		while (g.turnPlayer().getHandCount() > 3) {
			g.getPieceEffector().discard(g.turnPlayer(),
					g.turnPlayer().getRandomInHand());
			stacks++;
		}
		while (g.offTurnPlayer().getHandCount() > 3) {
			g.getPieceEffector().discard(g.offTurnPlayer(),
					g.offTurnPlayer().getRandomInHand());
			stacks++;
		}
		g.getPieceEffector().givePieceBuff(playedPiece,
				new EnergyLeechBuff(playedPiece, playedPiece.getSourceCard(), stacks));
	}

	public class EnergyLeechBuff extends PieceBuff {
		public EnergyLeechBuff(Piece target, Card source, int stacks) {
			this.target = target;
			this.source = source;
			this.stacks = stacks;
			this.isRemoveAtEndOfTurn = false;
		}

		public int getAttackBuff(Game g) {
			return stacks * 2;
		}
	}
}
