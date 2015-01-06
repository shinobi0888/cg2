package card.pieces.pieceeffects.set1;

import game.Game;
import game.Player;
import board.Piece;
import buffs.PlayerBuff;
import card.Card;
import card.pieces.effects.EmptyPieceEffect;

public class LordOfThunderPieceEffect extends EmptyPieceEffect {
	public static final int ID = 65;
	private static final int HEALTH_LIMIT = 10;

	public LordOfThunderPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return g.getEnemy(playedPiece.getOwner()).getHealth() < HEALTH_LIMIT;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		g.getPieceEffector().givePlayerBuff(g.getEnemy(playedPiece.getOwner()),
				new LordOfThunderBuff(playedPiece.getSourceCard()));
	}

	public class LordOfThunderBuff extends PlayerBuff {
		public LordOfThunderBuff(Card source) {
			super(source);
			hasOnTurnEnd = true;
			stacks = 0;
		}

		public boolean conditionOnTurnEnd(Game g, Player player) {
			return true;
		}

		public void effectOnTurnEnd(Game g, Player player) {
			stacks++;
			if (stacks == HEALTH_LIMIT) {
				g.getPieceEffector().setHealth(player, 0);
			}
		}
	}
}
