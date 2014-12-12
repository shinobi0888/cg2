package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.pieces.pieceeffects.SoulstealerPieceEffect;

public class SoulstealerCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { SoulstealerPieceEffect.ID, 24 });
		setDeck(players[1], new int[] { SoulstealerPieceEffect.ID, 24 });
	}

	@Test
	public void test() {
		// Test by playing a bunch of soulstealers and checking stats
		game.beginTurn();
		actionPlay(0, 4, 0);
		assertEquals(game.getBoard().getPiece(4, 0).getAttack(), 0);
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(), 0);
		actionCycleTurn();
		actionPlay(0, 4, 8);
		assertEquals(game.getBoard().getPiece(4, 0).getAttack(), 0);
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(), 0);
		assertEquals(game.getBoard().getPiece(4, 8).getAttack(), 0);
		assertEquals(game.getBoard().getPiece(4, 8).getDefense(), 0);
		actionCycleTurn();
		actionPlay(0, 5, 0);
		assertEquals(game.getBoard().getPiece(4, 0).getAttack(), 0);
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(), 0);
		assertEquals(game.getBoard().getPiece(4, 8).getAttack(), 0);
		assertEquals(game.getBoard().getPiece(4, 8).getDefense(), 0);
		assertEquals(game.getBoard().getPiece(5, 0).getAttack(), 1);
		assertEquals(game.getBoard().getPiece(5, 0).getDefense(), 1);
		actionCycleTurn();
		actionPlay(0, 5, 8);
		assertEquals(game.getBoard().getPiece(4, 0).getAttack(), 0);
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(), 0);
		assertEquals(game.getBoard().getPiece(4, 8).getAttack(), 0);
		assertEquals(game.getBoard().getPiece(4, 8).getDefense(), 0);
		assertEquals(game.getBoard().getPiece(5, 0).getAttack(), 1);
		assertEquals(game.getBoard().getPiece(5, 0).getDefense(), 1);
		assertEquals(game.getBoard().getPiece(5, 8).getAttack(), 1);
		assertEquals(game.getBoard().getPiece(5, 8).getDefense(), 1);
	}
}
