package org.androidtown.catchbug;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by JoonSooKang on 2015-10-11.
 */
public class Bug {


    float bugCenterX, bugCenterY;
    float bugX, bugY;
    float radius = 50;
    int currentSelfAngle = 0;
    int directionTemp;
    float speedX, speedY;

    private Paint paint;
    private RectF bugBody;

    public Bug(int color, int centX, int centY){
        paint = new Paint();
        paint.setColor(color);
        bugBody = new RectF();
        bugCenterX = (float)centX;
        bugCenterY = (float)centY;


        directionTemp = (int)(Math.random()*360);
        speedX = (float)((centX + (radius * Math.cos(Math.toRadians(directionTemp))) - centX)/100);
        speedY = (float)((centY +(radius * Math.sin(Math.toRadians(directionTemp))) - centY)/100);
    }


    public void draw(Canvas canvas){
        update();
        bugBody.set(bugX - 15, bugY - 15, bugX + 15, bugY + 15);
        canvas.drawRect(bugBody,paint);
    }

    public void update(){
        bugX = (float)(bugCenterX + (radius * Math.cos(Math.toRadians(currentSelfAngle))));
        bugY = (float)(bugCenterY + (radius * Math.sin(Math.toRadians(currentSelfAngle))));
        currentSelfAngle = (currentSelfAngle+(-1*2)%360);

        bugCenterX += speedX;
        bugCenterY += speedY;
    }

    public float getBugX(){
        return bugX;
    }

    public float getBugY(){
        return bugY;
    }

    public float getBugRadius(){
        return 15;
    }

}
