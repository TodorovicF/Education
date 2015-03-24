package uwb.css390.FilipTodorovicMP2;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class CenteredImageView extends ImageView {

	// Region: class variable support
	static Bitmap sDefaultImage = null;
	final static String kTheDuck = "duck.jpg";
	final static int kDefaultSize = 200;
	boolean check = false;
	
	static private void LoadDuckImage(AssetManager loader) {
		InputStream bitmapStream=null;
		try {
			//open the file from the assets folder with the given name
			bitmapStream=loader.open(kTheDuck);

			//decode the stream as a bitmap
			sDefaultImage = BitmapFactory.decodeStream(bitmapStream);
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

	// a little of a hack, but this shows the point ... 
	static MainActivity sMainActivity = null;
	static public void SetMainActivity(MainActivity v) { sMainActivity = v; }
	
	
	public CenteredImageView(Context context) {
		super(context);
		initImageView(context);
	}
	
	public CenteredImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initImageView(context);
	}	
	
	private void initImageView(Context context)	{
		if (isInEditMode())
			return;
		// default behavior only if no image is defined yet
		if (null == this.getDrawable()) {				
			if (null == sDefaultImage)
				LoadDuckImage(context.getAssets());
			setImageBitmap(sDefaultImage);
	
			// now set the size
			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(kDefaultSize, kDefaultSize);
			setLayoutParams(layoutParams);
		}
	}
	
	public void moveTo(float x, float y)
	{
		// center position is the reference
		setX(x-getWidth()/2);
		setY(y-getHeight()/2);
	}
	
	public void removeView(View v) {
		sMainActivity.mCurrentWorking.removeView(v);
	}
	
	public void setupSelectBehavior() {
		// now set up to support selection
		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					assert(null != sMainActivity);
//					sMainActivity.removeView(v);
//					sMainActivity.totalDroids--;
//					sMainActivity.Droids.setText("Total Droids: " + sMainActivity.totalDroids);
					break;
				}
				return true;
			}
		});
	}
}
