// Filip Todorovic
// Machine Problem 1
// Due: 6/30/2014

package uwb.css390.FilipTodorovicMP1;

import uwb.css390.FilipTodorovicMP1.R;

import java.util.Timer;
import java.util.TimerTask;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.os.Build;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	int counter = 0;
	int progress = 0;
	int max = 100;
	boolean start = true;
	
	TextView myCount;
	Button myPlus, myMinus, myQuit;
	ProgressBar myPBar;
	Timer myTimer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		myPBar = (ProgressBar) findViewById(R.id.progress_bar);
		myCount = (TextView) findViewById(R.id.countText);
		myQuit = (Button) findViewById(R.id.quitButton);
		myPlus = (Button) findViewById(R.id.plusButton);
		myMinus = (Button) findViewById(R.id.minusButton);
		
		myCount.setText("Count: " + counter);

		myPBar.setMax(max);
		myTimer = new Timer();
		myTimer.schedule(new TimerTask() {
			
			public void run() {
				TimerMethod();
			}
		}, 0, 50);
		
		// Exit app when 'Quit' button is clicked
		myQuit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				quit();
			}
		});
		
		// Increment counter
		myPlus.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				add();
			}
		});
		
		// Decrement counter
		myMinus.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				subtract();
			}
		});
		
	}
	
	private void TimerMethod()
	{
		this.runOnUiThread(Timer_Tick);
	}

	// Update progress bar and counter based on timer
	private Runnable Timer_Tick = new Runnable() {
		public void run() {
			
			if (progress % 100 == 0 && start == false) {
				counter++;
				myCount.setText("Count: " + counter);
			}
			myPBar.setProgress(progress++);
			start = false;
			if (progress == max) {
				
				progress = 0;
			}
		}
	};
	
	// add to the counter
	private void add()
 	{
 		counter += 1;
 		myCount.setText("Count: " + counter);
 	}
	
	// subtract from the counter
	private void subtract()
 	{
 		counter -= 1;
 		myCount.setText("Count: " + counter);
 	}
 	
	// exit the application
 	private void quit()
 	{
 		System.exit(0);
 		finish();
 	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
