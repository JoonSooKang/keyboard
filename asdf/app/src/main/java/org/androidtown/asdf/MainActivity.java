package org.androidtown.asdf;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;


public class MainActivity extends Activity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GraphicsView(this));
    }


    static public class GraphicsView extends View{
        private int xMin = 0;
        private int xMax;
        private int yMin = 0;
        private int yMax;
        private float ballRadius = 17;
        private float ballX = ballRadius + 200;
        private float ballY = ballRadius + 330;
        private float ballSpeedX = 10;
        private float ballSpeedY = 10;
        private RectF ballBounds;
        private Paint paint;
        private Bar bar;
        private Tank tank;

        private float previousX;
        private float previousY;

        public GraphicsView(Context context){
            super(context);
            ballBounds = new RectF();
            paint = new Paint();

            paint.setTypeface(Typeface.MONOSPACE);
            paint.setTextSize(16);

             bar = new Bar(Color.YELLOW);
            tank = new Tank(Color.GRAY);

            this.setFocusableInTouchMode(true);
        }

        protected void onDraw(Canvas canvas) {
            ballBounds.set(ballX - ballRadius, ballY - ballRadius, ballX + ballRadius, ballY + ballRadius);
            paint.setColor(Color.GREEN);
            //canvas.drawOval(ballBounds, paint);

            bar.draw(canvas);
            tank.draw(canvas);
           // paint.setColor(Color.CYAN);
           // canvas.drawText(statusMsg.toString(), 10, 720, paint);

            update();
            invalidate();
        }

        private void update(){
            ballX += ballSpeedX;
            ballY += ballSpeedY;

            if(ballX + ballRadius > xMax) {
                ballSpeedX = -ballSpeedX;
                ballX = xMax - ballRadius;
            }else if (ballX - ballRadius < xMin){
                ballSpeedX = -ballSpeedX;
                ballX = xMin + ballRadius;
            }

            if(ballY + ballRadius > yMax){
                ballSpeedY =-ballSpeedY;
                ballY = yMax - ballRadius;
            }else if(ballY - ballRadius < yMin){
                ballSpeedY =- ballSpeedY;
                ballY=yMin + ballRadius;
            }

            }

        @Override
        public void onSizeChanged(int w, int h, int oldW, int oldH){
            xMax = w-1;
            yMax = h-1;
        }

        public boolean onTouchEvent(MotionEvent event) {

            float currentX = event.getX();
            float currentY = event.getY();
            float deltaX = 0;
            float deltaY = 0;
            float scalingFactor = 5.0f / ((xMax > yMax) ? yMax : xMax);

            switch(event.getAction()){
                case MotionEvent.ACTION_MOVE:
                    deltaX = currentX - previousX;
                    deltaY = currentY - previousY;
                    ballSpeedX += deltaX * scalingFactor;
                    ballSpeedY += deltaY * scalingFactor;

                    bar.moveWithCollisionDetection(xMax, yMax, deltaY * scalingFactor * 130, ballX, ballY);

                    break;

                case MotionEvent.ACTION_DOWN:
                    previousX = currentX;
                    previousY = currentY;
                    break;
                case MotionEvent.ACTION_UP:
                    bar.moveWithCollisionDetection(xMax, yMax, deltaY * scalingFactor*130, ballX, ballY);
                    break;
            }

            previousX = currentX;
            previousY = currentY;
            return true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
