package game;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import board.Piece;
import buffs.PieceBuff;
import buffs.PlayerBuff;
import card.Card;
import card.CardBase;

public class GameSimulatorText {
	public static void main(String[] args) throws IOException {
		Player[] players = new Player[2];
		players[0] = new Player("Player 1");
		players[1] = new Player("Player 2");
		players[0].addListener(new PlayerReporter(players[0]));
		players[1].addListener(new PlayerReporter(players[1]));
		players[0].loadDeck(dummyLoadFakeDeck());
		players[1].loadDeck(dummyLoadFakeDeck());
		BufferedReader inReader = new BufferedReader(new InputStreamReader(
				System.in));
		Game game = new Game(players[0], players[1], new GameReporter(inReader));
		game.startNewGame();
		while (!game.isGameOver()) {
			game.beginTurn();
			System.out.println("It is " + game.turnPlayer().getName() + "'s turn.");
			String cmd = inReader.readLine();
			while (!cmd.equals("end")) {
				String[] cmdPieces = cmd.split(" ");
				if (cmdPieces[0].equals("play") && cmdPieces.length == 2) {
					try {
						int index = Integer.parseInt(cmdPieces[1]);
						if (!game.playIndex(index)) {
							System.out.println("Cannot play that card.");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (cmdPieces[0].equals("move") && cmdPieces.length == 3) {
					try {
						Point piecePoint = parsePoint(cmdPieces[1]);
						Point targetPoint = parsePoint(cmdPieces[2]);
						if (piecePoint == null
								|| targetPoint == null
								|| !game.movePiece(piecePoint.x, piecePoint.y, targetPoint.x,
										targetPoint.y)) {
							System.out.println("Cannot move piece.");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (cmdPieces[0].equals("attack") && cmdPieces.length == 3) {
					try {
						Point piecePoint = parsePoint(cmdPieces[1]);
						Point targetPoint = parsePoint(cmdPieces[2]);
						if (piecePoint == null
								|| targetPoint == null
								|| !game.attackPiece(piecePoint.x, piecePoint.y, targetPoint.x,
										targetPoint.y)) {
							System.out.println("Cannot attack as specified.");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (cmdPieces[0].equals("ascend") && cmdPieces.length == 2) {
					Point piecePoint = parsePoint(cmdPieces[1]);
					if (piecePoint == null
							|| !game.ascendPiece(piecePoint.x, piecePoint.y)) {
						System.out.println("Cannot ascend with given piece.");
					}
				} else if (cmdPieces[0].equals("active") && cmdPieces.length == 2) {
					Point piecePoint = parsePoint(cmdPieces[1]);
					if (piecePoint == null
							|| !game.activatePiece(piecePoint.x, piecePoint.y)) {
						System.out
								.println("Cannot activate the effect of the provided piece.");
					}
				} else if (cmd.equals("hand")) {
					String handOutput = "";
					if (game.turnPlayer().getHandCount() == 0) {
						handOutput = "Empty hand";
					} else {
						for (int i = 0; i < game.turnPlayer().getHandCount(); i++) {
							handOutput += i + ": "
									+ game.turnPlayer().getHandCard(i).getCardBase() + "\t";
						}
					}
					System.out.println(handOutput);
				} else if (cmd.equals("field")) {
					String fieldOutput = "";
					ArrayList<Piece> allPieces = game.getAllPieces();
					if (allPieces.size() == 0) {
						fieldOutput = "Empty field";
					} else {
						for (Piece p : allPieces) {
							fieldOutput += getBoardPieceDesc(p) + "\n";
						}
					}
					System.out.println(fieldOutput);
				} else if (cmd.equals("deck")) {
					System.out.println(game.turnPlayer().getName() + "'s deck has "
							+ game.turnPlayer().getDeckCount() + " cards.");
				} else if (cmd.equals("grave")) {
					String graveOutput = "";
					if (game.turnPlayer().getGraveCount() == 0) {
						graveOutput = "Empty grave";
					} else {
						for (int i = 0; i < game.turnPlayer().getGraveCount(); i++) {
							graveOutput += i + ": "
									+ game.turnPlayer().getGraveCard(i).getCardBase().getName()
									+ "\t";
						}
					}
					System.out.println(graveOutput);
				} else if (cmdPieces[0].equals("about") && cmdPieces.length == 2) {
					try {
						int cardId = Integer.parseInt(cmdPieces[1]);
						CardBase requestedCard = CardBase.getCard(cardId);
						if (requestedCard != null) {
							System.out.println(requestedCard.getDetails());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (!game.isGameOver()) {
					cmd = inReader.readLine();
				} else {
					break;
				}
			}
			game.endTurn();
		}
	}

	static String getBoardPieceDesc(Piece p) {
		return "[" + p.getX() + ", " + p.getY() + "]: " + p.getOwner().getName()
				+ "'s " + p.toDetailedString();
	}

	private static String dummyLoadFakeDeck() {
		int[] cards = { 89, 12, 91, 12 };
		String result = "";
		for (int i = 0; i < cards.length; i += 2) {
			for (int j = 0; j < cards[i + 1]; j++) {
				result += (cards[i] + ",");
			}
		}
		return result;
	}

	private static Point parsePoint(String response) {
		String[] pieces = response.split(",");
		if (pieces.length != 2) {
			return null;
		}
		try {
			return new Point(Integer.parseInt(pieces[0].trim()),
					Integer.parseInt(pieces[1].trim()));
		} catch (Exception e) {
			return null;
		}
	}

}

class PlayerReporter implements Player.PlayerListener {
	private Player player;

	public PlayerReporter(Player player) {
		this.player = player;
	}

	public void cardDrawn(Card card) {
		System.out.println(player.getName() + " drew " + card + ".");
	}

	public void health(int oldHealth, int newHealth) {
		System.out.println(player.getName() + "'s health changed from " + oldHealth
				+ " to " + newHealth + ".");
	}

	public void cardRemovedFromPlayed(Card card) {
		System.out.println(card.getCardBase().getName() + " is no longer in play.");
	}

	public void cardSentToGrave(Card card) {
		System.out
				.println(card.getCardBase().getName() + " was sent to the grave.");
	}

	public void onDamage(int amount) {
		System.out.println(player.getName() + " was damaged for " + amount + ".");
	}

	public void overturned(int numCards) {
		System.out.println(player.getName() + " overturned their grave and added "
				+ numCards + " to the deck.");
	}

	public void onDamageFromNoDraw() {
		System.out.println(player.getName() + " was unable to draw!");
	}

	public void onPlayToField(Card card) {
		System.out.println(player.getName() + " played "
				+ card.getCardBase().getName() + ".");
	}

	public void onAddFromDeckToHand(Card card) {
		System.out.println(player.getName() + " added card "
				+ card.getCardBase().getName() + " from the deck to the hand.");
	}

	public void onShuffle() {
		System.out.println(player.getName() + "'s deck was shuffled.");
	}

	public void onHeal(int amount) {
		System.out.println(player.getName() + " was healed for " + amount + ".");
	}

	public void onCardReturnedToDeckFromHand(Card card) {
		System.out.println(player.getName() + "'s " + card.getCardBase().getName()
				+ " was added back to the deck.");
	}

	public void onDiscardFromHand(Card card) {
		System.out.println(player.getName() + "'s " + card.getCardBase().getName()
				+ " was sent to the grave.");
	}

	public void onOverturnPrevented(Card reason) {
		System.out.println(player.getName()
				+ "'s attempt to overturn was prevented by "
				+ reason.getCardBase().getName() + ".");
	}

	public void onMillFromDeck(Card card) {
		System.out.println(player.getName() + "'s " + card.getCardBase().getName()
				+ " was sent from the deck to the grave.");
	}

	public void cardReturnedFromPlayedToHand(Card card) {
		System.out.println(player.getName() + "'s " + card.getCardBase().getName()
				+ " was sent from the board to the hand.");
	}

	public void onCardToTopOfDeck(Card c) {
		System.out.println(player.getName() + "'s " + c.getCardBase().getName()
				+ " was moved to the top of the deck.");
	}

	public void cardReturnedFromPlayedToDeck(Card card) {
		System.out.println(player.getName() + "'s " + card.getCardBase().getName()
				+ " was sent to the bottom of the deck.");
	}
}

class GameReporter implements Game.GameInterface {
	private BufferedReader in;
	private static final Point POINT_CANCEL = new Point(-1, -1);

	public GameReporter(BufferedReader in) {
		this.in = in;
	}

	public void piecePlayed(Piece p, int x, int y) {
		System.out.println(p.getOwner().getName() + " played " + p.getName()
				+ " on location [" + x + ", " + y + "].");
	}

	public Point requestBoardPos(String prompt, ArrayList<Point> valid) {
		try {
			System.out.println(prompt);
			String valids = "Valid points: ";
			for (Point p : valid) {
				valids += "(" + p.x + ", " + p.y + ") ";
			}
			System.out.println(valids.trim());
			String response = in.readLine();
			Point answer = parsePoint(response);
			while ((answer == null || !valid.contains(answer))
					&& answer != POINT_CANCEL) {
				System.out.println("Invalid input.");
				System.out.println(prompt);
				response = in.readLine();
				answer = parsePoint(response);
			}
			return answer.equals(POINT_CANCEL) ? null : answer;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Point parsePoint(String response) {
		if (response.equals("cancel")) {
			return POINT_CANCEL;
		}
		String[] pieces = response.split(",");
		if (pieces.length != 2) {
			return null;
		}
		try {
			return new Point(Integer.parseInt(pieces[0].trim()),
					Integer.parseInt(pieces[1].trim()));
		} catch (Exception e) {
			return null;
		}
	}

	public Piece requestBoardPiece(String prompt, ArrayList<Piece> valid) {
		try {
			System.out.println(prompt);
			System.out.println("Options:");
			for (int i = 0; i < valid.size(); i++) {
				System.out.println((i + 1) + ": "
						+ GameSimulatorText.getBoardPieceDesc(valid.get(i)));
			}
			String response = in.readLine();
			int answer = Integer.parseInt(response);
			while (answer < 1 || answer > valid.size()) {
				System.out.println("Invalid input.");
				System.out.println(prompt);
				response = in.readLine();
				answer = Integer.parseInt(response);
			}
			return valid.get(answer - 1);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void pieceMoved(Piece target, int oldX, int oldY, int newX, int newY) {
		System.out.println(target.getName() + " moved from [" + oldX + ", " + oldY
				+ "] to [" + newX + ", " + newY + "].");
	}

	public void pieceAttacked(Piece attacker, Piece target) {
		System.out.println(attacker.getOwner().getName() + "'s "
				+ attacker.getName() + " attacked " + target.getOwner().getName()
				+ "'s " + target.getName() + ".");
	}

	public void pieceDestroyed(Piece target, int x, int y) {
		System.out.println(target.getOwner().getName() + "'s " + target.getName()
				+ " at [" + x + ", " + y + "] was destroyed.");
	}

	public void pieceAscended(Piece target) {
		System.out.println(target.getName() + " has ascended.");
	}

	public void pieceForceAscended(Piece target) {
		System.out.println(target.getName() + " has forcibly ascended.");
	}

	public void playerLost(Player p) {
		System.out.println(p.getName() + " has lost the game.");
	}

	public void pieceReleased(Piece target) {
		System.out.println(target.getName() + " was released.");
	}

	public void attackNothingHappened() {
		System.out
				.println("The attack was evenly matched. Neither piece was destroyed.");
	}

	public void pieceRemoved(Piece p, int oldX, int oldY) {
		System.out.println(p.getName() + " was removed from the board.");
	}

	public void revealCard(Player p, Card c) {
		System.out.println(p.getName() + " has revealed card " + c + ".");
	}

	public void pieceEffectActivated(Piece p) {
		System.out.println(p.getOwner().getName() + "'s " + p.getName()
				+ " was activated.");
	}

	public void hexEffectActivated(Card c) {
		System.out.println(c.getOwner().getName() + "'s "
				+ c.getCardBase().getName() + " was activated.");
	}

	public void playerGainedPiecePlays(Player p, int count) {
		System.out.println(p.getName() + " gained " + count
				+ " additional Piece plays this turn.");
	}

	public void playerGainedBuff(Player p, PlayerBuff b) {
		System.out.println(p.getName() + " gained buff "
				+ b.getClass().getSimpleName() + ".");
	}

	public void pieceGainedBuff(Piece p, PieceBuff b) {
		System.out.println(p.getName() + " gained buff "
				+ b.getClass().getSimpleName() + ".");
	}

	public void playerLostBuff(Player p, PlayerBuff b) {
		System.out.println(p.getName() + " lost buff "
				+ b.getClass().getSimpleName() + ".");

	}

	public void pieceLostBuff(Piece p, PieceBuff b) {
		System.out.println(p.getName() + " lost buff "
				+ b.getClass().getSimpleName() + ".");
	}

	public boolean requestYesNo(String prompt, boolean defaultAnswer) {
		try {
			System.out.println(prompt);
			String response = in.readLine();
			return (response.equals("yes") || response.equals("y")) ? true
					: ((response.equals("no") || response.equals("n")) ? false
							: defaultAnswer);
		} catch (Exception e) {
			e.printStackTrace();
			return defaultAnswer;
		}
	}

	public void playerPreventedFromDrawing(Player p, Card source) {
		System.out.println(p.getName()
				+ " was prevented from drawing due to the effect of "
				+ source.getCardBase().getName() + ".");
	}
}
