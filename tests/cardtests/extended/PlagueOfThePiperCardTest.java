package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.hexes.cardeffects.PlagueOfThePiperHexEffect;

public class PlagueOfThePiperCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { PlagueOfThePiperHexEffect.ID, 24 });
		setDeck(players[1], new int[] { 1001, 24 });
	}

	@Test
	public void test() {
		// Play a plague and see the effects on the next turn
		game.beginTurn();
		actionPlay(0);
		actionCycleTurn();
		assertEquals(game.turnPlayer().getPiecePlays(), 3);
		actionCycleTurn();
		assertEquals(game.offTurnPlayer().getHandCount(), 0);
		assertEquals(game.offTurnPlayer().getGraveCount(), 7);
		assertEquals(game.offTurnPlayer().getHealth(), Player.MAX_HEALTH - 14);
		// Cycle another turn and make sure no effects linger
		actionCycleTurn();
		assertEquals(game.turnPlayer().getPiecePlays(), 1);
		actionCycleTurn();
		assertEquals(game.offTurnPlayer().getHealth(), Player.MAX_HEALTH - 14);
	}
}