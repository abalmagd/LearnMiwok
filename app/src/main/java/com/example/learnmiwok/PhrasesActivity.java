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

public class PhrasesActivity extends AppCompatActivity
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

        items.add(new Word("Where are you going?", "Minto wuksus?",
                R.raw.phrase_where_are_you_going));
        items.add(new Word("What is your name?", "Tinnә oyaase'nә?",
                R.raw.phrase_what_is_your_name));
        items.add(new Word("My name is...", "Oyaaset...",
                R.raw.phrase_my_name_is));
        items.add(new Word("How are you feeling?", "Michәksәs?",
                R.raw.phrase_how_are_you_feeling));
        items.add(new Word("I'm feeling good.", "Kuchi achit",
                R.raw.phrase_im_feeling_good));
        items.add(new Word("Are you coming?", "әәnәs'aa?",
                R.raw.phrase_are_you_coming));
        items.add(new Word("Yes, I'm coming.", "Hәә’ әәnәm",
                R.raw.phrase_yes_im_coming));
        items.add(new Word("I'm coming.", "әәnәm",
                R.raw.phrase_im_coming));
        items.add(new Word("Let's go.", "Yoowutis",
                R.raw.phrase_lets_go));
        items.add(new Word("Come here.", "әnni'nem",
                R.raw.phrase_come_here));


        WordAdapter adapter = new WordAdapter(this, 0, items);

        ListView list_tv = findViewById(R.id.list_tv);

        list_tv.setBackgroundColor(getResources().getColor(R.color.phrases_category_background));
        list_tv.setAdapter(adapter);

        list_tv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                releaseMediaPlayer();
                Word wordLocation = items.get(position);
                int focusResult = myAudioManager.requestAudioFocus(myOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (focusResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this,
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
