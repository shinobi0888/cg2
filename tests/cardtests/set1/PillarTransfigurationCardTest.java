package tests.cardtests.set1;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.hexes.hexeffects.set1.PillarTransfigurationHexEffect;
import card.pieces.pieceeffects.set1.AncientPillarPieceEffect;

public class PillarTransfigurationCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { 1001, 2, AncientPillarPieceEffect.ID, 2,
				PillarTransfigurationHexEffect.ID, 20 });
		setDeck(players[1], new int[] { 1001, 2, AncientPillarPieceEffect.ID, 2,
				PillarTransfigurationHexEffect.ID, 20 });
	}

	@Test
	public void test() {
		// Player 1 plays a pawn, allowing player 2 to transfigure it
		// No pillars exist so the secondary effect is not proceed.
		game.beginTurn();
		actionPlay(0, 4, 0);
		actionCycleTurn();
		actionPlay(4, new int[] { 4, 0 });
		assertEquals(game.getBoard().getPiece(4, 0), null);
		// Player 2 plays a pawn to destroy
		actionPlay(0, 4, 8);
		actionCycleTurn();
		// Player 1 plays a pillar and swaps it using transfigure
		actionPlay(1, 4, 0);
		actionPlay(4, new int[] { 4, 8, 4, 0 });
		assertEquals(game.getBoard().getPiece(4, 8).getCardBase().getId(),
				AncientPillarPieceEffect.ID);
		assertEquals(game.getBoard().getPiece(4, 8).getOwner(), game.turnPlayer());
		assertEquals(game.getBoard().getPiece(4, 0), null);
	}
}