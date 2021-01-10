package content.classes.cards.card_attributes;

public class Ability {

    private String name, text;
    private String[] keywords;

    public Ability(String name, String text, String[] keywords) {
            this.name = name;
            this.text = text;
            this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String[] getKeywords() {
        return keywords;
    }
}