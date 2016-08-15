package se.tda367.flashcards.controllers;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import se.tda367.flashcards.models.Deck;

/**
 * Created by Emilia on 16-08-15.
 */
public class SortDecks {
    private ArrayList<Deck> your_array_list;

    public SortDecks(ArrayList<Deck> your_array_list, String itemSelected, ListView lv){

    if (your_array_list.size() != 0) {
        ArrayList<Deck> your_new_array_list = new ArrayList<Deck>();

        if (itemSelected.equals("Leasts numbers of cards")) {
            for (Deck d : your_array_list) {
                for (int i = 0; i < your_array_list.size(); i++) {
                    if (d.getSize() < your_array_list.get(i).getSize())
                        your_new_array_list.add(i, d);

                }
            }
            your_array_list = your_new_array_list;
            ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();


        } else if (itemSelected.equals("Most numbers of cards")) {
            for (int i = 0; i < your_array_list.size(); i++) {
                for (Deck d : your_array_list) {
                    if (d.getSize() > your_array_list.get(i).getSize())
                        your_new_array_list.add(i, d);
                }
            }
            your_array_list = your_new_array_list;
            ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();

        } else if (itemSelected.equals("Most often played")) {
            for (int i = 0; i < your_array_list.size(); i++) {
                for (Deck d : your_array_list) {
                    if (d.getNbrOfTimesPlayed() < your_array_list.get(i).getNbrOfTimesPlayed())
                        your_new_array_list.add(i, d);
                }
            }
            your_array_list = your_new_array_list;
            ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();


        } else if (itemSelected.equals("Name")) {
            Collections.sort(your_array_list, new Comparator<Deck>() {
                public int compare(Deck deck, Deck deck2) {
                    return deck.getName().compareTo(deck2.getName());
                }
            });
            ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();


        } else if (itemSelected.equals("Newest")) {
            for (int i = 0; i < your_array_list.size(); i++) {
                for (Deck d : your_array_list) {
                    if (d.getMade() > your_array_list.get(i).getMade())
                        your_new_array_list.add(i, d);
                }
            }
            your_array_list = your_new_array_list;
            ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();


        } else if (itemSelected.equals("Least often played")) {
            for (int i = 0; i < your_array_list.size(); i++) {
                for (Deck d : your_array_list) {
                    if (d.getNbrOfTimesPlayed() > your_array_list.get(i).getNbrOfTimesPlayed())
                        your_new_array_list.add(i, d);
                }
            }
            your_array_list = your_new_array_list;
            ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();


        } else if (itemSelected.equals("Longest time played since")) {
            if (your_array_list.size() != 0) {
                for (int i = 0; i < your_array_list.size(); i++) {
                    for (Deck d : your_array_list) {
                        if (d.getPlayedSince() < your_array_list.get(i).getPlayedSince())
                            your_new_array_list.add(i, d);
                    }
                }
                your_array_list = your_new_array_list;
                ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();
            }

        } else if (itemSelected.equals("Shortest time played since")) {
            for (int i = 0; i < your_array_list.size(); i++) {
                for (Deck d : your_array_list) {
                    if (d.getPlayedSince() > your_array_list.get(i).getPlayedSince())
                        your_new_array_list.add(i, d);
                }
            }
            your_array_list = your_new_array_list;
            ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();


        }
    }
}
}
