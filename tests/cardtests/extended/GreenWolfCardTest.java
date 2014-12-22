package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.CardBase;
import card.PieceCardBase;
import card.pieces.pieceeffects.set1.GreenWolfPieceEffect;

public class GreenWolfCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { GreenWolfPieceEffect.ID, 24 });
		setDummyDeck(players[1]);
	}

	@Test
	public void test() {
		// Play a wolf, then another one and move the original wolf
		// and see if the aura is properly removed
		PieceCardBase b = ((PieceCardBase) CardBase
				.getCard(GreenWolfPieceEffect.ID));
		game.beginTurn();
		actionPlay(0, 4, 0);
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(), b.getDefense());
		actionCycleTurn();
		actionCycleTurn();
		actionPlay(0, 5, 0);
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(), b.getDefense() + 2);
		assertEquals(game.getBoard().getPiece(5, 0).getDefense(), b.getDefense() + 2);
		game.movePiece(4, 0, 3, 0);
		assertEquals(game.getBoard().getPiece(3, 0).getDefense(), b.getDefense());
		assertEquals(game.getBoard().getPiece(5, 0).getDefense(), b.getDefense());
	}

}
