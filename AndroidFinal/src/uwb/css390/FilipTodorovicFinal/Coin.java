package uwb.css390.FilipTodorovicFinal;

import java.io.IOException;
import java.io.InputStream;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Coin extends ImageView {

	// Image Parameters
	final static String kStar = "ring1.png";
	static Bitmap bStar = null;
	final static int kSizeX = 75;
	final static int kSizeY = 75;
	
	// Variables
	boolean alive = true;
	static float left, right, top, bottom;
	int anim = 0;
	static boolean changed = false;
	
	// Handler
	Handler mFutureTask;
	Handler animateHandler;
	final int kUpdatePeriod = 25;
	final int kUpdatePeriod2 = 100;

//--------------------------------------------------------------------------------------------
// Constructor
	public Coin(Context context) {
		super(context);
		initImageView(context);
		setupHandler();
		setupAnimate();
	}
//--------------------------------------------------------------------------------------------
// Initialize Hero Image
	private void initImageView(Context context)	{
		if (isInEditMode())
			return;
			
		if (MainActivity.yourSelectedImage == null) {
			LoadStarImage(context.getAssets());
		}
			setImageBitmap(MainActivity.yourSelectedImage);
			
		// now set the size
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(kSizeX, kSizeY);
		setLayoutParams(layoutParams);
	}
	
	static private void LoadStarImage(AssetManager loader) {
		InputStream bitmapStream=null;
		try {
			//open the file from the assets folder with the given name
			bitmapStream = loader.open(kStar);
			//decode the stream as a bitmap
			MainActivity.yourSelectedImage = BitmapFactory.decodeStream(bitmapStream);
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
	// Check if coin is taken
	public void checkCaught() {
		// if hero is within bounds of star, delete star, add to Hit
		if ((Hero.yLoc+kSizeX/2) >= top && (Hero.yLoc+kSizeX/2) <= bottom && (Hero.xLoc+kSizeX/2) <= right && (Hero.xLoc+kSizeX/2) >= left) {
			setVisibility(View.GONE);
			alive = false;
			MainActivity.created = false;
			MainActivity.resetTime();
			MainActivity.num = 3;
			MainActivity.caught++;
			MainActivity.mHit.setText("Hit: " + MainActivity.caught);
		}
	}
	// Handler for checking if Coin is grabbed
	public void setupHandler() {
		Runnable periodicTask = new Runnable() {
			@Override
			public void run() {
				// make sure Play is selected
				if (alive) {
					getCoords();
					checkCaught();
					checkTimer();
				}
				// Setup next run
				mFutureTask.postDelayed(this, kUpdatePeriod);
			}
		};
		// Setup next job
		mFutureTask = new Handler();
		mFutureTask.postDelayed(periodicTask, kUpdatePeriod); 
	}
	
	// Handler for Coin animation
	public void setupAnimate() {
		Runnable periodicTask = new Runnable() {
			@Override
			public void run() {
				// make sure Play is selected
				if (alive) {
					animateCoin();
				}
				// Setup next run
				mFutureTask.postDelayed(this, kUpdatePeriod2);
			}
		};
		// Setup next job
		mFutureTask = new Handler();
		mFutureTask.postDelayed(periodicTask, kUpdatePeriod2); 
	}
	// Animate Coin
	public void animateCoin() {
		if (!changed) {
			switch(anim){
			case 0:
				anim++;
				setImageResource(R.drawable.ring1);
				break;
			case 1:
				anim++;
				setImageResource(R.drawable.ring2);
				break;
			case 2:
				anim++;
				setImageResource(R.drawable.ring3);
				break;
			case 3:
				anim++;
				setImageResource(R.drawable.ring4);
				break;
			case 4:
				anim++;
				setImageResource(R.drawable.ring5);
				break;
			case 5:
				anim++;
				setImageResource(R.drawable.ring6);
				break;
			case 6:
				anim++;
				setImageResource(R.drawable.ring7);
				break;
			case 7:
				anim++;
				setImageResource(R.drawable.ring8);
				break;
			case 8:
				anim++;
				setImageResource(R.drawable.ring9);
				break;
			case 9:
				anim++;
				setImageResource(R.drawable.ring10);
				break;
			case 10:
				anim++;
				setImageResource(R.drawable.ring11);
				break;
			case 11:
				anim++;
				setImageResource(R.drawable.ring12);
				break;
			case 12:
				anim++;
				setImageResource(R.drawable.ring13);
				break;
			case 13:
				anim++;
				setImageResource(R.drawable.ring14);
				break;
			case 14:
				anim++;
				setImageResource(R.drawable.ring15);
				break;
			case 15:
				anim=0;
				setImageResource(R.drawable.ring16);
				break;
			}
		}
	}
	// Check if Time has run out
	public void checkTimer() {
		if (MainActivity.num == 0) {
			MainActivity.num = 3;
			setVisibility(View.GONE);
			alive = false;
			MainActivity.created = false;
			MainActivity.missed++;
			MainActivity.mMiss.setText("Miss: " + MainActivity.missed);
		}
	}
	// Get Coin coordinates
	public void getCoords() {
		top = getY();
		right = getX()+75;
		left = getX();
		bottom = getY()+75;
	}
	// Move Coin to a specified location
	public void moveTo(float x, float y)
	{
		setX(x);
		setY(y);
	}
}
