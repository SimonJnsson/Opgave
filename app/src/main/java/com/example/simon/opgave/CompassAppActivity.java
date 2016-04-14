package com.example.simon.opgave;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.os.Bundle;

public class CompassAppActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView compass;
    private SensorManager sensorM;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private float[] lastMagnetometer = new float[3];
    private  float[] lastAccelerometer = new float[3];
    private boolean lastAccelerometerSet = false;
    private boolean lastMagnetometerSet = false;
    private float[] mR = new float[9];
    private float[] orientation = new float[3];
    private float currentDegree = 0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass_app);

        sensorM = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        compass = (ImageView)findViewById(R.id.compass);
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorM.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorM.registerListener(this,magnetometer,SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sensorM.unregisterListener(this,accelerometer);
        sensorM.unregisterListener(this,magnetometer);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
     if (event.sensor == accelerometer)
     {
         System.arraycopy(event.values,0,lastAccelerometer,0,event.values.length);
         lastAccelerometerSet = true;
     }
     else if (event.sensor == magnetometer)
     {
         System.arraycopy(event.values,0,lastMagnetometer,0,event.values.length);
         lastMagnetometerSet = true;
     }
     if (lastAccelerometerSet && lastMagnetometerSet)
     {
         SensorManager.getRotationMatrix(mR,null,lastAccelerometer,lastMagnetometer);
         SensorManager.getOrientation(mR,orientation);
         float rotationInRadians = orientation[0];
         float rotationInDegrees = (float)(Math.toDegrees(rotationInRadians)+360)%360;
         RotateAnimation rotateAnimation = new RotateAnimation(currentDegree,-rotationInDegrees,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
         rotateAnimation.setDuration(250);
         rotateAnimation.setFillAfter(true);
         compass.startAnimation(rotateAnimation);
         currentDegree = -rotationInDegrees;
     }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
