package tests.cardtests.set1;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.pieces.pieceeffects.set1.OberonPieceEffect;
import card.pieces.pieceeffects.set1.WinglessFairyPieceEffect;

public class WinglessFairyCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { WinglessFairyPieceEffect.ID, 2,
				OberonPieceEffect.ID, 22 });
		setDummyDeck(players[1]);
	}

	@Test
	public void test() {
		// Play a wingless without oberon, deals 4 damage
		game.beginTurn();
		assertEquals(game.turnPlayer().getHealth(), Player.MAX_HEALTH);
		actionPlay(0, 4, 0);
		assertEquals(game.turnPlayer().getHealth(), Player.MAX_HEALTH - 4);
		// No additional effects on wingless
		assertEquals(game.turnPlayer().getHandCount(), 6);
		assertEquals(game.turnPlayer().getPiecePlays(), 0);
		actionCycleTurn();
		actionCycleTurn();
		// Play oberon and another wingless, gets additional effects of wingless
		actionPlay(2, 5, 0);
		assertEquals(game.turnPlayer().getHealth(), Player.MAX_HEALTH - 4);
		actionCycleTurn();
		actionCycleTurn();
		assertEquals(game.turnPlayer().getHandCount(), 7);
		actionPlay(0, 3, 0);
		assertEquals(game.turnPlayer().getHandCount(), 7);
		assertEquals(game.turnPlayer().getPiecePlays(), 1);
		assertEquals(game.turnPlayer().getHealth(), Player.MAX_HEALTH - 8);
	}
}
