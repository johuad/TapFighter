package com.josh.tapfighter;

public class enemy extends fighter {

    //private member variables
    private int hp;

    public enemy(int hp) {
        super(hp);
        this.hp = hp;
    }

    @Override
    public void setHp(int h) {
        //decrement enemy HP by h.
        this.hp = hp - h;
    }

    @Override
    public int getHp() {
        //return hp
        return hp;
    }
}
