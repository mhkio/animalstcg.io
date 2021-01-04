package content.classes.tools;

import content.carddb.deserializers.CardDeserializer;

import java.io.File;
import java.util.Scanner;

// Run with
// javac classes/tools/DeckBuilder.java && java classes/tools/DeckBuilder

class DeckBuilder {
    
    CardDeserializer cardDeserializer;

    public static void main(String[] args) {
        new DeckBuilder();
    }

    public DeckBuilder() {
        cardDeserializer = new CardDeserializer();
        presentMenu();
    }

    private void presentMenu() {
        String filepath = "classes/tools/decks";
        File dir = new File(filepath);
        File[] files = dir.listFiles();
        Scanner sc = new Scanner(System.in);
        System.out.println("What would you like to do?\n1. [Create a new deck]");
        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + files[i].toString());
        }
        System.out.print("Enter a number:\n  >> ");
        int choice = sc.nextInt();
        if (choice == 1) createNewDeck();
        if (choice != 1) editDeck(files[choice]);
        sc.close();
    }

    private void createNewDeck() {

    }

    private void editDeck(File deck) {

    }
}