package com.example.android.softkeyboard;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by JoonSooKang on 2015-11-30.
 */
public class ShakeActivity extends Service implements SensorEventListener {

    private long pastTime;
    private double speed;
    private float lastX, lastY, lastZ;
    private float x, y, z;

    private static final int SHAKE_THRESHOLD = 350;
    private static final int X_Data = SensorManager.DATA_X;
    private static final int Y_Data = SensorManager.DATA_Y;
    private static final int Z_data = SensorManager.DATA_Z;
    private SensorManager sensorManager;
    private Sensor MoveSensor;

    public void onCreate() {
        super.onCreate();
        Log.d("test", "크리에이트");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d("test", "시작");
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        MoveSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, MoveSensor, SensorManager.SENSOR_DELAY_NORMAL);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0){
        return null;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            long currentTime = System.currentTimeMillis();
            long timeGab = (currentTime - pastTime);
            if (timeGab > 500) { // 500 == 0.5초
                pastTime = currentTime;
                x = event.values[SensorManager.DATA_X];
                y = event.values[SensorManager.DATA_Y];
                z = event.values[SensorManager.DATA_Z];

                speed = Math.sqrt(Math.pow((double)(x - lastX), 2) + Math.pow((double)(y - lastY), 2)
                        + Math.pow((double)(z - lastZ), 2)) / timeGab * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    
                    Log.d("test", "흔들림감지");

                    // 이벤트발생!!
                }

                lastX = event.values[X_Data];
                lastY = event.values[Y_Data];
                lastZ = event.values[Z_data];
            }
        }
    }

    public void onDestroy(){
        sensorManager.unregisterListener(this);
        super.onDestroy();
        Log.d("test", "끝내봄");
    }
}
