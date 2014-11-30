package card.hexes.cardeffects;

import game.Game;
import game.Player;

import java.util.ArrayList;

import board.Piece;
import card.Card;
import card.PieceCardBase.CardClass;
import card.hexes.effects.HexEffect;

public class PillarTransfigurationHexEffect implements HexEffect {
	public static final int ID = 8;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		ArrayList<Piece> enemyTargets = new ArrayList<Piece>();
		for (Piece enemyP : g.getBoard().getPlayersPieces(g.getEnemy(owningPlayer))) {
			if (enemyP.getDefense() <= 5) {
				enemyTargets.add(enemyP);
			}
		}
		Piece target = g.getIface().requestBoardPiece(
				"Select an enemy piece to destroy:", enemyTargets);
		int x = target.getX();
		int y = target.getY();
		g.simulateDestroy(target);
		ArrayList<Piece> playerPillars = g.getBoard().find(owningPlayer,
				CardClass.PILLAR);
		if (playerPillars.size() != 0 && g.getBoard().getPiece(x, y) == null) {
			Piece targetPillar = g.getIface().requestBoardPiece(
					"Select an ally [Pillar] to shift:", playerPillars);
			g.simulateShift(targetPillar, x, y);
		}
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		for (Piece enemyP : g.getBoard().getPlayersPieces(g.getEnemy(owningPlayer))) {
			if (enemyP.getDefense() <= 5) {
				return true;
			}
		}
		return false;
	}
}
