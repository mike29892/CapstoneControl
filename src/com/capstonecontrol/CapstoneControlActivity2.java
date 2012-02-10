package com.capstonecontrol;

import java.util.ArrayList;
import java.util.List;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CapstoneControlActivity2 extends BarActivity {

	public static String serverIPAddress;
	private Button loginButton;
	public List<String> googleAccounts;
	public String googleAccount;
	RadioGroup radioGroup;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.login);
		// initialize objects
		this.loginButton = (Button) this.findViewById(R.id.loginButton);
		// start listeners
		enableLoginButtonListener();
		// get synced google accounts
		getGoogleAccounts();
		// create radio buttons
		makeRadioButtons();
		// display welcome message
		displayLoginMessage();

	}

	private void displayLoginMessage() {
		TextView loginMessage = (TextView) this.findViewById(R.id.loginMessage);
		if (googleAccounts.isEmpty()) {
			loginMessage
					.setText("No google account was detected.  Please add a google account to your device.");
		} else {
			loginMessage
					.setText("Please choose one of the following google accounts to use.");
		}

	}

	public void getGoogleAccounts() {
		googleAccounts = new ArrayList<String>();
		Account[] accounts = AccountManager.get(this).getAccounts();
		for (Account account : accounts) {
			if (account.type.equals("com.google")) {
				googleAccounts.add(account.name);
			}
		}
	}

	public void enableLoginButtonListener() {
		// create listeners for the buttons
		// login button
		this.loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// change layout to main menu layout
				// only if an account is selected
				if (googleAccount != null) {
					Intent myIntent = new Intent(view.getContext(),
							MainMenuActivity.class);
					startActivity(myIntent);
				} else {
					noAccountSelectedMsg();
				}
			}
		});
	}

	public void makeRadioButtons() {
		for (int i = 0; i < googleAccounts.size(); i++) {
			RadioButton rb = new RadioButton(this);
			rb.setText((String) googleAccounts.get(i));
			radioGroup = (RadioGroup) this.findViewById(R.id.radiobuttons);
			// select first radio button, so at least one
			radioGroup.addView(rb, 0);
		}
		// create listener
		if (radioGroup != null) {
			radioGroup
					.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
						public void onCheckedChanged(RadioGroup arg0, int id) {
							//googleAccount = googleAccounts.get(1);
							//Log.d("id value",Integer.toString(id));
							RadioButton tempRb = (RadioButton) radioGroup.findViewById(id);
							googleAccount = (String) tempRb.getText();
							//Log.d("username", googleAccount);
						}
					});
		}
	}

	public void noAccountSelectedMsg() {
		new AlertDialog.Builder(this)
				.setTitle("Please select a google account to use.")
				// .setMessage("")
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// do nothing, returns to the main menu
					}
				}).show();
	}
}