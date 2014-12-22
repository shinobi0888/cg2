package tests.cardtests.set1;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.pieces.pieceeffects.set1.ParasiticSwarmPieceEffect;

public class ParasiticSwarmCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { ParasiticSwarmPieceEffect.ID, 24 });
		setDeck(players[1], new int[] { ParasiticSwarmPieceEffect.ID, 24 });
	}

	@Test
	public void test() {
		game.beginTurn();
		// Play a swarm, deck count goes -1 for player
		assertEquals(game.turnPlayer().getDeckCount(), 17);
		actionPlay(0, 4, 0);
		assertEquals(game.turnPlayer().getDeckCount(), 16);
		actionCycleTurn();
		// Play a swarm, deck count goes -1 for player, -1 for enemy
		assertEquals(game.turnPlayer().getDeckCount(), 17);
		actionPlay(0, 4, 8);
		assertEquals(game.turnPlayer().getDeckCount(), 16);
		assertEquals(game.offTurnPlayer().getDeckCount(), 15);
	}

}
