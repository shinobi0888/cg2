package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.CardBase;
import card.PieceCardBase;
import card.hexes.cardeffects.debug.SmiteHexEffect;
import card.pieces.pieceeffects.set1.CelebrationalPillarPieceEffect;

public class CelebrationalPillarCardTest extends BasicCardTest {

	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { CelebrationalPillarPieceEffect.ID, 3,
				SmiteHexEffect.ID, 21 });
		setDeck(players[1], new int[] { CelebrationalPillarPieceEffect.ID, 3,
				SmiteHexEffect.ID, 21 });
	}

	@Test
	public void test() {
		PieceCardBase b = ((PieceCardBase) CardBase
				.getCard(CelebrationalPillarPieceEffect.ID));
		game.beginTurn();
		// Play a pair of celebrationals
		actionPlay(0, 4, 0);
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(), b.getDefense());
		actionCycleTurn();
		actionPlay(0, 4, 8);
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(),
				b.getDefense() + 1);
		assertEquals(game.getBoard().getPiece(4, 8).getDefense(),
				b.getDefense() + 1);
		actionCycleTurn();
		// Player 1 plays a second celebrational, observe change in enemy
		// celebrational
		actionPlay(0, 5, 0);
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(),
				b.getDefense() + 1);
		assertEquals(game.getBoard().getPiece(5, 0).getDefense(),
				b.getDefense() + 1);
		assertEquals(game.getBoard().getPiece(4, 8).getDefense(),
				b.getDefense() + 2);
		actionCycleTurn();
		// Player 2 smites newly played piece, reverting own celebrational
		actionPlay(2, new int[] { 5, 0 });
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(),
				b.getDefense() + 1);
		assertEquals(game.getBoard().getPiece(5, 0), null);
		assertEquals(game.getBoard().getPiece(4, 8).getDefense(),
				b.getDefense() + 1);
	}
}
