package com.capstonecontrol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SettingsActivity extends BarActivity {

	public static String serverIPAddress;
	Button applyButton;
	TextView ipAddressField;
	private Pattern pattern;
	private Matcher matcher;

	private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.settings);
		// create instances of each text field or option selection
		createFieldInstances();
		// populate fields with current data
		populateFields();
		// enable the main button listeners
		enableApplyButtonListener();
		// enable bar
		enableBar();
	}

	public void createFieldInstances() {
		ipAddressField = (TextView) this.findViewById(R.id.ipAddress);
	}

	public void enableApplyButtonListener() {
		// create listeners for the buttons
		// lights button
		this.applyButton = (Button) this.findViewById(R.id.applyButton);
		this.applyButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// update all entered fields
				serverIPAddress = ipAddressField.getText().toString();
				//@TEST print out in logcat to see if IP is saved correctly
				//Log.d("serverIPAddress",serverIPAddress);
				// show settings saved message if all fields are valid
				if (checkFieldValidities()) {
					settingsSavedMsg();
				}
			}
		});
	}
	
	
	/**
	* this method will fill in any text fields or select any options
	* on the settings page based on the currently saved settings
	*/
	public void populateFields() {
			ipAddressField.setText(serverIPAddress);
	}

	public boolean checkFieldValidities() {
		// create pattern
		pattern = Pattern.compile(IPADDRESS_PATTERN);
		// check each field
		if (!validateIPAddress(serverIPAddress)) {
			//give error msg
			invalidFieldMsg("Server IP Address");
			return false;
		}
		// if code got to here return true
		return true;
	}

	/**
	 * Validate ip address with regular expression
	 * @param ip
	 *            ip address for validation
	 * @return true valid ip address, false invalid ip address
	 */
	public boolean validateIPAddress(final String ip) {
		matcher = pattern.matcher(ip);
		return matcher.matches();
	}

	public void settingsSavedMsg() {
		new AlertDialog.Builder(this).setTitle("Settings have been saved.")
		// .setMessage("")
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// do nothing, returns to the main menu
					}
				})
				/*
				 * .setPositiveButton("Yes", new
				 * DialogInterface.OnClickListener() { public void
				 * onClick(DialogInterface dialog, int which) { // continue with
				 * delete } }) .setNegativeButton("No", new
				 * DialogInterface.OnClickListener() { public void
				 * onClick(DialogInterface dialog, int which) { // do nothing }
				 * })
				 */
				.show();
	}
	
	/**
	 * Validate ip address with regular expression
	 * @param fieldName
	 *            name of the field which contains the invalid entry
	 */
	public void invalidFieldMsg(String fieldName) {
		new AlertDialog.Builder(this).setTitle(fieldName + " contains an invalid value, please fix it.")
		// .setMessage("")
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// do nothing, returns to the main menu
					}
				})
				/*
				 * .setPositiveButton("Yes", new
				 * DialogInterface.OnClickListener() { public void
				 * onClick(DialogInterface dialog, int which) { // continue with
				 * delete } }) .setNegativeButton("No", new
				 * DialogInterface.OnClickListener() { public void
				 * onClick(DialogInterface dialog, int which) { // do nothing }
				 * })
				 */
				.show();
	}

}