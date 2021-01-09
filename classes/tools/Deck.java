package content.classes.tools;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import content.classes.cards.*;

public class Deck {
    private String name;
    private ArrayList<Card> cards;
    private Map<Card, Integer> cardMap;
    private ArrayList<Card> uniqueCards;
    private static int DECK_SIZE = 60;

    public Deck(String name) {
        this.name = name;
        cards = new ArrayList<>(DECK_SIZE);
        cardMap = new HashMap<>();
    }

    // TODO: Print as sorted hashmap: Iterate trough arraylist and 
    // TODO: get corresponding card from map. 

    public boolean addCard(Card card) {
        try {
            cards.add(card);
            Card sameCard = getSameCardInMap(card);
            if (sameCard == null) {
                cardMap.put(card, 1);
            } else {
                cardMap.replace(sameCard, cardMap.get(sameCard) + 1);
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void randomizeDeck() {
        Card card;
        for (int i = 0; i < 1000; i++) {
            card = cards.remove(getRandomIndexFromDeck());
            cards.add(getRandomIndexFromDeck(), card);
        } 
        System.out.println("Randomized deck...");
    }

    /**
     * Deck sort implemented with radix sort.
     * @return
     */
    public void sortDeck() {
        cards = RadixSort.radixSort(cards);
        System.out.println("Sorted deck...");
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getDeck() {
        return cards;
    }

    public Map<Card, Integer> getDeckAsMap() {
        return cardMap;
    }

    public int getRandomIndexFromDeck() {
        return (int) Math.floor(Math.random() * cards.size());
    }

    public HashMap<Card, Integer> createMap() {
        HashMap<Card, Integer> newMap = new HashMap<>();
        for (Card card : cards) {
            for (Card key : newMap.keySet()) {
                if (card.compareTo(key) == 0) {
                    newMap.replace(key, newMap.get(key) + 1);
                    break;
                }
            }
        }
        return null;
    }

    // TODO: 
    /*
    public void createUniqueList() {
        ArrayList<Card> array = new ArrayList<>();
        for (Card card : cards) {
            for (Card uniqueCards : array) {
                if (card.compareTo(uniqueCards) == 0) break;
            }
            array.add(card);
        }
        uniqueCards = array;
    }
    */

    public Card getSameCardInMap(Card newCard) {
        for (Card card : cardMap.keySet()) {
            if (card.compareTo(newCard) == 0) return card;
        }
        return null;
    }

    public boolean containsInCardMap(Card newCard) {
        for (Card card : cardMap.keySet()) {
            if (card.compareTo(newCard) != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method to get all unique cards as string.
     * @return
     */
    public String uniqueCardsToString() {
        StringBuilder sb = getStringBuilder();
        for (Card card : uniqueCards) {
            sb.append("    " + card.getName() + "\n");
        }
        return sb.toString();
    }

    /**
     * Method to get map as a string.
     * @return
     */
    public String mapToString() {
        StringBuilder sb = getStringBuilder();
        for (Card card : cardMap.keySet()) {
            sb.append("    " + cardMap.get(card) + " " + card.getName() + "\n");
        }
        return sb.toString();
    }

    /**
     * Method to get deck as string.
     * @return
     */
    public String arrayToString() {
        StringBuilder sb = getStringBuilder();
        for (Card card : cards) {
            sb.append("   " + card.getName() + "\n");
        }
        return sb.toString();
    }

    private StringBuilder getStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append("Deck name: " + name + "\n");
        return sb;
    }
}