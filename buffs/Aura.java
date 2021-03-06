package buffs;

import game.Game;
import board.Piece;

public abstract class Aura {
	/**
	 * Auras need to be reapplied on any of these events: Piece enters field Piece
	 * removed from field Piece changes position
	 */
	protected Piece source;
	protected boolean removeOnLeaveField = true;

	public boolean isRemoveOnLeaveField() {
		return removeOnLeaveField;
	}

	public abstract boolean shouldApplyAura(Game g, Piece p);

	public abstract PieceBuff getNewBuff(Game g, Piece p);

	public Piece getSource() {
		return source;
	}

	public void applyBuffs(Game g) {
		for (Piece p : g.getAllPieces()) {
			PieceBuff foundBuff = p.findBuff(this);
			if (foundBuff == null && shouldApplyAura(g, p)) {
				g.getPieceEffector().givePieceBuff(p, getNewBuff(g, p));
			} else if (foundBuff != null && !shouldApplyAura(g, p)) {
				g.getPieceEffector().removePieceBuff(p, foundBuff);
			}
		}
	}
}
