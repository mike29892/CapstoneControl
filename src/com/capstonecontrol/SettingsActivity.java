package com.capstonecontrol;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capstonecontrol.client.ModulesRequestFactory;
import com.capstonecontrol.client.ModulesRequestFactory.ModuleFetchRequest;
import com.capstonecontrol.shared.ModuleInfoProxy;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SettingsActivity extends BarActivity {

	public static String serverIPAddress;
	Button applyButton;
	Button refreshButton;
	TextView ipAddressField;
	private Pattern pattern;
	private Matcher matcher;

	private Context mContext = this;
	@SuppressWarnings("unused")
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
		// set modules count
		setModulesCount();
		// enable bar
		enableBar();
	}

	private void setModulesCount() {
		int buzzerCount = 0;
		int dimmerCount = 0;
		if (CapstoneControlActivity.modules != null) {
		int moduleCount = CapstoneControlActivity.modules.size();
		for (int i = 0; i < moduleCount; i++) {
			if (CapstoneControlActivity.modules.get(i).getModuleType()
					.equals("Dimmer")) {
				dimmerCount++;
			}
			if (CapstoneControlActivity.modules.get(i).getModuleType()
					.equals("Door Buzzer")) {
				buzzerCount++;
			}
		}
		// now update TextView
		TextView dimmerCountText = (TextView) this
				.findViewById(R.id.dimmerCount);
		dimmerCountText.setText("Light Dimmers: " + dimmerCount);
		TextView buzzerCountText = (TextView) this
				.findViewById(R.id.buzzerCount);
		buzzerCountText.setText("Door Buzzers : " + buzzerCount);
		}
	}

	public void createFieldInstances() {
		// ipAddressField = (TextView) this.findViewById(R.id.ipAddress);
	}

	public void enableApplyButtonListener() {
		// create listeners for the buttons
		// apply button
		this.applyButton = (Button) this.findViewById(R.id.applyButton);
		this.applyButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// update all entered fields
				//serverIPAddress = ipAddressField.getText().toString();
				// @TEST print out in logcat to see if IP is saved correctly
				// Log.d("serverIPAddress",serverIPAddress);
				// show settings saved message if all fields are valid
				if (checkFieldValidities()) {
					settingsSavedMsg();
				}
			}
		});
		
		// refresh modules button
				this.refreshButton = (Button) this.findViewById(R.id.refreshModulesButton);
				this.refreshButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						//clear module list
						CapstoneControlActivity.modules.clear();
						//now get the modules
						getModuleInfo();
						//refresh the number of modules
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						setModulesCount();
					}
				});

		// logout button
		Button accountsButton = (Button) this.findViewById(R.id.accountsButton);
		accountsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// featureNotEnabledMsg();
				Intent myIntent = new Intent(view.getContext(),
						AccountsActivity.class);
				startActivity(myIntent);
			}
		});
	}

	/**
	 * this method will fill in any text fields or select any options on the
	 * settings page based on the currently saved settings
	 */
	public void populateFields() {
		// ipAddressField.setText(serverIPAddress);
	}

	public boolean checkFieldValidities() {
		// check ip validity
		// create pattern
		/*
		 * pattern = Pattern.compile(IPADDRESS_PATTERN); // check each field if
		 * (!validateIPAddress(serverIPAddress)) { // give error msg
		 * invalidFieldMsg("Server IP Address"); return false; }
		 */
		// if code got to here return true
		return true;
	}

	/**
	 * Validate ip address with regular expression
	 * 
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
						finish();
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
	 * 
	 * @param fieldName
	 *            name of the field which contains the invalid entry
	 */
	public void invalidFieldMsg(String fieldName) {
		new AlertDialog.Builder(this)
				.setTitle(
						fieldName
								+ " contains an invalid value, please fix it.")
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
	
	
	private void getModuleInfo() {
		new AsyncTask<Void, Void, List<ModuleInfo>>() {
			@SuppressWarnings("unused")
			String foundModules;

			@Override
			protected List<ModuleInfo> doInBackground(Void... arg0) {
				ModulesRequestFactory requestFactory = Util.getRequestFactory(
						mContext, ModulesRequestFactory.class);
				final ModuleFetchRequest request = requestFactory
						.moduleFetchRequest();
				Log.i("SettingsActivity", "Sending request to server");
				request.getModules().fire(
						new Receiver<List<ModuleInfoProxy>>() {
							@Override
							public void onFailure(ServerFailure error) {
								// do nothing, no modules found
								foundModules = "There was an error!";
							}

							@Override
							public void onSuccess(List<ModuleInfoProxy> arg0) {
								foundModules = "The modules found were: ";
								for (int i = 0; i < arg0.size(); i++) {
									// create temporary module infro
									// proxy, tmi
									ModuleInfoProxy tmi = arg0.get(i);
									CapstoneControlActivity.modules
											.add(new ModuleInfo(tmi
													.getModuleMacAddr(), tmi
													.getModuleName(), tmi
													.getModuleType(), tmi
													.getUser()));
								}
								if (CapstoneControlActivity.modules.isEmpty())
									foundModules = "No modules were found!";
							}
						});
				return CapstoneControlActivity.modules;
			}

			@Override
			protected void onPostExecute(List<ModuleInfo> result) {
				// save into global module list in this class

				// print found modules
				if (result != null) {
					for (int i = 0; i < result.size(); i++) {
						foundModules += result.get(i).getModuleName();
						if (i != (result.size() - 1)) {
							foundModules += ", ";
						}
					}
				}

				foundModules += ".";
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.execute();

	}

}