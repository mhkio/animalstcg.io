package content.classes.cards;

public class Item extends Trainer {
    private static final long serialVersionUID = 21;
    
    public Item(String id, String name, String illustrator, Integer rarity,
        String text, String[] keywords) {
        super(id, name, illustrator, rarity, text, keywords);
    }
}