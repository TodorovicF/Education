package uwb.css390.FilipTodorovicMP3;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	static RelativeLayout mPlayLayout;
	RelativeLayout mMainLayout;
	Button mCreate;   // Create button
	Button mPlay;     // Play button
	Button mDelete;   // Delete button
	ImageButton mDuck;    // Duck button
	ImageButton mCroc;    // Crocodile button
	static TextView mCount;   // Count text
	TextView mMode;    // Mode text
	
	Hero mCurrent;   // Hero to hold the current dropped ImageView
	
	static int count = 0;   // Variable for ImageViews count
	String mode = "Create";   // String for the current mode selected
	
	static boolean create = true;   // Select Create
	static boolean play = false;    // Select Play
	static boolean delete = false;  // Select Delete
	static boolean duck = true;     // Select Duck
	static boolean croc = false;    // Select Crocodile
	
	int x = 0;
	int y = 0;
	static int velocity = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LinkGUItoVars();
		SetupButtons();
		
//----------------------------------------------------------------------------------
// Set what happens when you click on the playLayout RelativeLayout
		mPlayLayout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
	    		if (create) {
	    			mCurrent = new Hero(mPlayLayout.getContext());
	    			mPlayLayout.addView(mCurrent);
	    			mCurrent.moveTo(event.getX(), event.getY());
	    			
	    			count++;
	    			mCount.setText("Count: " + count);
	    			return false;
	    		}
	    		return false;
			}
		});
	}
//----------------------------------------------------------------------------------
	static public void updateCount(String update) {
		if (update == "sub") {
			count--;
			mCount.setText("Count: " + count);
		}
		else if (update == "add") {
			count++;
			mCount.setText("Count: " + count);
		}
	}
//----------------------------------------------------------------------------------
// Link object variables to GUI
	private void LinkGUItoVars() {
		mPlayLayout = (RelativeLayout) findViewById(R.id.playLayout);
		mCreate = (Button) findViewById(R.id.createButton);
		mPlay = (Button) findViewById(R.id.playButton);
		mDelete = (Button) findViewById(R.id.deleteButton);
		mDuck = (ImageButton) findViewById(R.id.duckButton);
		mCroc = (ImageButton) findViewById(R.id.crocButton);
		mCount = (TextView) findViewById(R.id.count);
		mMode = (TextView) findViewById(R.id.mode);
	}
//----------------------------------------------------------------------------------
// Button functions
	private void SetupButtons() {
		mPlayLayout.setBackgroundColor(Color.GRAY);
		mDuck.setBackgroundColor(Color.RED);
		mCroc.setBackgroundColor(Color.GRAY);
		
		mCreate.setOnClickListener(new OnClickListener() {
	    	@Override
			public void onClick(View v) {
				create = true;
				play = false;
				delete = false;
				
				velocity = 0;
				mode = "Create";
				mMode.setText("Mode: " + mode);
			}
		});
		
		mPlay.setOnClickListener(new OnClickListener() {
	    	@Override
			public void onClick(View v) {
	    		create = false;
				play = true;
				delete = false;
				
				velocity++;
				mode = "Play";
				mMode.setText("Mode: " + mode);
			}
		});
		
		mDelete.setOnClickListener(new OnClickListener() {
	    	@Override
			public void onClick(View v) {
	    		create = false;
				play = false;
				delete = true;
				
				velocity = 0;
				mode = "Delete";
				mMode.setText("Mode: " + mode);
			}
		});
		
		mDuck.setOnClickListener(new OnClickListener() {
	    	@Override
			public void onClick(View v) {
				duck = true;
				croc = false;
				
				mDuck.setBackgroundColor(Color.RED);
				mCroc.setBackgroundColor(Color.GRAY);
			}
		});
		
		mCroc.setOnClickListener(new OnClickListener() {
	    	@Override
			public void onClick(View v) {
	    		duck = false;
				croc = true;
				
				mDuck.setBackgroundColor(Color.GRAY);
				mCroc.setBackgroundColor(Color.RED);
			}
		});
	}
}
