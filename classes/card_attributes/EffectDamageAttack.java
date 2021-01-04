package content.classes.card_attributes;

public class EffectDamageAttack extends Attack implements Damage {
    String[] keywords;
    String text;
    Integer damage;
    public EffectDamageAttack(String name, char[] cost, String text,
        Integer damage, String[] keywords) {
        super(name, cost);
        this.damage = damage;
        this.keywords = keywords;
        this.text = text;
    }
}