package content;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import content.classes.cards.*;

// Unit test with
// javac content/CardContainer.java && java content/CardContainer

public class CardContainer {
    private static Map<String, ArrayList<Card>> allCards = new HashMap<>();
    private static Map<String, Integer> sets = new HashMap<>();

    /**
     * Method invoked from CardDeserializer after successful
     * deserialization.
     * @param map String setName : ArrayList<Card>
     */
    public static void getAllCards(Map<String, ArrayList<Card>> map) {
        allCards = map;
    }

    public static Map<String, ArrayList<Card>> getContainer() {
        return allCards;
    }

    public static Map<String, Integer> getSets() {
        if (sets.isEmpty()) {
            sets.put("bas", 1);
            sets.put("oce", 2);
            sets.put("ene", 3);
        }
        return sets;
    }

    public static void printAllCards() {
        for (String s : allCards.keySet()) {
            System.out.print(s + " (" + allCards.get(s).size() + "): ");
            for (Card card : allCards.get(s)) {
                System.out.printf(card.getName() + ", ");
            }
            System.out.println();
        }
    }
}