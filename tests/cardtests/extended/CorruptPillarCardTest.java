package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.CardBase;
import card.PieceCardBase;
import card.hexes.cardeffects.debug.SmiteHexEffect;
import card.pieces.pieceeffects.AncientPillarPieceEffect;
import card.pieces.pieceeffects.CorruptPillarPieceEffect;

public class CorruptPillarCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { CorruptPillarPieceEffect.ID, 24 });
		setDeck(players[1], new int[] { CorruptPillarPieceEffect.ID, 3,
				AncientPillarPieceEffect.ID, 3, SmiteHexEffect.ID, 18 });
	}

	@Test
	public void test() {
		// Player 1's corrupt pillar decays naturally
		// Player 2's corrupt pillar has
		PieceCardBase b = ((PieceCardBase) CardBase
				.getCard(CorruptPillarPieceEffect.ID));
		game.beginTurn();
		actionPlay(0, 4, 0);
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(), b.getDefense());
		actionCycleTurn();
		actionPlay(0, 4, 8);
		assertEquals(game.getBoard().getPiece(4, 8).getDefense(), b.getDefense());
		actionCycleTurn();
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(),
				b.getDefense() - 2);
		actionCycleTurn();
		actionCycleTurn();
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(),
				b.getDefense() - 4);
		actionCycleTurn();
		actionCycleTurn();
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(),
				b.getDefense() - 6);
		actionCycleTurn();
		actionCycleTurn();
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(),
				b.getDefense() - 8);
		actionCycleTurn();
		actionCycleTurn();
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(),
				b.getDefense() - 10);
		actionCycleTurn();
		actionPlay(3, 3, 8);
		assertEquals(game.getBoard().getPiece(4, 8).getDefense(),
				b.getDefense() - 8);
		actionCycleTurn();
		assertEquals(game.getBoard().getPiece(4, 0), null);
		actionCycleTurn();
		assertEquals(game.getBoard().getPiece(4, 8).getDefense(),
				b.getDefense() - 10);
		actionPlay(4, new int[] { 3, 8 });
		assertEquals(game.getBoard().getPiece(4, 8).getDefense(), 0);
	}
}
