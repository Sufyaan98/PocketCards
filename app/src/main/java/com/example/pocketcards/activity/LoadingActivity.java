package com.example.pocketcards.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.pocketcards.R;
import com.example.pocketcards.database.Database;
import com.example.pocketcards.classes.FlashCardSet;
import com.example.pocketcards.classes.Student;
import com.example.pocketcards.classes.Teacher;
import com.example.pocketcards.classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//Does preloading for the app to retrieve information from the database and uses algorithms accordingly
public class LoadingActivity extends Activity {
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    String welcomeMessage = "";
    String schoolCode = "";
    Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        Intent intent = getIntent();
        final String userType = intent.getStringExtra("User");

        //Algorithms to check if the Student/Teacher object is already saved in the database, otherwise create it
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot snapshot = dataSnapshot.child(User.getInstance().getUserID());
                if(userType.equals("Student")) {
                    if(Student.newStudent.getFirstName() == null) {
                        welcomeMessage = snapshot.getValue(Student.class).getFirstName() + " " + snapshot.getValue(Student.class).getSecondName() + "!";
                    } else {
                        DatabaseReference addRef = myRef.child(User.getInstance().getUserID());
                        addRef.setValue(Student.newStudent);
                        welcomeMessage = Student.newStudent.getFirstName() + " " + Student.newStudent.getSecondName() + "!";
                    }
                    myIntent = new Intent(LoadingActivity.this, HomepageActivity.class);
                    myIntent.putExtra("msg", welcomeMessage);
                    startActivity(myIntent);
                    finish();

                } else {
                    if(Teacher.newTeacher.getFirstName() == null) {
                        welcomeMessage = snapshot.getValue(Teacher.class).getFirstName() + " " + snapshot.getValue(Teacher.class).getSecondName() + "!";
                        schoolCode = snapshot.getValue(Teacher.class).getSchoolCode();
                    } else  {
                        DatabaseReference addRef = myRef.child(User.getInstance().getUserID());
                        addRef.setValue(Teacher.newTeacher);
                        welcomeMessage = Teacher.newTeacher.getFirstName() + " " + Teacher.newTeacher.getSecondName() + "!";
                        schoolCode = snapshot.getValue(Teacher.class).getSchoolCode();
                    }
                    myIntent = new Intent(LoadingActivity.this, AdminActivity.class);
                    myIntent.putExtra("msg", welcomeMessage);
                    myIntent.putExtra("code", schoolCode);
                    startActivity(myIntent);
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        FlashCardSet.init();
        Database.init();
    }
}
