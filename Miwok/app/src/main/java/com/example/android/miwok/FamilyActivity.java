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

public class FamilyActivity extends AppCompatActivity {

    AudioManager myManager;
    AudioManager.OnAudioFocusChangeListener afChangeListener;
    MediaPlayer myPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        myManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Word> words = getWords();
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);
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
                myPlayer = MediaPlayer.create(FamilyActivity.this, words.get(i).getAudioID());
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

        words.add(new Word("ede", "father", R.raw.family_father, R.drawable.family_father));
        words.add(new Word("eta", "mother", R.raw.family_mother, R.drawable.family_mother));
        words.add(new Word("angsi", "son", R.raw.family_son, R.drawable.family_son));
        words.add(new Word("tune", "daughter", R.raw.family_daughter, R.drawable.family_daughter));
        words.add(new Word("taachi", "older brother", R.raw.family_older_brother, R.drawable.family_older_brother));
        words.add(new Word("chalitti", "younger brother", R.raw.family_younger_brother, R.drawable.family_younger_brother));
        words.add(new Word("tete","older sister", R.raw.family_older_sister, R.drawable.family_older_sister));
        words.add(new Word("kolliti", "younger sister", R.raw.family_younger_sister, R.drawable.family_younger_sister));
        words.add(new Word("ama","grandmother", R.raw.family_grandmother, R.drawable.family_grandmother));
        words.add(new Word("paapa", "grandfather", R.raw.family_grandfather, R.drawable.family_grandfather));

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
