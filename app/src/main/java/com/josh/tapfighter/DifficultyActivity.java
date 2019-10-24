package com.josh.tapfighter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioGroup;

public class DifficultyActivity extends AppCompatActivity {

    //SharedPreferences handle the values for the game difficulties.
    SharedPreferences dPrefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        //set up shared preferences.
        dPrefs = getSharedPreferences("difficultyPrefs", MODE_PRIVATE);
        editor = dPrefs.edit();

        //force activity into landscape mode.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //radio group containing radio buttons for difficulty selection.
        RadioGroup diffCheck = findViewById(R.id.diffButtons);

        diffCheck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //check ID of the radio button selected, saves difficulty setting accordingly.
                switch(checkedId) {
                    case R.id.easyMode:
                        //default is 3, but we set this up anyway in case difficulty is lowered.
                        editor.putInt("diff_key", 15);
                        editor.commit();
                    break;
                    case R.id.midMode:
                        //sets damage to 6
                        editor.putInt("diff_key", 30);
                        editor.commit();
                    break;
                    case R.id.hardMode:
                        //sets damage to 9.
                        editor.putInt("diff_key", 45);
                        editor.commit();
                }
            }
        });
    }

    //return to main activity
    public void returnToMain(View view) {
        Intent mainMenu = new Intent(this, MainActivity.class);

        startActivity(mainMenu);
    }
}
