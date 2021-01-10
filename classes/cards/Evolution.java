package content.classes.cards;

import content.classes.cards.card_attributes.*;

public class Evolution extends Pokemon {
    private static final long serialVersionUID = 12;

    private String evolvesFrom;

    public Evolution(String id, String name, String illustrator, Integer rarity,
        Integer hp, char type, String description, Integer length, Integer weight,
        Character weakness, String weaknessOp, Character resistance, String resistanceOp,
        Integer retreat, Ability ability, Attack attack1, Attack attack2, 
        String flavour, String evolvesFrom) {
        super(id, name, illustrator, rarity, hp, type, description,
            length, weight, weakness, weaknessOp, resistance, 
            resistanceOp, retreat, ability, attack1, attack2, flavour);
        this.evolvesFrom = evolvesFrom;
    }

    public boolean canEvolveFrom(Pokemon pokemon) {
        if (evolvesFrom.equals(pokemon.getName())) return true;
        return false;
    }

}