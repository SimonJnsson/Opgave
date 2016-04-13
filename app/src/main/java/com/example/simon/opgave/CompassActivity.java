package com.example.simon.opgave;

/**
 * Created by Patrick Q Jensen on 12-04-2016.
 */
import android.app.Activity;
    import android.hardware.Sensor;
    import android.hardware.SensorEvent;
    import android.hardware.SensorEventListener;
    import android.hardware.SensorManager;
    import android.os.Bundle;
    import android.view.animation.Animation;
    import android.view.animation.RotateAnimation;
    import android.widget.ImageView;
    import android.widget.TextView;

    public class CompassActivity extends Activity implements SensorEventListener {

        private ImageView image;
        private float currentDegree = 0f;
        private SensorManager sensorM;

    TextView tvHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.imageViewCompass);
        tvHeading = (TextView)findViewById(R.id.tvHeading);
        sensorM =(SensorManager)getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sensorM.registerListener(this,sensorM.getDefaultSensor(Sensor.TYPE_ORIENTATION),sensorM.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sensorM.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        float degree = Math.round(event.values[0]);

        tvHeading.setText("Heading " + Float.toString(degree) + " degrees");

        RotateAnimation ra = new RotateAnimation(currentDegree,-degree,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        ra.setDuration(210);
        ra.setFillAfter(true);
        image.startAnimation(ra);
        currentDegree =- degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
