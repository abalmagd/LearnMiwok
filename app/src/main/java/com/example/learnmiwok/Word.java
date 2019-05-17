package com.example.learnmiwok;

import android.media.MediaPlayer;

public class Word
{
    private String defaultTranslation;
    private String miwokTranslation;
    private int imageResourceId;
    private int audioResourceId;

    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId)
    {
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        this.audioResourceId = audioResourceId;
    }

    public Word(String defaultTranstlation, String miwokTranslation, int imageResourceId, int audioResourceId)
    {
        this.defaultTranslation = defaultTranstlation;
        this.miwokTranslation = miwokTranslation;
        this.imageResourceId = imageResourceId;
        this.audioResourceId = audioResourceId;
    }

    public String getDefaultTranstlation()
    {
        return defaultTranslation;
    }

    public String getMiwokTranslation()
    {
        return miwokTranslation;
    }

    public int getImageResourceId()
    {
        return imageResourceId;
    }

    public int getAudioResourceId()
    {
        return audioResourceId;
    }
}
