package content.classes.cards;

import content.classes.card_attributes.*;

public abstract class Pokemon extends Card {
    private static final long serialVersionUID = 10;
    protected int hp, length, weight, retreat;
    protected char type;
    protected Character weakness, resistance;
    protected String description;
    protected String weaknessOperator, resistanceOperator;
    protected Ability ability;
    protected Attack attack1, attack2;
    protected String flavour;
    
    public Pokemon(String id, String name, String illustrator, Integer rarity,
        Integer hp, Character type, String description, Integer length, Integer weight,
        Character weakness, String weaknessOp, Character resistance, String resistanceOp,
        Integer retreat, Ability ability, Attack attack1, Attack attack2, 
        String flavour) {
        super(id, name, illustrator, rarity);
        this.hp = hp; this.type = type;
        this.description = description;
        this.length = length; this.weight = weight;
        this.weakness = weakness; weaknessOperator = weaknessOp;
        this.resistance = resistance; resistanceOperator = resistanceOp;
        this.retreat = retreat;
        this.ability = ability;
        this.attack1 = attack1; this.attack2 = attack2;
        this.flavour = flavour;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(super.toString());
        if (ability != null) s.append("\nAbility: " + ability.getName());
        if (attack1 != null) s.append("\nAttack 1: " + attack1.getName());
        if (attack2 != null) s.append("\nAttack 2: " + attack2.getName());
        return s.toString();
    }
}