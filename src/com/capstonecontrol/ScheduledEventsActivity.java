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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class ScheduledEventsActivity extends BarActivity {

	@SuppressWarnings("unused")
	private Context mContext = this;
	private Button moduleButton, timeButton, occurenceButton,
			daysAndDateButton, valueButton, submitButton;
	private int hour, minute, day, month, year, value, moduleInt,
			occurence = 0;
	private final int TIME_DIALOG_ID = 0;
	private final int DATE_DIALOG_ID = 1;
	private final int VALUE_DIALOG_ID = 2;
	private final int MODULE_DIALOG_ID = 3;
	private final int OCCURENCE_DIALOG_ID = 4;
	private TimePickerDialog.OnTimeSetListener mTimeSetListener;
	private DatePickerDialog.OnDateSetListener mDateSetListener;
	private NumberPicker valuePicker, modulePicker, occurencePicker;
	private LinearLayout dayPicker;
	private CheckBox monCheckBox, tueCheckBox, wedCheckBox, thuCheckBox,
			friCheckBox, satCheckBox, sunCheckBox;
	boolean mon, tue, wed, thu, fri, sat, sun;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.scheduled_events);
		// @TODO REMOVE BELOW AND PUT AND POST STATEMENT IN ITS OWN THREAD
		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);
		// create check boxes
		createCheckBoxs();
		// enable bar
		enableBar();
		// enable POST capabilities
		enablePOST();
		// find the buttons
		this.moduleButton = (Button) this.findViewById(R.id.moduleButton);
		this.timeButton = (Button) this.findViewById(R.id.timeButton);
		this.occurenceButton = (Button) this.findViewById(R.id.occurenceButton);
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

	private void createCheckBoxs() {
		monCheckBox = new CheckBox(mContext);
		monCheckBox.setText("Mon");
		monCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mon = isChecked;
			}
		});
		tueCheckBox = new CheckBox(mContext);
		tueCheckBox.setText("Tue");
		tueCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				tue = isChecked;
			}
		});
		wedCheckBox = new CheckBox(mContext);
		wedCheckBox.setText("Wed");
		wedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				wed = isChecked;
			}
		});
		thuCheckBox = new CheckBox(mContext);
		thuCheckBox.setText("Thu");
		thuCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				thu = isChecked;
			}
		});
		friCheckBox = new CheckBox(mContext);
		friCheckBox.setText("Fri");
		friCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				fri = isChecked;
			}
		});
		satCheckBox = new CheckBox(mContext);
		satCheckBox.setText("Sat");
		satCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				sat = isChecked;
			}
		});
		sunCheckBox = new CheckBox(mContext);
		sunCheckBox.setText("Sun");
		sunCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				sun = isChecked;
			}
		});

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

		// code for the module button
		moduleButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(MODULE_DIALOG_ID);
			}
		});

		// code for the occurrence button
		occurenceButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(OCCURENCE_DIALOG_ID);
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

	private void updateValueDisplay() {
		valueButton.setText("Value: " + value);
	}

	private void updateDayDisplay() {
		String days = "";
		if (mon)
			days += "Mon, ";
		if (tue)
			days += "Tue, ";
		if (wed)
			days += "Wed, ";
		if (thu)
			days += "Thu, ";
		if (fri)
			days += "Fri, ";
		if (sat)
			days += "Sat, ";
		if (sun)
			days += "Sun, ";
		if (days.length() > 1) {
			// remove the last comma
			days = days.substring(0, days.length() - 2);
		}
		daysAndDateButton.setText("Date/Days: " + days);

	}

	private void updateOccurenceDisplay() {
		if (occurence == 0) { // 0 = onetime 1 = weekly
			occurenceButton.setText("Occurence: One-time");
		} else {
			occurenceButton.setText("Occurence: Weekly");
		}
	}

	private void updateModuleDisplay() {
		moduleButton.setText("Module: "
				+ CapstoneControlActivity.modules.get(moduleInt)
						.getModuleName());
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

	private void updateDateChoices() {
		removeDialog(DATE_DIALOG_ID);
		if (occurence == 0) { // use date choose
			updateDateDisplay();
		} else {// assume occurence == 1, so use day chooser
			daysAndDateButton.setText("Date/Days: Choose Days");
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, hour, minute,
					false);
		case DATE_DIALOG_ID:

			// need to adjust date selections based on the occurrence selection
			if (occurence == 1) { // 0 means weekly
				dayPicker = new LinearLayout(mContext);
				dayPicker.setOrientation(1); // sets it to be vertical
				dayPicker.addView(monCheckBox);
				dayPicker.addView(tueCheckBox);
				dayPicker.addView(wedCheckBox);
				dayPicker.addView(thuCheckBox);
				dayPicker.addView(friCheckBox);
				dayPicker.addView(satCheckBox);
				dayPicker.addView(sunCheckBox);
				return new AlertDialog.Builder(this)
						.setTitle("Select value to send:")
						.setView(dayPicker)
						// .setMessage("")
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// do nothing, returns to the main menu
										updateDayDisplay();
									}
								}).show();
			} else { // assume 0 so its one time

				return new DatePickerDialog(this, mDateSetListener, year,
						month, day);
			}

		case VALUE_DIALOG_ID:
			valuePicker = new NumberPicker(mContext);
			valuePicker.setMaxValue(100);
			valuePicker.setMinValue(0);
			return new AlertDialog.Builder(this)
					.setTitle("Select value to send:")
					.setView(valuePicker)
					// .setMessage("")
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// do nothing, returns to the main menu
									value = valuePicker.getValue();
									updateValueDisplay();
								}
							}).show();
		case MODULE_DIALOG_ID:
			// create array of module names
			String[] moduleNames = new String[CapstoneControlActivity.modules
					.size()];
			for (int i = 0; i < CapstoneControlActivity.modules.size(); i++) {
				moduleNames[i] = CapstoneControlActivity.modules.get(i)
						.getModuleName();
			}
			modulePicker = new NumberPicker(mContext);
			modulePicker.setMaxValue(moduleNames.length - 1);
			modulePicker.setMinValue(0);
			modulePicker.setDisplayedValues(moduleNames);
			// disable software keyboard input
			modulePicker
					.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
			return new AlertDialog.Builder(this)
					.setTitle("Select Module:")
					.setView(modulePicker)
					// .setMessage("")
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// do nothing, returns to the main menu
									moduleInt = modulePicker.getValue();
									updateModuleDisplay();
								}
							}).show();
		case OCCURENCE_DIALOG_ID:
			occurencePicker = new NumberPicker(mContext);
			// disable software keyboard input
			occurencePicker
					.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
			occurencePicker.setMaxValue(1);
			occurencePicker.setMinValue(0);
			String[] occurenceChoices = { "One Time", "Weekly" };
			occurencePicker.setDisplayedValues(occurenceChoices);
			return new AlertDialog.Builder(this)
					.setTitle("Select occurance:")
					.setView(occurencePicker)
					// .setMessage("")
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// do nothing, returns to the main menu
									occurence = occurencePicker.getValue();
									updateOccurenceDisplay();
									updateDateChoices();
								}
							}).show();
		}
		return null;
	}
}
