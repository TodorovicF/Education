package uwb.css390.FilipTodorovicEX5;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Basket extends ImageView {

	final static String kBasket = "basket.png";
	static Bitmap bBasket = null;
	final static int kSize = 100;
	static int top, right, bottom, left;
	
	static int[] pXY = new int[2];
	
	Handler bHandler;
	final int kUpdatePeriod = 30;
	
	static MainActivity sMainActivity = null;
	static public void SetMainActivity(MainActivity v) { sMainActivity = v; }
	
	public Basket(Context context) {
		super(context);
		initImageView(context);
		setupHandler();
	}
//----------------------------------------------------------------------------------------------
// Initialize Image
	private void initImageView(Context context)	{
		if (isInEditMode())
			return;
			
		if (null == bBasket) {
			LoadBasketImage(context.getAssets());
		}
		setImageBitmap(bBasket);
		// now set the size
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(kSize, kSize);
		setLayoutParams(layoutParams);
	}
	
	static private void LoadBasketImage(AssetManager loader) {
		InputStream bitmapStream=null;
		try {
			//open the file from the assets folder with the given name
			bitmapStream = loader.open(kBasket);
			//decode the stream as a bitmap
			bBasket = BitmapFactory.decodeStream(bitmapStream);
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
//----------------------------------------------------------------------------------------------
// Check bounds
	public void setupHandler() {
		Runnable periodicTask = new Runnable() {
			@Override
			public void run() {
				getLocationOnScreen(pXY);
				// Setup next run
				bHandler.postDelayed(this, kUpdatePeriod);
			}
		};
		// Setup next job
		bHandler = new Handler();
		bHandler.postDelayed(periodicTask, kUpdatePeriod); 
	}
	
	public void getBasketCoords() {
		top = getTop();
		right = getRight();
		bottom = getBottom();
		left = getLeft();
	}
//----------------------------------------------------------------------------------------------
// Check bounds
	public void checkBounds() {
		// Hitting two sides at the same time
		if(getY() <= MainActivity.getPlayTop()-70 && getX() <= MainActivity.getPlayLeft()) {
			setY(MainActivity.getPlayTop()-71);
			setX(MainActivity.getPlayLeft()-1);
		}
		else if(getY() <= MainActivity.getPlayTop()-70 && getX() >= MainActivity.getPlayRight()-100) {
			setY(MainActivity.getPlayTop()-71);
			setX(MainActivity.getPlayRight()-101);
		}
		else if (getY() >= MainActivity.getPlayBottom()-150 && getX() <= MainActivity.getPlayLeft()) {
			setY(MainActivity.getPlayBottom()-151);
			setX(MainActivity.getPlayLeft()-1);
		}
		else if (getY() >= MainActivity.getPlayBottom()-150 && getX() >= MainActivity.getPlayRight()-100) {
			setY(MainActivity.getPlayBottom()-151);
			setX(MainActivity.getPlayRight()-101);
		}
		// Hitting one side
		if (getY() <= MainActivity.getPlayTop()-70) {
			setY(MainActivity.getPlayTop()-71);
		}
		else if(getY() >= MainActivity.getPlayBottom()-150) {
			setY(MainActivity.getPlayBottom()-151);
		}
		else if(getX() <= MainActivity.getPlayLeft()) {
			setX(MainActivity.getPlayLeft()-1);
		}
		else if(getX() >= MainActivity.getPlayRight()-100) {
			setX(MainActivity.getPlayRight()-101);
		}
	}
	
	public static boolean hitBasket(int bTop, int bRight, int bBottom, int bLeft) {
		if (top >= bBottom ) {
			return true;
		}
		
		return false;
	}
//----------------------------------------------------------------------------------------------
// Move Basket
	public void moveTo(float x, float y)
	{
		// Ceneter of Hero is the reference for create location
		setX(x);
		setY(y);
	}
}
