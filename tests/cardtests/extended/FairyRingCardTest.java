package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.hexes.cardeffects.FairyRingHexEffect;
import card.pieces.pieceeffects.OberonPieceEffect;

public class FairyRingCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { FairyRingHexEffect.ID, 2, 1001, 21,
				OberonPieceEffect.ID, 1 });
		setDummyDeck(players[1]);
	}

	@Test
	public void test() {
		// Play a fairy ring, adding oberon
		game.beginTurn();
		assertEquals(game.turnPlayer().getHandCount(), 7);
		actionPlay(0);
		assertEquals(game.turnPlayer().getHandCount(), 7);
		assertEquals(game.turnPlayer().getHandCard(6).getCardBase().getId(),
				OberonPieceEffect.ID);
		// Play another fairy ring, adding nothing
		actionPlay(0);
		assertEquals(game.turnPlayer().getHandCount(), 6);
	}

}
