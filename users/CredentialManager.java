package content.users;

import content.tools.JsonHandler;

import java.util.Scanner;
import java.util.Map;
import java.util.ArrayList;

public class CredentialManager {
    private Map<String, String> usersAndPasswords;
    //private SecureRandom random = new SecureRandom();

    public CredentialManager() {
        usersAndPasswords = JsonHandler.readUsersAndPasswords();
    }

    public String logIn(Scanner sc) {
        System.out.print("Enter username: ");
        String username = sc.next();
        System.out.print("Enter password: ");
        String password = JsonHandler.hashPassword(sc.next());
        if (usersAndPasswords.containsKey(username)) {
            if (usersAndPasswords.get(username).equals(password)) {
                System.out.println("Login successful.");
            } else {
                System.out.println("Wrong password!");
                return null;
            }
        } else {
            System.out.println("Username '" + username + "' does not exist.");
            return null;
        }
        return username;
    }

    public boolean newUser(Scanner sc) {
        System.out.print("Enter desired username: ");
        String username = sc.next();
        System.out.print("Enter desired password: ");
        String password = JsonHandler.hashPassword(sc.next());
        if (usersAndPasswords.containsKey(username)) {
            System.out.println("Error: username '" + username + "' already exist.");
            return false;
        }
        if (JsonHandler.containsInvalidChars(username)) {
            System.out.println("Error: some characters in username are invalid.");
            return false;
        }
        usersAndPasswords.put(username, password);
        JsonHandler.writeToCredentialFile(username, password);
        return true;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        for (String string : JsonHandler.readUsers()) {
            users.add(new User(string));
        }
        return users;
    }
}