package content.classes.cards;

public class Trainer extends Card {
    private static final long serialVersionUID = 20;
    protected String text;
    protected String[] keywords;

    public Trainer(String id, String name, String illustrator, Integer rarity,
        String text, String[] keywords) {
        super(id, name, illustrator, rarity);
        this.text = text; this.keywords = keywords;
    }
}