package tests.cardtests.set1;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.pieces.pieceeffects.set1.AgileFairyPieceEffect;
import card.pieces.pieceeffects.set1.ChampionGerynPieceEffect;

public class ChampionGerynCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { ChampionGerynPieceEffect.ID, 1,
				AgileFairyPieceEffect.ID, 23 });
		setDummyDeck(players[1]);
	}

	@Test
	public void test() {
		game.beginTurn();
		// Play all agile fairies in hand
		actionPlay(1, 1, 0);
		actionPlay(1, 2, 0);
		actionPlay(1, 3, 0);
		actionPlay(1, 4, 0);
		actionPlay(1, 5, 0);
		actionPlay(1, 6, 0);
		actionCycleTurn();
		actionCycleTurn();
		// Set up for a geryn
		game.movePiece(4, 0, 4, 1);
		game.movePiece(5, 0, 5, 1);
		actionCycleTurn();
		actionCycleTurn();
		game.movePiece(5, 1, 5, 2);
		// Do some damage and allow geryn to heal
		game.getPieceEffector().damage(game.turnPlayer(), 25);
		assertEquals(game.turnPlayer().getHealth(), Player.MAX_HEALTH - 25);
		actionPlay(0, 4, 1);
		assertEquals(game.getBoard().getPiece(4, 1).getCardBase().getId(),
				ChampionGerynPieceEffect.ID);
		assertEquals(game.turnPlayer().getHealth(), Player.MAX_HEALTH - 25 + 4 * 2);
	}

}
