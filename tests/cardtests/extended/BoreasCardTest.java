package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.pieces.pieceeffects.AgileFairyPieceEffect;
import card.pieces.pieceeffects.BoreasPieceEffect;

public class BoreasCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { BoreasPieceEffect.ID, 1,
				BoreasPieceEffect.EURUS_ID, 1, BoreasPieceEffect.NOTUS_ID, 1,
				BoreasPieceEffect.ZEPHYRUS_ID, 1, AgileFairyPieceEffect.ID, 20 });
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
		actionCycleTurn();
		actionPlay(0, 2, 0);
		actionCycleTurn();
		actionCycleTurn();
		actionPlay(0, 4, 0);
		actionCycleTurn();
		actionCycleTurn();
		actionPlay(0, 6, 0);
		// Make sure boreas can't be used without proper positioning
		assertFalse(game.activatePiece(0, 0));
		// Move the others to the appropriate corners
		game.getBoard().movePiece(game.getBoard().getPiece(2, 0), 8, 0);
		game.getBoard().movePiece(game.getBoard().getPiece(4, 0), 8, 8);
		game.getBoard().movePiece(game.getBoard().getPiece(6, 0), 0, 8);
		// Activate boreas with corners occupied
		game.activatePiece(0, 0);
		assertEquals(game.offTurnPlayer().getHealth(), 0);
	}

}
