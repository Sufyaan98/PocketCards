package com.example.pocketcards.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.pocketcards.database.Database;
import com.example.pocketcards.R;

import yuku.ambilwarna.AmbilWarnaDialog;

//Create activity for making FlashCard objects and storing them in the Firebase database
public class CreateActivity extends AppCompatActivity {

    private Button createCardButton, colourButton;
    private EditText cardHeader;
    private EditText cardContent;
    private EditText cardAnswer;
    private Spinner diff;
    private String diffOption;

    int mDefaultColour;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_card);

        diff = findViewById(R.id.difficultySpinner);
        String[] options = new String[] {"Select Difficulty", "Easy", "Medium", "Hard"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diff.setAdapter(adapter);

        diff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diffOption = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        createCardButton = findViewById(R.id.createButton);
        cardHeader = findViewById(R.id.headerEdit);
        cardContent = findViewById(R.id.contentEdit);
        cardAnswer = findViewById(R.id.answerEdit);

        colourButton = findViewById(R.id.colour_button);
        mDefaultColour = ContextCompat.getColor(CreateActivity.this, R.color.colorPrimary);
        colourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColourPicker();
            }
        });

        createCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String card_header = cardHeader.getText().toString();
                String card_content = cardContent.getText().toString();
                String card_answer = cardAnswer.getText().toString();

                if((card_header.equals(""))) {
                    Toast.makeText(CreateActivity.this, "You need to fill out he required fields!", Toast.LENGTH_LONG).show();
                    return;
                }
                if((card_content.equals(""))) {
                    Toast.makeText(CreateActivity.this, "You need to fill out he required fields!", Toast.LENGTH_LONG).show();
                    return;
                }
                if((diffOption.equals("Select Difficulty"))) {
                    Toast.makeText(CreateActivity.this, "You need to fill out he required fields!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(mDefaultColour == R.color.colorPrimary) {
                    Toast.makeText(CreateActivity.this, "You need to fill out he required fields!", Toast.LENGTH_LONG).show();
                    return;
                }
                Database.getInstance().addCard(card_header, card_content, card_answer, diffOption, mDefaultColour);

                Toast.makeText(CreateActivity.this, "Flash Card created!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void openColourPicker() { //Colour picker method
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mDefaultColour, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColour = color;
            }
        });
        colorPicker.show();
    }
}

