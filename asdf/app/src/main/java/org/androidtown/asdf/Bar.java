package org.androidtown.asdf;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by JoonSooKang on 2015-09-28.
 */
public class Bar {

    private int width = 20;
    private int height = 110;
    private int halfWidth = width/2;
    private int halfHeight = height/2;

    private float speedX = 0.0f;
    private float speedY = 3;

    private int i = 0;

    private int x;
    private int y;

    private int xMin, yMin;
    private Paint paint;
    private Rect bounds;

        private Integer [] colorArray = {Color.YELLOW, Color.BLUE, Color.GREEN, Color.RED, Color.CYAN};

        public Bar(int color) {
            paint = new Paint();
            paint.setColor(color);
            bounds = new Rect();
        }

        public void set(int xMax, int yMax){
            x = xMax-width;
            y = yMax-height;
            bounds.set(xMax-width ,yMax-height, xMax, yMax);
        }

        public void draw(Canvas canvas){
            bounds.set(x-halfWidth, y-halfHeight, x+halfWidth, y+halfHeight);
            canvas.drawRect(bounds, paint);
        }

        public void moveWithCollisionDetection(int xMax, int yMax, float deltaY, float BallX, float BallY){
            x += speedX;

            if(x+halfWidth > xMax){
                speedX = -speedX;
                x = xMax - halfWidth;
            }else if(x - halfWidth < xMin){
                speedX = -speedX;
                x = xMin+halfWidth;
            }

            y+=deltaY;
            if(y+ halfHeight > yMax){
                speedY = -speedY;
                y = yMax - halfHeight;
            }else if ( y - halfHeight < yMin){
                speedY = -speedY;
                y = yMin + halfHeight;
            }

            if((BallX >= x-width && BallX <= x+halfWidth) && (BallY >= y-halfHeight && BallY <= y+halfHeight)){
                i = (i+1) %6;
                Log.d("DBG", "COLOR = " + i);
                paint.setColor(colorArray[i]);
            }
        }
    }

