package com.capstonecontrol;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ScheduledEventsActivity extends BarListActivity {

	@SuppressWarnings("unused")
	private Context mContext = this;
	Spinner valueSpinner;
	Spinner moduleSpinner;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.scheduled_events);
		// @TODO REMOVE BELOW AND PUT AND POST STATEMENT IN ITS OWN THREAD
		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);
		// enable bar
		enableBar();
		// enable POST capabilities
		enablePOST();
		// handle spinners
		setUpSpinners();
		// set up submit button
		setUpSubmitButton();
	}

	private void setUpSubmitButton() {
		// TODO Auto-generated method stub

	}

	private void setUpSpinners() {
		valueSpinner = (Spinner) findViewById(R.id.valueSpinner);
		final ArrayAdapter<CharSequence> valueAdapter = ArrayAdapter
				.createFromResource(this, R.array.module_event_array_OffOn,
						android.R.layout.simple_spinner_item);
		valueAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		valueSpinner.setAdapter(valueAdapter);
		// now do the other spinner
		moduleSpinner = (Spinner) findViewById(R.id.moduleTypeSpinner);
		ArrayAdapter<CharSequence> moduleAdapter = ArrayAdapter
				.createFromResource(this, R.array.moduleType_array_NoAll,
						android.R.layout.simple_spinner_item);
		moduleAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		moduleSpinner.setAdapter(moduleAdapter);

		// event for value spinner change based on module Type selected
		moduleSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				//arg 2 is the number of item selected starting with 0
				if (arg2 == 0){
					//then door buzzer selected
					
				}
				if (arg2 == 1){
					//then lights selected
					
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});

	}

}
