package com.example.pocketcards.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.pocketcards.classes.FlashCard;
import com.example.pocketcards.classes.FlashCardSet;
import com.example.pocketcards.classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class Database {
    private static DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private static Database instance;
    private static final String FLASH_REF = "flashCards";

    private Database() {
        loadIntoSingleton();
    }

    public static void init() {
        if(instance == null) {
            instance = new Database();
        }
        return;
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void loadIntoSingleton() {
        Log.d("SUFYAAN-Load singleton", "Accessed here");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot snapshot = dataSnapshot.child(User.getInstance().getUserID()).child(FLASH_REF);
                Iterable<DataSnapshot> iterableSnap = snapshot.getChildren();

                for (DataSnapshot snap : iterableSnap) {
                    FlashCardSet.getInstance().addToArray(snap.getValue(FlashCard.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    public void clear() {
        instance = null;
    }

    public void addCard(String header, String content, String answer, String difficulty, int color) {
        Log.d("SUFYAAN-Add card", "Accessed here");
        DatabaseReference addRef = myRef.child(User.getInstance().getUserID()).child(FLASH_REF).push();
        String id = addRef.getKey(); //Store into local variable

        FlashCard newCard = new FlashCard(header, content, answer, id, difficulty, color);
        addRef.setValue(newCard); //Add new FlashCard to database

        FlashCardSet.getInstance().addToArray(newCard); //Add new FlashCard to singleton ArrayList
    }

    public void editCard(String header, String content, String answer, String key, String difficulty, int listKey, int color) {
        Log.d("SUFYAAN-Edit card", "Accessed here");
        DatabaseReference editRef = myRef.child(User.getInstance().getUserID()).child(FLASH_REF);
        if(header.equals("")) {
            header = FlashCardSet.getInstance().getArray().get(listKey).getContent();
        }
        if(content.equals("")) {
            content = FlashCardSet.getInstance().getArray().get(listKey).getContent();
        }
        if(answer.equals("")) {
            answer = FlashCardSet.getInstance().getArray().get(listKey).getContent();
        }
        if(difficulty.equals("")) {
            difficulty = FlashCardSet.getInstance().getArray().get(listKey).getContent();
        }
        FlashCard editedCard = new FlashCard(header, content, answer, key, difficulty, color);
        editRef.child(key).setValue(editedCard);

        FlashCardSet.getInstance().getArray().set(listKey, editedCard);
    }

    public void deleteCard(String key, int arrayKey) {
        Log.d("SUFYAAN-Delete card", "Accessed here");
        myRef.child(User.getInstance().getUserID()).child(FLASH_REF).child(key).removeValue();
        FlashCardSet.getInstance().getArray().remove(arrayKey);
    }
}
