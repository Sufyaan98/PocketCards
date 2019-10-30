package com.example.pocketcards.classes;

import java.util.ArrayList;

//Uses singleton
public class FlashCardSet {
    private static FlashCardSet instance;

    private ArrayList<FlashCard> list = null;

    public static void init() {
        if(instance != null) {
            return;
        }
        instance = new FlashCardSet();
    }

    public static FlashCardSet getInstance() {
        if(instance == null) {
            instance = new FlashCardSet();
        }
        return instance;
    }

    public void clear() {
        instance = null;
    }

    public FlashCardSet() {
        list = new ArrayList<FlashCard>();
    }

    public ArrayList<FlashCard> getArray() {
        return this.list;
    }

    public void addToArray (FlashCard value) {
        list.add(value);
    }

    public void addToSpecified (int key, FlashCard value) {
        list.add(key, value);
    }
}
