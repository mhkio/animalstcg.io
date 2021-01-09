package content.classes.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

import content.carddb.deserializers.CardDeserializer;
import content.CardContainer;
import content.classes.cards.*;

// Unit test with
// javac content/classes/tools/DeckManager.java && java content.classes.tools.DeckManager

class DeckManager {
    
    CardDeserializer cardDeserializer;
    Scanner sc;

    public static void main(String[] args) {
        new DeckManager(args[0]);
    }

    public DeckManager(String username) {
        cardDeserializer = new CardDeserializer(); // Remove
        presentMenu(username);
    }

    private void presentMenu(String username) {
        ArrayList<Deck> availableDecks = readDeckFile(username);
        printAvailableDecks(username, availableDecks);
        
        Scanner sc = new Scanner(System.in);
        //System.out.println("Choose deck:");
        
        sc.close();
    }

    /**
     * Method that reads and creates deck of the containing saved decks of given username.
     * @param username
     * @return a list of created decks
     */
    private ArrayList<Deck> readDeckFile(String username) {
        try {
            sc = new Scanner(new File("content/classes/tools/decks/" + username + ".txt"));
        } catch (FileNotFoundException f) {
            System.out.println("Username don't exist or " + username + ".txt does not exist in directory.");
        }

        ArrayList<Deck> availableDecks = new ArrayList<>();

        Deck deck = null;
        Integer number, setNumber;
        String set;
        String line;

        while (sc.hasNext()) {
            line = sc.nextLine();
            if (deck == null || line.equals(".")) {
                deck = new Deck(line);
                line = sc.nextLine();
            }

            number = Integer.parseInt(line.substring(0, 1));
            set = line.substring(2, 5);
            setNumber = Integer.parseInt(line.substring(5, 8));

            addCardsToDeck(deck, number, set, setNumber);

            if (!sc.hasNext()) availableDecks.add(deck);
        }

        return availableDecks;
    }

    /**
     * Method that looks up all cards in CardContainer, finds the matching card,
     * clones it and puts it into the presented deck.
     * @param deck deck to add card into
     * @param number number of card copies
     * @param set the card's set
     * @param setNumber the set's card number
     */
    private void addCardsToDeck(Deck deck, Integer number, String set, Integer setNumber) {
        for (int i = 0; i < number; i++) {
            Object card = null;
            try {
                card = (Card) CardContainer.getContainer().get(set).get(setNumber-1).clone();
            } catch (CloneNotSupportedException c) {
                System.out.println(c);
            }
            deck.addCard((Card) card);
        }
    }

    /**
     * Print method for debugging.
     * @param username
     * @param decks
     */
    private void printAvailableDecks(String username, ArrayList<Deck> decks) {
        String string;
        if (decks.size() == 1) {
            string = decks.size() + " available deck for " + username + ": ";
        } else {
            string = decks.size() + " available decks for " + username + ": ";
        }
        System.out.println(string);
        for (Deck deck : decks) {
            deck.randomizeDeck();
            deck.sortDeck();
            //deck.createUniqueList();
            System.out.println(deck.uniqueCardsToString());
        }
    }    
}