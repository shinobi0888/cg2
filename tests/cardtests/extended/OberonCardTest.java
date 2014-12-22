package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.pieces.pieceeffects.set1.OberonPieceEffect;
import card.pieces.pieceeffects.set1.TitaniaPieceEffect;

public class OberonCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { OberonPieceEffect.ID, 3,
				TitaniaPieceEffect.ID, 21 });
		setDummyDeck(players[1]);
	}

	@Test
	public void test() {
		// Play oberon to pull a titania, prove on playing titania that oberon
		// cant be pulled
		game.beginTurn();
		assertEquals(game.turnPlayer().getHandCount(), 7);
		actionYesNo(true);
		actionPlay(0, 4, 0);
		assertEquals(game.turnPlayer().getHandCount(), 7);
		assertEquals(game.turnPlayer().getHandCard(6).getCardBase().getId(),
				TitaniaPieceEffect.ID);
		actionCycleTurn();
		actionCycleTurn();
		actionPlay(6, 5, 0);
		assertEquals(game.turnPlayer().getHandCount(), 7);
		actionCycleTurn();
		actionCycleTurn();
		// Finally play another oberon, saying no and thus not adding a card
		assertEquals(game.turnPlayer().getHandCount(), 8);
		actionYesNo(false);
		actionPlay(0, 3, 0);
		assertEquals(game.turnPlayer().getHandCount(), 7);
	}

}
