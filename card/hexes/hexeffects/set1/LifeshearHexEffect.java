package card.hexes.hexeffects.set1;

import game.Game;
import game.Player;
import board.Piece;
import buffs.PieceBuff;
import card.Card;
import card.PieceCardBase;
import card.hexes.effects.HexEffect;

public class LifeshearHexEffect implements HexEffect {
	public static final int ID = 78;

	public void simulateEffect(Game g, Player owningPlayer, Card source) {
		Piece target = g.getIface().requestBoardPiece(
				"Select a piece to give buff to:", g.getAllPieces());
		g.getHexEffector().givePieceBuff(target, new LifeshearBuff(source, target));
	}

	public boolean canActivateEffect(Game g, Player owningPlayer, Card source) {
		return g.getAllPieces().size() > 0;
	}

	public class LifeshearBuff extends PieceBuff {
		public LifeshearBuff(Card source, Piece target) {
			this.source = source;
			this.target = target;
			this.isRemoveAtEndOfTurn = true;
		}

		public int getAttackBuff(Game g) {
			int numPieces = 0;
			for (int i = 0; i < g.turnPlayer().getGraveCount(); i++) {
				if (g.turnPlayer().getGraveCard(i).getCardBase() instanceof PieceCardBase) {
					numPieces++;
				}
			}
			for (int i = 0; i < g.offTurnPlayer().getGraveCount(); i++) {
				if (g.offTurnPlayer().getGraveCard(i).getCardBase() instanceof PieceCardBase) {
					numPieces++;
				}
			}
			return numPieces;
		}
	}
}
