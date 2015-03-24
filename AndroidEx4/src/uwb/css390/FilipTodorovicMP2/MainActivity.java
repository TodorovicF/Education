package uwb.css390.FilipTodorovicMP2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener, LocationListener  {

	final String kTheDuck = "duck.jpg";
	final int kDuckSize = 200;
	
	float ax = 0, ay = 0, az = 0;
	
	int totalDroids = 0;
	int totalTouches = 0;
	int mHeroLocation[] = new int[2];

	TextView Droids;
	TextView Touches;
	Timer myTimer;
	ImageView mCroc;
	
	// Region GUI elements
	RelativeLayout mMainView;
	// EndRegion
	
	Bitmap mBitmap;  // use for ImageView
	
	// for supporting dragging: we need a variable that can persists over event services!!
	CenteredImageView mCurrentWorking = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Droids = (TextView) findViewById(R.id.Droids);
		Touches = (TextView) findViewById(R.id.Touches);
		mMainView = (RelativeLayout) findViewById(R.id.mainView);
		mCroc = (ImageView) findViewById(R.id.croc);
		
		CenteredImageView.SetMainActivity(this);
		
		// load the bitmap we will be using
		loadBitmap();
//		myTimer = new Timer();
//		myTimer.schedule(new TimerTask() {
//			
//			public void run() { 
//				TimerMethod(); 
//			}
//		}, 0, 25);
		myTimer = new Timer();
		
		mMainView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						enableAccelerometer(false);
						totalDroids++;
						Droids.setText("Total Droids: " + totalDroids);
						mCurrentWorking = new CenteredImageView(mMainView.getContext());
						mCurrentWorking.setupSelectBehavior();
						mMainView.addView(mCurrentWorking);
						mCurrentWorking.moveTo(event.getX(), event.getY());
						mCurrentWorking.check = true;
						myTimer.schedule(new TimerTask() {
							
							@Override
							public void run() { 
								TimerMethod(); 
							}
						}, 0, 50);

						break;
					case MotionEvent.ACTION_MOVE:
						if (null != mCurrentWorking) {
							mCurrentWorking.moveTo(event.getX(), event.getY());
						}
						break;
					case MotionEvent.ACTION_UP:
						enableAccelerometer(true);
						break;
				}
				return true;
			}
		});
		
	}
	
	private void TimerMethod()
	{
		this.runOnUiThread(Timer_Tick);
	}
	
	private Runnable Timer_Tick = new Runnable() {
		@Override
		public void run() {
			setImageParams(-ax, ay);
		}
	};
//--------------------------------------------------------------------------------------
	@Override
	protected void onResume() {
	      super.onResume();
	      //enableAccelerometer(true);
	}
	
	@Override
	protected void onPause() {
	      super.onPause();
	      enableAccelerometer(false);
	}
//--------------------------------------------------------------------------------------
    @Override
    public void onAccuracyChanged(Sensor sensor, int acc) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
    	if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
	    	ax = event.values[0];
	        ay = event.values[1];
	        az = event.values[2];
	        
	        mCurrentWorking.getLocationInWindow(mHeroLocation);
    	}
    	else {
    		
    	}
    	if (mCurrentWorking.getY() >= mCroc.getY() && mCurrentWorking.getX() >= mCroc.getX() ) {
    		removeView(mCurrentWorking);
    		
    		remove(mCurrentWorking.check);
    		Droids.setText("Total Droids: " + totalDroids);
    	}
    	
    	if (mCurrentWorking.getY() <= mMainView.getY() || mCurrentWorking.getY() >= mMainView.getBottom()) {
    		removeView(mCurrentWorking);
    		
    		removeTotal(mCurrentWorking.check);
    		
    	}
    	
    }

	public void setImageParams(float x, float y) {
		mCurrentWorking.offsetLeftAndRight((int) x);
		mCurrentWorking.offsetTopAndBottom((int) y);
	}
	
	// Region loading the bitmap
	private void loadBitmap() {
		InputStream bitmapStream=null;
		try {
			//open the file from the assets folder with the given name
			bitmapStream=getAssets().open(kTheDuck);
				// getAssets: is the utility for accessing the Asset folder!

			//decode the stream as a bitmap
			mBitmap=BitmapFactory.decodeStream(bitmapStream);
			//set up an inputStream	  
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(bitmapStream!=null)
				try {
					//close the inputstream if it was loaded successfully
					bitmapStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void remove(boolean check) {
		if (mCurrentWorking.check) {
			totalDroids--;
			Droids.setText("Total Droids: " + totalDroids);
			totalTouches++;
			Touches.setText("Total Touches: " + totalTouches);
		}
		mCurrentWorking.check = false;
	}
	
	public void removeTotal(boolean check) {
		if (mCurrentWorking.check) {
			totalDroids--;
			Droids.setText("Total Droids: " + totalDroids);
		}
		mCurrentWorking.check = false;
	}
	
	public void removeView(View v) {
		mMainView.removeView(v);
	}
	
	public void enableAccelerometer(boolean enabled) {
		SensorManager accelManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		if (enabled) { 
			//get a sensor manager for accelerometer
			accelManager.registerListener(this, 
	    				accelManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
	    				SensorManager.SENSOR_DELAY_NORMAL);
		} else {
			accelManager.unregisterListener(this);
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
}
