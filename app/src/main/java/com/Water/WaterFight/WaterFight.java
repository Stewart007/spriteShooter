package com.Water.WaterFight;

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

public class WaterFight extends View {
    Context context;
    Bitmap background, lifeImage;
    Handler handler;
    long UPDATE_MILLIS = 30;
    static int screenWidth, screenHeight;
    int points = 0;
    int life = 3;
    Paint scorePaint;
    int TEXT_SIZE = 80;
    boolean paused = false;
    User user;
    EnemyPlayer enemyPlayer;
    Random random;
    ArrayList<Water> enemyWaters, ourWaters;
    Splash splash;
    ArrayList<Splash> splashes;
    boolean enemyShotAction = false;
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
           invalidate();
        }
    };


    public WaterFight(Context context) {
        super(context);
        this.context = context;
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        random = new Random();
        enemyWaters = new ArrayList<>();
        ourWaters = new ArrayList<>();
        splashes = new ArrayList<>();
        user = new User(context);
        enemyPlayer = new EnemyPlayer(context);
        handler = new Handler();
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        lifeImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.life);
        scorePaint = new Paint();
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw background, Points and life on Canvas
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawText("Pt: " + points, 0, TEXT_SIZE, scorePaint);
        for(int i=life; i>=1; i--){
            canvas.drawBitmap(lifeImage, screenWidth - lifeImage.getWidth() * i, 0, null);
        }
        // When life becomes 0, stop game and launch GameOver Activity with points
        if(life == 0){
            paused = true;
            handler = null;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("points", points);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
        enemyPlayer.ex += enemyPlayer.enemyVelocity;
        if(enemyPlayer.ex + enemyPlayer.getEnemyshape() >= screenWidth){
            enemyPlayer.enemyVelocity *= -1;
        }
        if(enemyPlayer.ex <=0){
            enemyPlayer.enemyVelocity *= -1;
        }
        if(enemyShotAction == false){
            if(enemyPlayer.ex >= 200 + random.nextInt(400)){
                Water enemyWater = new Water(context, enemyPlayer.ex + enemyPlayer.getEnemyshape() / 2, enemyPlayer.ey );
                enemyWaters.add(enemyWater);
                // We're making enemyShotAction to true so that enemy can take a short at a time
                enemyShotAction = true;
            }
            if(enemyPlayer.ex >= 400 + random.nextInt(800)){
                Water enemyWater = new Water(context, enemyPlayer.ex + enemyPlayer.getEnemyshape() / 2, enemyPlayer.ey );
                enemyWaters.add(enemyWater);
                // We're making enemyShotAction to true so that enemy can take a short at a time
                enemyShotAction = true;
            }
            else{
                Water enemyWater = new Water(context, enemyPlayer.ex + enemyPlayer.getEnemyshape() / 2, enemyPlayer.ey );
                enemyWaters.add(enemyWater);
                // We're making enemyShotAction to true so that enemy can take a short at a time
                enemyShotAction = true;
            }
        }
        // Draw the enemy player
        canvas.drawBitmap(enemyPlayer.getEnemy(), enemyPlayer.ex, enemyPlayer.ey, null);

        if(user.ox > screenWidth - user.getOurUserW()){
            user.ox = screenWidth - user.getOurUserW();
        }else if(user.ox < 0){
            user.ox = 0;
        }
        // Draw our user

        canvas.drawBitmap(user.getUserP(), user.ox, user.oy, null);

        for(int i = 0; i < enemyWaters.size(); i++){
            enemyWaters.get(i).shy += 15;
            canvas.drawBitmap(enemyWaters.get(i).getShot(), enemyWaters.get(i).shx, enemyWaters.get(i).shy, null);
            if((enemyWaters.get(i).shx >= user.ox)
                && enemyWaters.get(i).shx <= user.ox + user.getOurUserW()
                && enemyWaters.get(i).shy >= user.oy
                && enemyWaters.get(i).shy <= screenHeight){
                life--;
                enemyWaters.remove(i);
                splash = new Splash(context, user.ox, user.oy);
                splashes.add(splash);
            }else if(enemyWaters.get(i).shy >= screenHeight){
                enemyWaters.remove(i);
            }
            if(enemyWaters.size() < 1){
                enemyShotAction = false;
            }
        }

        for(int i = 0; i < ourWaters.size(); i++){
            ourWaters.get(i).shy -= 15;
            canvas.drawBitmap(ourWaters.get(i).getShot(), ourWaters.get(i).shx, ourWaters.get(i).shy, null);
            if((ourWaters.get(i).shx >= enemyPlayer.ex)
               && ourWaters.get(i).shx <= enemyPlayer.ex + enemyPlayer.getEnemyshape()
               && ourWaters.get(i).shy <= enemyPlayer.getEnemyshape()
               && ourWaters.get(i).shy >= enemyPlayer.ey){
                points++;
                ourWaters.remove(i);
                splash = new Splash(context, enemyPlayer.ex, enemyPlayer.ey);
                splashes.add(splash);
            }else if(ourWaters.get(i).shy <=0){
                ourWaters.remove(i);
            }
        }
        // Do the Splash
        for(int i = 0; i < splashes.size(); i++){
            canvas.drawBitmap(splashes.get(i).getSplash(splashes.get(i).splashF), splashes.get(i).eX, splashes.get(i).eY, null);
            splashes.get(i).splashF++;
            if(splashes.get(i).splashF > 8){
                splashes.remove(i);
            }
        }
        // If not paused, we’ll call the postDelayed() method on handler object which will cause the
        // run method inside Runnable to be executed after 30 milliseconds, that is the value inside
        // UPDATE_MILLIS.
        if(!paused)
            handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int)event.getX();
        // When event.getAction() is MotionEvent.ACTION_UP, if ourShots arraylist size < 1,
        // create a new Shot.
        // This way we restrict ourselves of making just one shot at a time, on the screen.
        if(event.getAction() == MotionEvent.ACTION_UP){
            if(ourWaters.size() < 1){
                Water ourWater = new Water(context, user.ox + user.getOurUserW() / 2, user.oy);
                ourWaters.add(ourWater);
            }
        }
        // When event.getAction() is MotionEvent.ACTION_DOWN, control user
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            user.ox = touchX;
        }
        // When event.getAction() is MotionEvent.ACTION_MOVE, control user
        // along with the touch.
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            user.ox = touchX;
        }
        // Returning true in an onTouchEvent() tells Android system that you already handled
        // the touch event and no further handling is required.
        return true;
    }
}
