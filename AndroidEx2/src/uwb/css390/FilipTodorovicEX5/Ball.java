package uwb.css390.FilipTodorovicEX5;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.os.Handler;

public class Ball extends ImageView {

	final static String kBall = "soccerball.png";
	static Bitmap bBall = null;
	final static int kSize = 50;
	boolean visible = true;
	
	Basket mBasket;
	float functionY = 0;
	float functionX = 0;
	int top = 0, right = 0, bottom = 0, left = 0;
	int[] pXY = new int[2];
	int wallHits = 0;
	
	
	Handler mFutureTask;
	final int kUpdatePeriod = 30;
	
	static MainActivity sMainActivity = null;
	static public void SetMainActivity(MainActivity v) { sMainActivity = v; }
	
//----------------------------------------------------------------------------------------------
// Constructor
	public Ball(Context context) {
		super(context);
		initImageView(context);
		getVars();
		setupHandler();
	}
//----------------------------------------------------------------------------------------------
// Get Variables
	private void getVars() {
		functionY = MainActivity.functionY;
		functionX = MainActivity.functionX;
	}
//----------------------------------------------------------------------------------------------
// Initialize Image
	private void initImageView(Context context)	{
		if (isInEditMode())
			return;
			
		if (null == bBall) {
			LoadDuckImage(context.getAssets());
		}
		setImageBitmap(bBall);
		// now set the size
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(kSize, kSize);
		setLayoutParams(layoutParams);
	}
	
	static private void LoadDuckImage(AssetManager loader) {
		InputStream bitmapStream=null;
		try {
			//open the file from the assets folder with the given name
			bitmapStream = loader.open(kBall);
			//decode the stream as a bitmap
			bBall = BitmapFactory.decodeStream(bitmapStream);
			//set up an inputStream	  
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(bitmapStream!=null) {
				try {
					//close the inputstream if it was loaded successfully
					bitmapStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void getBallCoords() {
		top = getTop();
		right = getRight();
		bottom = getBottom();
		left = getLeft();
	}
//----------------------------------------------------------------------------------------------
// Handler
	public void setupHandler() {
		Runnable periodicTask = new Runnable() {
			@Override
			public void run() {
				getLocationOnScreen(pXY);
				checkOutOfBounds();
				checkBasketHit();
				checkHits();
				setY(getY() + functionY);
				setX(getX() + functionX);
				// Setup next run
				mFutureTask.postDelayed(this, kUpdatePeriod);
			}
		};
		// Setup next job
		mFutureTask = new Handler();
		mFutureTask.postDelayed(periodicTask, kUpdatePeriod); 
	}
//----------------------------------------------------------------------------------------------
// Checking for out of bounds, wall hits, and basket hits
	public void checkOutOfBounds() {
		if (getY() <= MainActivity.mPlayLayout.getTop() -70 ||
			getY() >= MainActivity.mPlayLayout.getBottom()-110) {
				functionY = -functionY;
				wallHits++;
		}
		else if (getX() <= MainActivity.mPlayLayout.getLeft() ||
				 getX() >= MainActivity.mPlayLayout.getRight()-50) {
			functionX = -functionX;
			wallHits++;
		}
	}
	
	public void checkBasketHit() {
		if (pXY[1] >= Basket.pXY[1]-75 &&
			pXY[1] <= Basket.pXY[1]+75 &&
			pXY[0] <= Basket.pXY[0]+75 &&
			pXY[0] >= Basket.pXY[0]-75) {
			if(visible == true) {
				setVisibility(View.GONE);
				MainActivity.caught++;
				MainActivity.count--;
				MainActivity.mCaught.setText("Caught: " + MainActivity.caught);
				MainActivity.mOnScreen.setText("On Screen: " + MainActivity.count);
				visible = false;
			}
		}
	}

	public void checkHits() {
		if (wallHits == 5) {
			if (visible == true) {
				setVisibility(View.GONE);
				MainActivity.count--;
				MainActivity.missed++;
				MainActivity.mOnScreen.setText("On Screen: " + MainActivity.count);
				MainActivity.mMissed.setText("Missed: " + MainActivity.missed);
				visible = false;
			}
		}
	}
//----------------------------------------------------------------------------------------------
// Move the Object
	public void removeView() {
		if (visible == true) {
			setVisibility(View.GONE);
			MainActivity.caught++;
			MainActivity.mCaught.setText("Caught: " + MainActivity.caught);
		}
	}
//----------------------------------------------------------------------------------------------
// Move the Object
	public void moveTo(float x, float y)
	{
		// Ceneter of Hero is the reference for create location
		setX(x-getWidth()/2);
		setY(y-getHeight()/2);
	}
}
