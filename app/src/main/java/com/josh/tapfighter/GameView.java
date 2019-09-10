package com.josh.tapfighter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
    //private member variables for GameView
    private Canvas canvas;
    private Bitmap blufighter;
    private Bitmap blufighter1;
    private Bitmap redfighter;
    private Bitmap redfighter1;
    private Bitmap ring;
    private Paint textPaint;
    private int width;
    private int height;
    private player gamePlayer;
    private enemy gameEnemy;
    private boolean punch = false;
    private boolean ePunch = false;
    private int ePunchTimer = 0;
    private int diff;

    public GameView(Context context, int diff) {
        super(context);

        //sets difficulty every time the game is started.
        this.diff = diff;

        //player and enemy objects.
        gamePlayer = new player(100);
        gameEnemy = new enemy(100);

        //font stuff for displaying HP.
        textPaint = new Paint();
        textPaint.setTextSize(50);
        textPaint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC));
        textPaint.setColor(Color.BLACK);

        //bitmap for ring
        ring = BitmapFactory.decodeResource(getResources(), R.drawable.ring);

        //bitmaps for player
        blufighter = BitmapFactory.decodeResource(getResources(), R.drawable.blufighter);
        blufighter1 = BitmapFactory.decodeResource(getResources(), R.drawable.blufighter1);

        //bitmaps for npc enemy.
        redfighter = BitmapFactory.decodeResource(getResources(), R.drawable.redfighter);
        redfighter1 = BitmapFactory.decodeResource(getResources(), R.drawable.redfighter1);

    }

    @Override
    public void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        //set width and height of canvas.
        width = canvas.getWidth();
        height = canvas.getHeight();

        //fills in background color
        canvas.drawColor(Color.WHITE);

        //display HP of both fighters.
        canvas.drawText("HP: " + this.gamePlayer.getHp(),
                (width/3) - blufighter.getWidth(), (height/10), textPaint);

        canvas.drawText("HP: " + this.gameEnemy.getHp(),
                (width/3) + blufighter.getWidth() * 2, (height/10), textPaint);

        //draws ring.
        canvas.drawBitmap(ring, (width/2) - ring.getWidth()/2,
                (height/2) - (ring.getHeight())/2, null);

        //draws player based on whether or not they are pressing on the screen.
        if(punch) {
            canvas.drawBitmap(blufighter1, (width/2) - blufighter.getWidth(),
                    (height/2) - blufighter.getHeight()/2, null);
            //knock 1 hp off of the enemy every time a punch happens.
            this.gameEnemy.setHp(1);

            if(gameEnemy.getHp() == 0) {
                //if enemy hp = 0 launch victory activity
                Intent victory = new Intent(getContext(), VictoryActivity.class);
                //start new activity and destroy the previous one (hitting back will exit instead
                //of taking you back to the old game screen
                victory.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //start the gameOver activity
                getContext().startActivity(victory);

            }
            //set punch back to false.
            punch = false;
        }
        else {
            //draws the regular non-punching blufighter.
            canvas.drawBitmap(blufighter, (width/2) - blufighter1.getWidth(),
                    (height/2) - blufighter1.getHeight()/2, null);
        }

        //kind of a dirty hack. Increments ePunchTimer and then
        //draws sprites based on the value of the timer, then
        //resets the timer and decrements player HP.
        if(!ePunch)
            ePunchTimer++;

        if(ePunchTimer % 30 == 0) {
            //if ePunchTimer / difficulty has no remainder draw punch sprite.
            canvas.drawBitmap(redfighter1, (width/2) - redfighter1.getWidth()/2,
                    (height/2) - redfighter1.getHeight()/2, null);
            //set ePunchTimer back to 0 so it doesn't get crazy big.
            ePunchTimer = 0;
            //decrement player HP with each punch.
            this.gamePlayer.setHp(diff);

            if(gamePlayer.getHp() <= 0 ) {
                //if player HP = 0 launch game over activity
                Intent gameOver = new Intent(getContext(), GameOverActivity.class);
                //start new activity and destroy the previous one (hitting back will exit instead
                //of taking you back to the old game screen
                gameOver.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //start the gameOver activity
                getContext().startActivity(gameOver);

            }
        }
        else {
            canvas.drawBitmap(redfighter, (width/2) - redfighter1.getWidth()/2,
                    (height/2) - redfighter1.getHeight()/2, null);
        }
    }

    //method for taking user input.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //when the user pushes on the screen it sets punch = true
            //when punch is true onDraw animates the punch and sets punch to false again.
            punch = true;
        }
        return punch;
    }
}
