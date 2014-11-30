package card;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CardBase {
	private static final ArrayList<CardBase> CARDS = new ArrayList<CardBase>();
	static {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					"src/assets/cards.txt"));
			String cardFile = "";
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("//") || line.trim().length() == 0) {
					continue;
				}
				cardFile += line + "\n";
			}
			for (String data : cardFile.split("\n")) {
				CardBase c = CardBase.parseCard(data);
				while (CARDS.size() < c.cardId) {
					CARDS.add(null);
				}
				CARDS.add(c);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String name;
	protected int cardId;
	protected String lore;

	public String getName() {
		return name;
	}

	public int getId() {
		return cardId;
	}

	public String getDetails() {
		return name;
	}

	private static CardBase parseCard(String cardData) {
		CardBase result = new CardBase();
		String[] cardBaseInfo = cardData.split("\\|\\|")[0].split("::");
		result.name = cardBaseInfo[0];
		result.cardId = Integer.parseInt(cardBaseInfo[1]);
		result.lore = cardBaseInfo[2];
		if (cardBaseInfo[3].equals("Piece")) {
			result = PieceCardBase.parseCard(result, cardData.split("\\|\\|")[1]);
		} else if (cardBaseInfo[3].equals("Hex")) {
			result = HexCardBase.parseCard(result);
		}
		return result;
	}

	public static CardBase getCard(int index) {
		return (index >= 0 && index < CARDS.size()) ? CARDS.get(index) : null;
	}

	protected void setBase(CardBase other) {
		name = other.name;
		cardId = other.cardId;
		lore = other.lore;
	}
}
