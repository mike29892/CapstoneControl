package com.capstonecontrol;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AlertsActivity extends ListActivity {
	private Button clearAlertsButton;
	private ListView lv;
	private Button alertCountButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.alerts);
		updateAlertsList();
		// enable Clear Alerts Button
		enableClearAlertsButtonListener();
		// set counter equal to the alert count
		alertCountButton = (Button) this.findViewById(R.id.alertsButton);
		alertCountButton
				.setText(Integer.toString(BarActivity.alertsList.size()));
	}

	public void updateAlertsList() {
		// create list
		lv = getListView();
		// This is the array adapter, it takes the context of the activity as a
		// first
		// parameter, the type of list view as a second parameter and your array
		// as
		// a third parameter
		ArrayAdapter<String> arrayAdapter =
		// new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
		// BarActivity.alertsList);
		new ArrayAdapter<String>(this, R.layout.list_text_style,
				BarActivity.alertsList);
		lv.setAdapter(arrayAdapter);
	}

	public void enableClearAlertsButtonListener() {
		// clearAlerts button
		this.clearAlertsButton = (Button) this
				.findViewById(R.id.clearAlertsButton);
		this.clearAlertsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// featureNotEnabledMsg();
				confirmDialog();
			}
		});

	}

	public void confirmDialog() {
		new AlertDialog.Builder(this)
				.setTitle("Clear Alerts?")
				// .setMessage("")
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// do nothing, returns to the main menu
					}
				})

				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// continue with delete
								// clear alerts array
								BarActivity.clearAlertsList();
								// now update the display list
								updateAlertsList();
								// finally set the alert count to 0
								alertCountButton.setText("0");
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// do nothing
					}
				})

				.show();
	}

}