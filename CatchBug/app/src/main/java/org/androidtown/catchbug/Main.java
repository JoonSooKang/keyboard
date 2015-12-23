package org.androidtown.catchbug;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Main extends Activity {

    boolean tankDirection = true;

    private Button directionButton;
    private MyView myView;
    private TextView scoreView;
    private TextView overView;
    private TextView stageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_my_view);

        myView = (MyView) findViewById(R.id.myView);
        scoreView = (TextView)findViewById(R.id.ScoreView);
        overView = (TextView)findViewById(R.id.gameOver);
        stageView = (TextView)findViewById(R.id.stageView);
        mHandler.sendEmptyMessage(0);
        directionButton = (Button) findViewById(R.id.directionButton);
        directionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tankDirection) {
                    tankDirection = false;
                } else {
                    tankDirection = true;
                }
                myView.changeDirection(tankDirection);
            }
        });

    }
    public boolean onTouchEvent(MotionEvent event){

        switch(event.getAction()){
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_DOWN:
                myView.bulletDraw();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }


    Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg){
            scoreView.setText("Score : " + Integer.toString(myView.getScore()));
            stageView.setText("Stage : " + Integer.toString(myView.getStage()));
            if(myView.getFinish() == 1){
                overView.setText("gameOver");
            }
            mHandler.sendEmptyMessageDelayed(0, 100);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so
        // long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
