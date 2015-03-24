package uwb.css390.FilipTodorovicEX5;

import android.location.Location;
import android.location.LocationListener;
import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;

public class MainActivity extends Activity implements SensorEventListener, LocationListener {

	static RelativeLayout mPlayLayout;
	static RelativeLayout mParent;
	static TextView mMissed;
	static TextView mCaught;
	static TextView mOnScreen;
	static Ball mBall;
	static Basket mBasket;
	boolean hasMoved = false;
	
	Handler mHandler;
	final int kUpdatePeriod = 20;
	
	int mBasketLocation[] = new int[2];
	
	static int count = 0;
	static int missed = 0;
	static int caught = 0;
	long lastMove = 0;
	long moveUp = 0;
	long velocity = 0;
	float downX = 0, downY = 0;
	float upX = 0, upY = 0;
	static float functionX = 0;
	static float functionY = 0;
	long time = 0;
	float ax = 0, ay = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setupHandler();
		linkGui();
		placeBasket();
		setupObjects();
	}

	public void setupHandler() {
		Runnable periodicTask = new Runnable() {
			@Override
			public void run() {
				//mBasket.getLocationInWindow(mBasketLocation);
		        mBasket.moveTo(mBasket.getX()-(ax), mBasket.getY()+(ay));
		        mBasket.checkBounds();
		        
				// Setup next run
				mHandler.postDelayed(this, kUpdatePeriod);
			}
		};
		// Setup next job
		mHandler = new Handler();
		mHandler.postDelayed(periodicTask, kUpdatePeriod); 
	}
	
	private void linkGui() {
		mPlayLayout = (RelativeLayout) findViewById(R.id.playLayout);
		mParent = (RelativeLayout) findViewById(R.id.parent);
		mMissed = (TextView) findViewById(R.id.missed);
		mCaught = (TextView) findViewById(R.id.caught);
		mOnScreen = (TextView) findViewById(R.id.onScreen);
	}
	
	private void setupObjects() {
		
		mPlayLayout.setBackgroundColor(Color.GRAY);
		mMissed.setText("Missed: " + missed);
		mCaught.setText("Caught: " + caught);
		enableAccelerometer(true);
		mPlayLayout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

    			switch (event.getAction()) {
    			
					case MotionEvent.ACTION_DOWN:
						
		    			getFingerDownPosition(event.getX(), event.getY());
						break;
						
					case MotionEvent.ACTION_MOVE:
						hasMoved = true;
						break;
						
					case MotionEvent.ACTION_UP:
						if (hasMoved) {
							setVelocityTime(moveUp, lastMove);       // setup the velocity
							getFingerUpPosition(event.getX(), event.getY());   // set	
							getFunction();
							placeBall(event);
							hasMoved = false;
							break;
						}
						break;
						
    			}
    			return true;
			}
		});
	}
	
	public void placeBall(MotionEvent event) {
		mBall = new Ball(mPlayLayout.getContext());
		mPlayLayout.addView(mBall);
		mBall.moveTo(event.getX(), event.getY());
		count++;
		mOnScreen.setText("On Screen: " + count);
	}
	
	public void placeBasket() {
		mBasket = new Basket(mPlayLayout.getContext());
		mPlayLayout.addView(mBasket);
		mBasket.moveTo(mPlayLayout.getX()/2, mPlayLayout.getY()/2);
		
	}
	
	private void getFingerDownPosition(float x, float y) {
		downX = x;
		downY = y;
	}
	
	private void getFingerUpPosition(float x, float y) {
		upX = x;
		upY = y;
	}
	
	public void setVelocityTime(long down, long up) {
		velocity = up - down;
	}
	
	public void getFunction() {
		functionX = (upX - downX)/50;
		functionY = (upY - downY)/50;
	}

//----------------------------------------------------------------------------------------
// mPlayLayout getters
	
	public static float getPlayTop(){
		return mPlayLayout.getTop();
	}
	
	public static float getPlayRight() {
		return mPlayLayout.getRight();
	}
	
	public static float getPlayBottom() {
		return mPlayLayout.getBottom();
	}
	
	public static float getPlayLeft() {
		return mPlayLayout.getLeft();
	}
	
//----------------------------------------------------------------------------------------
// Accelerometer
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
	
	//getting accelerometer information
	@Override
    public void onSensorChanged(SensorEvent event) {
    	if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
	    	ax = event.values[0];
	        ay = event.values[1];
    	}
    }
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// NOT IMPLEMENTED, BUT REQUIRED
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
//----------------------------------------------------------------------------------------
}
