package game;

import java.util.ArrayList;
import java.util.Collections;

import buffs.PlayerBuff;
import card.Card;
import card.CardBase;

public class Player {
	public static final int MAX_HEALTH = 50;
	public static final int DRAW_BURN = 2;

	private int health;
	private ArrayList<Card> deck;
	private ArrayList<Card> played;
	private ArrayList<Card> grave;
	private ArrayList<Card> hand;
	private ArrayList<PlayerBuff> buffs;
	private ArrayList<PlayerListener> listeners;
	private int piecePlays;
	private String name;

	public Player(String name) {
		this.name = name;
		piecePlays = 0;
		health = 0;
		deck = new ArrayList<Card>();
		played = new ArrayList<Card>();
		grave = new ArrayList<Card>();
		hand = new ArrayList<Card>();
		buffs = new ArrayList<PlayerBuff>();
		listeners = new ArrayList<PlayerListener>();
	}

	public String getName() {
		return name;
	}

	public void addListener(PlayerListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public int piecePlays() {
		return piecePlays;
	}

	public void resetPiecePlays() {
		piecePlays = 1;
	}

	public void incrPiecePlays() {
		piecePlays++;
	}

	public void decrPiecePlays() {
		piecePlays--;
	}

	// Losing condition: currently 0 health
	public boolean lost() {
		return health == 0;
	}

	public void resetHealth() {
		int oldHealth = health;
		health = MAX_HEALTH;
		for (PlayerListener listener : listeners) {
			listener.health(oldHealth, health);
		}
	}

	public void lowerHealth(int amount) {
		int oldHealth = health;
		amount = Math.min(amount, health);
		health -= amount;
		for (PlayerListener listener : listeners) {
			listener.onDamage(amount);
			listener.health(oldHealth, health);
		}
	}

	public int getHealth() {
		return health;
	}

	public void loadDeck(String deckList) {
		deck = new ArrayList<Card>();
		for (String cardNum : deckList.split(",")) {
			deck.add(new Card(CardBase.getCard(Integer.parseInt(cardNum)), this));
		}
	}

	public void resetCards() {
		resetHealth();
		for (Card c : played) {
			deck.add(c);
		}
		for (Card c : grave) {
			deck.add(c);
		}
		for (Card c : hand) {
			deck.add(c);
		}
		played.clear();
		grave.clear();
		hand.clear();
		shuffleDeck();
	}

	public void shuffleDeck() {
		Collections.shuffle(deck);
	}

	public Card drawCard() {
		// TODO: handle things like draw card locks
		// Logic for overturning
		if (deck.size() == 0) {
			overturn();
		}
		if (deck.size() == 0) {
			damageOnNoDraw();
			return null;
		}
		Card drawn = deck.remove(0);
		hand.add(drawn);
		for (PlayerListener listener : listeners) {
			listener.cardDrawn(drawn);
		}
		return drawn;
	}

	public void damageOnNoDraw() {
		for (PlayerListener listener : listeners) {
			listener.onDamageFromNoDraw();
		}
		lowerHealth(DRAW_BURN);
	}

	public void overturn() {
		int cardsAdded = grave.size();
		for (Card c : grave) {
			deck.add(c);
		}
		grave.clear();
		shuffleDeck();
		for (PlayerListener listener : listeners) {
			listener.overturned(cardsAdded);
		}
		lowerHealth(health / 2);
	}

	public void removePlayedCard(Card c) {
		if (played.contains(c)) {
			played.remove(c);
			grave.add(c);
			for (PlayerListener listener : listeners) {
				listener.cardRemovedFromPlayed(c);
			}
		}
	}

	public int deckCount() {
		return deck.size();
	}

	public void turnGrave() {
		if (deck.size() == 0 && grave.size() != 0) {
			for (Card c : grave) {
				deck.add(c);
			}
			shuffleDeck();
		}
	}

	public Card getHandCard(int index) {
		return hand.get(index);
	}

	public int getHandCount() {
		return hand.size();
	}

	public Card getGraveCard(int index) {
		return grave.get(index);
	}

	public int getGraveCount() {
		return grave.size();
	}

	public void playFromHandToField(int index) {
		Card c = hand.remove(index);
		played.add(c);
		for (PlayerListener listener : listeners) {
			listener.onPlayToField(c);
		}
	}

	public void addBuff(PlayerBuff b) {
		buffs.add(b);
	}

	public void removeBuff(PlayerBuff b) {
		buffs.remove(b);
	}

	public void removeBuff(Card c) {
		for (int i = 0; i < buffs.size(); i++) {
			if (buffs.get(i).getSource().equals(c)) {
				buffs.remove(i);
				i--;
			}
		}
	}

	public ArrayList<PlayerBuff> getBuffs() {
		return buffs;
	}

	public void removeEndOfTurnBuffs() {
		for (int i = 0; i < buffs.size(); i++) {
			if (buffs.get(i).isRemoveAtEndOfTurn()) {
				buffs.remove(i);
				i--;
			}
		}
	}

	public interface PlayerListener {
		public void cardDrawn(Card card);

		public void health(int oldHealth, int newHealth);

		public void onDamage(int amount);

		public void cardRemovedFromPlayed(Card card);

		public void overturned(int numCards);

		public void onDamageFromNoDraw();

		public void onPlayToField(Card card);

		// For hexes and stuff
		public void cardSentToGrave(Card card);
	}
}
