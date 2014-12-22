package tests.cardtests.set1;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.pieces.pieceeffects.set1.AgileFairyPieceEffect;
import card.pieces.pieceeffects.set1.SealOfEqualityPieceEffect;

public class SealOfEqualityCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { AgileFairyPieceEffect.ID, 6,
				SealOfEqualityPieceEffect.ID, 18 });
		setDummyDeck(players[1]);
	}

	@Test
	public void test() {
		game.beginTurn();
		// Play a seal of equality
		actionPlay(0, 0, 0);
		actionPlay(0, 1, 0);
		actionPlay(4, 0, 0);
		assertEquals(game.getBoard().getPiece(0, 0).getCardBase().getId(),
				SealOfEqualityPieceEffect.ID);
		assertEquals(game.offTurnPlayer().getHandCount(), 6);
		actionCycleTurn();
		// Drew a card since he has less pieces
		assertEquals(game.turnPlayer().getHandCount(), 7);
		assertEquals(game.offTurnPlayer().getHandCount(), 4);
		actionCycleTurn();
		// No draw since more pieces
		assertEquals(game.turnPlayer().getHandCount(), 4);
		actionCycleTurn();
		// Drew a card since he has less pieces
		assertEquals(game.turnPlayer().getHandCount(), 8);
		// Play a card to even the field
		actionPlay(0, 0, 8);
		actionCycleTurn();
		// Yes draw equal pieces
		assertEquals(game.turnPlayer().getHandCount(), 5);
		actionCycleTurn();
		// Yes draw equal pieces
		assertEquals(game.turnPlayer().getHandCount(), 8);
		actionPlay(0, 1, 8);
		// Reverse numbers; player 2 doesn't draw anymore
		actionCycleTurn();
		assertEquals(game.turnPlayer().getHandCount(), 6);
		actionCycleTurn();
		assertEquals(game.turnPlayer().getHandCount(), 7);
	}

}
