package content.classes.card_attributes;

public class DamageAttack extends Attack implements Damage {
    Integer damage;
    public DamageAttack(String name, char[] cost, String text,
        Integer damage) {
        super(name, cost);
        this.damage = damage;
    }
}