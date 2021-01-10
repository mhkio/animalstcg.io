package content.classes.cards;

import content.classes.cards.card_attributes.Ability;
import content.classes.cards.card_attributes.Attack;

public class Basic extends Pokemon {
    private static final long serialVersionUID = 11;

    public Basic(String id, String name, String illustrator, Integer rarity,
        int hp, char type, String description, Integer length, Integer weight,
        Character weakness, String weaknessOp, Character resistance, String resistanceOp,
        int retreat, Ability ability, Attack attack1, Attack attack2, 
        String flavour) {
        super(id, name, illustrator, rarity, hp, type, description,
            length, weight, weakness, weaknessOp, resistance, 
            resistanceOp, retreat, ability, attack1, attack2, flavour);
    }
}