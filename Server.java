package content;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

import content.users.User;
import content.users.CredentialManager;
import content.carddb.deserializers.CardDeserializer;
import content.classes.deck.DeckManager;
import content.classes.deck.Deck;
import content.tools.JsonHandler;

// Run with
// javac content/Server.java && java content/Server 

public class Server {
    private static ArrayList<User> users;
    private DeckManager deckManager;
    private ArrayList<Deck> allDecks;
    private CredentialManager credentialManager;

    // Files containing card data.
    // Before keywords are properly handled, each attack and ability 
    // containing an effect, must have a keyword.
    static String[] FILEPATHS = {
                                    "content/carddb/data/bas.json", 
                                    "content/carddb/data/oce.json",
                                    "content/carddb/data/ene.json"
        };


    public static void main(String[] args) throws FileNotFoundException {
        new Server();
    }

    /**
     * Constructor method that instantiates users, cards and decks.
     */
    public Server() {

        // Load cards
        JsonHandler.instantiate(this);
        new CardDeserializer().deserializeAllCards(FILEPATHS);

        credentialManager = new CredentialManager();
        deckManager = new DeckManager();

        // Load users
        users = credentialManager.getUsers();

        // Load decks
        JsonHandler.readAllDecks();
        allDecks = deckManager.getDecksFromUsers(users);

        // Present menu
        presentMenu(new Scanner(System.in));
    }

    // --- User interface methods --- 

    private void presentMenu(Scanner sc) {
        User user = presentUserLogin(sc);
        presentUserChoices(sc, user);
        sc.close();
    }

    private void presentUserChoices(Scanner sc, User user) {
        System.out.print("\n1. Deck Manager\n   >> ");
        int input = sc.nextInt();
        if (input == 1) {
            Deck deck = presentDecks(sc, user);
            System.out.println("Chose " + deck + ".");
        }
        // Stoppet her -----------------------------------------------------
    }

    private Deck presentDecks(Scanner sc, User user) {
        System.out.println("Choose a deck.");
        deckManager.printAvailableDecks(user);
        System.out.print("    >> ");
        return user.getDecks().get(sc.nextInt() - 1);
    }

    private User presentUserLogin(Scanner sc) {
        String username = "";
        System.out.print("1. Log in\n2. Create new user\n   >> ");
        int input = sc.nextInt();
        if (input == 1) {
            username = credentialManager.logIn(sc);
            if (username == null) presentMenu(sc);
        } 
        if (input == 2) {
            credentialManager.newUser(sc);
            presentMenu(sc);
        }
        System.out.print("Welcome, " + username + ".");
        return getUserFromString(username);
    }

    // Methods for debugging

    private void printAllUsers() {
        int num = 1;
        for (User user : users) {
            System.out.println("User " + num++ + ": " + user.getUsername());
        }
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static User getUserFromString(String username) {
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        return null;
    }
}