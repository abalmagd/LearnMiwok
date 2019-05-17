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

public class ColorsActivity extends AppCompatActivity
{
    private MediaPlayer mediaPlayer;
    private MediaPlayer.OnCompletionListener myCompletionListener = new MediaPlayer.OnCompletionListener()
    {
        @Override
        public void onCompletion(MediaPlayer mp)
        {
            releaseMediaPlayer();
        }
    };

    private AudioManager myAudioManager;

    private AudioManager.OnAudioFocusChangeListener myOnAudioFocusChangeListener
            = new AudioManager.OnAudioFocusChangeListener()
    {
        @Override
        public void onAudioFocusChange(int focusChange)
        {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
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
                mediaPlayer.stop();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_components);

        myAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> items = new ArrayList<>();

        items.add(new Word("Red", "Weṭeṭṭi",
                R.drawable.color_red, R.raw.color_red));
        items.add(new Word("Green", "Chokokki",
                R.drawable.color_green, R.raw.color_green));
        items.add(new Word("Brown", "ṭakaakki",
                R.drawable.color_brown, R.raw.color_brown));
        items.add(new Word("Gray", "ṭopoppi",
                R.drawable.color_gray, R.raw.color_gray));
        items.add(new Word("Black", "Kululli",
                R.drawable.color_black, R.raw.color_black));
        items.add(new Word("White", "Kelelli",
                R.drawable.color_white, R.raw.color_white));
        items.add(new Word("Dusty Yellow", "ṭopiisә",
                R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        items.add(new Word("Mustard Yellow", "Chiwiiṭә",
                R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));


        WordAdapter adapter = new WordAdapter(this, 0, items);

        ListView list_tv = findViewById(R.id.list_tv);

        list_tv.setBackgroundColor(getResources().getColor(R.color.colors_category_background));
        list_tv.setAdapter(adapter);

        list_tv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                releaseMediaPlayer();
                Word wordLocation = items.get(position);

                int focusResult = myAudioManager.requestAudioFocus(myOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(focusResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    mediaPlayer = MediaPlayer.create(ColorsActivity.this,
                            wordLocation.getAudioResourceId());
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
