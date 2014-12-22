package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.pieces.pieceeffects.set1.AncientArchitectPieceEffect;
import card.pieces.pieceeffects.set1.AncientPillarPieceEffect;

public class AncientArchitectCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { AncientArchitectPieceEffect.ID, 3,
				AncientPillarPieceEffect.ID, 21 });
		setDeck(players[1], new int[] { AncientArchitectPieceEffect.ID, 3,
				AncientPillarPieceEffect.ID, 21 });
	}

	@Test
	public void test() {
		// Test that with no pillars, cannot activate architect
		game.beginTurn();
		actionPlay(0, 4, 0);
		assertFalse(game.activatePiece(4, 0));
		actionCycleTurn();
		actionCycleTurn();
		// Play a pillar and prove can activate
		actionPlay(2, 5, 0);
		assertTrue(game.activatePiece(4, 0));
		assertEquals(game.getBoard().getPiece(5, 0), null);
		assertEquals(game.getBoard().getPiece(4, 0).getCardBase().getId(),
				AncientPillarPieceEffect.ID);
	}
}
