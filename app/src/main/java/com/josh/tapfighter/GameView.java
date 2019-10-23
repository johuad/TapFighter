package com.josh.tapfighter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    private Bitmap ring;
    private Bitmap playerHealthBar;
    private Bitmap enemyHealthBar;
    private Bitmap healthBarOutline;

    private int width;
    private int height;
    private int timer;

    private player gamePlayer;
    private enemy gameEnemy;

    public GameView(Context context, int d, int w, int h) {
        super(context);

        this.width = w;
        this.height = h;

        //player and enemy objects.
        gamePlayer = new player(context, 1000, width, height);
        gameEnemy = new enemy(context, 1000, width, height);

        //set player and enemy damage.
        //enemy uses value passed in by difficulty settings.
        gamePlayer.setDamage(10);
        gameEnemy.setDamage(d);

        //bitmap for ring
        ring = BitmapFactory.decodeResource(getResources(), R.drawable.ring);
        ring = Bitmap.createScaledBitmap(ring, width, height, true);

        healthBarOutline = BitmapFactory.decodeResource(getResources(), R.drawable.hp2);
        healthBarOutline = Bitmap.createScaledBitmap(healthBarOutline, 500, height / 10, true);

        playerHealthBar = BitmapFactory.decodeResource(getResources(), R.drawable.hp1);

        enemyHealthBar = BitmapFactory.decodeResource(getResources(), R.drawable.hp1);

    }

    @Override
    public void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        update(canvas);

    }

    public void update(Canvas canvas) {
        try {
            //fills in background color
            canvas.drawColor(Color.WHITE);

            //draws ring.
            canvas.drawBitmap(ring,
                    (width / 2) - ring.getWidth() / 2,
                    (height / 2) - (ring.getHeight())/2,
                    null);

            //display HP of both fighters
            canvas.drawBitmap(healthBarOutline,
                    0,
                    0,
                    null
            );

            canvas.drawBitmap(healthBarOutline,
                    width - healthBarOutline.getWidth(),
                    0,
                    null
            );

            int playerHealth = gamePlayer.getHp() / 2;
            int enemyHealth = gameEnemy.getHp() / 2;

            if(playerHealth > 0)
            {
                playerHealthBar = Bitmap.createScaledBitmap(playerHealthBar, playerHealth, height / 10, true);

                canvas.drawBitmap(playerHealthBar,
                        0,
                        0,
                        null
                );
            }

            if(enemyHealth > 0)
            {
                enemyHealthBar = Bitmap.createScaledBitmap(enemyHealthBar, enemyHealth, height / 10, true);

                canvas.drawBitmap(enemyHealthBar,
                        width - enemyHealthBar.getWidth(),
                        0,
                        null
                        );
            }

            //player animation
            if(!gamePlayer.getPunch()) {
                canvas.drawBitmap(gamePlayer.getSprite1(),
                        (width / 2) - (gamePlayer.getWidth() / 4),
                        (height / 2) - (gamePlayer.getHeight() / 3),
                        null);
            }
            else {
                canvas.drawBitmap(gamePlayer.getSprite2(),
                        (width / 2) - (gamePlayer.getWidth() / 4),
                        (height / 2) - (gamePlayer.getHeight() / 3),
                        null);

                gamePlayer.setPunch(false);

                gameEnemy.setHp(gamePlayer.getDamage());

                if(gameEnemy.getHp() <= 0) {
                    Intent victory = new Intent(getContext(), VictoryActivity.class);
                    victory.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getContext().startActivity(victory);
                }

            }

            //enemy animation
            if(!gameEnemy.getPunch()){
                timer++;
            }

            if(timer == 30) {
                canvas.drawBitmap(gameEnemy.getSprite2(),
                        (width / 2) - (gameEnemy.getWidth() / 8),
                        (height / 2) - (gameEnemy.getHeight() / 3),
                        null);
                gameEnemy.setPunch(true);
                gamePlayer.setHp(gameEnemy.getDamage());
                timer = 0;

                if(gamePlayer.getHp() <= 0) {
                    Intent gameOver = new Intent(getContext(), GameOverActivity.class);
                    gameOver.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getContext().startActivity(gameOver);
                }
            }
            else {
                canvas.drawBitmap(gameEnemy.getSprite1(),
                        (width / 2) - gameEnemy.getWidth() / 8,
                        (height / 2) - (gameEnemy.getHeight() / 3),
                        null);
                gameEnemy.setPunch(false);
            }
        }
        catch(Exception e) {

        }
    }

    //method for taking user input.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            gamePlayer.setPunch(true);
        }
        return gamePlayer.getPunch();
    }
}
