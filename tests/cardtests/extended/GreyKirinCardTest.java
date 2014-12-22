package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.CardBase;
import card.PieceCardBase;
import card.pieces.pieceeffects.set1.GreyKirinPieceEffect;

public class GreyKirinCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { GreyKirinPieceEffect.ID, 24 });
		setDummyDeck(players[1]);
	}

	@Test
	public void test() {
		PieceCardBase b = ((PieceCardBase) CardBase
				.getCard(GreyKirinPieceEffect.ID));
		game.beginTurn();
		actionPlay(0, 4, 0);
		assertEquals(game.getBoard().getPiece(4, 0).getAttack(), b.getAttack());
		actionCycleTurn();
		actionCycleTurn();
		actionPlay(0, 5, 0);
		assertEquals(game.getBoard().getPiece(4, 0).getAttack(), b.getAttack() + 1);
		assertEquals(game.getBoard().getPiece(5, 0).getAttack(), b.getAttack() + 1);
		game.movePiece(4, 0, 3, 0);
		assertEquals(game.getBoard().getPiece(3, 0).getAttack(), b.getAttack());
		assertEquals(game.getBoard().getPiece(5, 0).getAttack(), b.getAttack());
	}
}
