package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.hexes.cardeffects.TitheOfProtectionHexEffect;

public class TitheOfProtectionCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { TitheOfProtectionHexEffect.ID, 24 });
		setDummyDeck(players[1]);
	}

	@Test
	public void test() {
		// Keep playing tithes until the hand cannot be emptied anymore
		game.beginTurn();
		assertEquals(game.getEnemy(game.turnPlayer()).getHandCount(), 6);
		actionPlay(0);
		assertEquals(game.turnPlayer().getHealth(), Player.MAX_HEALTH - 8);
		assertEquals(game.getEnemy(game.turnPlayer()).getHandCount(), 4);
		actionPlay(0);
		assertEquals(game.turnPlayer().getHealth(), Player.MAX_HEALTH - 16);
		assertEquals(game.getEnemy(game.turnPlayer()).getHandCount(), 2);
		actionPlay(0);
		assertEquals(game.turnPlayer().getHealth(), Player.MAX_HEALTH - 24);
		assertEquals(game.getEnemy(game.turnPlayer()).getHandCount(), 0);
		actionPlay(0);
		assertEquals(game.turnPlayer().getHealth(), Player.MAX_HEALTH - 32);
		assertEquals(game.getEnemy(game.turnPlayer()).getHandCount(), 0);
	}
}
