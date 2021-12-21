package com.Water.WaterFight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class EnemyPlayer {
    Context context;
    Bitmap enemySpaceship;
    int ex, ey;
    int enemyVelocity;
    Random random;
///////// create enemy to move on screen//////////
    public EnemyPlayer(Context context) {
        this.context = context;
        enemySpaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.user);
        random = new Random();
        ex = 200 + random.nextInt(400);
        ey = 0;
        enemyVelocity = 14 + random.nextInt(10);
    }
///put enemy on bitmap to show on screen
    public Bitmap getEnemy(){
        return enemySpaceship;
    }

    int getEnemyshape(){
        return enemySpaceship.getWidth();
    }

}
