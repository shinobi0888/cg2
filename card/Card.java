package card;

import game.Player;

public class Card {
	private CardBase base;
	private Player owner;

	public Card(CardBase base, Player owner) {
		this.base = base;
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}

	public CardBase getCardBase() {
		return base;
	}

	public String toString() {
		return base.name;
	}
}
