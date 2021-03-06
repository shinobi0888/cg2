package tests.cardtests.set1;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.pieces.pieceeffects.set1.IdolOfRenewalPieceEffect;

public class IdolOfRenewalCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { IdolOfRenewalPieceEffect.ID, 2, 1001, 8,
				91, 1, 1001, 13 });
		setDeck(players[1], new int[] { IdolOfRenewalPieceEffect.ID, 1, 1001, 23 });
	}

	@Test
	public void test() {
		// Test by playing an idol and then observing draw phase
		game.beginTurn();
		actionPlay(2, 4, 0);
		actionCycleTurn();
		// Ensure card counts for player 2 don't get affected
		assertEquals(game.turnPlayer().getHandCount(), 7);
		actionCycleTurn();
		actionPlay(0, new int[] { 4, 0 });
		assertEquals(game.turnPlayer().getHandCount(), 6);
		actionCycleTurn();
		// Ensure card counts for player 2 don't get affected
		assertEquals(game.turnPlayer().getHandCount(), 8);
		actionCycleTurn();
		// Draws and gets 2 cards due to idol
		assertEquals(game.turnPlayer().getHandCount(), 8);
		actionCycleTurn();
		actionCycleTurn();
		// Draws a protector, getting 1 draw
		assertEquals(game.turnPlayer().getHandCount(), 9);
		// Play a pawn, then an idol
		actionPlay(1, 5, 0);
		actionCycleTurn();
		actionCycleTurn();
		actionPlay(0, new int[] { 5, 0 });
		actionCycleTurn();
		actionCycleTurn();
		// Draws 2 for double idol
		assertEquals(game.turnPlayer().getHandCount(), 12);
	}
}
