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

public class FamilyActivity extends AppCompatActivity
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

    private AudioManager.OnAudioFocusChangeListener myOnAudiFocusChangeListener = new AudioManager.OnAudioFocusChangeListener()
    {
        @Override
        public void onAudioFocusChange(int focusChange)
        {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
            {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN)
            {
                mediaPlayer.start();
            }
            else
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

        items.add(new Word("Father", "әpә",
                R.drawable.family_father, R.raw.family_father));
        items.add(new Word("Mother", "әṭa",
                R.drawable.family_mother, R.raw.family_mother));
        items.add(new Word("Son", "Angsi",
                R.drawable.family_son, R.raw.family_son));
        items.add(new Word("Daughter", "Tune",
                R.drawable.family_daughter, R.raw.family_daughter));
        items.add(new Word("Older Brother", "Taachi",
                R.drawable.family_older_brother, R.raw.family_older_brother));
        items.add(new Word("Younger Brother", "Chalitti",
                R.drawable.family_younger_brother, R.raw.family_younger_brother));
        items.add(new Word("Older Sister", "Teṭe",
                R.drawable.family_older_sister, R.raw.family_older_sister));
        items.add(new Word("Younger Sister", "Kolliti",
                R.drawable.family_younger_sister, R.raw.family_younger_sister));
        items.add(new Word("Grandmother", "Ama",
                R.drawable.family_grandmother, R.raw.family_grandmother));
        items.add(new Word("Grandfather", "Paapa",
                R.drawable.family_grandfather, R.raw.family_grandfather));


        WordAdapter adapter = new WordAdapter(this, 0, items);

        ListView list_tv = findViewById(R.id.list_tv);

        list_tv.setBackgroundColor(getResources().getColor(R.color.family_category_background));
        list_tv.setAdapter(adapter);

        list_tv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                releaseMediaPlayer();
                Word wordLocation = items.get(position);
                int result = myAudioManager.requestAudioFocus(myOnAudiFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    mediaPlayer = MediaPlayer.create(FamilyActivity.this,
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
        myAudioManager.abandonAudioFocus(myOnAudiFocusChangeListener);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        releaseMediaPlayer();
    }
}
