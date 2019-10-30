package com.example.pocketcards.activity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pocketcards.classes.FlashCardSet;
import com.example.pocketcards.R;
import com.example.pocketcards.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


//This activity is the homepage for the Student, with buttons to other activities
public class HomepageActivity extends AppCompatActivity {
    private static DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private TextView welcome, expand;
    private TextView headerView, contentView, difficultyView;
    private Button createCard, viewCard, uploadButton, downloadButton, quizButton;

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.homepage);

        Intent intent = getIntent();
        String welcomeMsg = intent.getStringExtra("msg");

        welcome = findViewById(R.id.userInfo);

        welcome.setText(welcomeMsg);

        headerView = findViewById(R.id.view_header);
        contentView = findViewById(R.id.view_content);
        difficultyView = findViewById(R.id.view_difficulty);

        final int listIndex = (FlashCardSet.getInstance().getArray().size() -1);

        Log.d("Size", String.valueOf(listIndex));

        if(listIndex ==-1) {
            TextView not_available = findViewById(R.id.notAvailable);
            not_available.setText("Sorry, you have no Flash Cards currently");
        } else {
            headerView.setText(FlashCardSet.getInstance().getArray().get(listIndex).getHeader());
            headerView.setVisibility(View.VISIBLE);
            contentView.setText(FlashCardSet.getInstance().getArray().get(listIndex).getContent());
            contentView.setVisibility(View.VISIBLE);
            difficultyView.setText(FlashCardSet.getInstance().getArray().get(listIndex).getDifficulty());
            difficultyView.setVisibility(View.VISIBLE);

            int color = FlashCardSet.getInstance().getArray().get(listIndex).getColor();

            GradientDrawable bgShape = (GradientDrawable)headerView.getBackground();
            bgShape.setColor(color);
            GradientDrawable bgShape1 = (GradientDrawable)contentView.getBackground();
            bgShape1.setColor(color);
            GradientDrawable bgShape2 = (GradientDrawable)difficultyView.getBackground();
            bgShape2.setColor(color);

            expand = findViewById(R.id.expand_txt);
            expand.setVisibility(View.VISIBLE);

            expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(HomepageActivity.this, DetailCardActivity.class);
                    myIntent.putExtra("key", listIndex);
                    startActivity(myIntent);
                }
            });
        }

        createCard = findViewById(R.id.createFlashCard);
        viewCard = findViewById(R.id.viewFlashCard);
        uploadButton = findViewById(R.id.uploadB);
        downloadButton = findViewById(R.id.downloadB);
        quizButton = findViewById(R.id.quiz_button);

        createCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this, CreateActivity.class));
            }
        });

        viewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this, ViewActivity.class));
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomepageActivity.this, "Sorry, this feature is currently under construction", Toast.LENGTH_SHORT).show();
            }
        });

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomepageActivity.this, "Sorry, this feature is currently under construction", Toast.LENGTH_SHORT).show();
            }
        });

        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomepageActivity.this, "Sorry, this feature is currently under construction", Toast.LENGTH_SHORT).show();
            }
        });
}

    @Override
    public boolean onCreateOptionsMenu (Menu menu) { //creates menu item
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setIcon(R.drawable.user_icon);
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
    } //disables back button
}
