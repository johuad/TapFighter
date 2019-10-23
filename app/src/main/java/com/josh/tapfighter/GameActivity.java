package com.josh.tapfighter;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {
    private GameView game; //game object
    private Handler gameHandler = new Handler(); //creates handler to run the game

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set up a call to SharedPreferences
        SharedPreferences dPrefs = getSharedPreferences("difficultyPrefs",
                DifficultyActivity.MODE_PRIVATE);

        //set gameDiff to the value stored in SharedPreferences, gameDiff used in game constructor.
        int gameDiff = dPrefs.getInt("diff_key", 3);

        //set up display to get screen dimensions.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //create game object
        game = new GameView(this, gameDiff, width, height);
        setContentView(game);

        //force activity into landscape mode.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Handler to run the game.
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
               game.invalidate();
               gameHandler.postDelayed(this, 0);
            }
        };

        runnable.run();
    }
}
