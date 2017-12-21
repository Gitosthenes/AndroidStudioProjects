package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {

    private AudioManager myManager;

    private MediaPlayer myPlayer;

    private AudioManager.OnAudioFocusChangeListener afChangeListener = focusChange -> {
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

    private MediaPlayer.OnCompletionListener myCompletionListener = mediaPlayer -> {
        releaseMediaPlayer();
    };

    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        myManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Word> words = getWords();
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_colors);
        ListView listView = rootView.findViewById(R.id.list_view);
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
                myPlayer = MediaPlayer.create(getActivity(), words.get(i).getAudioID());
                myPlayer.start();
                myPlayer.setOnCompletionListener(player -> releaseMediaPlayer());
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private ArrayList<Word> getWords() {
        ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("wetetti", "red", R.raw.color_red, R.drawable.color_red));
        words.add(new Word("chokokki", "green", R.raw.color_green, R.drawable.color_green));
        words.add(new Word("takaakki", "brown", R.raw.color_brown, R.drawable.color_brown));
        words.add(new Word("topoppi", "gray", R.raw.color_gray, R.drawable.color_gray));
        words.add(new Word("kululli", "black", R.raw.color_black, R.drawable.color_black));
        words.add(new Word("kelelli", "white", R.raw.color_white, R.drawable.color_white));
        words.add(new Word("topiise","dusty yellow", R.raw.color_dusty_yellow, R.drawable.color_dusty_yellow));
        words.add(new Word("chiwiite", "mustard yellow", R.raw.color_mustard_yellow, R.drawable.color_mustard_yellow));

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
