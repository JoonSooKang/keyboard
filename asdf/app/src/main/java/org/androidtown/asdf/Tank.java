package org.androidtown.asdf;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by JoonSooKang on 2015-10-02.
 */
public class Tank {


    private int width = 20;
    private int height = 110;
    private int halfwidth = width/2;
    private int halfheight = height/2;

    int x = 100;
    int y = 200;
    int currentAngle = 0;
    int currentSelfAngle = 0;

    int centX = 300, centY = 500 ;
    int xBullet = 0;

    int xMin, yMin;
    private Paint paint;
    private Rect bounds;
    private Rect gunBounds, bulletBounds;


    private Integer [] colorArray = {Color.YELLOW, Color.BLUE, Color.GREEN, Color.RED, Color.CYAN};

    public Tank(int color) {
        paint = new Paint();
        paint.setColor(color);
        bounds = new Rect();
        gunBounds = new Rect();
        bulletBounds = new Rect();
    }



    public void draw(Canvas canvas){

        bounds.set(centX * 2 - halfwidth *2 , centY-halfheight, centX*2, centY + halfheight);
        gunBounds.set(centX * 2 - halfwidth * 6, centY-3, centX*2-halfwidth*2, centY+3);
        if(-xBullet >= centX+2) xBullet = 0; else xBullet +=3;
        bulletBounds.set(centX*2-halfwidth *8+xBullet,centY-3, centX*2-halfwidth*8+xBullet, centX+3);

        canvas.rotate(currentSelfAngle,centX,centY);
        canvas.drawRect(bounds, paint);
        canvas.drawRect(gunBounds, paint);
        canvas.drawRect(bulletBounds, paint);
        currentSelfAngle++;
    }


}
