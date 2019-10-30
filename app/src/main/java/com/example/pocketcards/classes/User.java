package com.example.pocketcards.classes;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.pocketcards.database.Database;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Singleton User class to access authentication from the database, and used in accordance with Database class to access Firebase database
public class User {
    private FirebaseAuth mAuth;
    private static DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private static FirebaseAuth.AuthStateListener mAuthListener;
    private static User instance;

    private boolean loginVal;
    private boolean registerVal;

    public User() {
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user !=null) {
                    //User is signed in
                    Log.d("Signed in", "User signed in" + user.getUid());
                } else {
                    //User is signed out
                    Log.d("Signed out", "User is signed out");
                }
            }
        };
    }

    public static void init() {
        if(instance == null) {
            instance = new User();
        }
        return;
    }

    public static User getInstance() {
        if(instance == null) {
            instance = new User();
        }
        return instance;
    }

    public String getUserID() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = null;
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            uid = user.getUid();
        }
        return uid;
    }

    public boolean login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    loginVal = true;
                } else {
                    loginVal = false;
                }
            }
        });
        return loginVal;
    }

    public void logOut () {
        Log.d("SUFYAAN-Log out method", "Accessed this");
        mAuth.signOut();
        Student.newStudent.clear();
        FlashCardSet.getInstance().clear();
        Database.getInstance().clear(); }

    public void register(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                    registerVal = true;
                } else {
                    registerVal = false;
                }
            }
        });
    }

    public void onStart () {
        instance.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void onStop() {
        instance.onStop();
        if(mAuthListener !=null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
