package content.classes.cards;

public class Energy extends Card {
    private static final long serialVersionUID = 31;
    protected char[] provides;
    protected String text;
    
    public Energy(String id, String name, String illustrator, Integer rarity, 
        char[] provides, String text) {
        super(id, name, illustrator, rarity);
        this.provides = provides;
        this.text = text;
    }
}