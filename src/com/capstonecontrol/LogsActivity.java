package com.capstonecontrol;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.capstonecontrol.client.ModulesRequestFactory;
import com.capstonecontrol.client.ModulesRequestFactory.ModuleEventFetchRequest;
import com.capstonecontrol.client.ModulesRequestFactory.ModuleFetchRequest;
import com.capstonecontrol.shared.ModuleEventProxy;
import com.capstonecontrol.shared.ModuleInfoProxy;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class LogsActivity extends BarListActivity {

	private Context mContext = this;
	private static final String TAG = "LogsActivity";
	public static List<ModuleEvent> moduleEvents = new ArrayList<ModuleEvent>();
	public static List<ModuleInfo> modules = new ArrayList<ModuleInfo>();
	public static ArrayList<String> moduleEventsList = new ArrayList<String>();
	private Button submitButton;
	private ListView lv;
	Spinner dateSpinner;
	Spinner moduleSpinner;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.logs);
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
		this.submitButton = (Button) this.findViewById(R.id.submitButton);
		this.submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// first disable the button
				submitButton.setEnabled(false);
				// clear previous request
				moduleEvents.clear();
				// now get the new info
				displayRefreshingList();
				getLogInfo();
			}
		});

	}

	private void setUpSpinners() {
		dateSpinner = (Spinner) findViewById(R.id.dateRangeSpinner);
		ArrayAdapter<CharSequence> dateAdapter = ArrayAdapter
				.createFromResource(this, R.array.dateRange_array,
						android.R.layout.simple_spinner_item);
		dateAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dateSpinner.setAdapter(dateAdapter);
		// now do the other spinner
		moduleSpinner = (Spinner) findViewById(R.id.moduleTypeSpinner);
		ArrayAdapter<CharSequence> moduleAdapter = ArrayAdapter
				.createFromResource(this, R.array.moduleType_array,
						android.R.layout.simple_spinner_item);
		moduleAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		moduleSpinner.setAdapter(moduleAdapter);
	}

	private void displayRefreshingList() {
		moduleEventsList.clear();
		moduleEventsList.add("Refreshing...");
		// create list
		lv = getListView();
		ArrayAdapter<String> arrayAdapter =
		// new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
		// BarActivity.alertsList);
		new ArrayAdapter<String>(this, R.layout.list_text_style2,
				moduleEventsList);
		lv.setAdapter(arrayAdapter);
	}

	private void updateEventListView() {
		// done search for re-enable the submit button
		submitButton.setEnabled(true);
		String tempString;
		// clear old list
		moduleEventsList.clear();
		// This is the array adapter, it takes the context of the activity as a
		// first
		// parameter, the type of list view as a second parameter and your array
		// as
		// a third parameter
		// create list of strings based on event info
		for (int i = 0; i < moduleEvents.size(); i++) {
			tempString = moduleEvents.get(i).getDate().toGMTString();
			tempString += "      " + moduleEvents.get(i).getModuleName();
			tempString += "      " + moduleEvents.get(i).getModuleType();
			tempString += "      " + moduleEvents.get(i).getValue();
			boolean displayEvent = checkToAddEvent(moduleEvents.get(i)
					.getDate(), moduleEvents.get(i).getModuleType());
			if (displayEvent) {
				moduleEventsList.add(tempString);
			}
		}
		ArrayAdapter<String> arrayAdapter2 =
		// new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
		// BarActivity.alertsList);
		new ArrayAdapter<String>(this, R.layout.list_text_style2,
				moduleEventsList);
		lv.setAdapter(arrayAdapter2);
	}

	private boolean checkToAddEvent(Date date, String moduleType) {
		String moduleSpinnerValue = moduleSpinner.getSelectedItem().toString();
		String dateSpinnerValue = dateSpinner.getSelectedItem().toString();
		// start with getting the date now
		Date currentDate = new Date();
		// subtract from current date based on selection
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		if (dateSpinnerValue.equals("Past Day")) {
			cal.add(Calendar.DATE, -1);
		}
		if (dateSpinnerValue.equals("Past Week")) {
			cal.add(Calendar.DATE, -7);
		}
		if (dateSpinnerValue.equals("Past 30 Days")) {
			cal.add(Calendar.DATE, -30);
		}
		if (dateSpinnerValue.equals("Past 60 Days")) {
			cal.add(Calendar.DATE, -60);
		}
		if (dateSpinnerValue.equals("Past 90 Days")) {
			cal.add(Calendar.DATE, -90);
		}
		if (!cal.getTime().before(date)) {
			return false;
		}
		// see if the type is matched
		if (!moduleType.equals(moduleSpinnerValue)
				&& !moduleSpinnerValue.equals("All")) {
			return false;
		}

		return true;
	}

	private void getLogInfo() {
		new AsyncTask<Void, Void, List<ModuleEvent>>() {
			String foundModuleEvents;

			@Override
			protected List<ModuleEvent> doInBackground(Void... arg0) {
				ModulesRequestFactory requestFactory = Util.getRequestFactory(
						mContext, ModulesRequestFactory.class);
				final ModuleEventFetchRequest request = (ModuleEventFetchRequest) requestFactory
						.moduleEventFetchRequest();
				Log.i(TAG, "Sending request to server");
				request.getModules().fire(
						new Receiver<List<ModuleEventProxy>>() {
							@Override
							public void onFailure(ServerFailure error) {
								// do nothing, no modules found
								foundModuleEvents = "There was an error!";
							}

							@Override
							public void onSuccess(List<ModuleEventProxy> arg0) {
								foundModuleEvents = "The module events found were: ";
								for (int i = 0; i < arg0.size(); i++) {
									// create temporary module infro
									// proxy, tmi
									ModuleEventProxy tmi = arg0.get(i);
									moduleEvents.add(new ModuleEvent(tmi
											.getModuleName(), tmi
											.getModuleType(), tmi.getUser(),
											tmi.getAction(), tmi.getDate(), tmi
													.getValue()));
								}
								if (moduleEvents.isEmpty())
									foundModuleEvents = "No module events were found!";
							}
						});
				return moduleEvents;
			}

			protected void onPostExecute(List<ModuleEvent> result) {
				// if events found that match update the shown list
				if (!moduleEvents.isEmpty()) {
					updateEventListView();
				}

			}
		}.execute();

	}

}
