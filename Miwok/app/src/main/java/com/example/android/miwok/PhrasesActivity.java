/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    AudioManager myManager;
    AudioManager.OnAudioFocusChangeListener afChangeListener;
    MediaPlayer myPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        myManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Word> words = getWords();
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            releaseMediaPlayer();

            afChangeListener = focusChange -> {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT
                        || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    myPlayer.pause();
                    myPlayer.seekTo(0);
                } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    releaseMediaPlayer();
                } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                    myPlayer.start();
                }
            };
            int result = myManager.requestAudioFocus(afChangeListener,
                    AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                myPlayer = MediaPlayer.create(PhrasesActivity.this, words.get(i).getAudioID());
                myPlayer.start();
                myPlayer.setOnCompletionListener(player -> releaseMediaPlayer());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private ArrayList<Word> getWords() {
        ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("minto wuksus?", "Where are you going?", R.raw.phrase_where_are_you_going));
        words.add(new Word("tinne oyaase'ne?", "What is your name?", R.raw.phrase_what_is_your_name));
        words.add(new Word("oyaaset...", "My name is...", R.raw.phrase_my_name_is));
        words.add(new Word("michekses?", "How are you feeling?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("kuchi achit", "I'm feeling good.", R.raw.phrase_im_feeling_good));
        words.add(new Word("eenes'aa?", "Are you coming?", R.raw.phrase_are_you_coming));
        words.add(new Word("hee'eenem","Yes, I am coming.", R.raw.phrase_yes_im_coming));
        words.add(new Word("eenem", "I'm coming", R.raw.phrase_im_coming));
        words.add(new Word("yoowutis","Let's go.", R.raw.phrase_lets_go));
        words.add(new Word("enni'nem", "Come here.", R.raw.phrase_come_here));

        return words;
    }

    private void releaseMediaPlayer() {
        if (myPlayer != null) {
            myPlayer.release();
            myPlayer = null;
            myManager.abandonAudioFocus(afChangeListener);
        }
    }
}
