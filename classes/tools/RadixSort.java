package content.classes.tools;

import java.util.ArrayList;

import content.classes.cards.*;

public class RadixSort {

    static ArrayList<Card> radixSort(ArrayList<Card> array) {
        array = InsertionSort.insertionSort(array);

        ArrayList<ArrayList<Card>> buckets = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            buckets.add(new ArrayList<>());
        } 

        for (Card card : array) {
            if (card instanceof Pokemon) buckets.get(0).add(card);
            if (card instanceof Trainer) buckets.get(1).add(card);
            buckets.get(2).add(card);
        }

        array.clear();

        for (ArrayList<Card> bucket : buckets) {
            for (Card card : bucket) {
                array.add(card);
            }
        }

        return array;
    }
}