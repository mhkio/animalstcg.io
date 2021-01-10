package content.classes.cards.card_attributes;

public class EffectAttack extends Attack implements Effect {
    String[] keywords;
    String text;
    public EffectAttack(String name, char[] cost, String text,
        String[] keywords) {
        super(name, cost);
        this.text = text;
        this.keywords = keywords;
    }
}