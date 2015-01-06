package game;

import board.Piece;

public class PieceEffector extends Effector {

	public PieceEffector(Game g) {
		super(g);
	}

	public void damage(Player p, int amount) {
		g.simulateAnyDamage(p, amount);
	}

	public void destroy(Piece p) {
		g.simulateDestroy(p);
	}

	public void setHealth(Player p, int amount) {
		p.setHealth(amount);
	}

	public void heal(Player p, int amount) {
		g.simulateAnyHeal(p, amount);
	}

	public void draw(Player p) {
		p.drawCard(g);
	}
}
