package com.josh.tapfighter;

public class player extends fighter {

    //private member variables
    private int hp;

    public player(int hp) {
        super(hp);
        this.hp = hp;
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
}
