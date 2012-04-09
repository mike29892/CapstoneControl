package com.capstonecontrol;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
// This activity handles the common bar at the top of the screen that all other activities share
// it will also be used to handle sending POST to the server

public class BarListActivity extends ListActivity {

	// declare variables
	private Button alertsButton;
	private Button barButton;
	public static ArrayList<String> alertsList = new ArrayList<String>();
	DefaultHttpClient httpClient;
	HttpPost httpPost, httpPostScheduledEvent, httpPostLog;
	String progressString;
	@SuppressWarnings("unused")
	private Context mContext = this;

	// must call this method on the oncreate of all the other activities
	public void enableBar() {
		// enable listener for alerts button
		// alerts button
		this.alertsButton = (Button) this.findViewById(R.id.alertsButton);
		updateAlertCount();
		this.alertsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// featureNotEnabledMsg();
				// change layout to alerts layout
				// Intent myIntent = new Intent(v.getContext(),
				// AlertsActivity.class);
				// startActivityForResult(myIntent, 0);
				Intent myIntent = new Intent(view.getContext(),
						AlertsActivity.class);
				// startActivityForResult(myIntent, 0);
				startActivity(myIntent);
			}
		});
		// FOR TEST
		// clicking bar will make a fake alert
		this.barButton = (Button) this.findViewById(R.id.mainBar);
		updateAlertCount();
		this.barButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// FOR TEST - create fake alert!!!
				addAlert("Fake Test Alert");
			}
		});
		// disable button if alerts is disabled
		if (CapstoneControlActivity.testItemsDisabled) {
			barButton.setEnabled(false);
		}
		// set up for thread that will search for alerts
		final Button alertsButton = (Button) this
				.findViewById(R.id.alertsButton);
		final Handler handler = new Handler();
		final Runnable updateRunner = new Runnable() {
			public void run() {
				// we are now in the event handling so to speak, update the gui
				if (alertsList.isEmpty()) {
					// set 0
					alertsButton.setText("0");
				} else {
					// set to the number of alerts
					String alertSize = Integer.toString(alertsList.size());
					alertsButton.setText(alertSize);
				}
			}
		};
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					// sleep to slow down, and keep processes not as heavy
					// @TODO find out if my intuition about above comment
					// is infact true
					SystemClock.sleep(2000);
					handler.post(updateRunner);
				}
			}
		}).start();

	}

	public void addAlert(String alert) {
		// first add the alert to the alertsList
		alertsList.add(alert);
		// now call alertFound()
		// alertFound();
	}

	public static void clearAlertsList() {
		// method clears alertsList
		alertsList.clear();
	}

	public void updateAlertCount() {
		if (BarActivity.alertsList.isEmpty()) {
			// set 0
			alertsButton.setText("0");
		} else {
			// set to the number of alerts
			String alertSize = Integer.toString(BarActivity.alertsList.size());
			alertsButton.setText(alertSize);
		}
	}

	public void enablePOST() {
		// create POST variables
		// client = new DefaultHttpClient();
		httpPost = new HttpPost("http://23.21.229.136/message.php");
		httpPostLog = new HttpPost(
				"http://capstonecontrol.appspot.com/LogModuleEventServlet");
		httpPostScheduledEvent = new HttpPost(
				"http://capstonecontrol.appspot.com/ScheduleEvent");
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = 3000;
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 3000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		httpClient = new DefaultHttpClient(httpParameters);
	}

	public void sendPOST(String channel, String value) {
		// NOW POST
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("where", channel));
		pairs.add(new BasicNameValuePair("message", value));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(pairs));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			HttpResponse response = httpClient.execute(httpPost);
			Log.i("HttpResponse", response.getEntity().toString());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendPOSTScheduledEvent(ScheduledModuleEvent schedEvent) {
		// NOW POST
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		if (CapstoneControlActivity.googleUserName.length() > 10) {
			if (CapstoneControlActivity.googleUserName.contains("@gmail.com")) {
				pairs.add(new BasicNameValuePair("user",
						CapstoneControlActivity.googleUserName
								.substring(0,
										CapstoneControlActivity.googleUserName
												.length() - 10)));
			} else {
				pairs.add(new BasicNameValuePair("user",
						CapstoneControlActivity.googleUserName));
			}
		}
		pairs.add(new BasicNameValuePair("moduleName", schedEvent
				.getModuleName()));
		pairs.add(new BasicNameValuePair("moduleType", schedEvent
				.getModuleType()));
		pairs.add(new BasicNameValuePair("value", Long.toString(schedEvent
				.getValue())));
		pairs.add(new BasicNameValuePair("action", schedEvent.getAction()));
		pairs.add(new BasicNameValuePair("schedDate", Long.toString(schedEvent
				.getSchedDate().getTime())));
		pairs.add(new BasicNameValuePair("active", Boolean.toString(schedEvent
				.getActive())));
		pairs.add(new BasicNameValuePair("Sun", Boolean.toString(schedEvent
				.getSun())));
		pairs.add(new BasicNameValuePair("Mon", Boolean.toString(schedEvent
				.getMon())));
		pairs.add(new BasicNameValuePair("Tue", Boolean.toString(schedEvent
				.getTue())));
		pairs.add(new BasicNameValuePair("Wed", Boolean.toString(schedEvent
				.getWed())));
		pairs.add(new BasicNameValuePair("Thu", Boolean.toString(schedEvent
				.getThu())));
		pairs.add(new BasicNameValuePair("Fri", Boolean.toString(schedEvent
				.getFri())));
		pairs.add(new BasicNameValuePair("Sat", Boolean.toString(schedEvent
				.getSat())));
		pairs.add(new BasicNameValuePair("offset", Long.toString(schedEvent
				.getTimeOffset())));
		pairs.add(new BasicNameValuePair("recur", Boolean.toString(schedEvent
				.getRecur())));
		try {
			httpPostScheduledEvent.setEntity(new UrlEncodedFormEntity(pairs));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			HttpResponse response = httpClient.execute(httpPostScheduledEvent);
			Log.i("HttpResponse", response.getEntity().toString());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void logModuleEvent(ModuleInfo module, String action, String value) {
		// log the event in datastore, do it via post
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("user", module.getUser()));
		pairs.add(new BasicNameValuePair("moduleName", module.getModuleName()));
		pairs.add(new BasicNameValuePair("moduleType", module.getModuleType()));
		pairs.add(new BasicNameValuePair("action", action));
		pairs.add(new BasicNameValuePair("message", value));
		try {
			httpPostLog.setEntity(new UrlEncodedFormEntity(pairs));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			HttpResponse response = httpClient.execute(httpPostLog);
			Log.i("HttpResponse", response.getEntity().toString());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}