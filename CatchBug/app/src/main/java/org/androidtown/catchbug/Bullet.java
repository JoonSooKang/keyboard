package org.androidtown.catchbug;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by JoonSooKang on 2015-10-10.
 */
public class Bullet {

    private Paint paint;
    private RectF Bullet;

    int centX;
    int centY;
    float bulletX, bulletY;
    float bulletXspeed = 0 , bulletYspeed = 0;


    public Bullet(int color){
        paint = new Paint();
        paint.setColor(color);
        Bullet = new RectF();
    }


    public void setCenter(int x, int y){
        centX = x;
        centY = y;
    }

    public void setBullet(float x,float y){
        bulletX = x;
        bulletY = y;

        bulletXspeed = bulletX - centX;
        bulletYspeed  = bulletY - centY;
    }


    public void draw(Canvas canvas){

        Bullet.set(bulletX -10, bulletY-10, bulletX+10, bulletY+10);
        canvas.drawOval(Bullet, paint);
        update();
    }

    public void update(){

        bulletX -= bulletXspeed/20;
        bulletY -= bulletYspeed/20;

    }

    public float getBulletX(){
        return bulletX;
    }

    public float getBulletY(){
        return bulletY;
    }

    public float getBulletRadius(){
        return 10;
    }


}
