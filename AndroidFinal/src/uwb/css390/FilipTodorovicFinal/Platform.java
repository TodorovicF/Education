package uwb.css390.FilipTodorovicFinal;

import java.io.IOException;
import java.io.InputStream;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Platform extends ImageView{

	// Image Parameters
	final static String kPlat = "star.png";
	static Bitmap bPlat = null;
	final static int kSizeX = 200;
	final static int kSizeY = 50;
	
	static float x, y;

	// Object Coordinates
	static float T_Top, T_Bottom, R_Right, R_Left, B_Bottom, B_Top, L_Left, L_Right;
	static int offset = 10;
	
//--------------------------------------------------------------------------------------------
// Constructor
	public Platform(Context context) {
		super(context);
		initImageView(context);
	}
//--------------------------------------------------------------------------------------------
// Initialize Hero Image
	private void initImageView(Context context)	{
		if (isInEditMode())
			return;
			
		if (null == bPlat) {
			LoadPlatformImage(context.getAssets());
		}
		setImageBitmap(bPlat);
		// now set the size
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(kSizeX, kSizeY);
		setLayoutParams(layoutParams);
	}
	// Load platform image
	static private void LoadPlatformImage(AssetManager loader) {
		InputStream bitmapStream=null;
		try {
			//open the file from the assets folder with the given name
			bitmapStream = loader.open(kPlat);
			//decode the stream as a bitmap
			bPlat = BitmapFactory.decodeStream(bitmapStream);
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
	// Get platform coordinates
	public void getCoords() {
		x = getX();
		y = getY();
	}
	// Move platform to a specified location
	public void moveTo(float x, float y)
	{
		setX(x);
		setY(y);
	}
}
