package com.capstonecontrol;

import java.util.ArrayList;
import java.util.List;
import com.capstonecontrol.client.ModulesRequestFactory;
import com.capstonecontrol.client.ModulesRequestFactory.PowerDataFetchService;
import com.capstonecontrol.shared.PowerDataProxy;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class PowerManagementActivity extends BarListActivity {

	private Context mContext = this;
	private static final String TAG = "LogsActivity";
	public static List<PowerData> powerDataArray = new ArrayList<PowerData>();
	public static List<ModuleEvent> moduleEventsSuggested = new ArrayList<ModuleEvent>();
	public static ArrayList<String> moduleEventsList = new ArrayList<String>();
	public static List<ScheduledModuleEvent> scheduledModuleEvents = new ArrayList<ScheduledModuleEvent>();
	private Button submitButton;
	private ListView lv;
	Spinner dateSpinner, moduleSpinner, typeSpinner;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.power_management);
		// @TODO REMOVE BELOW AND PUT AND POST STATEMENT IN ITS OWN THREAD
		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);
		// enable bar
		enableBar();
		// enable POST capabilities
		enablePOST();
		// handle spinners
		// set up submit button
		setUpSubmitButton();

	}

	private void setUpSubmitButton() {
		this.submitButton = (Button) this.findViewById(R.id.submitButton);
		this.submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// first disable the button
				submitButton.setEnabled(false);
				// clear previous request
				powerDataArray.clear();
				scheduledModuleEvents.clear();
				// now get the new info
				getPowerDataInfo(true);
				displayMessageList("Refreshing...");
			}
		});
	}

	private void displayMessageList(String message) {
		moduleEventsList.clear();
		moduleEventsList.add(message);
		// create list
		lv = getListView();
		ArrayAdapter<String> arrayAdapter =
		// new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
		// BarActivity.alertsList);
		new ArrayAdapter<String>(this, R.layout.list_text_style2,
				moduleEventsList);
		lv.setAdapter(arrayAdapter);
	}

	private void getPowerDataInfo(final boolean display) {
		new AsyncTask<Void, Void, List<PowerData>>() {
			@SuppressWarnings("unused")
			String foundModuleEvents;

			@Override
			protected List<PowerData> doInBackground(Void... arg0) {
				ModulesRequestFactory requestFactory = Util.getRequestFactory(
						mContext, ModulesRequestFactory.class);
				final PowerDataFetchService request = (PowerDataFetchService) requestFactory
						.powerDataFetchService();
				Log.i(TAG, "Sending request to server");
				request.getPowerData().fire(
						new Receiver<List<PowerDataProxy>>() {
							@Override
							public void onFailure(ServerFailure error) {
								// do nothing, no modules found
								foundModuleEvents = "There was an error!";
							}

							@Override
						 	public void onSuccess(List<PowerDataProxy> arg0) {
								foundModuleEvents = "The module events found were: ";
								for (int i = 0; i < arg0.size(); i++) {
									// create temporary module infro
									// proxy, tmi
									PowerDataProxy tmi = arg0.get(i);
									powerDataArray.add(new PowerData(tmi
											.getModuleName(), tmi.getData(),
											tmi.getUser(), tmi.getDate()));
								}
								if (powerDataArray.isEmpty())
									foundModuleEvents = "No module events were found!";
							}
						});
				return powerDataArray;
			}

			protected void onPostExecute(List<PowerData> result) {
			}

		}.execute();
	}
}
