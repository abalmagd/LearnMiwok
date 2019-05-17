package com.example.learnmiwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity
{
    private MediaPlayer mediaPlayer;
    private MediaPlayer.OnCompletionListener myCompletionListener = new MediaPlayer.OnCompletionListener()
    {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer)
        {
            releaseMediaPlayer();
        }
    };

    private AudioManager myAudioManager;

    private AudioManager.OnAudioFocusChangeListener myOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener()
    {
        @Override
        public void onAudioFocusChange(int focusChange)
        {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
            {
                mediaPlayer.start();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
            {
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_components);

        myAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> items = new ArrayList<Word>();

        items.add(new Word("One", "Lutti",
                R.drawable.number_one, R.raw.number_one));
        items.add(new Word("Two", "Otiiku",
                R.drawable.number_two, R.raw.number_two));
        items.add(new Word("Three", "Tolookoosu",
                R.drawable.number_three, R.raw.number_three));
        items.add(new Word("Four", "Oyyisa",
                R.drawable.number_four, R.raw.number_four));
        items.add(new Word("Five", "Massokka",
                R.drawable.number_five, R.raw.number_five));
        items.add(new Word("Six", "Temmokka",
                R.drawable.number_six, R.raw.number_six));
        items.add(new Word("Seven", "Kenekaku",
                R.drawable.number_seven, R.raw.number_seven));
        items.add(new Word("Eight", "Kawinta",
                R.drawable.number_eight, R.raw.number_eight));
        items.add(new Word("Nine", "Wo'e",
                R.drawable.number_nine, R.raw.number_nine));
        items.add(new Word("Ten", "Na'aacha",
                R.drawable.number_ten, R.raw.number_ten));


        WordAdapter adapter = new WordAdapter(this, 0, items);

        ListView list_tv = findViewById(R.id.list_tv);

        list_tv.setBackgroundColor(getResources().getColor(R.color.numbers_category_background));
        list_tv.setAdapter(adapter);

        list_tv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {

                releaseMediaPlayer();

                Word word = items.get(position);

                int result = myAudioManager.requestAudioFocus(myOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceId());

                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(myCompletionListener);
                }
            }
        });
    }

    private void releaseMediaPlayer()
    {
        if (mediaPlayer != null)
            mediaPlayer.release();

        mediaPlayer = null;

        myAudioManager.abandonAudioFocus(myOnAudioFocusChangeListener);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        releaseMediaPlayer();
    }
}
