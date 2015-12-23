package org.androidtown.catchbug;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;


/**
 * Created by JoonSooKang on 2015-10-10.
 */
public class Tank {
    int width = 20;
    int height = 110;
    int halfwidth = width/2;
    int halfheight = height/2;
    float tankX , tankY;
    int currentSelfAngle = 0;
    int centX,centY;
    float radius;
    int direction = 1;

    boolean B = true;

    private Paint paint;
    private RectF Body;
    private RectF Gun;

    public Tank(int color){
        paint = new Paint();
        paint.setColor(color);
        Body = new RectF();
        Gun = new RectF();
    }


    public void setCenter(int x, int y){
        centX = x;
        centY = y;

        tankX = centX * 2 - halfwidth - 50;
        tankY = centY;
        radius = tankX - centX;
    }


    public void buttonDirection(boolean semiB){

        if(semiB){
         direction = 1;
        }
        else{
            direction = -1;
        }
    }

    public float getTankX(){
        return tankX;
    }

    public float getTankY(){
        return tankY;
    }

    public void draw(Canvas canvas){
        update();

        Body.set(tankX - halfwidth , tankY-halfheight, tankX+halfwidth, tankY + halfheight);
        Gun.set(tankX - halfwidth * 5, tankY - 3, tankX - halfwidth, tankY + 3);

        canvas.save();
        canvas.rotate(currentSelfAngle, tankX, tankY);
        canvas.drawRect(Body, paint);
        canvas.drawRect(Gun, paint);
        canvas.restore();
    }

    public void update(){
        tankX = (float)(centX + (radius * Math.cos(Math.toRadians(currentSelfAngle))));
        tankY = (float)(centY + (radius * Math.sin(Math.toRadians(currentSelfAngle))));
        currentSelfAngle = (currentSelfAngle+(direction*2)%360);
    }

    public float ballStartX(){
        return (float)(centX + ((radius-halfwidth*5) * Math.cos(Math.toRadians(currentSelfAngle))));
    }
    public float ballStartY(){
        return (float)(centY + ((radius-halfwidth*5) * Math.sin(Math.toRadians(currentSelfAngle))));
    }
}
