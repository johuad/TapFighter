package com.josh.tapfighter;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    //main menu activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //force activity into landscape mode.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    }

    public void startGame(View view) {
        Intent startGame = new Intent(this, GameActivity.class);
        startActivity(startGame);
    }

    public void startDiffAct(View view) {
        Intent setDiff = new Intent(this, DifficultyActivity.class);
        startActivity(setDiff);
    }
}
