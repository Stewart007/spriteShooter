package com.Water.WaterFight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Splash {
    Bitmap SplashBM[] = new Bitmap[9];
    int splashF;
    int eX, eY;

    public Splash(Context context, int eX, int eY) {
        SplashBM[0] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.Splash0);
        SplashBM[1] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.Splash1);
        SplashBM[2] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.Splash2);
        SplashBM[3] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.Splash3);
        SplashBM[4] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.Splash4);
        SplashBM[5] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.Splash5);
        SplashBM[6] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.Splash6);
        SplashBM[7] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.Splash7);
        SplashBM[8] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.Splash8);
        splashF = 0;
        this.eX = eX;
        this.eY = eY;
    }

    public Bitmap getSplash(int splashF){
        return SplashBM[splashF];
    }
}
