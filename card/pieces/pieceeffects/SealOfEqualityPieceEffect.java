package card.pieces.pieceeffects;

import game.Game;
import game.Player;
import card.pieces.effects.EmptyPieceEffect;

public class SealOfEqualityPieceEffect extends EmptyPieceEffect {
	public static final int ID = 35;

	public SealOfEqualityPieceEffect() {
		this.hasPreventStartOfTurnDraw = true;
	}

	public boolean conditionPreventStartOfTurnDraw(Game g, Player drawing) {
		int playersPieces = g.getBoard().find(drawing, null).size();
		int enemysPieces = g.getBoard().find(g.getEnemy(drawing), null).size();
		return playersPieces > enemysPieces;
	}

}
