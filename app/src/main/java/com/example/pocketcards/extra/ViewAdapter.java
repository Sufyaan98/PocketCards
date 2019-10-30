package com.example.pocketcards.extra;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pocketcards.R;
import com.example.pocketcards.classes.FlashCard;

import java.util.ArrayList;

public class ViewAdapter extends ArrayAdapter<FlashCard> {

    private Context mContext;
    private int mResource;
    private String header;
    private String content;
    private String difficulty;
    private int mColor;

    public ViewAdapter(Context context, int resource, ArrayList<FlashCard> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        mColor = getItem(position).getColor();
        header = getItem(position).getHeader();
        content = getItem(position).getContent();
        difficulty = getItem(position).getDifficulty();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView headerL = convertView.findViewById(R.id.headerView);
        TextView contentL = convertView.findViewById(R.id.contentView);
        TextView difficultyL = convertView.findViewById(R.id.diffView);

        headerL.setText(header);
        contentL.setText(content);
        difficultyL.setText(difficulty);

        GradientDrawable bgShape = (GradientDrawable)headerL.getBackground();
        bgShape.setColor(mColor);
        GradientDrawable bgShape1 = (GradientDrawable)contentL.getBackground();
        bgShape1.setColor(mColor);
        GradientDrawable bgShape2 = (GradientDrawable)difficultyL.getBackground();
        bgShape2.setColor(mColor);

        return convertView;
    }

    public String getAdapterHeader() {
        return header;
    }
}
