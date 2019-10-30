package com.example.pocketcards.classes;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Student extends User {

    public static String forename;
    public static String surname;
    public static Student newStudent = new Student(forename, surname);

    public Student(String fName, String sName) {
        forename = fName;
        surname = sName;
    }

    public Student() {}

    public void clear() {
        forename = null;
        surname = null;
    }

    public String getFirstName() {
        return forename;
    }

    public void setFirstName(String forename) {
        this.forename = forename;
    }

    public String getSecondName() {
        return surname;
    }

    public void setSecondName(String surname) {
        this.surname = surname;
    }

    public String StudentID() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = null;
        if(user !=null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            boolean emailVerified = user.isEmailVerified();

            uid = user.getUid();
        }
        return uid;
    }
}
