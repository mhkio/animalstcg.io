package content.classes.cards;

import java.io.Serializable;
import java.util.Map;

import content.CardContainer;

public abstract class Card implements Serializable, Cloneable, Comparable<Card> {
    private static final long serialVersionUID = 0;
    protected String id;
    protected String name;
    protected String illustrator;
    protected Integer rarity;

    public Card(String id, String name, String illustrator, Integer rarity) {
        this.id = id;
        this.name = name;
        this.illustrator = illustrator;
        this.rarity = rarity;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    protected String getSetName() {
        String abbr = id.substring(0, 4);
        if (abbr.equals("bas")) return "Base Set";
        if (abbr.equals("oce")) return "Ocean Expansion";
        return null;
    }

    public String getSet() {
        return id.substring(0, 3);
    }

    public int getSetNumber() {
        return Integer.parseInt(id.substring(3, 6));
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(id.toUpperCase() + " " + name);
        return s.toString();
    }

    @Override
    public int compareTo(Card card) {
        Map<String, Integer> sets = CardContainer.getSets();
        if (sets.get(this.getSet()) < sets.get(card.getSet())) return 1;
        if (sets.get(this.getSet()) > sets.get(card.getSet())) return -1;
        if (this.getSetNumber() < card.getSetNumber()) return 1;
        if (this.getSetNumber() > card.getSetNumber()) return -1;
        return 0;
    }
    
}