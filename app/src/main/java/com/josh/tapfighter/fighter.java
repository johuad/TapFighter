package com.josh.tapfighter;

import android.graphics.Bitmap;

public abstract class fighter {
    //abstract class for fighter objects.

    public fighter() {}

    //abstract methods.
    public abstract void setHp(int h);
    public abstract int getHp();

    public abstract int getWidth();
    public abstract int getHeight();

    public abstract void setPunch(boolean p);
    public abstract boolean getPunch();

    public abstract void setDamage(int d);
    public abstract int getDamage();

    public abstract Bitmap getSprite1();
    public abstract Bitmap getSprite2();
}
