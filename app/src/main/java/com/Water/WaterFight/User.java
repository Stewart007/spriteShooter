package com.Water.WaterFight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class User {
    Context context;
    Bitmap userP;
    int ox, oy;
    Random random;

    public User(Context context) {
        this.context = context;
        userP = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
        random = new Random();
        ox = random.nextInt(WaterFight.screenWidth);
        oy = WaterFight.screenHeight - userP.getHeight();
    }

    public Bitmap getUserP(){
        return userP;
    }

    int getOurUserW(){
        return userP.getWidth();
    }
}
