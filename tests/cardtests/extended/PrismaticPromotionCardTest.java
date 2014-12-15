package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.hexes.cardeffects.PrismaticPromotionHexEffect;
import card.pieces.pieceeffects.RedTigerPieceEffect;

public class PrismaticPromotionCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0],
				new int[] { RedTigerPieceEffect.ID, 1, PrismaticPromotionHexEffect.ID,
						2, 1001, 20, RedTigerPieceEffect.ID, 1 });
		setDummyDeck(players[1]);
	}

	@Test
	public void test() {
		game.beginTurn();
		actionPlay(0, 4, 0);
		// First promotion plays down a new Red Tiger
		actionPlay(0, 4, 0);
		assertEquals(game.turnPlayer().getGraveCount(), 2);
		assertEquals(game.getBoard().getPiece(4, 0).getCardBase().getId(),
				RedTigerPieceEffect.ID);
		// Second promotion does nothing and kills tiger
		actionPlay(0, 4, 0);
		assertEquals(game.getBoard().getPiece(4, 0), null);
	}
}