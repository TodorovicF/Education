package uwb.css390.FilipTodorovicMP3;

import java.io.IOException;
import java.io.InputStream;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.os.Handler;

public class Hero extends ImageView {

		final static String kDuck = "duck.png";
		final static String kCroc = "crocodile.png";
		static Bitmap bDuck = null;
		static Bitmap bCroc = null;
		final static int kSize = 50;
		Hero hero = this;
		boolean visible = true;
		String Add = "add";
		String Subtract = "sub";
		
		Handler mFutureTask;
		final int kUpdatePeriod = 25;

		float count = 0.10f;
		
		// a little of a hack, but this shows the point ... 
		static MainActivity sMainActivity = null;
		static public void SetMainActivity(MainActivity v) { sMainActivity = v; }
		
		static private void LoadDuckImage(AssetManager loader) {
			InputStream bitmapStream=null;
			try {
				//open the file from the assets folder with the given name
				bitmapStream = loader.open(kDuck);
				//decode the stream as a bitmap
				bDuck = BitmapFactory.decodeStream(bitmapStream);
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
		
		static private void LoadCrocImage(AssetManager loader) {
			InputStream bitmapStream=null;
			try {
				//open the file from the assets folder with the given name
				bitmapStream=loader.open(kCroc);

				//decode the stream as a bitmap
				bCroc = BitmapFactory.decodeStream(bitmapStream);
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
		// EndRegion
		
		public Hero(Context context) {
			super(context);
			initImageView(context);
			setupTouchBehavior();
			setupHandler();
			
		}
		
		private void initImageView(Context context)	{
			if (isInEditMode())
				return;
			// if the duck is set to true, create a duck
			if (null == this.getDrawable() && MainActivity.duck) {				
				if (null == bDuck) {
					LoadDuckImage(context.getAssets());
				}
				setImageBitmap(bDuck);
				// now set the size
				FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(kSize, kSize);
				setLayoutParams(layoutParams);
			}
			// if the croc is set to true, create a croc
			else if (null == this.getDrawable() && MainActivity.croc) {
				if (null == bCroc) {
					LoadCrocImage(context.getAssets());
				}
				setImageBitmap(bCroc);
				// now set the size
				FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(kSize, kSize);
				setLayoutParams(layoutParams);
			}
		}
//--------------------------------------------------------------------------------------------------
// Move the Heroes and Delete them if they reach the top
		public void setupHandler() {
			Runnable periodicTask = new Runnable() {
				@Override
				public void run() {
					// make sure Play is selected
					if (MainActivity.play) {
						hero.moveImage(hero.getX(), hero.getY());
						if (hero.getY() <= MainActivity.mPlayLayout.getTop()-150 ||
								hero.getX() >= MainActivity.mPlayLayout.getRight()-50 ||
								hero.getY() >= MainActivity.mPlayLayout.getBottom()-175 ||
								hero.getX() <= MainActivity.mPlayLayout.getLeft()) {
							hero.setVisibility(View.GONE);
							if (visible == true) {
								MainActivity.updateCount(Subtract);
								visible = false;
							}
						}
					}
					// Setup next run
					mFutureTask.postDelayed(this, kUpdatePeriod);
				}
			};
			// Setup next job
			mFutureTask = new Handler();
			mFutureTask.postDelayed(periodicTask, kUpdatePeriod); 
		}
//--------------------------------------------------------------------------------------------------
// What happens when a Hero is clicked
		public void setupTouchBehavior() {
			// now set up to support selection
			hero.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN: {
							if (MainActivity.delete) {
								assert(null != sMainActivity);
								hero.setVisibility(View.GONE);
								MainActivity.updateCount(Subtract);
								MainActivity.mCount.setText("Count: " + MainActivity.count);
								visible = false;
							}
							break;
						}
					}
					return true;
				}
			});
		}
//--------------------------------------------------------------------------------------------------
// Move image (for use with Handler)
// EDIT: Added spiral motion for Heroes, they will die if they go outside the bounds of the layout
		public void moveImage(float x, float y) {
			this.setX(x + ((float) (count * Math.cos(count))));
			this.setY(y + ((float) (count * Math.sin(count))));
			count += 0.1;
		}
//--------------------------------------------------------------------------------------------------
// Set the initial position of the Hero when created
		public void moveTo(float x, float y)
		{
			// Ceneter of Hero is the reference for create location
			setX(x-getWidth()/2);
			setY(y-getHeight()/2);
		}
}
