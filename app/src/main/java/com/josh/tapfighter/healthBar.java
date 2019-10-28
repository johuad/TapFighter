package com.josh.tapfighter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class healthBar {

    private Bitmap bar;
    private Bitmap outline;

    healthBar(Resources res) {

        bar = BitmapFactory.decodeResource(res, R.drawable.hp1);
        outline = BitmapFactory.decodeResource(res, R.drawable.hp2);

    }

    void createOutline(int w, int h) {
        outline = Bitmap.createScaledBitmap(outline, w, h, true);
    }

    Bitmap getOutline() {
        return outline;
    }

    void createBar(int w, int h) {
        bar = Bitmap.createScaledBitmap(bar, w, h, true);
    }

    Bitmap getBar() {
        return bar;
    }
}
