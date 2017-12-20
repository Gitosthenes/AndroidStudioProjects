package com.example.android.musicplayerdemo;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer player = MediaPlayer.create(this, R.raw.confetti);
        Button play = findViewById(R.id.play);
        Button pause = findViewById(R.id.pause);

        play.setOnClickListener(view -> player.start());

        pause.setOnClickListener(view -> player.pause());
    }
}