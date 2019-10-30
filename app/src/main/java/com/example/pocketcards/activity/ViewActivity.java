package com.example.pocketcards.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pocketcards.classes.FlashCard;
import com.example.pocketcards.classes.FlashCardSet;
import com.example.pocketcards.R;
import com.example.pocketcards.extra.ViewAdapter;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

//This activity handles viewing the flash cards, sortign them, and clicking on a flash card to view a detailed view
public class ViewActivity extends AppCompatActivity {

    ListView list_view;
    Spinner sortBy;
    Button sort_button, reset_button;
    String diffSelected;
    ViewAdapter adapter;
    EditText filter;

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.view_card);

        FirebaseApp.initializeApp(this);

        list_view = findViewById(R.id.listView);
        sort_button = findViewById(R.id.sortButton);
        reset_button = findViewById(R.id.resetButton);

        sortBy = findViewById(R.id.sortOption);
        String[] options = new String[] {"Select Difficulty", "Easy", "Medium", "Hard"};

        ArrayAdapter<String> spinAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBy.setAdapter(spinAdapter);

        adapter = new ViewAdapter(this, R.layout.adapter_view_layout_card, FlashCardSet.getInstance().getArray());
        int count = adapter.getCount();
        if(count == 0) {
            Log.d("Count", "Accessed");
            TextView empty = findViewById(R.id.empty_view);
            empty.setText("You have no Flash Cards currently!");
            empty.setVisibility(View.VISIBLE);
        }
        list_view.setAdapter(adapter);

        filter = findViewById(R.id.searchFilter);

        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (ViewActivity.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adapter.notifyDataSetChanged();

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent myIntent = new Intent(ViewActivity.this, DetailCardActivity.class);
                myIntent.putExtra("key", i);
                startActivity(myIntent);
            }
        });

        sort_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortData();
            }
        });

        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = new ViewAdapter(ViewActivity.this, R.layout.adapter_view_layout_card, FlashCardSet.getInstance().getArray());
                list_view.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        sortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("Select Difficulty")) {
                    diffSelected = null;
                } else {
                    diffSelected = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void sortData() {
        ArrayList<FlashCard> sortedList = new ArrayList<>();
        if(diffSelected == null) {
            Toast.makeText(this, "Pick a difficulty option!", Toast.LENGTH_SHORT).show();
            return;
        }
        for(int i = 0; i<FlashCardSet.getInstance().getArray().size(); i++) {
            if(FlashCardSet.getInstance().getArray().get(i).getDifficulty().equals(diffSelected)) {
                sortedList.add(FlashCardSet.getInstance().getArray().get(i));
            }
        }
        adapter = new ViewAdapter(this, R.layout.adapter_view_layout_card, sortedList);
        list_view.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}