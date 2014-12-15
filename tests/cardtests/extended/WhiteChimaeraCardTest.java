package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.pieces.pieceeffects.WhiteChimaeraPieceEffect;

public class WhiteChimaeraCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { WhiteChimaeraPieceEffect.ID, 24 });
		setDummyDeck(players[1]);
	}

	@Test
	public void test() {
		// Play first chimaera, getting nothing
		game.beginTurn();
		assertEquals(game.turnPlayer().getPiecePlays(), 1);
		actionPlay(0, 0, 0);
		assertEquals(game.turnPlayer().getPiecePlays(), 0);
		actionCycleTurn();
		actionCycleTurn();
		// Play another chimaera, getting benefits
		assertEquals(game.turnPlayer().getPiecePlays(), 1);
		actionPlay(0, 1, 0);
		assertEquals(game.turnPlayer().getPiecePlays(), 1);
		// Spam chimaera
		actionPlay(0, 2, 0);
		assertEquals(game.turnPlayer().getPiecePlays(), 1);
		actionPlay(0, 3, 0);
		assertEquals(game.turnPlayer().getPiecePlays(), 1);
		actionPlay(0, 4, 0);
		assertEquals(game.turnPlayer().getPiecePlays(), 1);
		// Play one off, getting no boost
		actionPlay(0, 6, 0);
		assertEquals(game.turnPlayer().getPiecePlays(), 0);
		actionCycleTurn();
		actionCycleTurn();
		// Move all chimaera up one, then spawn another getting 3 effects
		game.movePiece(2, 0, 2, 1);
		game.movePiece(3, 0, 3, 1);
		game.movePiece(4, 0, 4, 1);
		actionPlay(0, 3, 0);
		assertEquals(game.turnPlayer().getPiecePlays(), 3);
	}
}
