package org.androidtown.catchbug;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by JoonSooKang on 2015-10-11.
 */
public class Bug_B {
    float bugCenterX, bugCenterY;
    float bugX, bugY;
    float radius = 50;
    int direction = 1;
    int currentSelfAngle = 0;
    int directionTemp;
    float speedX, speedY;

    private Paint paint;
    private RectF bugBody;

    public Bug_B(int color, int centX, int centY){
        paint = new Paint();
        paint.setColor(color);
        bugBody = new RectF();
        bugCenterX = (float)centX;
        bugCenterY = (float)centY;


        directionTemp = (int)(Math.random()*360);
        currentSelfAngle = directionTemp;
        if(currentSelfAngle > 180 ) direction = -1;
        else direction = 1;

        speedX = (float)((centX + (radius * Math.cos(Math.toRadians(directionTemp))) - centX)/150);
        speedY = (float)((centY +(radius * Math.sin(Math.toRadians(directionTemp))) - centY)/150);
    }


    public void draw(Canvas canvas){
        update();
        bugBody.set(bugX - 15, bugY - 15, bugX + 15, bugY + 15);
        canvas.drawOval(bugBody,paint);
    }

    public void update(){
        bugX = (float)(bugCenterX + (radius * Math.cos(Math.toRadians(currentSelfAngle))));
        bugY = (float)(bugCenterY + (radius * Math.sin(Math.toRadians(currentSelfAngle))));
        if(currentSelfAngle == directionTemp + 120) if(direction == -1) direction = 1; else direction = -1 ;
        if(currentSelfAngle == directionTemp - 120) if(direction == -1) direction = 1; else direction = -1 ;
        currentSelfAngle = (currentSelfAngle+(direction*3)%360);

        bugCenterX += speedX;
        bugCenterY += speedY;
    }

    public float getBugbX(){
        return bugX;
    }

    public float getBugbY(){
        return bugY;
    }

    public float getBugbRadius(){
        return 15;
    }

}
