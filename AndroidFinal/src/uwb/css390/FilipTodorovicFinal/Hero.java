package uwb.css390.FilipTodorovicFinal;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Hero extends ImageView {
	
	// Image Parameters
	final static String heroImage = "star.png";
	static Bitmap heroBitmap = null;
	final static int heroSize = 75;
	
	// Physics Jump/Fall
	float grav = 0.5f;
	float jVel = 40;
	float mVel = 0;
	float resist = 0;
	
	// Maximum Values
	float jVelMax = 40;
	
	// Physics Left/Right
	float moveHero = 0;
	int left = 0;
	int right = 0;
	int jumpAnim = 0;
	boolean direction = false;
	boolean inAir = false;
	
	// Checks
	boolean jump = false;
	boolean jumpD = false;
	boolean mLeft = false;
	boolean mRight = false;
	static boolean mRightOff = true;
	static boolean mLeftOff = true;
	
	// Layout Bounds
	int Left = 0;
	int Top = 0;
	int Right = 1205;
	int Bottom = 405;
	
	// Hero Location
	static float xLoc;
	static float yLoc;
	
	// Objects
	RelativeLayout mPlay;
	
	// Object Coordinates
	int layoutRight, layoutBottom, layoutLeft, layoutTop;
	
	// Handler
	Handler mHandler;
	Handler animateHandler;
	int updateInterval = 10;
	int updateInterval2 = 100;
	
//--------------------------------------------------------------------------------------------
// Constructor
	public Hero(Context context) {
		super(context);
		initImageView(context);
		getGameCoords();
		setupHandler();
		setupAnimate();
	}
//--------------------------------------------------------------------------------------------
// Handler
	// Movement Handler
	public void setupHandler() {
		Runnable periodicTask = new Runnable() {
			@Override
			public void run() {
				move();
		        checkOutOfBounds();
				moveOff();
				getHeroCoords();
		        
				mHandler.postDelayed(this, updateInterval);
			}
		};
		mHandler = new Handler();
		mHandler.postDelayed(periodicTask, updateInterval);
	}
	
	// Animation Handler
	public void setupAnimate() {
		Runnable periodicTask = new Runnable() {
			@Override
			public void run() {
				animateHero();
				mHandler.postDelayed(this, updateInterval2);
			}
		};
		mHandler = new Handler();
		mHandler.postDelayed(periodicTask, updateInterval2);
	}
//--------------------------------------------------------------------------------------------
// Get Coords
	public void getGameCoords() {
		// PlayLayout
		layoutRight = MainActivity.playRight;
		layoutLeft = MainActivity.playLeft;
		layoutBottom = MainActivity.playBottom;
		layoutTop = MainActivity.playTop;
	}
//--------------------------------------------------------------------------------------------
// Movement
	public void move() {
		// Jump enabled
		if (jump) {
			inAir = true;
			if (getY() <= Bottom) {
				moveBy(0, -jVel);
				if (jVel >= 0) {
					jVel-= resist;
					resist++;
				}
				else {
					jVel = jVelMax;
					resist = 0;
					jump = false;
				}
				if (getY() >= Bottom) {
					jump = false;
					inAir = false;
				}
			}
		}
		// Jump Disabled
		else {
			if (checkGrav()) {
				moveBy(0, grav);
				grav += 0.5f;
			}
			else {
				moveBy(0, grav);
			}
		}
		// Right/Left enabled
		if (mRight && mLeft) {
			if (moveHero > 0) {
				moveHero--;
			}
			else if (moveHero < 0) {
				moveHero++;
			}
		}
		else if (mLeft && !mRight) {
			moveBy(moveHero, 0);
			if (moveHero <= 20 && moveHero >= -20) {
				moveHero--;
			}
		}
		else if (mRight && !mLeft) {
			moveBy(moveHero, 0);
			if (moveHero >= -20 && moveHero <= 20) {
				moveHero++;
			}
		}
	}
	
	// Slide to stop
	public void moveOff() {
		if (mRightOff) {
			moveBy(moveHero,0);
			moveHero--;
			if (moveHero <= 0) {
				mRightOff = false;
				moveHero = 0;
			}
		}
		if (mLeftOff) {
			moveBy(moveHero,0);
			moveHero++;
			if (moveHero >= 0) {
				mLeftOff = false;
				moveHero = 0;
			}
		}
	}
	
	public void makeRun() {
		moveBy(0, grav);
	}
	// Animation for Hero
	public void animateHero() {
		// jump
		if (inAir) {
			if (!direction) {
				switch(jumpAnim) {
					case 0:
						jumpAnim++;
						setImageResource(R.drawable.lj1);
						break;
					case 1:
						jumpAnim++;
						setImageResource(R.drawable.lj2);
						break;
					case 2:
						jumpAnim++;
						setImageResource(R.drawable.lj3);
						break;
					case 3:
						jumpAnim = 0;
						setImageResource(R.drawable.lj4);
						break;
				}
			}
			else {
				switch(jumpAnim) {
					case 0:
						jumpAnim++;
						setImageResource(R.drawable.rj1);
						break;
					case 1:
						jumpAnim++;
						setImageResource(R.drawable.rj2);
						break;
					case 2:
						jumpAnim++;
						setImageResource(R.drawable.rj3);
						break;
					case 3:
						jumpAnim = 0;
						setImageResource(R.drawable.rj4);
						break;
				}
			}
		}
		// run left
		else if (moveHero < 0) {
			direction = false;
			switch(left) {
				case 0:
					left++;
					setImageResource(R.drawable.ll1);
					break;
				case 1:
					left++;
					setImageResource(R.drawable.ll2);
					break;
				case 2:
					left++;
					setImageResource(R.drawable.ll3);
					break;
				case 3:
					left++;
					setImageResource(R.drawable.lr1);
					break;
				case 4:
					left++;
					setImageResource(R.drawable.lr2);
					break;
				case 5:
					left = 0;
					setImageResource(R.drawable.lr3);
					break;
			}
			
		}
		// run right
		else if (moveHero > 0) {
			direction = true;
			switch(right) {
				case 0:
					right++;
					setImageResource(R.drawable.rl1);
					break;
				case 1:
					right++;
					setImageResource(R.drawable.rl2);
					break;
				case 2:
					right++;
					setImageResource(R.drawable.rl3);
					break;
				case 3:
					right++;
					setImageResource(R.drawable.rr1);
					break;
				case 4:
					right++;
					setImageResource(R.drawable.rr2);
					break;
				case 5:
					right = 0;
					setImageResource(R.drawable.rr3);
					break;
			}
			
		}
		if (moveHero == 0 && !inAir) {
			if (!direction) {
				setImageResource(R.drawable.ln1);
			}
			else if (direction) {
				setImageResource(R.drawable.rn1);
			}
		}
	}
//--------------------------------------------------------------------------------------------
// Physics
	public boolean checkGrav() {
		return grav < 15;
	}
	
	public boolean checkOutOfBounds() {
		// Left and Right and Bottom
		if (getY() >= Bottom) {
			inAir = false;
			if (getX() >= Right) {
				moveTo(Right, Bottom);
				if (mRight)
					moveHero = 0;
				grav = 1;
			}
			else if (getX() <= Left) {
				moveTo(Left, Bottom);
				if (mLeft)
					moveHero = 0;
				grav = 1;
			}
			else {
				moveTo(getX(), Bottom);
				grav = 1;
			}
			return true;
		}
		else if (getY() <= Top) {
			if (getX() >= Right) {
				moveTo(Right, getY());
			}
			else if (getX() <= Left) {
				moveTo(Left, getY());
			}
			else {
				moveTo(getX(), Top);
			}
			return true;
		}
		else if (getX() >= Right) {
			moveTo(Right, getY());
			return true;
		}
		else if (getX() <= Left) {
			moveTo(Left, getY());
			return true;
		}
		checkPlatHits();
		return false;
	}
	
	public void checkPlatHits() {
		// LEFT
		if (getY() > MainActivity.L_T_Top-75 && getY() < MainActivity.L_T_Bottom-75 &&
			getX() > MainActivity.L_L_Left-75 && getX() < MainActivity.L_R_Right) {
			moveTo(getX(), MainActivity.L_T_Top-75);
			inAir = false;
		}
		// RIGHT
		if (getY() > MainActivity.R_T_Top-75 && getY() < MainActivity.R_T_Bottom-75 &&
			getX() > MainActivity.R_L_Left-75 && getX() < MainActivity.R_R_Right) {
			moveTo(getX(), MainActivity.R_T_Top-75);
			inAir = false;
		}
	}
//--------------------------------------------------------------------------------------------
// Initialize Hero Image
	private void initImageView(Context context)	{
		if (isInEditMode())
			return;
			
		if (null == heroBitmap) {
			LoadHeroImage(context.getAssets());
		}
		setImageBitmap(heroBitmap);
		// now set the size
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(heroSize, heroSize);
		setLayoutParams(layoutParams);
	}
	
	static private void LoadHeroImage(AssetManager loader) {
		InputStream bitmapStream = null;
		try {
			//open the file from the assets folder with the given name
			bitmapStream = loader.open(heroImage);
			//decode the stream as a bitmap
			heroBitmap = BitmapFactory.decodeStream(bitmapStream);
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
//--------------------------------------------------------------------------------------------
// Set Move Hero
	// Jump
	public void setJump() {
		if (!jump) {
			jump = true;
		}
		else {
			jump = false;
		}
	}
	// Right
	public void setRight() {
		if (!mRight) {
			mRight = true;
		}
		else {
			mRight = false;
			mRightOff = true;
		}
	}
	// Left
	public void setLeft() {
		if (!mLeft) {
			mLeft = true;
		}
		else {
			mLeft = false;
		}
	}
	// Get hero location
	public void getHeroCoords() {
		xLoc = getX();
		yLoc = getY();
	}
	// Move Hero by a specified amount
	public void moveBy(float x, float y) {
		setX(getX()+x);
		setY(getY()+y);
	}
	// Move Hero to a specified location
	public void moveTo(float x, float y)
	{
		setX(x);
		setY(y);
	}
}
