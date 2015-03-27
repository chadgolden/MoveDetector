package com.chadgolden.movedetector;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements SensorEventListener {

    private float x;
    private float y;
    private float z;

    private float previousX;
    private float previousY;
    private float previousZ;

    private TextView textSensorX;
    private TextView textSensorY;
    private TextView textSensorZ;
    private TextView textMoved;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private long previousUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        mAccelerometer = event.sensor;

        if (mAccelerometer.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();

            if ((currentTime - previousUpdate) > 100) {
                previousUpdate = currentTime;
                previousX = x;
                previousY = y;
                previousZ = z;
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];

                if (Math.abs(previousX - x) > 0.05 ||
                        Math.abs(previousY - y) > 0.05 ||
                        Math.abs(previousZ - z) > 0.05) {
                    textMoved.setText("I moved!");
                } else {
                    textMoved.setText("");
                    // blahblahabalh
                }

                setTextViewText();
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void init() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.previousUpdate = 0l;
        textSensorX = (TextView) findViewById(R.id.textSensorX);
        textSensorY = (TextView) findViewById(R.id.textSensorY);
        textSensorZ = (TextView) findViewById(R.id.textSensorZ);
        textMoved = (TextView) findViewById(R.id.textMoved);
        setTextViewText();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(this, mAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        } else {

        }



    }

    private void setTextViewText() {
        textSensorX.setText(String.valueOf(x));
        textSensorY.setText(String.valueOf(y));
        textSensorZ.setText(String.valueOf(z));
    }
}
