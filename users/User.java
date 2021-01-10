package content.users;

import java.util.ArrayList;

import content.classes.deck.Deck;

public class User implements UserInterface {
    private String username;
    private ArrayList<Deck> decks;

    public User(String username) {
        this.username = username;
        decks = new ArrayList<Deck>();
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public boolean addDeck(Deck deck) {
        return decks.add(deck);
    }

    @Override
    public String toString() {
        return username;
    }
}

