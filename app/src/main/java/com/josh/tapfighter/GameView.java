package com.josh.tapfighter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameView extends View {

    private Bitmap ring;

    private int width;
    private int height;
    private int timer;
    private int counter;

    private player gamePlayer;
    private healthBar playerHP;
    private enemy gameEnemy;
    private healthBar enemyHP;

    private blueOrb bOrb;

    private blueOrb[] orbs;

    private boolean orbTouched = false;

    private Random r = new Random();

    public GameView(Context context, int d, int w, int h) {
        super(context);

        //sets width & height to screen dimensions.
        this.width = w;
        this.height = h;

        //player and enemy objects.
        gamePlayer = new player(context, 1000, width, height);
        gameEnemy = new enemy(context, 1000, width, height);

        //set player and enemy damage.
        gamePlayer.setDamage(10);
        gameEnemy.setDamage(d);

        //bitmap for ring
        ring = BitmapFactory.decodeResource(getResources(), R.drawable.ring);
        ring = Bitmap.createScaledBitmap(ring, width, height, true);

        //bitmap for player & enemy healthbars
        playerHP = new healthBar(getResources());
        enemyHP = new healthBar(getResources());

        //orb array.
        orbs = new blueOrb[1];

        //bitmap for orb.
        bOrb = new blueOrb(getResources());

        //random x/y coordinates for initial orb.
        bOrb.x = r.nextInt(width - bOrb.width);
        bOrb.y = r.nextInt(height - bOrb.height);

        //send orb to array.
        orbs[0] = bOrb;

    }

    //draw frame.
    @Override
    public void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        update(canvas);
    }

    //user input.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            gamePlayer.setPunch(true);

            for(blueOrb orb : orbs) {
                if(x > orb.x && x < orb.x + orb.width && y > orb.y && y < orb.y + orb.height) {
                    orbTouched = true;
                }
            }
        }
        return gamePlayer.getPunch();
    }

    //update method.
    public void update(Canvas canvas) {
        try {
            //draw background color.
            canvas.drawColor(Color.WHITE);

            //draw ring.
            canvas.drawBitmap(ring,
                    (width / 2) - ring.getWidth() / 2,
                    (height / 2) - (ring.getHeight())/2,
                    null);

            //display HP of both fighters.
            playerHealth(canvas);
            enemyHealth(canvas);

            //animate player.
            playerAction(canvas);

            //animate enemy fighter.
            enemyAction(canvas);

            //spawn orbs.
            createOrb(canvas);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    //creates orbs.
    void createOrb(Canvas canvas) {
        if(counter < 120)
            counter++;

        if(counter == 120) {
            //spawn an orb in a random location.
            for(blueOrb orb : orbs) {
                canvas.drawBitmap(orb.getOrb(), orb.x, orb.y, null);
            }
        }

        if(orbTouched) {
            counter = 0;
            bOrb.x = r.nextInt(width - bOrb.width);
            bOrb.y = r.nextInt(height - bOrb.height);
            gameEnemy.setHp(25);
            orbTouched = false;
        }
    }

    //animates player & applies damage to enemy.
    void playerAction(Canvas canvas) {
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

            gameEnemy.setHp(gamePlayer.getDamage());

            gamePlayer.setPunch(false);

            if(gameEnemy.getHp() <= 0) {
                Intent victory = new Intent(getContext(), VictoryActivity.class);
                victory.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(victory);
            }
        }
    }

    //animates enemy & applies damage to player.
    void enemyAction(Canvas canvas) {
        timer++;

        if(timer == 60) {
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

    //draws & animates player healthbar according to player health.
    void playerHealth(Canvas canvas) {
        int playerHealth = gamePlayer.getHp() / 2;

        if(playerHealth > 0)
            playerHP.createBar(playerHealth, height / 10);
        playerHP.createOutline(500, height / 10);
        canvas.drawBitmap(playerHP.getOutline(),
                0,
                0,
                null);
        canvas.drawBitmap(playerHP.getBar(),
                0,
                0,
                null
        );
    }

    //draws & animates enemy healthbar according to enemy health.
    void enemyHealth(Canvas canvas) {
        int enemyHealth = gameEnemy.getHp() / 2;

        //enemy health
        if(enemyHealth > 0)
            enemyHP.createBar(enemyHealth, height / 10);
        enemyHP.createOutline(500, height / 10);
        canvas.drawBitmap(enemyHP.getOutline(),
                width - enemyHP.getOutline().getWidth(),
                0,
                null
        );
        canvas.drawBitmap(enemyHP.getBar(),
                width - enemyHP.getBar().getWidth(),
                0,
                null);
    }
}
