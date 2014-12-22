package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import java.awt.Point;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.pieces.pieceeffects.set1.BlueHeronPieceEffect;

public class BlueHeronCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { BlueHeronPieceEffect.ID, 24 });
		setDeck(players[1], new int[] { BlueHeronPieceEffect.ID, 24 });
	}

	@Test
	public void test() {
		game.beginTurn();
		actionPlay(0, 4, 0);
		assertEquals(game.getBoard().getPiece(4, 0).canBeAttacked(), true);
		actionCycleTurn();
		actionPlay(0, 4, 8);
		assertEquals(game.getBoard().getPiece(4, 8).canBeAttacked(), true);
		actionCycleTurn();
		actionPlay(0, 5, 0);
		assertEquals(game.getBoard().getPiece(4, 0).canBeAttacked(), false);
		assertEquals(game.getBoard().getPiece(5, 0).canBeAttacked(), false);
		game.movePiece(4, 0, 4, 1);
		assertEquals(game.getBoard().getPiece(4, 1).canBeAttacked(), true);
		assertEquals(game.getBoard().getPiece(5, 0).canBeAttacked(), true);
		actionCycleTurn();
		game.movePiece(4, 8, 4, 7);
		actionCycleTurn();
		game.movePiece(5, 0, 5, 1);
		actionCycleTurn();
		game.movePiece(4, 7, 4, 6);
		actionCycleTurn();
		actionCycleTurn();
		game.movePiece(4, 6, 4, 5);
		actionCycleTurn();
		actionCycleTurn();
		game.movePiece(4, 5, 4, 4);
		actionCycleTurn();
		actionCycleTurn();
		game.movePiece(4, 4, 4, 3);
		actionCycleTurn();
		actionCycleTurn();
		game.movePiece(4, 3, 4, 2);
		assertEquals(game.getBoard().getPiece(4, 2).canAttack(new Point(4, 1)),
				false);
		actionCycleTurn();
		game.movePiece(5, 1, 5, 2);
		actionCycleTurn();
		assertEquals(game.getBoard().getPiece(4, 2).canAttack(new Point(4, 1)),
				true);
	}
}
