package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.pieces.pieceeffects.OverweightToadPieceEffect;

public class OverweightToadCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { OverweightToadPieceEffect.ID, 24 });
		setDummyDeck(players[1]);
	}

	@Test
	public void test() {
		// Play an overweight toad
		game.beginTurn();
		actionPlay(0, 0, 0);
		assertEquals(game.offTurnPlayer().getHandCount(), 4);
		actionCycleTurn();
		actionCycleTurn();
		assertEquals(game.offTurnPlayer().getHandCount(), 9);
	}

}
