package com.example.android.miwok;

public class Word {

    private String myMiwok;
    private String myEnglish;
    private int myAudioID;
    private int myImgSrcID;

    public Word(String miwok, String english, int audioID) {
        this(miwok, english, audioID, android.R.color.transparent);
    }

    public Word(String miwok, String english, int audioID, int imgSrcID) {
        myMiwok = miwok;
        myEnglish = english;
        myAudioID = audioID;
        myImgSrcID = imgSrcID;
    }

    public String getMiwok() {
        return myMiwok;
    }

    public String getEnglish() {
        return myEnglish;
    }

    public int getAudioID() {
        return myAudioID;
    }

    public int getImgSrcID() {
        return myImgSrcID;
    }
}