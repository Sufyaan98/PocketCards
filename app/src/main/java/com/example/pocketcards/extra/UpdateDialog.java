package com.example.pocketcards.extra;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.pocketcards.R;
import com.example.pocketcards.activity.CreateActivity;

import yuku.ambilwarna.AmbilWarnaDialog;


@SuppressLint("ValidFragment")
public class UpdateDialog extends AppCompatDialogFragment {
    private EditText editHeader;
    private EditText editContent;
    private EditText editAnswer;
    private Spinner diff_spinner;
    private String diffOption;
    private Button colorPicker;
    int mDefaultColor;
    private DialogListener listener;

    @SuppressLint("ValidFragment")
    public UpdateDialog(int color) {
        mDefaultColor = color;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);

        builder.setView(view)
                .setTitle("Update Details!")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String header = editHeader.getText().toString();
                        String content = editContent.getText().toString();
                        String answer = editAnswer.getText().toString();
                        listener.updateChanges(header, content, answer, diffOption, mDefaultColor);
                    }
                });

        editHeader = view.findViewById(R.id.header_edit);
        editContent = view.findViewById(R.id.content_edit);
        editAnswer = view.findViewById(R.id.answer_edit);

        diff_spinner = view.findViewById(R.id.diffSpinner);
        String[] options = new String[] {"Select Difficulty", "Easy", "Medium", "Hard"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diff_spinner.setAdapter(adapter);

        diff_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diffOption = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        colorPicker = view.findViewById(R.id.picker_button);
        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColourPicker();
            }
        });

        return builder.create();
    }

    public void openColourPicker() {

        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(getActivity(), mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor = color;
            }
        });
        colorPicker.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface DialogListener {
        void updateChanges(String header, String content, String password, String diffOption, int newColor);
    }
}