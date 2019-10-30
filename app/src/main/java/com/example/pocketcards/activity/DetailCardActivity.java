package com.example.pocketcards.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.pocketcards.database.Database;
import com.example.pocketcards.classes.FlashCardSet;
import com.example.pocketcards.R;
import com.example.pocketcards.extra.UpdateDialog;

//This activity is for expanding a FlashCard and performing actions on it
public class DetailCardActivity extends AppCompatActivity implements UpdateDialog.DialogListener {

    private int listKey;
    public String key;

    private TextView header_text;
    private TextView content_text;
    private TextView diff_text;
    private TextView ans_text;

    private Button edit;
    private Button delete;
    private Button flip;

    private ViewFlipper flipper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        Intent intent = getIntent();
        listKey = intent.getIntExtra("key", 0);

        key = FlashCardSet.getInstance().getArray().get(listKey).getKey();

        String header = FlashCardSet.getInstance().getArray().get(listKey).getHeader();
        String content = FlashCardSet.getInstance().getArray().get(listKey).getContent();
        String diff = FlashCardSet.getInstance().getArray().get(listKey).getDifficulty();
        final String answer = FlashCardSet.getInstance().getArray().get(listKey).getAnswer();
        int color = FlashCardSet.getInstance().getArray().get(listKey).getColor();

        header_text = findViewById(R.id.header_view);
        content_text = findViewById(R.id.contentViewing);
        diff_text = findViewById(R.id.diff_view);
        ans_text = findViewById(R.id.answer_view);

        header_text.setText(header);
        content_text.setText(content);
        diff_text.setText(diff);
        ans_text.setText(answer);

        GradientDrawable bgShape = (GradientDrawable)header_text.getBackground();
        bgShape.setColor(color);
        GradientDrawable bgShape1 = (GradientDrawable)content_text.getBackground();
        bgShape1.setColor(color);
        GradientDrawable bgShape2 = (GradientDrawable)diff_text.getBackground();
        bgShape2.setColor(color);
        GradientDrawable bgShape3 = (GradientDrawable)ans_text.getBackground();
        bgShape3.setColor(color);

        edit = findViewById(R.id.editButton);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        delete = findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCard();
            }
        });

        flipper = findViewById(R.id.viewFlipper);

        flip = findViewById(R.id.flip_button);
        flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer.isEmpty()) {
                    Toast.makeText(DetailCardActivity.this, "There is no answer associated with this Flash Card", Toast.LENGTH_SHORT).show();
                } else {
                    flipper.showNext();
                }

            }
        });
    }

    public void openDialog() { //opens a dialog box to edit flash cards
        int mDefaultColor = FlashCardSet.getInstance().getArray().get(listKey).getColor();
        UpdateDialog ud = new UpdateDialog(mDefaultColor);
        ud.show(getSupportFragmentManager(), "Update here!");
    }


    @Override
    public void updateChanges(String header, String content, String answer, String diff, int color) { //Updates to the database and singleton
        //int color = FlashCardSet.getInstance().getArray().get(listKey).getColor();
        Database.getInstance().editCard(header, content, answer, key, diff, listKey, color);
        Toast.makeText(DetailCardActivity.this, "You updated this card!", Toast.LENGTH_SHORT).show();
        finish();

    }

    public void deleteCard() { //delete from database are singleton
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailCardActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Database.getInstance().deleteCard(key, listKey);
                Toast.makeText(DetailCardActivity.this, "You deleted this flashcard!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
