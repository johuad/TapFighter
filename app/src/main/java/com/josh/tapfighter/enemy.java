package com.josh.tapfighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class enemy extends fighter {

    //private member variables
    private int hp; //fighter hit points.
    private int width;
    private int height;

    private Bitmap frame1;
    private Bitmap frame2;

    private boolean punch;
    private int damage;

    enemy(Context context, int hp, int width, int height) {
        super();
        this.hp = hp;
        this.width = width;
        this.height = height;

        frame1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.redfighter);
        frame2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.redfighter1);

    }

    @Override
    public void setHp(int h) {
        //decrement player hp by h
        this.hp = hp - h;
    }

    @Override
    public int getHp() {
        //return hp.
        return hp;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setPunch(boolean p) {
        punch = p;
    }

    @Override
    public boolean getPunch() {
        return punch;
    }

    @Override
    public void setDamage(int d) {
        damage = d;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public Bitmap getSprite1() {
        frame1 = Bitmap.createScaledBitmap(frame1, width / 3,
                height / 2, true);
        return frame1;
    }

    @Override
    public Bitmap getSprite2(){
        frame2 = Bitmap.createScaledBitmap(frame2, width / 3,
                height / 2, true);
        return frame2;
    }
}
