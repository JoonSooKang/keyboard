package org.androidtown.catchbug;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;

/**
 * Created by JoonSooKang on 2015-10-11.
 */
public class Bug_C {
    float bugCenterX, bugCenterY;
    float bugX, bugY;
    float radius = 50;
    int direction = 1;
    int currentSelfAngle = 0;
    int directionTemp;
    float speedX, speedY;

    private Paint paint;
    private Path bugBody;

    public Bug_C(int color, int centX, int centY){
        paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);

        bugBody = new Path();
        bugCenterX = (float)centX;
        bugCenterY = (float)centY;


        directionTemp = (int)(Math.random()*360);
        currentSelfAngle = directionTemp;
        if(currentSelfAngle > 180 ) direction = -1;
        else direction = 1;

        speedX = (float)((centX + (radius * Math.cos(Math.toRadians(directionTemp))) - centX)/70);
        speedY = (float)((centY +(radius * Math.sin(Math.toRadians(directionTemp))) - centY)/70);
    }


    public void draw(Canvas canvas){
        update();
        bugBody.reset();
        setTriangle((int) bugX, (int) bugY);
        canvas.drawPath(bugBody,paint);
    }

    public void setTriangle(int xRefer, int yRefer){

        Point X,Y,Z;

        X = new Point(xRefer -15,yRefer -15);
        Y = new Point(xRefer +15, yRefer -15);
        Z = new Point(xRefer,yRefer+15);

        bugBody.setFillType(Path.FillType.EVEN_ODD);
        bugBody.moveTo(Y.x, Y.y);
        bugBody.lineTo(Z.x, Z.y);
        bugBody.lineTo(X.x, X.y);
        bugBody.close();
    }

    public void update(){
        bugX = bugCenterX;
        bugY = bugCenterY;

        bugCenterX += speedX;
        bugCenterY += speedY;
    }

    public float getBugcX(){
        return bugX;
    }

    public float getBugcY(){
        return bugY;
    }

    public float getBugcRadius(){
        return 15;
    }

}