package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.hexes.cardeffects.ForcefulExchangeHexEffect;

public class ForcefulExchangeCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { ForcefulExchangeHexEffect.ID, 24 });
		setDeck(players[1], new int[] { 1001, 8, ForcefulExchangeHexEffect.ID, 16 });
	}

	@Test
	public void test() {
		// Test by playing a forceful exchange and seeing that all cards are
		// unplayable and the playcount is 1. Ensure hexes are still able to
		// be play
		game.beginTurn();
		assertEquals(game.offTurnPlayer().getHandCount(), 6);
		actionPlay(0);
		assertEquals(game.offTurnPlayer().getHandCount(), 8);
		actionCycleTurn();
		assertEquals(game.turnPlayer().getPiecePlays(), 1);
		for (int i = 0; i < 8; i++) {
			assertFalse(game.playIndex(i));
		}
		assertTrue(game.playIndex(8));
	}
}
