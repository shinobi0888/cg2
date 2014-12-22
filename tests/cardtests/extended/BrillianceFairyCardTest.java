package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.hexes.cardeffects.debug.SmiteHexEffect;
import card.pieces.pieceeffects.set1.BrillianceFairyPieceEffect;
import card.pieces.pieceeffects.set1.OberonPieceEffect;

public class BrillianceFairyCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { BrillianceFairyPieceEffect.ID, 2,
				SmiteHexEffect.ID, 2, 1001, 19, OberonPieceEffect.ID, 1 });
		setDummyDeck(players[1]);
	}

	@Test
	public void test() {
		// Play a brilliance and smite it
		game.beginTurn();
		actionPlay(0, 4, 0);
		actionPlay(1, 4, 0);
		// Should add an oberon to the hand
		assertEquals(game.turnPlayer().getHealth(), Player.MAX_HEALTH - 2);
		assertEquals(game.turnPlayer().getHandCard(5).getCardBase().getId(),
				OberonPieceEffect.ID);
		actionCycleTurn();
		actionCycleTurn();
		// Repeat process; this time no oberon should be added
		actionPlay(0, 4, 0);
		actionPlay(0, 4, 0);
		assertEquals(game.turnPlayer().getHealth(), Player.MAX_HEALTH - 4);
		assertEquals(game.turnPlayer().getHandCount(), 5);
	}
}
