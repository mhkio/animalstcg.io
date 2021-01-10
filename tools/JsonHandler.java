package content.tools;

// import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import content.Server;
import content.users.User;
import content.classes.deck.DeckManager;
import content.classes.cards.Card;
import content.classes.deck.Deck;

/**
 * Class that deals with all JSON parsing and writing.
 */
public class JsonHandler {

    private static String CREDENTIAL_PATH = "content/users/credentials.json";
    private static String DECK_PATH = "content/classes/deck/decks.json";
    private static MessageDigest messageDigest;
    
    public static void instantiate(Server s) {
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
    }

    // --- General utility methods ---

    public static Scanner getScanner(String filepath) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filepath));
        } catch (FileNotFoundException f) {
            System.out.println(f);
        }
        return sc;
    }

    public static Scanner getScanner(File file) {
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException f) {
            System.out.println(f);
        }
        return sc;
    }

    /**
     * Method that extracts all lines except the last two
     * from json file. This allows for writing to the file.
     * @return
     */
    private static String copyFile() {
        StringBuilder sb = new StringBuilder();
        try {
            int numOfLines = findNumOfLinesInFile(
                new BufferedReader(new FileReader(CREDENTIAL_PATH)));
            BufferedReader br = 
                new BufferedReader(new FileReader(CREDENTIAL_PATH));
            for (int i = 0; i < numOfLines; i++) {
                sb.append(br.readLine() + "\n");
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return sb.toString();
    }

    /**
     * Method to found invalid characters in username
     * @param string username 
     * @return true if invalid characters are in username, otherwise false.
     */
    public static boolean containsInvalidChars(String string) {
        String INVALID_CHARS = " ";
        for (int i = 0; i < string.length(); i++) {
            for (int j = 0; j < INVALID_CHARS.length(); j++) {
                if (string.charAt(i) == INVALID_CHARS.charAt(j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void skipLines(Scanner sc, int numberOfLines) {
        for (int i = 0; i < numberOfLines; i++) {
            sc.nextLine();
        }
    }

    private static int findNumOfLinesInFile(BufferedReader br) {
        int num = 0; 
        try {
            while (br.readLine() != null) num++;
            br.close();
        } catch (IOException i) {
            System.out.println(i);
        } 
        return num - 2;
    }

    // TODO: regular expression
    public static String sanitize(String string, String expression) {
        for (int i = 0; i < expression.length(); i++) {
            string = string.replace(expression.substring(i, i+1), "");
        }
        return string;
    }

    /**
     * Returns card from the given string 'number setSetNumber'
     */
    public static Card getCardFromString(String string) {
        // Destructure string
        String set = string.substring(1, 4);
        int setNumber = Integer.parseInt(string.substring(4, 7));
        return DeckManager.createCard(set, setNumber);
    }

    private static String getValue(String line) {
        return sanitize(line.split(":")[1], ",\" ");
    }

    // --- Decks ---

    /**
     * Method that reads creates all decks and assigns them to correct user.
     * Contains a four-nested loop, however, this method runs in exponential time:
     * O(number of users * number of decks)
     */
    public static void readAllDecks() {
        Scanner sc = getScanner(DECK_PATH);
        skipLines(sc, 2);
        
        User user;
        Deck deck = null;
        Card card;
        int numOfDecks;
        String line;

        // For every user
        while (sc.hasNext()) {
            user = Server.getUserFromString(getValue(sc.nextLine()));
            numOfDecks = Integer.parseInt(getValue(sc.nextLine()));
            skipLines(sc, 2);

            // For every user deck
            for (int i = 0; i < numOfDecks; i++) {
                deck = new Deck(getValue(sc.nextLine()));
                skipLines(sc, 1);

                // For every card in deck (always 60)
                while (!(line = sanitize(sc.nextLine(), "\", ")).equals("]")) {
                    card = getCardFromString(line);

                    // For every copy of card (between 1 and 4)
                    for (int j = 0; j < Integer.parseInt(line.substring(0, 1)); j++) {
                        DeckManager.addCardToDeck(deck, card);
                    }
                }
                if (i < numOfDecks - 1) skipLines(sc, 2);

                // Add deck to correct user
                user.addDeck(deck);
            }
            skipLines(sc, 4);
        }
        sc.close();
    }

    // --- Authentication ---

    /**
     * Method that reads user data from file 'users/credentials.json'.
     * The file must be removed before pushing. 
     * @return map of usernames and passwords
     */
    public static Map<String, String> readUsersAndPasswords() {
        Map<String, String> map = new HashMap<>();
        Scanner sc = getScanner(CREDENTIAL_PATH);

        String username, password;
        skipLines(sc, 2);

        // Add usernames and password to map and ArrayList users
        while (sc.hasNext()) {
            username = getValue(sc.nextLine());
            password = getValue(sc.nextLine());
            map.put(username, password);
            skipLines(sc, 2);
        }      
        sc.close();
        return map;
    }

    public static ArrayList<String> readUsers() {
        ArrayList<String> usernames = new ArrayList<>();
        Scanner sc = getScanner(CREDENTIAL_PATH);
        skipLines(sc, 2);

        // Add usernames and password to map
        while (sc.hasNext()) {
            usernames.add(getValue(sc.nextLine()));
            skipLines(sc, 3);
        }
        sc.close();
        return usernames;
    }

    /**
     * Method that hashes password.
     * Source: https://www.baeldung.com/java-password-hashing
     */
    public static String hashPassword(String password) {
        //byte[] salt = new byte[16];
        //random.nextBytes(salt);
        //messageDigest.update(salt);
        byte[] hashCode = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        return getStringFromBytes(hashCode);
    }

    public static String getStringFromBytes(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(bytes[i]);
        }
        return sb.toString();
    }

    /**
     * Method that adds new username and password in .json format
     */
    public static void writeToCredentialFile(String username, String password) {
        try {
            String string = copyFile();
            FileWriter writer = new FileWriter(CREDENTIAL_PATH);
            writer.write(string);
            writer.write("    },\n    {\n");
            writer.write("        \"username\" : \"" + username + "\",\n");
            writer.write("        \"password\" : \"" + password + "\"\n");
            writer.write("    }\n]");
            writer.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }    

    // ---- Used for debugging ----

    public static void printAllLinesInFile(File file) {
        Scanner sc = getScanner(file);
        while (sc.hasNext()) {
            System.out.println(sc.nextLine());
        }
        sc.close();
    }  
}