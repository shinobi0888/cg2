package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;

import java.util.ArrayList;

import board.Piece;
import card.Card;
import card.hexes.effects.HexEffect;

public class IgnobleSacrificeHexEffect implements HexEffect {
	public static final int ID = 85;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		ArrayList<Piece> endZonePieces = new ArrayList<Piece>();
		for (Piece p : g.getBoard().getPiecesInEndZone(owningPlayer)) {
			if (p.getOwner().equals(owningPlayer)) {
				endZonePieces.add(p);
			}
		}
		Piece target1;
		target1 = endZonePieces.size() != 1 ? g.getIface().requestBoardPiece(
				"Select an ally piece in the end zone:", endZonePieces) : endZonePieces
				.get(0);
		Piece target2;
		ArrayList<Piece> otherPieces = g.getBoard().getPlayersPieces(owningPlayer);
		otherPieces.remove(target1);
		target2 = otherPieces.size() != 1 ? g.getIface().requestBoardPiece(
				"Select another ally piece:", otherPieces) : otherPieces.get(0);
		g.getHexEffector().swapPieces(target1, target2);
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		for (Piece p : g.getBoard().getPiecesInEndZone(owningPlayer)) {
			if (p.getOwner().equals(owningPlayer)) {
				return g.getBoard().getPlayersPieces(owningPlayer).size() > 2;
			}
		}
		return false;
	}

}
