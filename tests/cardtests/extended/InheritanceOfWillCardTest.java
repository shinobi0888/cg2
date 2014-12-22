package tests.cardtests.extended;
import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.hexes.cardeffects.debug.SmiteHexEffect;
import card.hexes.cardeffects.set1.InheritanceOfWillHexEffect;

public class InheritanceOfWillCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { InheritanceOfWillHexEffect.ID, 1, 1001, 3,
				SmiteHexEffect.ID, 20 });
		setDummyDeck(players[1]);
	}

	@Test
	public void test() {
		// Test by smiting 2 pawns after an inheritance
		game.beginTurn();
		actionPlay(0);
		actionPlay(0, 4, 0);
		assertEquals(game.turnPlayer().getPiecePlays(), 0);
		actionPlay(2, new int[] { 4, 0 });
		assertEquals(game.turnPlayer().getPiecePlays(), 1);
		actionPlay(0, 4, 0);
		assertEquals(game.turnPlayer().getPiecePlays(), 0);
		actionPlay(1, new int[] { 4, 0 });
		assertEquals(game.turnPlayer().getPiecePlays(), 1);
		actionCycleTurn();
		actionCycleTurn();
		// Make sure effect doesn't carry over
		actionPlay(0, 4, 0);
		assertEquals(game.turnPlayer().getPiecePlays(), 0);
		actionPlay(0, new int[] { 4, 0 });
		assertEquals(game.turnPlayer().getPiecePlays(), 0);
	}
}
