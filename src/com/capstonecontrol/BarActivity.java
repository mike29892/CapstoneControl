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
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
// This activity handles the common bar at the top of the screen that all other activities share
// it will also be used to handle sending POST to the server
public class BarActivity extends Activity {

	// declare variables
	private Button alertsButton;
	private Button barButton;
	public static ArrayList<String> alertsList = new ArrayList<String>();
	DefaultHttpClient httpClient;
	HttpPost httpPost;
	String progressString;

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
		if (alertsList.isEmpty()) {
			// set 0
			alertsButton.setText("0");
		} else {
			// set to the number of alerts
			String alertSize = Integer.toString(alertsList.size());
			alertsButton.setText(alertSize);
		}
	}
	
	public void enablePOST(){
		//create POST variables
				//client = new DefaultHttpClient();
				httpPost = new HttpPost("http://23.21.229.136/message.php");
				
				HttpParams httpParameters = new BasicHttpParams();
				// Set the timeout in milliseconds until a connection is established.
				int timeoutConnection = 3000;
				HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
				// Set the default socket timeout (SO_TIMEOUT) 
				// in milliseconds which is the timeout for waiting for data.
				int timeoutSocket = 3000;
				HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

				httpClient = new DefaultHttpClient(httpParameters);			
	}
	
	public void sendPOST(String channel, String value){
		//NOW POST
		
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("where",channel));
		pairs.add(new BasicNameValuePair("message",value));
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

}