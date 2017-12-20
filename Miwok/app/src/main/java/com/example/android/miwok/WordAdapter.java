package com.example.android.miwok;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int myColor;

    public WordAdapter(Activity context, ArrayList<Word> words, int color) {
        super(context, 0, words);
        myColor = ContextCompat.getColor(getContext(), color);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        /** Check if the existing view is being reused, otherwise inflate the view */
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);

        View container = listItemView.findViewById(R.id.word_background_linear_layout);
        ImageView imageView = listItemView.findViewById(R.id.img);
        TextView miwokTextView = listItemView.findViewById(R.id.text_top);
        TextView englishTextView = listItemView.findViewById(R.id.text_bot);

                container.setBackgroundColor(myColor);
        miwokTextView.setText(currentWord.getMiwok());
        englishTextView.setText(currentWord.getEnglish());
        imageView.setImageResource(currentWord.getImgSrcID());
        if (currentWord.getImgSrcID() == android.R.color.transparent) {
            imageView.setVisibility(View.GONE);
        }

        return listItemView;
    }
}
