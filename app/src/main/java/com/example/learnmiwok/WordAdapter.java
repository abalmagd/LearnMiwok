package com.example.learnmiwok;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word>
{

    public WordAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Word> word)
    {
        super(context, 0, word);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View numListItem = convertView;
        if (numListItem == null)
        {
            numListItem = LayoutInflater.from(getContext()).inflate(R.layout.num_list_item, parent, false);
        }

        Word currentWord = getItem(position);

        TextView miwok_word_tv = numListItem.findViewById(R.id.miwok_word_tv);
        miwok_word_tv.setText(currentWord.getMiwokTranslation());

        TextView english_word_tv = numListItem.findViewById(R.id.english_word_tv);
        english_word_tv.setText(currentWord.getDefaultTranstlation());

        ImageView img = numListItem.findViewById(R.id.img_word);
        img.setImageResource(currentWord.getImageResourceId());

        return numListItem;
    }
}
