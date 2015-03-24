package uwb.css390.FilipTodorovicFinal;
/*
 * NOTES: Images of Sonic The Hedgehog and background music from Sonic the Hedgehog 3 and 
 * 		  Knuckles soundtrack are trademarks of SEGA Corporation. I do not own the rights 
 * 		  to any of the images used in this application. Sale of this application is 
 *        prohibited by law.
 */

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.graphics.Bitmap;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	// Objects
	Hero mHero;
	Coin mStar;
	ImageButton Jump;
	ImageButton Left;
	ImageButton Right;
	ImageButton Create;
	ImageButton Camera;
	static TextView mMiss;
	static TextView mHit;
	Platform mLeftPlat;
	Platform mRightPlat;
	Timer mTimer;
	static ImageView mTime;
	static RelativeLayout mPlay;
	static RelativeLayout mStarArea;
	
	// Timer
	static int num = 3;
	static boolean on = false;
	static int caught = 0;
	static int missed = 0;
	static int ticks = 0;
	
	// Camera
	final int CAMERA_PIC_REQUEST = 0;
	static Bitmap bCam = null;
	final static String kCam = "camera.png";
	public static Bitmap yourSelectedImage = null;
	
	// Music
	MediaPlayer clock;
	static boolean soundOn = false;
	
	// Star
	static boolean created = false;
	Random rand = new Random();
	
	// TEST
	static TextView mLPlatTop;
	static TextView mHeroX, mHeroY;
	
	// mPlay Coordinates
	int mPlayCoords[] = new int[2];
	static int playTop, playRight, playBottom, playLeft;
	
	// Platform Coordinates
	static int L_T_Top, L_R_Right, L_T_Bottom, L_R_Left, L_B_Top, L_L_Right, L_B_Bottom, L_L_Left;
	static int R_T_Top, R_R_Right, R_T_Bottom, R_R_Left, R_B_Top, R_L_Right, R_B_Bottom, R_L_Left;
	
	static int[] leftPlatform = new int[2];
	int[] rightPlatform = new int[2];
	int offset = 20;
	
	static int leftPlatX, leftPlatY, rightPlatX, rightPlatY;
//--------------------------------------------------------------------------------------------
// Creation
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		linkGui();
		placeHero();
		getPlayCoords();
		placePlatforms();
		setupAssets();
		setupJump();
		setupLR();
		setupCreate();
		setupTimer();
		setupCamera();
	}
//--------------------------------------------------------------------------------------------
// All objects shown on-screen
	@Override
	public void onWindowFocusChanged (boolean hasFocus){
	    super.onWindowFocusChanged(hasFocus);
	    if(hasFocus){
	    	getPlatXY();
	    	getPlatCoords();
	    	getLeftPlatCoords();
	    	getRightPlatCoords();
	    }
	}
//--------------------------------------------------------------------------------------------
// Buttons
	//Jump
	public void setupJump() {
		Jump.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
    			switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						if (mHero.getY() >= 405 || 
							(mHero.getY() == 250-25 && mHero.getX() <= 400-25 && mHero.getX() >= 150-25) ||
							(mHero.getY() == 250-25 && mHero.getX() <= 980-25 && mHero.getX() >= 730-25)){ 
							mHero.setJump();
						}
						break;
						
    			}
    			return true;
			}
		});
	}
	
	// Left and Right
	public void setupLR() {
		// Left
		Left.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
    			switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						mHero.setLeft();
						break;
						
					case MotionEvent.ACTION_UP:
						mHero.setLeft();
						Hero.mLeftOff = true;
						break;
						
    			}
    			return true;
			}
		});
		// Right
		Right.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						mHero.setRight();
						break;
					case MotionEvent.ACTION_UP:
						mHero.setRight();
						Hero.mRightOff = true;
//						mHero.moveHero = 0;
						break;
				}
				return true;
			}
		});
	}

	public void setupCreate() {
		Create.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
    			switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						if (!created) {
							created = true;
							placeStar();
							on = true;
						}
						break;
    			}
    			return true;
			}
		});
	}
	
	// Camera
	public void setupCamera() {
		Camera.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            Intent camera_intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	            startActivityForResult(camera_intent, CAMERA_PIC_REQUEST);
	        }
	    });
	}
	//--------------------------------------------------------------------------------------------
// Setup
	public void linkGui() {
		mPlay = (RelativeLayout) findViewById(R.id.playLayout);
		mStarArea = (RelativeLayout) findViewById(R.id.starArea);
		Jump = (ImageButton) findViewById(R.id.buttonJump);
		Left = (ImageButton) findViewById(R.id.buttonLeft);
		Right = (ImageButton) findViewById(R.id.buttonRight);
		Create = (ImageButton) findViewById(R.id.createButton);
		Camera = (ImageButton) findViewById(R.id.cameraButton);
		mHit = (TextView) findViewById(R.id.textHits);
		mMiss = (TextView) findViewById(R.id.textMiss);
		mTime = (ImageView) findViewById(R.id.timeImage);
		
		// TEST
		mLPlatTop = (TextView) findViewById(R.id.leftTop);

		// TEST
		mHeroX = (TextView) findViewById(R.id.heroX);
		mHeroY = (TextView) findViewById(R.id.heroY);
	}
	
	// Place the HERO
	public void placeHero() {
		mHero = new Hero(mPlay.getContext());
		mPlay.addView(mHero);
		mHero.moveTo(615, 300);
		mHero.setImageResource(R.drawable.rn1);
	}
	
	// Place a COIN
	public void placeStar() {
		mStar = new Coin(mPlay.getContext());
		mPlay.addView(mStar);
		mStar.moveTo(rand.nextInt(1200), rand.nextInt(250));
	}

//--------------------------------------------------------------------------------------------------
// Timer
	public void setupTimer() {
		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {
			
			public void run() { 
				TimerMethod(); 
			}
		}, 0, 100);
		
		mTime.setVisibility(View.INVISIBLE);
	}
	
	private void TimerMethod()
	{
		this.runOnUiThread(Timer_Tick);
	}
	
	private Runnable Timer_Tick = new Runnable() {
		public void run() {
			//showStats(false);
			if (on) {
				if (ticks == 0) {
					changeTime();
				}
				if (ticks != 10) {
					ticks++;
				}
				if (ticks == 10) {
					num--;
					if (num == 0) {
						resetTime();
					}
					ticks = 0;
				}
			}
		}
	};
	
	public void changeTime() {
		if (num == 3) {
			mTime.setVisibility(View.VISIBLE);
			mTime.setImageResource(R.drawable.three);
		}
		else if (num == 2) {
			mTime.setImageResource(R.drawable.two);
		}
		else if (num == 1) {
			mTime.setImageResource(R.drawable.one);
		}
	}
	
	public static void resetTime() {
		on = false;
		ticks = 0;
		mTime.setVisibility(View.INVISIBLE);
	}
//--------------------------------------------------------------------------------------------------
// Platform
	public void placePlatforms() {
		mLeftPlat = new Platform(mPlay.getContext());
		mPlay.addView(mLeftPlat);
		mLeftPlat.moveTo(200, 300);
		
		mRightPlat = new Platform(mPlay.getContext());
		mPlay.addView(mRightPlat);
		mRightPlat.moveTo(780, 300);
		
		Create.setBackground(null);
		Jump.setBackground(null);
		Left.setBackground(null);
		Right.setBackground(null);
	}
	
	public void getPlatXY() {
		leftPlatX = 200;
		leftPlatY = 300;
		rightPlatX = 780;
		rightPlatY = 300;
	}
//--------------------------------------------------------------------------------------------------
// Get Coordinates
	public void getLeftPlatCoords() {
		L_T_Top = leftPlatY;
		L_R_Right = leftPlatX+200;
		L_T_Bottom = leftPlatY+offset;
		L_R_Left = leftPlatX+200-offset;
		L_B_Top = leftPlatY+50-offset;
		L_L_Right = leftPlatX+offset;
		L_B_Bottom = leftPlatY+50;
		L_L_Left = leftPlatX;
	}
	
	public void getRightPlatCoords() {
		R_T_Top = rightPlatY;
		R_R_Right = rightPlatX+200;
		R_T_Bottom = rightPlatY+offset;
		R_R_Left = rightPlatX+200-offset;
		R_B_Top = rightPlatY+50-offset;
		R_L_Right = rightPlatX+offset;
		R_B_Bottom = rightPlatY+50;
		R_L_Left = rightPlatX;
	}
	
	public void getPlatCoords() {
		mLeftPlat.getLocationOnScreen(leftPlatform);
		mRightPlat.getLocationOnScreen(rightPlatform);
	}
	
	public void getPlayCoords() {
		mPlay.getLocationOnScreen(mPlayCoords);
		playTop = mPlay.getTop();
		playRight = mPlay.getRight();
		playBottom = mPlay.getBottom();
		playLeft = mPlay.getRight();
	}
	
	public void setupAssets() {
		// Platforms
		mLeftPlat.setImageResource(R.drawable.left_platform2);
		mRightPlat.setImageResource(R.drawable.right_platform2);
		
		// Camera
		Camera.setImageResource(R.drawable.camera);
		Camera.setBackground(null);
		
		// Background
		mPlay.setBackgroundResource(R.drawable.landscape);
		
		// Text
		mHit.setTextColor(Color.WHITE);
		mMiss.setTextColor(Color.WHITE);
		
		// Music
		clock = MediaPlayer.create(getApplicationContext(),R.raw.music_theme);
		clock.setLooping(true);
		clock.start();
	}
//--------------------------------------------------------------------------------------------------
// TEST
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    switch(requestCode){
	        case CAMERA_PIC_REQUEST:
				if(resultCode==RESULT_OK){
					//getting the raw bitmap data from the image that was taken
					yourSelectedImage = (Bitmap) data.getExtras().get("data");
					Coin.changed = true;
					Create.setImageBitmap(yourSelectedImage);
				}
				break;
	    }
	}
//--------------------------------------------------------------------------------------------------
// TEST
	
	public void showStats(boolean check) {
		if (check) {
			mLPlatTop.setText("Ticks: " + ticks + " On: " + on + " Num: " + num);
		}
	}
}
