package com.josh.tapfighter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class blueOrb {

    private Bitmap orb;

    int width;
    int height;

    int x;
    int y;

    private Random r = new Random();

    public blueOrb(Resources res) {

        orb = BitmapFactory.decodeResource(res, R.drawable.blueorb);

        width = orb.getWidth();
        height = orb.getHeight();

        orb = Bitmap.createScaledBitmap(orb, width, height, true);

        x = -width;
        y = -height;
    }

    public Bitmap getOrb() {
        return orb;
    }



}
