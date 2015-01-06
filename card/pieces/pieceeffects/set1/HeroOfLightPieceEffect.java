package card.pieces.pieceeffects.set1;

import game.Game;

import java.util.ArrayList;

import board.Piece;
import board.PieceSnapshot;
import buffs.PieceBuff;
import card.Card;
import card.pieces.effects.EmptyPieceEffect;

public class HeroOfLightPieceEffect extends EmptyPieceEffect {
	public static final int ID = 96;

	public HeroOfLightPieceEffect() {
		hasOnReleasePlay = true;
	}

	public boolean conditionOnReleasePlay(Game g, Piece playedPiece,
			ArrayList<PieceSnapshot> released) {
		return released.size() == 1;
	}

	public void effectOnReleasePlay(Game g, Piece playedPiece,
			ArrayList<PieceSnapshot> released) {
		g.getPieceEffector().givePieceBuff(
				playedPiece,
				new HeroOfLightBuff(playedPiece, playedPiece.getSourceCard(), released
						.get(0).getAttack(), released.get(0).getDefense()));
	}

	public class HeroOfLightBuff extends PieceBuff {
		private int attack, defense;

		public HeroOfLightBuff(Piece target, Card source, int attack, int defense) {
			this.target = target;
			this.source = source;
			this.attack = attack;
			this.defense = defense;
			this.isRemoveAtEndOfTurn = false;
		}

		public int getAttackBuff(Game g) {
			return attack;
		}

		public int getDefenseBuff(Game g) {
			return defense;
		}
	}
}
