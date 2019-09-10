package com.josh.tapfighter;

public abstract class fighter {
    //abstract class for fighter objects.

    private int hp; //fighter hit points.

    public fighter(int hp) {}

    //abstract methods.
    public abstract void setHp(int h);
    public abstract int getHp();
}
