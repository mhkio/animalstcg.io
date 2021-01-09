package content;

// import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import content.users.User;
import content.carddb.deserializers.CardDeserializer;
import content.classes.tools.DeckManager;

// Run with
// javac content/Server.java && java content/Server 

public class Server {
    private ArrayList<User> users;
    private Map<String, String> usersAndPasswords;
    private DeckManager deckManager;

    //private SecureRandom random;
    private MessageDigest messageDigest;

    // Files containing card data.
    // Before keywords are properly handled, each attack and ability 
    // containing an effect, must have a keyword.
    static String[] FILEPATHS = {
        "content/carddb/data/bas.json", 
        "content/carddb/data/oce.json",
        "content/carddb/data/ene.json"
        };

    static String CREDENTIAL_PATH = "content/users/credentials.json";

    public static void main(String[] args) throws NoSuchAlgorithmException,
        FileNotFoundException {
        new Server();
    }

    public Server() throws NoSuchAlgorithmException, FileNotFoundException {
        users = new ArrayList<>();
        deckManager = new DeckManager();
        //random = new SecureRandom();
        messageDigest = MessageDigest.getInstance("SHA-512");
        usersAndPasswords = readUsersAndPasswords();
        new CardDeserializer().deserializeAllCards(FILEPATHS);
        presentMenu();
    }

    // --- User interface methods --- 

    private void presentMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.print("1. Log in\n2. Create new user\n   >> ");
        int input = sc.nextInt();
        if (input == 1) logIn(sc);
        if (input == 2) newUser(sc);
        sc.close();
    }

    public boolean logIn(Scanner sc) {
        return true;
    }

    public boolean newUser(Scanner sc) {
        System.out.print("Enter desired username: ");
        String username = sc.next();
        System.out.print("Enter desired password: ");
        String password = hashPassword(sc.next());
        if (usersAndPasswords.containsKey(username)) {
            System.out.println("Error: username '" + username + "' already exist.");
            return false;
        } else {
            usersAndPasswords.put(username, password);
            writeToCredentialFile(username, password);
        }
        return true;
    }

    /**
     * Method that reads user data from file 'users/credentials.json'.
     * The file will (hopefully) be removed before pushing. 
     * @return map of usernames and passwords
     */
    private Map<String, String> readUsersAndPasswords() throws FileNotFoundException {
        Map<String, String> map = new HashMap<>();
        Scanner sc = new Scanner(new File(CREDENTIAL_PATH));
        String username, password;
        String[] line;
        sc.nextLine(); sc.nextLine();

        // Add usernames and password to map and ArrayList users
        while (sc.hasNext()) {
            line = sc.nextLine().split(":");
            username = sanitize(line[1]);
            users.add(new User(username));
            line = sc.nextLine().split(":");
            password = sanitize(line[1]);
            map.put(username, password);
            sc.nextLine(); sc.nextLine();
        }
        return map;
    }

    /**
     * Method that hashes password.
     * Source: https://www.baeldung.com/java-password-hashing
     */
    private String hashPassword(String password) {
        //byte[] salt = new byte[16];
        //random.nextBytes(salt);
        //messageDigest.update(salt);
        byte[] hashCode = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        return getStringFromBytes(hashCode);
    }

    private String getStringFromBytes(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(bytes[i]);
        }
        return sb.toString();
    }

    /**
     * Method that adds new username and password in .json format
     */
    private void writeToCredentialFile(String username, String password) {
        try {
            String string = copyFile();
            FileWriter writer = new FileWriter(CREDENTIAL_PATH);
            StringBuilder sb = new StringBuilder();
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

    private String copyFile() {
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

    private int findNumOfLinesInFile(BufferedReader br) {
        int num = 0; 
        try {
            while (br.readLine() != null) num++;
            br.close();
        } catch (IOException i) {
            System.out.println(i);
        } 
        return num - 2;
    }

    private void printAllUsersAndPasswords() {
        for (String username : usersAndPasswords.keySet()) {
            System.out.println(username + " : " + usersAndPasswords.get(username));
        }
    }

    private void printFile(File file) {
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                System.out.println(sc.nextLine());
            }
        } catch (Exception e) {

        }
    }

    // TODO: Replace with regular expression
    private String sanitize(String string) {
        string = string.replace("\"", "");
        string = string.replace(",", "");
        string = string.replace(" ", "");
        return string;
    }
}