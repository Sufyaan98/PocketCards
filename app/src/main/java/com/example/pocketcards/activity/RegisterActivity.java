package com.example.pocketcards.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pocketcards.R;
import com.example.pocketcards.classes.Student;
import com.example.pocketcards.classes.Teacher;
import com.example.pocketcards.classes.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

//This login handles registering using Firebase
public class RegisterActivity extends AppCompatActivity {
    EditText etEmail, etPassword, fName, sName;
    Button register;
    RadioGroup radioGroup;
    RadioButton radioType;

    private static DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        fName = findViewById(R.id.first_name);
        sName = findViewById(R.id.second_name);

        radioGroup = findViewById(R.id.type_group);

        register = findViewById(R.id.btnRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    public void registerUser() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String forename = fName.getText().toString();
        String surname = sName.getText().toString();

        int selectedID = radioGroup.getCheckedRadioButtonId();
        radioType = findViewById(selectedID);
        String userType = radioType.getText().toString();

        if(email.isEmpty()) {
            etEmail.setError("Please enter an email");
            etEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return;
        }


        if (password.isEmpty()) {
            etPassword.setError("Please enter a password");
            etPassword.requestFocus();
            return;
        }

        if(password.length()<6) {
            etPassword.setError("Minimum password length is 6");
            etPassword.requestFocus();
            return;
        }

        User.getInstance().register(email, password);

        if(userType.equals("Teacher")) {
            Random codeRandom = new Random();
            int code = codeRandom.nextInt(9000) + 1000;
            Teacher teacher = new Teacher(forename, surname, String.valueOf(code));
        } else {
            Student student = new Student(forename, surname);
        }
        Log.d("Created", Student.newStudent.getFirstName() + " " + Student.newStudent.getSecondName());
        Log.d("Accessed 2", "ACCESSED HERE");

        Toast.makeText(RegisterActivity.this, "Successfully created user!", Toast.LENGTH_SHORT);

        finish();
    }
}
