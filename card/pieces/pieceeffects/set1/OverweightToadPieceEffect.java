package card.pieces.pieceeffects.set1;

import game.Game;
import game.Player;
import board.Piece;
import buffs.PlayerBuff;
import card.Card;
import card.pieces.effects.EmptyPieceEffect;

public class OverweightToadPieceEffect extends EmptyPieceEffect {
	public static final int ID = 34;
	private static final int SEND_COUNT = 2;
	private static final int DRAW_COUNT = 4;

	public OverweightToadPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return true;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		Player enemy = g.getEnemy(playedPiece.getOwner());
		for (int i = 0; i < SEND_COUNT; i++) {
			if (enemy.getHandCount() == 0) {
				break;
			}
			int randomIndex = (int) (Math.random() * enemy.getHandCount());
			g.simulateSendFromHandToBottomOfDeck(enemy, randomIndex);
		}
		g.simulateGivePlayerBuff(enemy,
				new OverweightToadBuff(playedPiece.getSourceCard()));
	}

	public class OverweightToadBuff extends PlayerBuff {
		public OverweightToadBuff(Card source) {
			super(source);
			isRemoveAtEndOfOwnTurn = true;
			hasOnTurnEnd = true;
		}

		public boolean conditionOnTurnEnd(Game g, Player player) {
			return g.turnPlayer().equals(player);
		}

		public void effectOnTurnEnd(Game g, Player player) {
			for (int i = 0; i < DRAW_COUNT; i++) {
				g.simulateEffectDraw(player);
			}
		}

	}
}
