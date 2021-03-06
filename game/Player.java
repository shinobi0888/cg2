package game;

import java.util.ArrayList;
import java.util.Collections;

import board.Piece;
import buffs.PlayerBuff;
import card.Card;
import card.CardBase;
import card.HexCardBase;
import card.PieceCardBase;
import card.PieceCardBase.CardClass;

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

	public int getPiecePlays() {
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

	public void setHealth(int amount) {
		int oldHealth = health;
		health = amount;
		for (PlayerListener listener : listeners) {
			listener.health(oldHealth, health);
		}
	}

	public void raiseHealth(int amount) {
		int oldHealth = health;
		amount = Math.min(amount, MAX_HEALTH - health);
		health += amount;
		for (PlayerListener listener : listeners) {
			listener.onHeal(amount);
			listener.health(oldHealth, health);
		}
	}

	public int getHealth() {
		return health;
	}

	public boolean canAscendPieces() {
		for (PlayerBuff b : buffs) {
			if (b.isPreventAscends()) {
				return false;
			}
		}
		return true;
	}

	public void loadDeck(String deckList) {
		deck = new ArrayList<Card>();
		for (String cardNum : deckList.split(",")) {
			deck.add(new Card(CardBase.getCard(Integer.parseInt(cardNum)), this));
		}
	}

	public void resetCards() {
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

	public void cardsToTopOfDeck(int id, int quantity) {
		ArrayList<Card> cards = new ArrayList<Card>();
		for (Card c : deck) {
			if (c.getCardBase().getId() == id) {
				cards.add(c);
			}
			if (cards.size() >= quantity) {
				break;
			}
		}
		for (int i = 0; i < quantity; i++) {
			if (cards.size() == 0) {
				break;
			}
			deck.remove(cards.get(i));
			deck.add(0, cards.get(i));
		}
	}

	public int getCardInDeckCount(int id) {
		int result = 0;
		for (Card c : deck) {
			if (c.getCardBase().getId() == id) {
				result += 1;
			}
		}
		return result;
	}

	public ArrayList<Card> getCardsInDeckOfClass(CardClass cardClass) {
		ArrayList<Card> result = new ArrayList<Card>();
		for (Card c : deck) {
			if (c.getCardBase() instanceof PieceCardBase
					&& ((PieceCardBase) c.getCardBase()).getCardClass().equals(cardClass)
					&& !result.contains(c.getCardBase().getId())) {
				result.add(c);
			}
		}
		return result;
	}

	public ArrayList<Integer> getCardsIdsInDeckOfClass(CardClass cardClass) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (Card c : deck) {
			if (c.getCardBase() instanceof PieceCardBase
					&& ((PieceCardBase) c.getCardBase()).getCardClass().equals(cardClass)
					&& !result.contains(c.getCardBase().getId())) {
				result.add(c.getCardBase().getId());
			}
		}
		return result;
	}

	public ArrayList<Card> getHexesInDeck() {
		ArrayList<Card> result = new ArrayList<Card>();
		for (Card c : deck) {
			if (c.getCardBase() instanceof HexCardBase) {
				result.add(c);
			}
		}
		return result;
	}

	public Card findInGrave(int id) {
		for (Card c : grave) {
			if (c.getCardBase().getId() == id) {
				return c;
			}
		}
		return null;
	}

	public Card findInDeck(int id) {
		for (Card c : deck) {
			if (c.getCardBase().getId() == id) {
				return c;
			}
		}
		return null;
	}

	public void sendFromGraveToDeck(Card c, int positionInDeck) {
		if (grave.remove(c)) {
			deck.add(positionInDeck, c);
			for (PlayerListener listener : listeners) {
				listener.onAddFromGraveToDeck(c);
			}
		}
	}

	public void sendFromGraveToHand(Card c) {
		if (grave.remove(c)) {
			hand.add(c);
			for (PlayerListener listener : listeners) {
				listener.onAddFromGraveToHand(c);
			}
		}
	}

	public void addFromDeckToHand(Card c) {
		deck.remove(c);
		hand.add(c);
		for (PlayerListener listener : listeners) {
			listener.onAddFromDeckToHand(c);
		}
	}

	public void discardFromHand(Card c) {
		hand.remove(c);
		grave.add(c);
		for (PlayerListener listener : listeners) {
			listener.onDiscardFromHand(c);
		}
	}

	public void millFromDeck(Card c) {
		deck.remove(c);
		grave.add(c);
		for (PlayerListener listener : listeners) {
			listener.onMillFromDeck(c);
		}
	}

	public void shuffleDeck() {
		Collections.shuffle(deck);
		for (PlayerListener listener : listeners) {
			listener.onShuffle();
		}
	}

	public Card drawCard(Game g) {
		// TODO: handle things like draw card locks
		// Logic for overturning
		if (deck.size() == 0) {
			tryOverturn(g);
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

	public void tryOverturn(Game g) {
		for (Piece p : g.getAllPieces()) {
			if (p.getEffect().isPreventOverturn()) {
				for (PlayerListener listener : listeners) {
					listener.onOverturnPrevented(p.getSourceCard());
				}
				return;
			}
		}
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

	public void returnFromPlayedToHand(Card c) {
		if (played.contains(c)) {
			played.remove(c);
			hand.add(c);
			for (PlayerListener listener : listeners) {
				listener.cardReturnedFromPlayedToHand(c);
			}
		}
	}

	public int getDeckCount() {
		return deck.size();
	}

	public Card getDeckCard(int index) {
		return deck.get(index);
	}

	public void reorderDeck(Card c, int index) {
		deck.remove(c);
		deck.add(index, c);
		for (PlayerListener listener : listeners) {
			listener.onCardRepositionedInDeck(c);
		}
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

	public void playFromDeckToField(Card c) {
		if (!deck.contains(c)) {
			throw new IllegalArgumentException();
		}
		deck.remove(c);
		played.add(c);
		for (PlayerListener listener : listeners) {
			listener.onPlayToField(c);
		}
	}

	public void sendFromHandToDeck(Card c, int index) {
		hand.remove(c);
		deck.add(index, c);
		for (PlayerListener listener : listeners) {
			listener.onCardReturnedToDeckFromHand(c);
		}
	}

	public void returnFromPlayedToDeck(Card c, int pos) {
		if (played.contains(c)) {
			played.remove(c);
			deck.add(pos, c);
			for (PlayerListener listener : listeners) {
				listener.cardReturnedFromPlayedToDeck(c);
			}
		}
	}

	public void moveToTopOfDeck(Card c) {
		deck.remove(c);
		deck.add(0, c);
		for (PlayerListener listener : listeners) {
			listener.onCardToTopOfDeck(c);
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

	public void removeEndOfOwnTurnBuffs() {
		for (int i = 0; i < buffs.size(); i++) {
			if (buffs.get(i).isRemoveAtEndOfOwnTurn()) {
				buffs.remove(i);
				i--;
			}
		}
	}

	public boolean isUnableToPlayPieces() {
		for (PlayerBuff b : buffs) {
			if (b.isPreventFromPlayingPieces()) {
				return true;
			}
		}
		return false;
	}

	/* Random Card */
	public Card getRandomInHand() {
		return hand.size() == 0 ? null : hand.get((int) (Math.random() * hand
				.size()));
	}

	public Card getRandomInDeck() {
		return deck.size() == 0 ? null : deck.get((int) (Math.random() * deck
				.size()));
	}

	public Card getRandomInGrave() {
		return grave.size() == 0 ? null : grave.get((int) (Math.random() * grave
				.size()));
	}

	public interface PlayerListener {
		public void cardDrawn(Card card);

		public void health(int oldHealth, int newHealth);

		public void onDamage(int amount);

		public void onHeal(int amount);

		public void cardRemovedFromPlayed(Card card);

		public void cardReturnedFromPlayedToHand(Card card);

		public void cardReturnedFromPlayedToDeck(Card card);

		public void onDiscardFromHand(Card card);

		public void onMillFromDeck(Card card);

		public void overturned(int numCards);

		public void onDamageFromNoDraw();

		public void onPlayToField(Card card);

		public void onAddFromDeckToHand(Card card);

		public void onAddFromGraveToHand(Card card);

		public void onAddFromGraveToDeck(Card card);

		public void onShuffle();

		public void onCardReturnedToDeckFromHand(Card card);

		public void onOverturnPrevented(Card reason);

		public void onCardToTopOfDeck(Card c);

		public void onCardRepositionedInDeck(Card c);

		// For hexes and stuff
		public void cardSentToGrave(Card card);
	}
}
