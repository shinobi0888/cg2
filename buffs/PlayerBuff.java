package buffs;

import game.Game;
import game.Player;
import board.PieceSnapshot;
import card.Card;

public class PlayerBuff {
	protected int stacks;
	protected Card source;

	public PlayerBuff(Card source) {
		this.source = source;
	}

	public Card getSource() {
		return source;
	}

	protected boolean isRemoveAtEndOfTurn = false;

	public boolean isRemoveAtEndOfTurn() {
		return isRemoveAtEndOfTurn;
	}

	protected boolean hasOnPieceDestroyed = false;

	public boolean hasOnPieceDestroyed() {
		return hasOnPieceDestroyed;
	}

	public boolean conditionOnPieceDestroyed(Game g, Player player,
			PieceSnapshot p) {
		return false;
	}

	public void effectOnPieceDestroyed(Game g, Player player, PieceSnapshot p) {

	}
}
