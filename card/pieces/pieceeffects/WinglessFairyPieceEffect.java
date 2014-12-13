package card.pieces.pieceeffects;

import game.Game;
import board.Piece;
import card.pieces.effects.EmptyPieceEffect;

public class WinglessFairyPieceEffect extends EmptyPieceEffect {
	public static final int ID = 15;
	private static final int BURN_DAMAGE = 4;
	private static final int EXTRA_PLAYS = 1;

	public WinglessFairyPieceEffect() {
		this.hasOnPlay = true;
	}

	public boolean conditionOnPlay(Game g, Piece playedPiece) {
		return true;
	}

	public void effectOnPlay(Game g, Piece playedPiece) {
		g.simulateEffectDamage(playedPiece.getOwner(), BURN_DAMAGE);
		if (g.getBoard().find(playedPiece.getOwner(), OberonPieceEffect.ID).size() > 0
				|| g.getBoard().find(playedPiece.getOwner(), TitaniaPieceEffect.ID)
						.size() > 0) {
			g.simulateEffectDraw(playedPiece.getOwner());
			g.simulateGivePlayerPiecePlays(playedPiece.getOwner(), EXTRA_PLAYS);
		}
	}
}
