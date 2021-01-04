package content.classes.cards;

public class SpecialEnergy extends Energy {
    private static final long serialVersionUID = 32;
    protected String[] keywords;

    public SpecialEnergy(String id, String name, String illustrator, Integer rarity, 
        char[] provides, String text, String[] keywords) {
        super(id, name, illustrator, rarity, provides, text);
        this.keywords = keywords;
    }
    
}