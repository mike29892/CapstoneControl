package com.capstonecontrol;

import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DoorActivity extends BarActivity {

    @Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.door);
		// @TODO REMOVE BELOW AND PUT AND POST STATEMENT IN ITS OWN THREAD
		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);
		// enable bar
		enableBar();
		// enable POST capabilities
		enablePOST();
		// dynamically create labels and seekbars
		createDoorButtons();
	}

	private void createDoorButtons() {
		final TextView mqttChannnel = (TextView) this.findViewById(R.id.mqttChannel);
		final TextView mqttValue = (TextView) this.findViewById(R.id.mqttValue);
		LinearLayout linearlayout = (LinearLayout) this
				.findViewById(R.id.linearLayoutDoor);
		linearlayout.setOrientation(LinearLayout.VERTICAL);
		// now based on modules synced with account add labels and settings
		if (CapstoneControlActivity.modules != null) {
			for (int i = 0; i < CapstoneControlActivity.modules.size(); i++) {
				if (CapstoneControlActivity.modules.get(i).getModuleType()
						.equals("Door Buzzer")) {
					final int index = i;
					// add label
					TextView tv = new TextView(this);
					tv.setText(CapstoneControlActivity.modules.get(i)
							.getModuleName());
					linearlayout.addView(tv);
					// add open button
					Button button = new Button(this);
					button.setText("Open");
					linearlayout.addView(button);
					//add listener		
					button.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {
							 String mqttPath = "/" + CapstoneControlActivity.googleUserName + "/" + CapstoneControlActivity.modules.get(index).getModuleName();
							 sendPOST(mqttPath,progressString);
							 mqttChannnel.setText("MQTT Channel: " + mqttPath);
							 mqttValue.setText("MQTT Value: " + "1");
						}
					});
				}
			}
		}

	}
}