package game;

import card.Card;
import board.Piece;
import buffs.PieceBuff;
import buffs.PlayerBuff;

public class Effector {
	protected Game g;

	public Effector(Game g) {
		this.g = g;
	}

	public void revealCard(Player p, Card c) {
		g.getIface().revealCard(p, c);
	}

	public void givePieceBuff(Piece p, PieceBuff b) {
		p.addBuff(b);
		g.getIface().pieceGainedBuff(p, b);
	}

	public void givePlayerBuff(Player p, PlayerBuff b) {
		p.addBuff(b);
		g.getIface().playerGainedBuff(p, b);
	}

	public void removePieceBuff(Piece p, PieceBuff b) {
		p.removeBuff(b);
		g.getIface().pieceLostBuff(p, b);
	}

	public void removePlayerBuff(Player p, PlayerBuff b) {
		p.removeBuff(b);
		g.getIface().playerLostBuff(p, b);
	}

	public void givePlays(Player p, int count) {
		for (int i = 0; i < count; i++) {
			p.incrPiecePlays();
		}
	}

	public void mill(Player p, int index) {
		p.millFromDeck(p.getDeckCard(index));
	}

	public void discard(Player p, int index) {
		p.discardFromHand(p.getHandCard(index));
	}

	public void discard(Player p, Card c) {
		p.discardFromHand(c);
	}

	public void playFromDeck(Player p, Card c, int x, int y) {
		g.turnPlayer().playFromDeckToField(c);
		Piece newPiece = new Piece(c, g);
		g.getBoard().playNewPiece(newPiece, x, y);
		g.getBoard().calculateAllAllowedActions(g.turnPlayer());
	}

	public void returnToHand(Player p, Piece piece) {
		g.getBoard().removePiece(piece.getX(), piece.getY());
		piece.getOwner().returnFromPlayedToHand(piece.getSourceCard());
	}

	public void reorderDeck(Player p, Card c, int index) {
		p.reorderDeck(c, index);
	}

	public void sendFromGraveToDeck(Player p, Card c, int index) {
		p.sendFromGraveToDeck(c, index);
	}

	public void sendFromHandToDeck(Player p, Card c, int index) {
		p.sendFromHandToDeck(c, index);
	}

	public void sendFromHandToBottomOfDeck(Player p, Card c) {
		sendFromHandToDeck(p, c, p.getDeckCount());
	}

	public void sendFromHandToTopOfDeck(Player p, Card c) {
		sendFromHandToDeck(p, c, 0);
	}

	public void sendFromBoardToDeck(Player p, Piece piece, int index) {
		g.getBoard().removePiece(piece.getX(), piece.getY());
		piece.getOwner().returnFromPlayedToDeck(piece.getSourceCard(), 0);
	}

	public void sendFromBoardToBottomOfDeck(Player p, Piece piece) {
		sendFromBoardToDeck(p, piece, p.getDeckCount());
	}

	public void sendFromBoardToTopOfDeck(Player p, Piece piece) {
		sendFromBoardToDeck(p, piece, 0);
	}

	public void sendGraveToDeck(Player p) {
		while (p.getGraveCount() > 0) {
			p.sendFromGraveToDeck(p.getGraveCard(0), 0);
		}
	}

	public void sendFromGraveToHand(Player p, int id) {
		Card c = p.findInGrave(id);
		sendFromGraveToHand(p, c);
	}

	public void sendFromGraveToHand(Player p, Card c) {
		p.sendFromGraveToHand(c);
	}

	public void addFromDeckToHand(Player p, int cardId) {
		p.addFromDeckToHand(p.findInDeck(cardId));
	}

	public void swapPieces(Piece p1, Piece p2) {
		g.getBoard().swapPiece(p1, p2);
	}

	public void shiftPiece(Piece p, int x, int y) {
		g.getBoard().movePiece(p, x, y);
	}

	public void shuffle(Player p) {
		p.shuffleDeck();
	}
}
