package tests.cardtests.extended;

import static org.junit.Assert.*;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.CardBase;
import card.PieceCardBase;
import card.hexes.cardeffects.set1.StrangePotionHexEffect;
import card.pieces.pieceeffects.set1.AncientPillarPieceEffect;

public class StrangePotionCardTest extends BasicCardTest {

	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { StrangePotionHexEffect.ID, 3,
				AncientPillarPieceEffect.ID, 21 });
		setDeck(players[1], new int[] { StrangePotionHexEffect.ID, 3,
				AncientPillarPieceEffect.ID, 21 });
	}

	@Test
	public void test() {
		game.beginTurn();
		actionPlay(3, 4, 0);
		PieceCardBase b = ((PieceCardBase) CardBase
				.getCard(AncientPillarPieceEffect.ID));
		actionCycleTurn();
		actionPlay(3, 4, 8);
		actionCycleTurn();
		actionPlay(3, 5, 0);
		// Player 1's pillar pair buffs each other
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(),
				b.getDefense() + 4);
		assertEquals(game.getBoard().getPiece(5, 0).getDefense(),
				b.getDefense() + 4);
		assertEquals(game.getBoard().getPiece(4, 8).getDefense(),
				b.getDefense() + 2);
		actionPlay(0);
		// Buffs negated
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(),
				b.getDefense() - 4);
		assertEquals(game.getBoard().getPiece(5, 0).getDefense(),
				b.getDefense() - 4);
		assertEquals(game.getBoard().getPiece(4, 8).getDefense(),
				b.getDefense() - 2);
		actionCycleTurn();
		// Ensure Strange Potion was undone
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(),
				b.getDefense() + 4);
		assertEquals(game.getBoard().getPiece(5, 0).getDefense(),
				b.getDefense() + 4);
		assertEquals(game.getBoard().getPiece(4, 8).getDefense(),
				b.getDefense() + 2);
	}

}
