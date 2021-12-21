package com.example.waterfight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class waterfight extends View {
    Context context;
    Bitmap background;
    Handler handler;
    static int screenWidth, screenHeight;
    long UPDATE_MILLIS = 30;
    Random random;
    int TEXT_SIZE = 80;
    boolean paused = false;
    OurFighter ourFighter;
    ArrayList<Shot> ourShots;

    boolean enemyShotAction = false;

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    public waterfight(Context context) {
        super(context);
        this.context = context;
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        random = new Random();
        ourShots = new ArrayList<>();
        ourFighter = new OurFighter(context);
        handler = new Handler();
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);



    }
//          Doing the screen art and player
    @Override
    protected void onDraw(Canvas canvas) {
        // Draw background, Points and life on Canvas
        canvas.drawBitmap(background, 0, 0, null);
        // Draw our spaceship between the left and right edge of the screen
        if (ourFighter.ox > screenWidth - ourFighter.getOurFighterWidth()) {
            ourFighter.ox = screenWidth - ourFighter.getOurFighterWidth();
        } else if (ourFighter.ox < 0) {
            ourFighter.ox = 0;
        }
        // Draw our Spaceship
        canvas.drawBitmap(ourFighter.getOurFighter(), ourFighter.ox, ourFighter.oy, null);

        for (int i = 0; i < ourShots.size(); i++) {
            ourShots.get(i).shy -= 15;
            canvas.drawBitmap(ourShots.get(i).getShot(), ourShots.get(i).shx, ourShots.get(i).shy, null);

        }

        for(int i=0; i < ourShots.size(); i++){
            ourShots.get(i).shy -= 15;
            canvas.drawBitmap(ourShots.get(i).getShot(), ourShots.get(i).shx, ourShots.get(i).shy, null);
            if(ourShots.get(i).shy <=0){
                ourShots.remove(i);
            }
        }

        if(!paused)
            handler.postDelayed(runnable, UPDATE_MILLIS);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int)event.getX();
        //on touch move player on the screen
        // create a new Shot.
        // One shot at a time on the screen.
        if(event.getAction() == MotionEvent.ACTION_UP){
            if(ourShots.size() < 1){
                Shot ourShot = new Shot(context, ourFighter.ox + ourFighter.getOurFighterWidth() / 2, ourFighter.oy);
                ourShots.add(ourShot);
            }
        }
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            ourFighter.ox = touchX;
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            ourFighter.ox = touchX;
        }

        return true;
    }

}