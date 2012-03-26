package com.capstonecontrol;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;

public class ScheduledEventsActivity extends BarActivity {

	@SuppressWarnings("unused")
	private Context mContext = this;
	private Button moduleButton, timeButton, occuranceButton,
			daysAndDateButton, valueButton, submitButton;
	private int hour, minute, day, month, year;
	private final int TIME_DIALOG_ID = 0;
	private final int DATE_DIALOG_ID = 1;
	private final int VALUE_DIALOG_ID = 2;
	private TimePickerDialog.OnTimeSetListener mTimeSetListener;
	private DatePickerDialog.OnDateSetListener mDateSetListener;

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
		// find the buttons
		this.moduleButton = (Button) this.findViewById(R.id.moduleButton);
		this.timeButton = (Button) this.findViewById(R.id.timeButton);
		this.occuranceButton = (Button) this.findViewById(R.id.occuranceButton);
		this.daysAndDateButton = (Button) this
				.findViewById(R.id.daysAndDateButton);
		this.valueButton = (Button) this.findViewById(R.id.valueButton);

		// get the current time
		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
		day = c.get(Calendar.DAY_OF_MONTH);
		month = c.get(Calendar.MONTH);
		year = c.get(Calendar.YEAR);
		// display the current date
		updateTimeDisplay();
		updateDateDisplay();
		// set up settings buttons
		setUpButtons();
		// set up submit button
		setUpSubmitButton();
	}

	private void setUpButtons() {
		// Code for the time button!!
		timeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
			}
		});
		// the callback received when the user "sets" the time in the dialog
		mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker arg0, int arg1, int arg2) {
				hour = arg1;
				minute = arg2;
				updateTimeDisplay();
			}
		};

		// code for the date button
		daysAndDateButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		// the callback received when the user "sets" the time in the dialog
		mDateSetListener = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int arg1, int arg2, int arg3) {
				year = arg1;
				month = arg2;
				day = arg3;
				updateDateDisplay();
			}
		};

		// code for the value button
		valueButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(VALUE_DIALOG_ID);
			}
		});
	}

	// updates the time we display in the TextView
	private void updateTimeDisplay() {
		timeButton.setText("Time: "
				+ new StringBuilder().append(pad(hour)).append(":")
						.append(pad(minute)));
	}

	private void updateDateDisplay() {
		daysAndDateButton.setText("Date/Days: "
				+ new StringBuilder().append(month).append("/").append(day)
						.append("/").append(year));
	}

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	private void setUpSubmitButton() {
		// TODO Auto-generated method stub

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, hour, minute,
					false);

		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, year, month,
					day);
		case VALUE_DIALOG_ID:
			Spinner valueSpinner = new Spinner(mContext);
			final ArrayAdapter<CharSequence> valueAdapter = ArrayAdapter
					.createFromResource(this, R.array.module_event_array_OffOn,
							android.R.layout.simple_spinner_item);
			valueAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			valueSpinner.setAdapter(valueAdapter);
			return new AlertDialog.Builder(this)
					.setTitle("Function not yet implemented.")
					.setView(valueSpinner)
					// .setMessage("")
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// do nothing, returns to the main menu
								}
							})
					/*
					 * .setPositiveButton("Yes", new
					 * DialogInterface.OnClickListener() { public void
					 * onClick(DialogInterface dialog, int which) { // continue
					 * with delete } }) .setNegativeButton("No", new
					 * DialogInterface.OnClickListener() { public void
					 * onClick(DialogInterface dialog, int which) { // do
					 * nothing } })
					 */
					.show();
		}
		return null;
	}

	/*
	 * private void setUpSpinners() { valueSpinner = (Spinner)
	 * findViewById(R.id.valueSpinner); final ArrayAdapter<CharSequence>
	 * valueAdapter = ArrayAdapter .createFromResource(this,
	 * R.array.module_event_array_OffOn, android.R.layout.simple_spinner_item);
	 * valueAdapter
	 * .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	 * valueSpinner.setAdapter(valueAdapter); // now do the other spinner
	 * moduleSpinner = (Spinner) findViewById(R.id.moduleTypeSpinner);
	 * ArrayAdapter<CharSequence> moduleAdapter = ArrayAdapter
	 * .createFromResource(this, R.array.moduleType_array_NoAll,
	 * android.R.layout.simple_spinner_item); moduleAdapter
	 * .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	 * moduleSpinner.setAdapter(moduleAdapter);
	 * 
	 * // event for value spinner change based on module Type selected
	 * moduleSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
	 * 
	 * @Override public void onItemSelected(AdapterView<?> arg0, View arg1, int
	 * arg2, long arg3) { //arg 2 is the number of item selected starting with 0
	 * if (arg2 == 0){ //then door buzzer selected
	 * 
	 * } if (arg2 == 1){ //then lights selected
	 * 
	 * }
	 * 
	 * }
	 * 
	 * @Override public void onNothingSelected(AdapterView<?> arg0) { // TODO
	 * Auto-generated method stub
	 * 
	 * } });
	 * 
	 * }
	 */

}
