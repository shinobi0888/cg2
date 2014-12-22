package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.pieces.pieceeffects.set1.AccursedPillarPieceEffect;

public class AccursedPillarCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { AccursedPillarPieceEffect.ID, 24 });
		setDeck(players[1], new int[] { AccursedPillarPieceEffect.ID, 24 });
	}

	@Test
	public void test() {
		// Simple decay test; both players have an accursed pillar that tick down
		game.beginTurn();
		actionPlay(3, 4, 0);
		actionCycleTurn();
		assertEquals(game.turnPlayer().getHealth(), Player.MAX_HEALTH - 1);
		actionPlay(3, 4, 8);
		actionCycleTurn();
		assertEquals(game.turnPlayer().getHealth(), Player.MAX_HEALTH - 2);
		actionCycleTurn();
		assertEquals(game.turnPlayer().getHealth(), Player.MAX_HEALTH - 3);
	}
}
