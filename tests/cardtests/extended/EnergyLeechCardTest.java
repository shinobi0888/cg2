package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.CardBase;
import card.PieceCardBase;
import card.pieces.pieceeffects.set1.EnergyLeechPieceEffect;

public class EnergyLeechCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { EnergyLeechPieceEffect.ID, 24 });
		setDeck(players[1], new int[] { EnergyLeechPieceEffect.ID, 24 });
	}

	@Test
	public void test() {
		PieceCardBase b = ((PieceCardBase) CardBase
				.getCard(EnergyLeechPieceEffect.ID));
		game.beginTurn();
		// Play a leech; hands go from 6 and 6 to 3 and 3
		assertEquals(game.turnPlayer().getHandCount(), 7);
		assertEquals(game.offTurnPlayer().getHandCount(), 6);
		actionPlay(0, 4, 0);
		assertEquals(game.turnPlayer().getHandCount(), 3);
		assertEquals(game.offTurnPlayer().getHandCount(), 3);
		assertEquals(game.turnPlayer().getGraveCount(), 3);
		assertEquals(game.offTurnPlayer().getGraveCount(), 3);
		assertEquals(game.getBoard().getPiece(4, 0).getAttack(), b.getAttack() + 12);
		// Play another leech from enemy, doesn't get any buff at all due to dead
		// hands
		actionCycleTurn();
		actionPlay(0, 4, 8);
		assertEquals(game.getBoard().getPiece(4, 0).getAttack(), b.getAttack() + 12);
		assertEquals(game.getBoard().getPiece(4, 8).getAttack(), b.getAttack());
	}

}
