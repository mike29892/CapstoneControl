package com.capstonecontrol;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.view.Window;
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
		//@TODO REMOVE BELOW AND PUT AND POST STATEMENT IN ITS OWN THREAD
		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);
		//enable bar
		enableBar();
		//enable POST capabilities
		enablePOST();
		// set variables
		livingRoomBar = (SeekBar) this.findViewById(R.id.livingRoomBar);
		kitchenBar = (SeekBar) this.findViewById(R.id.kitchenBar);
		bedroomBar = (SeekBar) this.findViewById(R.id.bedroomBar);
		livingRoomText = (TextView) this.findViewById(R.id.livingRoomText);
		bedroomText = (TextView) this.findViewById(R.id.bedroomText);
		kitchenText = (TextView) this.findViewById(R.id.kitchenText);


		// livingRoomBar Listener
		livingRoomBar
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar arg0) {
						//send POST
						sendPOST("kitchenLights",progressString);				
					}

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
						// do nothing
					}

					@Override
					public void onProgressChanged(SeekBar arg0, int progress,
							boolean fromTouch) {
						// save progress
						progressString = Integer.toString(progress);
						// changed the display to off/on based on progress of
						// bar
						if (progress == 0) {
							livingRoomText.setText("Living Room - OFF");
						} else {
							livingRoomText.setText("Living Room - ON");		
						}
					}
				});

		// bedroomBar Listener
		bedroomBar
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar arg0) {
						//send POST
						sendPOST("bedroomLights",progressString);	
					}

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
						// do nothing
					}

					@Override
					public void onProgressChanged(SeekBar arg0, int progress,
							boolean fromTouch) {
						// save progress
						progressString = Integer.toString(progress);
						// changed the display to off/on based on progress of
						// bar
						if (progress == 0) {
							bedroomText.setText("Bedroom - OFF");
						} else {
							bedroomText.setText("Bedroom - ON");
						}
					}
				});

		// kitchenBar Listener
		kitchenBar
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar arg0) {
						// do nothing
					}

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
						// do nothing
					}

					@Override
					public void onProgressChanged(SeekBar arg0, int progress,
							boolean fromTouch) {
						// changed the display to off/on based on progress of
						// bar
						if (progress == 0) {
							kitchenText.setText("Kitchen - OFF");
						} else {
							kitchenText.setText("Kitchen - ON");
						}
					}
				});

	}
}