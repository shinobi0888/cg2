package tests.cardtests.set1;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.hexes.hexeffects.set1.LivingWallsHexEffect;
import card.pieces.pieceeffects.set1.AncientPillarPieceEffect;

public class LivingWallsCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { 1001, 4, AncientPillarPieceEffect.ID, 2,
				LivingWallsHexEffect.ID, 18 });
		setDeck(players[1], new int[] { 1001, 4, AncientPillarPieceEffect.ID, 2,
				LivingWallsHexEffect.ID, 18 });
	}

	@Test
	public void test() {
		// Test by surrounding pillar with pawns, activating living walls is a no-op
		game.beginTurn();
		actionPlay(0, 4, 0);
		actionCycleTurn();
		actionCycleTurn();
		actionPlay(0, 5, 0);
		game.movePiece(4, 0, 4, 1);
		actionCycleTurn();
		actionCycleTurn();
		actionPlay(0, 3, 0);
		actionCycleTurn();
		actionCycleTurn();
		actionPlay(1, 4, 0);
		game.playIndex(2);
		assertEquals(game.getBoard().getPiece(4, 0).getCardBase().getId(),
				AncientPillarPieceEffect.ID);
		actionCycleTurn();
		actionCycleTurn();
		// Move left pawn up to give room, then play another pillar and retry
		game.movePiece(3, 0, 3, 1);
		actionPlay(1, 0, 0);
		actionPlay(1, new int[] { 0, 1, 3, 0 });
		assertEquals(game.getBoard().getPiece(0, 0), null);
		assertEquals(game.getBoard().getPiece(4, 0), null);
		assertEquals(game.getBoard().getPiece(0, 1).getCardBase().getId(),
				AncientPillarPieceEffect.ID);
		assertEquals(game.getBoard().getPiece(3, 0).getCardBase().getId(),
				AncientPillarPieceEffect.ID);
		assertEquals(instructionQueue.size(), 0);
	}
}
