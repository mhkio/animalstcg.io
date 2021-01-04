package content.classes.cards;
import java.io.Serializable;

public abstract class Card implements Serializable {
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

    protected String getSetName() {
        String abbr = id.substring(0, 4);
        if (abbr.equals("bas")) return "Base Set";
        if (abbr.equals("oce")) return "Ocean Expansion";
        return null;
    }

    protected String getSetNumber() {
        return id.substring(3, 6);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(id.toUpperCase() + " " + name);
        return s.toString();
    }
}