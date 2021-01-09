package content.classes.tools;

import java.util.ArrayList;

import content.classes.cards.Card;

class InsertionSort {

    static ArrayList<Card> insertionSort(ArrayList<Card> array) {
        int j;
        for (int i = 1; i < array.size(); i++) {
            j = i;
            while (j > 0 && array.get(j - 1).compareTo(array.get(j)) > 0) {
                swap(array, j);
                j--;
            }
        }

        return array;
    }

    private static void swap(ArrayList<Card> array, int index) {
        Card tmp = array.get(index - 1);
        array.set(index - 1, array.get(index));
        array.set(index, tmp);
    }
}