package card.hexes.hexeffects.debug;

import game.Game;
import game.Player;

import java.util.ArrayList;

import board.Piece;
import card.Card;
import card.hexes.effects.HexEffect;

public class SmiteHexEffect implements HexEffect {
	public static final int ID = 1005;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		ArrayList<Piece> targets = g.getBoard().getAllPieces();
		Piece target = g.getIface().requestBoardPiece("Select a piece to destroy:",
				targets);
		g.simulateDestroy(target);
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return g.getBoard().getAllPieces().size() > 0;
	}
}