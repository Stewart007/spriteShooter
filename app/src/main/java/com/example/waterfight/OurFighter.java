package com.example.waterfight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class OurFighter {
    Context context;
    Bitmap ourFighter;
    int ox, oy;
    Random random;

    public OurFighter(Context context) {
        this.context = context;
        ourFighter = BitmapFactory.decodeResource(context.getResources(), R.drawable.user);
        random = new Random();
        ox = random.nextInt(waterfight.screenWidth);
        oy = waterfight.screenHeight - ourFighter.getHeight();
    }

    public Bitmap getOurFighter(){
        return ourFighter;
    }

    int getOurFighterWidth(){
        return ourFighter.getWidth();
    }
}
