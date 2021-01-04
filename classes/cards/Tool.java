package content.classes.cards;

public class Tool extends Item {
    private static final long serialVersionUID = 22;

    public Tool(String id, String name, String illustrator, Integer rarity,
        String text, String[] keywords) {
        super(id, name, illustrator, rarity, text, keywords);
    }
    
}