package board;

public class PieceSnapshot {
	public PieceSnapshot(Piece p) {
		this.p = p;
		this.attack = p.getAttack();
		this.defense = p.getDefense();
		this.x = p.getX();
		this.y = p.getY();
	}

	private Piece p;

	public Piece getPiece() {
		return p;
	}

	private int attack;

	public int getAttack() {
		return attack;
	}

	private int defense;

	public int getDefense() {
		return defense;
	}

	private int x;

	public int getX() {
		return x;
	}

	private int y;

	public int getY() {
		return y;
	}
}
