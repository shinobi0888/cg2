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

	protected boolean isRemoveAtEndOfOwnTurn = false;

	public boolean isRemoveAtEndOfOwnTurn() {
		return isRemoveAtEndOfOwnTurn;
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

	// Effects on turn start and turn end (of either player)
	protected boolean hasOnTurnStart = false;

	public boolean hasOnTurnStart() {
		return hasOnTurnStart;
	}
	
	public boolean conditionOnTurnStart(Game g, Player player) {
		return false;
	}
	
	public void effectOnTurnStart(Game g, Player player) {
	}
	
	protected boolean hasOnTurnEnd = false;

	public boolean hasOnTurnEnd() {
		return hasOnTurnEnd;
	}
	
	public boolean conditionOnTurnEnd(Game g, Player player) {
		return false;
	}
	
	public void effectOnTurnEnd(Game g, Player player) {
	}

	// Prevent playing pieces
	protected boolean isPreventFromPlayingPieces = false;

	public boolean isPreventFromPlayingPieces() {
		return isPreventFromPlayingPieces;
	}
}
