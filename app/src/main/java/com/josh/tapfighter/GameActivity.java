package com.josh.tapfighter;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {
    private GameView game; //game object
    private HandlerThread gameThread = new HandlerThread("GameThread");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //force landscape mode.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //get screen dimensions.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //Get difficulty.
        SharedPreferences dPrefs = getSharedPreferences("difficultyPrefs",
                DifficultyActivity.MODE_PRIVATE);

        int gameDiff = dPrefs.getInt("diff_key", 3);

        //create game object
        game = new GameView(this, gameDiff, width, height);

        //set up view.
        setContentView(game);

        //start game thread.
        gameThread.start();

        //set up the handler to run the game.
        final Handler gameHandler = new Handler(gameThread.getLooper());

        //runnable to run the game.
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
               game.invalidate();
               gameHandler.postDelayed(this, 1000 / 60);
            }
        };

        //start the runnable.
        runnable.run();
    }

}
