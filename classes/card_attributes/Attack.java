package content.classes.card_attributes;

public abstract class Attack {
    private String name;
    private char[] cost;

    public Attack(String name, char[] cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < cost.length; i++) {
            s.append(cost[i]);
        }
        s.append("  " + name);
        return s.toString();
    }
}