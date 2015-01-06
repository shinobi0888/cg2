package tests.cardtests.set1;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.pieces.pieceeffects.set1.AgileFairyPieceEffect;
import card.pieces.pieceeffects.set1.MokshaPriestPieceEffect;

public class MokshaPriestCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { MokshaPriestPieceEffect.ID, 1,
				AgileFairyPieceEffect.ID, 23 });
		setDummyDeck(players[1]);
	}

	@Test
	public void test() {
		// Play a a moksha priest and test overturn block
		game.beginTurn();
		actionPlay(1, 3, 0);
		actionPlay(1, 4, 0);
		actionPlay(1, 5, 0);
		actionCycleTurn();
		// Mill some cards
		game.getPieceEffector().discard(game.turnPlayer(),
				game.turnPlayer().getHandCard(0));
		game.getPieceEffector().discard(game.turnPlayer(),
				game.turnPlayer().getHandCard(0));
		actionCycleTurn();
		game.movePiece(4, 0, 4, 1);
		game.movePiece(5, 0, 5, 1);
		actionCycleTurn();
		actionCycleTurn();
		game.movePiece(5, 1, 5, 2);
		actionPlay(0, 4, 1);
		assertEquals(game.getBoard().getPiece(4, 1).getCardBase().getId(),
				MokshaPriestPieceEffect.ID);
		// Check that overturn is prevented for self
		while (game.turnPlayer().getDeckCount() != 0) {
			game.turnPlayer().drawCard(game);
		}
		// Draw once more and suffer burn damage instead of overturn
		game.turnPlayer().drawCard(game);
		assertEquals(game.turnPlayer().getHealth(), Player.MAX_HEALTH
				- Player.DRAW_BURN);
		actionCycleTurn();
		while (game.turnPlayer().getDeckCount() != 0) {
			game.turnPlayer().drawCard(game);
		}
		// Draw once more and suffer burn damage instead of overturn
		game.turnPlayer().drawCard(game);
		assertEquals(game.turnPlayer().getHealth(), Player.MAX_HEALTH
				- Player.DRAW_BURN);
		// Destroy moksha priest
		game.simulateDestroy(game.getBoard().getPiece(4, 1));
		// On next draw, suffer from overturn
		actionCycleTurn();
		assertEquals(game.turnPlayer().getHealth(),
				(Player.MAX_HEALTH - Player.DRAW_BURN) / 2);
		actionCycleTurn();
		assertEquals(game.turnPlayer().getHealth(),
				(Player.MAX_HEALTH - Player.DRAW_BURN) / 2);
	}
}
