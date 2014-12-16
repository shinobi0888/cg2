package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.hexes.cardeffects.TheDivineWindsHexEffect;
import card.pieces.pieceeffects.AgileFairyPieceEffect;
import card.pieces.pieceeffects.BoreasPieceEffect;

public class TheDivineWindsCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { BoreasPieceEffect.ID, 1,
				BoreasPieceEffect.EURUS_ID, 1, BoreasPieceEffect.NOTUS_ID, 1,
				BoreasPieceEffect.ZEPHYRUS_ID, 1, AgileFairyPieceEffect.ID, 10,
				TheDivineWindsHexEffect.ID, 10 });
		setDummyDeck(players[1]);
	}

	@Test
	public void test() {
		game.beginTurn();
		// Add a bunch of agile fairies
		for (int i = 0; i < 10; i++) {
			game.turnPlayer().drawCard(game);
		}
		actionPlay(4, 0, 0);
		actionPlay(4, 1, 0);
		actionPlay(4, 2, 0);
		actionPlay(4, 3, 0);
		actionPlay(4, 4, 0);
		actionPlay(4, 5, 0);
		actionPlay(4, 6, 0);
		actionPlay(4, 7, 0);
		actionPlay(0, 0, 0);
		actionCycleTurn();
		actionPlay(0, 4, 8);
		actionCycleTurn();
		actionPlay(0, 2, 0);
		actionCycleTurn();
		actionPlay(0, 5, 8);
		actionCycleTurn();
		actionPlay(0, 4, 0);
		actionCycleTurn();
		actionPlay(0, 6, 8);
		actionCycleTurn();
		// Before playing notus, return a target to hand (4,8)
		assertTrue(game.getBoard().getPiece(4, 8) != null);
		actionPlay(3, new int[] { 4, 8 });
		assertEquals(game.getBoard().getPiece(4, 8), null);
		actionPlay(0, 6, 0);
		// Try winds with all four
		actionPlay(3);
		assertEquals(game.getBoard().find(game.offTurnPlayer(), null).size(), 0);
		assertEquals(game.offTurnPlayer().getHandCount(), 9);
		assertEquals(game.offTurnPlayer().getGraveCount(), 0);
	}

}
