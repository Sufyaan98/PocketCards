package com.example.pocketcards.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pocketcards.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//This activity handles the login using Firebase
public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button login;
    private TextView register;
    private RadioGroup radioGroup;
    private RadioButton radioType;
    private String userType;
    private ImageView logo;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);

        logo = findViewById(R.id.logo_image);
        int imageResource = getResources().getIdentifier("@drawable/pc_logo", null, this.getPackageName());
        logo.setImageResource(imageResource);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        register = findViewById(R.id.tvRegisterhere);
        login = findViewById(R.id.btnLogin);

        radioGroup = findViewById(R.id.radioGroup2);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    public void userLogin() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        int selectedID = -1;
        selectedID = radioGroup.getCheckedRadioButtonId();
        if(selectedID == -1) {
            Toast.makeText(LoginActivity.this, "Please select if you are a Student or Teacher!", Toast.LENGTH_SHORT).show();
            return;
        }

        radioType = findViewById(selectedID);
        userType = radioType.getText().toString();

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

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent myIntent = new Intent(LoginActivity.this, LoadingActivity.class);
                    myIntent.putExtra("User", userType);
                    startActivity(myIntent);
                    etEmail.setText("");
                    etPassword.setText("");
                } else {
                    etEmail.setError("Wrong email!");
                    etPassword.setError("Wrong password!");
                    etPassword.requestFocus();
                }
            }
        });
    }
}
