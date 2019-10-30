package com.example.pocketcards.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pocketcards.R;
import com.example.pocketcards.classes.User;

//This is the homepage activity for the Teacher
public class AdminActivity extends AppCompatActivity {
    private TextView teacher_info;
    private Button review, upload, view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);

        Intent intent = getIntent();
        String welcomeMsg = intent.getStringExtra("msg");
        String code = intent.getStringExtra("code");
        teacher_info = findViewById(R.id.teacherInfo);
        teacher_info.setText(welcomeMsg);
        TextView schoolCode = findViewById(R.id.school_code);
        schoolCode.setText("School Code: " + code);

        review = findViewById(R.id.review_button);
        upload = findViewById(R.id.upload_button);
        view = findViewById(R.id.view_students_button);

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminActivity.this, "Sorry, this page is currently under construction!", Toast.LENGTH_SHORT).show();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminActivity.this, "Sorry, this page is currently under construction!", Toast.LENGTH_SHORT).show();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminActivity.this, "Sorry, this page is currently under construction!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        getSupportActionBar().setIcon(R.drawable.user_icon);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        User.getInstance().logOut();
        Toast.makeText(this, "You successfully logged out!", Toast.LENGTH_SHORT).show();
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
