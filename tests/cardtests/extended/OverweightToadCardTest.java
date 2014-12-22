package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.pieces.pieceeffects.set1.OverweightToadPieceEffect;

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
		// Play another one, destroying it to see if the effect stays
		actionPlay(0, 4, 0);
		assertEquals(game.offTurnPlayer().getHandCount(), 7);
		game.simulateDestroy(game.getBoard().getPiece(4, 0));
		actionCycleTurn();
		assertEquals(game.turnPlayer().getHandCount(), 8);
		actionCycleTurn();
		assertEquals(game.offTurnPlayer().getHandCount(), 12);
	}

}
