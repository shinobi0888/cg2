package tests.cardtests.extended;

import static org.junit.Assert.assertEquals;
import game.Player;

import org.junit.Test;

import tests.cardtests.BasicCardTest;
import card.CardBase;
import card.PieceCardBase;
import card.pieces.pieceeffects.AgileFairyPieceEffect;
import card.pieces.pieceeffects.EmeraldSerpentPieceEffect;

public class EmeraldSerpentCardTest extends BasicCardTest {
	public void setPlayers(Player[] players) {
		setDeck(players[0], new int[] { EmeraldSerpentPieceEffect.ID, 1,
				AgileFairyPieceEffect.ID, 23 });
		setDummyDeck(players[1]);
	}

	@Test
	public void test() {
		PieceCardBase b1 = ((PieceCardBase) CardBase
				.getCard(EmeraldSerpentPieceEffect.ID));
		PieceCardBase b2 = ((PieceCardBase) CardBase
				.getCard(AgileFairyPieceEffect.ID));
		game.beginTurn();
		// Play all agile fairies in hand
		actionPlay(1, 1, 0);
		actionPlay(1, 2, 0);
		actionPlay(1, 3, 0);
		actionPlay(1, 4, 0);
		actionPlay(1, 5, 0);
		actionPlay(1, 6, 0);
		// Then play the serpent
		actionPlay(0, new int[] { 4, 0, 5, 0 });
		assertEquals(game.getBoard().getPiece(4, 0).getCardBase().getId(),
				EmeraldSerpentPieceEffect.ID);
		assertEquals(game.getBoard().getPiece(4, 0).getDefense(), b1.getDefense() + 2);
		assertEquals(game.getBoard().getPiece(6, 0).getDefense(), b2.getDefense() + 2);
		assertEquals(game.getBoard().getPiece(3, 0).getDefense(), b2.getDefense() + 2);
		assertEquals(game.getBoard().getPiece(2, 0).getDefense(), b2.getDefense() + 2);
		assertEquals(game.getBoard().getPiece(1, 0).getDefense(), b2.getDefense() + 2);
	}

}
