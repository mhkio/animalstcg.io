package content.classes.deck;

import java.util.ArrayList;

import content.CardContainer;
import content.classes.cards.Card;
import content.users.User;

// Unit test with
// javac content/classes/tools/DeckManager.java && java content.classes.tools.DeckManager

public class DeckManager {

    public static void addCardToDeck(Deck deck, Card card) {
        deck.addCard(card);
    }

    public static Card createCard(String set, Integer setNumber) {
        Object card = null;
            try {
                card = (Card) CardContainer.getContainer().get(set).get(setNumber-1).clone();
            } catch (CloneNotSupportedException c) {
                System.out.println(c);
            }
        return (Card) card;
    }

    /**
     * Print method for debugging.
     */
    public void printAvailableDecks(User user) {
        int i = 0;
        for (Deck deck : user.getDecks()) {
            System.out.println(++i + ". " + deck);
        }
    }    

    public ArrayList<Deck> getDecksFromUsers(ArrayList<User> users) {
        ArrayList<Deck> decks = new ArrayList<>();
        for (User user : users) {
            for (Deck deck : user.getDecks()) {
                decks.add(deck);
            }
        }
        return decks;
    }
}