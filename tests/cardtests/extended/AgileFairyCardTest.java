package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.pieces.pieceeffects.set1.AgileFairyPieceEffect;

public class AgileFairyCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { AgileFairyPieceEffect.ID, 24 });
		setDummyDeck(players[1]);
	}

	@Test
	public void test() {
		// Play an agile fairy and make sure play count isn't diminished
		game.beginTurn();
		actionPlay(0, 0, 0);
		assertEquals(game.turnPlayer().getPiecePlays(), 1);
		// Play all agile fairies in hand
		actionPlay(0, 1, 0);
		actionPlay(0, 2, 0);
		actionPlay(0, 3, 0);
		actionPlay(0, 4, 0);
		actionPlay(0, 5, 0);
		actionPlay(0, 6, 0);
		assertEquals(game.turnPlayer().getPiecePlays(), 1);
		assertEquals(game.turnPlayer().getHandCount(), 0);
	}

}
