package com.example.android.courtcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int myTeamAScore;
    private int myTeamBScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void reset(View view) {
        myTeamAScore = 0;
        myTeamBScore = 0;

        displayTeamAScore();
        displayTeamBScore();
    }

    public void onePoint(View view) {
        if (view.getId() == R.id.team_a_1) {
            myTeamAScore += 1;
            displayTeamAScore();
        } else {
            myTeamBScore += 1;
            displayTeamBScore();
        }
    }

    public void twoPoints(View view) {
        if (view.getId() == R.id.team_a_2) {
            myTeamAScore += 2;
            displayTeamAScore();
        } else {
            myTeamBScore += 2;
            displayTeamBScore();
        }
    }

    public void threePoints(View view) {
        if (view.getId() == R.id.team_a_3) {
            myTeamAScore += 3;
            displayTeamAScore();
        } else {
            myTeamBScore += 3;
            displayTeamBScore();
        }
    }

    private void displayTeamAScore() {
        final TextView view = findViewById(R.id.team_a_score);
        view.setText(Integer.toString(myTeamAScore));
    }

    private void displayTeamBScore() {
        final TextView view = findViewById(R.id.team_b_score);
        view.setText(Integer.toString(myTeamBScore));
    }
}