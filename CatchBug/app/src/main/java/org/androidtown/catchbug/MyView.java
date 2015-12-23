package org.androidtown.catchbug;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * TODO: document your custom view class.
 */
public class MyView extends View {

    private int xMax;
    private int yMax;

    private Paint paint;
    private RectF path;
    private Tank tank;
    int i = 0, j = 0;
    int score = 0;
    int finish = 0;
    int endflag = 0;
    int escape = 0;
    int stage = 1;


    private ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
    private ArrayList<Bug> bugList;
    private ArrayList<Bug_B> bug_BList;
    private ArrayList<Bug_C> bug_CList;




    public MyView(Context context) {
        super(context);
        init(null, 0);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public void changeDirection(boolean X){
        tank.buttonDirection(X);
    }

    private void init(AttributeSet attrs, int defStyle) {


        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        tank = new Tank(Color.RED);

        bugList = new ArrayList<Bug>();
        bug_BList = new ArrayList<Bug_B>();
        bug_CList = new ArrayList<Bug_C>();

        paint.setColor(Color.BLACK);

        this.setFocusableInTouchMode(true);
    }

    public void bulletDraw(){
        Bullet bullet = new Bullet(Color.BLACK);
        bullet.setCenter(xMax / 2, yMax / 2);
        bullet.setBullet(tank.ballStartX(), tank.ballStartY());

        bulletList.add(bullet);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH){
        xMax = w-1;
        yMax = h-1;
    }


    public double getDistance(float x1, float y1, float x2, float y2){

        double retValue;
        retValue =  Math.sqrt(Math.pow(Math.abs((double) (x2 - x1)),2) +
                Math.pow(Math.abs((double) (y2 - y1)),2));
        return retValue;
    }

    public int getScore(){
        return score;
    }

    public int getFinish(){
        return finish;
    }

    public int getStage(){
        return stage;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path = new RectF();

        // 생성조건들

        if (bugList.size() == 0 && bug_BList.size() == 0 && bug_CList.size() == 0) {
            for (i = 0; i < 5; i++) {
                bugList.add(new Bug(Color.YELLOW, xMax / 2, yMax / 2));
            }

            for (i = 0; i < 5; i++) {
                bug_BList.add(new Bug_B(Color.GREEN, xMax / 2, yMax / 2));
            }

            for (i = 0; i < 5; i++) {
                bug_CList.add(new Bug_C(Color.BLUE, xMax / 2, yMax / 2));
            }

        }

        path.set(60, yMax / 2 - xMax / 2 + 60, xMax - 60, yMax / 2 + xMax / 2 - 60);
        canvas.drawOval(path, paint);

        tank.setCenter(xMax / 2, yMax / 2);
        tank.draw(canvas);

        for (i = 0; i < bugList.size(); i++)
            bugList.get(i).draw(canvas);

        for (i = 0; i < bulletList.size(); i++) {
            bulletList.get(i).draw(canvas);
        }

        for (i = 0; i < bug_BList.size(); i++) {
            bug_BList.get(i).draw(canvas);
        }

        for (i = 0; i < bug_CList.size(); i++) {
            bug_CList.get(i).draw(canvas);
        }

        // 충돌시 삭제 조건들

        for (i = 0; i < bulletList.size(); i++) {
            if (bulletList.get(i).getBulletX() > xMax || bulletList.get(i).getBulletX() < 0
                    || bulletList.get(i).getBulletY() > yMax || bulletList.get(i).getBulletY() < 0) {
                bulletList.remove(i--);
            }
        }


        for (i = 0; i < bugList.size(); i++) {
            if(getDistance(bugList.get(i).getBugX(), bugList.get(i).getBugY(), xMax/2, yMax/2) > Math.abs(xMax/2 - 60)){
                bugList.remove(i--);
                escape = 1;
            }
        }

        for (i = 0; i < bug_BList.size(); i++) {
            if(getDistance(bug_BList.get(i).getBugbX(), bug_BList.get(i).getBugbY(), xMax/2, yMax/2) > Math.abs(xMax/2 - 60)){
                bug_BList.remove(i--);
                escape =1;
            }
        }

        for (i = 0; i < bug_CList.size(); i++) {
            if(getDistance(bug_CList.get(i).getBugcX(), bug_CList.get(i).getBugcY(), xMax/2, yMax/2) > Math.abs(xMax/2 - 60)){
                bug_CList.remove(i--);
                escape = 1;
            }
        }

        for (i = -1; i < bulletList.size(); i++) {
            for (j = -1; j < bugList.size(); j++) {
                if (i == -1 || j == -1) continue;
                if (getDistance(bugList.get(j).getBugX(), bugList.get(j).getBugY(), bulletList.get(i).getBulletX(), bulletList.get(i).getBulletY())
                        < bugList.get(j).getBugRadius() + bulletList.get(i).getBulletRadius()) {
                    bulletList.remove(i--);
                    bugList.remove(j--);
                    score++;
                }
            }
        }

        for (i = -1; i < bulletList.size(); i++) {
            for (j = -1; j < bug_BList.size(); j++) {
                if (i == -1 || j == -1) continue;
                if (getDistance(bug_BList.get(j).getBugbX(), bug_BList.get(j).getBugbY(), bulletList.get(i).getBulletX(), bulletList.get(i).getBulletY())
                        < bug_BList.get(j).getBugbRadius() + bulletList.get(i).getBulletRadius()) {
                    bulletList.remove(i--);
                    bug_BList.remove(j--);
                    score++;
                }
            }
        }

        for (i = -1; i < bulletList.size(); i++) {
            for (j = -1; j < bug_CList.size(); j++) {
                if (i == -1 || j == -1) continue;
                if (getDistance(bug_CList.get(j).getBugcX(), bug_CList.get(j).getBugcY(), bulletList.get(i).getBulletX(), bulletList.get(i).getBulletY())
                        < bug_CList.get(j).getBugcRadius() + bulletList.get(i).getBulletRadius()) {
                    bulletList.remove(i--);
                    bug_CList.remove(j--);
                    score++;
                }
            }
        }

        // 종료조건 벌레를 모두잡았을때 종료는 말이 안된다고 생각하여 한마리라도 원 밖으로 나간 벌레가 있을때
        // 잡으면 종료되게 함

        for(i = 0; i< bugList.size(); i++){
            if(getDistance(bugList.get(i).getBugX(), bugList.get(i).getBugY(), tank.getTankX(), tank.getTankY())
                    < bugList.get(i).getBugRadius()+ 10) endflag = 1;
        }
        for(i = 0; i< bug_BList.size(); i++){
            if(getDistance(bug_BList.get(i).getBugbX(), bug_BList.get(i).getBugbY(), tank.getTankX(), tank.getTankY())
                    < bug_BList.get(i).getBugbRadius()+ 10) endflag = 1;
        }
        for(i = 0; i< bug_CList.size(); i++){
            if(getDistance(bug_CList.get(i).getBugcX(), bug_CList.get(i).getBugcY(), tank.getTankX(), tank.getTankY())
                    < bug_CList.get(i).getBugcRadius()+ 10) endflag = 1;
        }

        if ((bugList.size() == 0 && bug_BList.size() == 0 && bug_CList.size() == 0) && escape == 1) endflag =1;
        if (bugList.size() == 0 && bug_BList.size() == 0 && bug_CList.size() == 0) stage++;

        if(endflag == 1) {
            finish = 1;
        } else {
            invalidate();
        }
    }
}