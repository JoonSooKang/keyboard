package org.androidtown.keyboard;

import android.app.Activity;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;


public class MainActivity extends Activity implements SensorEventListener{

    private long pastTime;
    private double speed;
    private float lastX, lastY, lastZ;
    private float x, y, z;

    private static final int SHAKE_THRESHOLD = 500;
    private static final int X_Data = SensorManager.DATA_X;
    private static final int Y_Data = SensorManager.DATA_Y;
    private static final int Z_data = SensorManager.DATA_Z;

    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (accelerormeterSensor != null)
            sensorManager.registerListener(this, accelerormeterSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            long currentTime = System.currentTimeMillis();
            long timeGab = (currentTime - pastTime);
            if (timeGab > 300) { // 500 == 0.5초
                pastTime = currentTime;
                x = event.values[SensorManager.DATA_X];
                y = event.values[SensorManager.DATA_Y];
                z = event.values[SensorManager.DATA_Z];

                speed = Math.sqrt(Math.pow((double) (x - lastX), 2) + Math.pow((double) (y - lastY), 2)
                        + Math.pow((double) (z - lastZ), 2)) / timeGab * 10000;

                    if (speed > SHAKE_THRESHOLD) {
                        Log.d("test", "테스트 시작");

                        if( Math.abs(x) > Math.abs(z)) {
                            if(Math.abs(x) > 30) Log.d("testX", String.valueOf(x));
                        }
                        else {
                            if(Math.abs(z) > 15) Log.d("testZ", String.valueOf(z));
                        }

                        // 이벤트발생!!
                    }


                lastX = event.values[X_Data];
                lastY = event.values[Y_Data];
                lastZ = event.values[Z_data];

            }
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
