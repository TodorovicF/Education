package uwb.css390.FilipTodorovicEX4;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.MediaColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import 	android.media.ThumbnailUtils;

public class MainActivity extends Activity {
	
	final static int REQ_CODE_PICK_IMAGE = 1;
	
	ImageView mThumb;
	ImageView mFull;
	Button mSelectButton;
	CheckBox mShowSelected;
	
	boolean flag = false;
	long mOrig;
	Bitmap thumbView;
	Bitmap fullView;
	Uri selectedImage;
	String filePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		linkGUItoVars();
		setupButtons();
		
		
	}
	
	@Override
	//method tells application what too do when the call backs come from outside applications
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    switch(requestCode){
	        case REQ_CODE_PICK_IMAGE:
	            if(resultCode == RESULT_OK) {
	            	mThumb = (ImageView) findViewById(R.id.thumbView);
	            	mFull = (ImageView) findViewById(R.id.fullView);
	            	//getting the uri location of the selected picture from the gallery
	                Uri selectedImage = data.getData();
	                //getting the specific information on location within the gallery database
	                String[] requestedData = {MediaColumns.DATA};
	                // we are asking for data stream: the actual image
	                // we can also ask for e.g., title, size, resolution, etc.
	                //getting a cursor to the table of items we receive from the database querey (though we only get one in this case)
	                Cursor cursor = getContentResolver().query(selectedImage, requestedData, null, null, null);
	                //look at the first row
	                cursor.moveToFirst();
	                //get the column with the data we need
	                int columnIndex = cursor.getColumnIndex(requestedData[0]);
	                //save the file path
	                filePath = cursor.getString(columnIndex);
	                mOrig = cursor.getLong(columnIndex);
	                //String filePath = cursor.getString(columnIndex);
	                //decode the file into a bitmap
	                Bitmap thumbView = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(filePath), 150, 150);
	                Bitmap fullView = BitmapFactory.decodeFile(filePath);
	                //----------------------------------------------------------------------------------------------
	                // set both images but set the fullsize imageview as invisible, then toggle it with the checkbox
	                //----------------------------------------------------------------------------------------------
	                mThumb.setImageBitmap(thumbView);
	                mFull.setImageBitmap(fullView);
	                mFull.setVisibility(View.INVISIBLE);
	                flag = true;
	                cursor.close();
	                //mThumb.setImageBitmap(yourSelectedImage);
	            }
	            break;
	    }
	}
	
	private void setupButtons() {
		
		mSelectButton.setOnClickListener(new OnClickListener() {
			@Override 
			public void onClick(View v) {
	        	//create an intent to access the phones gallery giving the location it is found
	            Intent select_intent = new Intent(Intent.ACTION_PICK, 
	            							      android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	            //start activity and wait for specific call back
	            startActivityForResult(select_intent, REQ_CODE_PICK_IMAGE);
			}
		});
		
		mShowSelected.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (((CheckBox) v).isChecked() && flag == true) {
					mFull.setVisibility(View.VISIBLE);
					mThumb.setVisibility(View.INVISIBLE);
				}
				else if (flag == false) {

				}
				else {
					mFull.setVisibility(View.INVISIBLE);
					mThumb.setVisibility(View.VISIBLE);
				}
			}
		});
	}
	
	private void linkGUItoVars() {
		mThumb = (ImageView) findViewById(R.id.thumbView);
		mSelectButton = (Button) findViewById(R.id.selectButton);
		mShowSelected = (CheckBox) findViewById(R.id.showBox);
	}
}
