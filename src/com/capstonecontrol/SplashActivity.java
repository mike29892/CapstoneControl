package com.capstonecontrol;

import java.util.ArrayList;
import java.util.List;

import com.capstonecontrol.client.ModulesRequestFactory;
import com.capstonecontrol.client.ModulesRequestFactory.ModuleFetchRequest;
import com.capstonecontrol.shared.ModuleInfoProxy;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Window;

import android.content.Context;

public class SplashActivity extends Activity {

	/**
	 * The current context.
	 */
	private Context mContext = this;
	private static final String TAG = "SplashActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		CapstoneControlActivity.modules = new ArrayList<ModuleInfo>();
		// set policy to ignore network on main thread
		// @TODO fix this for real, this is a temp fix
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				// or .detectAll() for all detectable
				.build());
		this.setContentView(R.layout.splash_screen);
		// call get module info
		getModuleInfo();

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
				Log.i(TAG, "Sending request to server");
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
				finish();
			}
		}.execute();

	}

}
