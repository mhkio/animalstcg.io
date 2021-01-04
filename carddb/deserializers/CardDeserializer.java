package content.carddb.deserializers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

import content.classes.cards.*;
import content.classes.card_attributes.*;

// Run with
// javac carddb/deserializers/CardDeserializer.java && java carddb.deserializers.CardDeserializer

public class CardDeserializer {

    String[] filepaths = {"carddb/data/bas.json", 
                          "carddb/data/oce.json",
                          "carddb/data/ene.json"};
    private int lineNumber;
    private Map<String, ArrayList<Card>> cardCollection;
    
    public static void main(String[] args) { 
        new CardDeserializer();
    }

    /**
     * Constructor method
     */
    public CardDeserializer() {
        cardCollection = new HashMap<>();
        for (int i = 0; i < filepaths.length; i++) {
            try {
                deserialize(new BufferedReader(new FileReader(filepaths[i])), filepaths[i]);
            } catch (FileNotFoundException f) {
                System.out.println(filepaths[i] + " not found.");
            }
        }
        printCardCollection();
    }

    /**
     * Method that instantiates deserialization.
     * @param br A reader. Could be interchanged with a different reader e.g. Scanner.
     */
    private void deserialize(BufferedReader br, String filepath) {
        lineNumber = 0;
        String line;
        String subclass;
        try {
            skipLines(br, 2);
            while ((line = readLine(br)) != null) {
                subclass = readLineData(line);
                addToCardCollection(getSubclassString(subclass), 
                    deserializeCard(br, subclass));
                skipLines(br, 2);
            }  
        } catch (Exception e) {
            System.out.println("Line " + (lineNumber-1) + " in " +
                filepath + " creates an ." + e.toString());
                System.exit(0);
        }

        System.out.println("Deserialized " + filepath);
    }

    /**
     * TODO: Replace string with a regular expression
     * Method that sanitize line.
     * @param s String to be filtered.
     * @return value of a card attribute.
     */
    private String readLineData(String s) {
        if (checkInput(s)) {
            
            if (s == null) return "";
            s = s.split(":")[1].trim();
            s = s.replace("\"", ""); s = s.replace("[", "");
            s = s.replace("]", "");
            return s.replace(",", "");
            
            //return s.replaceAll("([[]\",])", "");
        }
        return null;        
    }

     /**
      * Intersection method.
      * @param br Buffered Reader
      * @param subclass Identifying string of type of Card.
      * @return Card of proper subclass.
      * @throws IOException
      */
    private Card deserializeCard(BufferedReader br, String subclass) throws IOException {
        if (subclass.equals("11")) return deserializeBasic(br);
        if (subclass.equals("12")) return deserializeEvolution(br);
        if (subclass.equals("21")) return deserializeItem(br);
        if (subclass.equals("22")) return deserializeTool(br);
        if (subclass.equals("23")) return deserializeSupporter(br);
        if (subclass.equals("24")) return deserializeStadium(br);
        if (subclass.equals("31")) return deserializeBasicEnergy(br);
        if (subclass.equals("32")) return deserializeSpecialEnergy(br);
        return null;
    }

    // Card instantiation methods --------------------------

    private Basic deserializeBasic(BufferedReader br) throws IOException {
        //System.out.println("Deserializing Pokemon...");
        String[] data = createData(br, 29);

        Ability ability = deserializeAbility(Arrays.copyOfRange(data, 14, 17));
        Attack attack1 = deserializeAttack(Arrays.copyOfRange(data, 17, 22));
        Attack attack2 = deserializeAttack(Arrays.copyOfRange(data, 22, 27));

        return new Basic(data[0], data[1], data[2], toInt(data[3]), 
            toInt(data[4]), toChar(data[5]), data[6], toInt(data[7]),
            toInt(data[8]), toChar(data[9]), data[10], 
            toChar(data[11]),
            data[12], toInt(data[13]), ability, attack1, attack2, data[27]);
    }

    private Evolution deserializeEvolution(BufferedReader br) throws IOException {
        //System.out.println("Deserializing Pokemon...");
        String[] data = createData(br, 29);

        Ability ability = deserializeAbility(Arrays.copyOfRange(data, 14, 17));
        Attack attack1 = deserializeAttack(Arrays.copyOfRange(data, 17, 22));
        Attack attack2 = deserializeAttack(Arrays.copyOfRange(data, 22, 27));

        return new Evolution(data[0], data[1], data[2], toInt(data[3]), 
            toInt(data[4]), toChar(data[5]), data[6], toInt(data[7]),
            toInt(data[8]), toChar(data[9]), data[10], toChar(data[11]),
            data[12], toInt(data[13]), ability, attack1, attack2, data[27],
            data[28]);
    }

    private Item deserializeItem(BufferedReader br) throws IOException {
        //System.out.println("Deserializing Item...");
        String[] data = createData(br, 6);
        String[] keywords = createKeywords(data[5]);
        return new Item(data[0], data[1], data[2], toInt(data[3]),
            data[4], keywords);
    }

    private Tool deserializeTool(BufferedReader br) throws IOException {
        //System.out.println("Deserializing Tool...");
        String[] data = createData(br, 6);
        return new Tool(data[0], data[1], data[2], toInt(data[3]),
            data[4], createKeywords(data[5]));
    }

    private Supporter deserializeSupporter(BufferedReader br) throws IOException {
        //System.out.println("Deserializing Supporter...");
        String[] data = createData(br, 6);
        return new Supporter(data[0], data[1], data[2], toInt(data[3]),
            data[4], createKeywords(data[5]));
    }

    private Stadium deserializeStadium(BufferedReader br) throws IOException {
        //System.out.println("Deserializing Stadium...");
        String[] data = createData(br, 6);
        return new Stadium(data[0], data[1], data[2], toInt(data[3]),
            data[4], createKeywords(data[5]));
    }

    private Energy deserializeBasicEnergy(BufferedReader br) throws IOException {
        //System.out.println("Deserializing Basic Energy...");
        String data[] = createData(br, 6);
        return new Energy(data[0], data[1], data[2], toInt(data[3]),
            createCharArray(data[4]), data[5]);
    }

    private SpecialEnergy deserializeSpecialEnergy(BufferedReader br) throws IOException {
        //System.out.println("Deserializing Special Energy...");
        String data[] = createData(br, 7);
        return new SpecialEnergy(data[0], data[1], data[2], toInt(data[3]),
            createCharArray(data[4]), data[6], createKeywords(data[6]));
    }

    private Attack deserializeAttack(String[] data) {

        // No attack
        if (data[0] == null) return null;

        char[] cost = createCharArray(data[1]);
        String[] keywords = createKeywords(data[4]);

        // Instance of DamageAttack
        if (data[3] != null && data[4] == null) {
            return new DamageAttack(data[0], cost, data[2], 
                Integer.parseInt(data[3]));
        } 

        // Instance of EffectAttack
        if (data[3] == null && data[4] != null) {
            return new EffectAttack(data[0], cost, data[2],
                keywords);
        }

        // Instance of EffectDamageAttack
        return new EffectDamageAttack(data[0], cost, data[2], 
            Integer.parseInt(data[3]), keywords);
    }

    private Ability deserializeAbility(String[] data) {
        if (data[0] == null) return null;
        String[] keywords = createKeywords(data[2]);
        return new Ability(data[0], data[1], keywords);
    }

    // Utility methods -------------------------------

    private void addToCardCollection(String subclass, Card card) {
        if (cardCollection.get(subclass) == null) {
            cardCollection.put(subclass, new ArrayList<>());
        }
        cardCollection.get(subclass).add(card);
    }

    private void printCardCollection() {
        for (String s : cardCollection.keySet()) {
            System.out.println(s + " : " + cardCollection.get(s).size());
        }
    }

    private String getSubclassString(String subclass) {
        int num = Integer.parseInt(subclass);
        if (num >= 10 && num < 20) return "Pokemon";
        if (num >= 20 && num < 30) return "Trainer";
        return "Energy";
    }

    private String[] createData(BufferedReader br, int size) throws IOException {
        String[] data = new String[size];
        int counter = 0;
        String line;

        while (counter < data.length) {
            line = readLine(br);

            if (checkInput(line)) {
                line = readLineData(line);
                data[counter] = line.equals("null") ? null : line;
                counter++;
            }
        }

        readLine(br);

        return data;
    }

    private Integer toInt(String s) {
        if (s == null) return null;
        return Integer.parseInt(s);
    }

    private Character toChar(String s) {
        if (s == null) return null;
        return s.charAt(0);
    }

    // TODO: Replace with regular expression
    private boolean checkInput(String s) {
        s = s.trim();
        return (s.startsWith("\"") && !(s.substring(0, 9).equals("\"attacks\"")
            || s.substring(0, 9).equals("\"ability\"")));
    }

    private char[] createCharArray(String s) {
        if (s == null) return null;
        char[] cost = new char[s.length()];
        for (int i = 0; i < cost.length; i++) {
            cost[i] = s.charAt(i);
        }
        return cost;
    }

    private String[] createKeywords(String s) {
        if (s == null) return null;
        return s.split(" ");
    }

    private void skipLines(BufferedReader br, int num) throws IOException {
        for (int i = 0; i < num; i++) {
            readLine(br);
        }
    }

    private String readLine(BufferedReader br) throws IOException {
        lineNumber++;
        return br.readLine();
    }

    public Map<String, ArrayList<Card>> getCardCollection() {
        return cardCollection;
    }
}