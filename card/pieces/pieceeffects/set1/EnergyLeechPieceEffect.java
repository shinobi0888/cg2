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
			int randomIndex = (int) (Math.random() * g.turnPlayer().getHandCount());
			g.simulateDiscardFromHand(g.turnPlayer(), randomIndex);
			stacks++;
		}
		while (g.offTurnPlayer().getHandCount() > 3) {
			int randomIndex = (int) (Math.random() * g.offTurnPlayer().getHandCount());
			g.simulateDiscardFromHand(g.offTurnPlayer(), randomIndex);
			stacks++;
		}
		g.simulateGivePieceBuff(playedPiece, new EnergyLeechBuff(playedPiece,
				playedPiece.getSourceCard(), stacks));
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
