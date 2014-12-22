package tests.cardtests.set1;

import static org.junit.Assert.*;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.CardBase;
import card.PieceCardBase;
import card.pieces.pieceeffects.set1.AncientPillarPieceEffect;

public class AncientPillarCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { AncientPillarPieceEffect.ID, 24 });
		setDeck(players[1], new int[] { AncientPillarPieceEffect.ID, 24 });
	}

	@Test
	public void test() {
		PieceCardBase b = ((PieceCardBase) CardBase
				.getCard(AncientPillarPieceEffect.ID));
		game.beginTurn();
		actionPlay(3, 4, 0);
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(),
				b.getDefense() + 2);
		actionCycleTurn();
		actionPlay(3, 4, 8);
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(),
				b.getDefense() + 2);
		assertEquals(game.getBoard().getPiece(4, 8).getDefense(),
				b.getDefense() + 2);
		actionCycleTurn();
		actionPlay(3, 5, 0);
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(),
				b.getDefense() + 4);
		assertEquals(game.getBoard().getPiece(5, 0).getDefense(),
				b.getDefense() + 4);
		assertEquals(game.getBoard().getPiece(4, 8).getDefense(),
				b.getDefense() + 2);
		actionCycleTurn();
		actionPlay(3, 5, 8);
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(),
				b.getDefense() + 4);
		assertEquals(game.getBoard().getPiece(5, 0).getDefense(),
				b.getDefense() + 4);
		assertEquals(game.getBoard().getPiece(4, 8).getDefense(),
				b.getDefense() + 4);
		assertEquals(game.getBoard().getPiece(5, 8).getDefense(),
				b.getDefense() + 4);
	}
}
