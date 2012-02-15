package com.capstonecontrol;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

public class LightsActivity extends BarActivity {
	// variables
	SeekBar livingRoomBar;
	SeekBar kitchenBar;
	SeekBar bedroomBar;
	TextView livingRoomText;
	TextView bedroomText;
	TextView kitchenText;
	HttpClient client;
	HttpPost post;
	String progressString;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.lights);
		// @TODO REMOVE BELOW AND PUT AND POST STATEMENT IN ITS OWN THREAD
		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);
		// enable bar
		enableBar();
		// enable POST capabilities
		enablePOST();
		// dynamically create labels and seekbars
		createLightSeekBars();

		/*
		 * old way // set variables livingRoomBar = (SeekBar)
		 * this.findViewById(R.id.livingRoomBar); livingRoomText = (TextView)
		 * this.findViewById(R.id.livingRoomText);
		 * 
		 * // livingRoomBar Listener livingRoomBar
		 * .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
		 * 
		 * @Override public void onStopTrackingTouch(SeekBar arg0) { //send POST
		 * sendPOST("kitchenLights",progressString); }
		 * 
		 * @Override public void onStartTrackingTouch(SeekBar arg0) { // do
		 * nothing }
		 * 
		 * @Override public void onProgressChanged(SeekBar arg0, int progress,
		 * boolean fromTouch) { // save progress progressString =
		 * Integer.toString(progress); // changed the display to off/on based on
		 * progress of // bar if (progress == 0) {
		 * livingRoomText.setText("Living Room - OFF"); } else {
		 * livingRoomText.setText("Living Room - ON"); } } });
		 */
	}

	private void createLightSeekBars() {
		ScrollView sv = new ScrollView(this);
		LinearLayout ll = (LinearLayout) this
				.findViewById(R.id.linearLayoutLights);
		ll.setOrientation(LinearLayout.VERTICAL);
		// now based on modules synced with account add labels and settings
		if (CapstoneControlActivity.modules != null) {
			for (int i = 0; i < CapstoneControlActivity.modules.size(); i++) {
				if (CapstoneControlActivity.modules.get(i).getModuleType()
						.equals("Dimmer")) {
					final int index = i;
					// add label
					TextView tv = new TextView(this);
					tv.setText(CapstoneControlActivity.modules.get(i)
							.getModuleName());
					ll.addView(tv);
					// add seekbar
					SeekBar sb = new SeekBar(this);
					ll.addView(sb);
					//add listener
					sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

						 @Override
						 public void onStopTrackingTouch(SeekBar arg0) {
						  // TODO Auto-generated method stub

						 }

						 @Override
						 public void onStartTrackingTouch(SeekBar arg0) {
							 sendPOST(CapstoneControlActivity.modules.get(index).getModuleName(),progressString);

						 }

						 @Override
						 public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
						  // TODO Auto-generated method stub
						 }
						});
				}
			}
		}

	}
}