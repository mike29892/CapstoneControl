package com.capstonecontrol;


import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MainMenuActivity extends BarActivity{
	private Button lightsButton;
	private Button doorButton;
	private Button securityButton;
	private Button appliancesButton;
	private Button settingsButton;
	public static ArrayList<String> alertsList = new ArrayList<String>(); 
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
    	requestWindowFeature(Window.FEATURE_NO_TITLE); 
    	super.onCreate(savedInstanceState);
    	this.setContentView(R.layout.main);
    	//enable the main button listeners
    	enableMainButtonListeners();
    	//enable bar
    	enableBar();
    }
    

    public void enableMainButtonListeners(){
    	//create listeners for the buttons
    	//lights button
    	  this.lightsButton = (Button)this.findViewById(R.id.lightsButton);
    	  this.lightsButton.setOnClickListener(new OnClickListener() {
    	    @Override
    	    public void onClick(View view) {
    	    	//featureNotEnabledMsg();
    	    	//change layout to lights layout
    	    	//setContentView(R.layout.lights);
    	    	Intent myIntent = new Intent(view.getContext(), LightsActivity.class);
    	    	startActivity(myIntent);
    	    }
    	  });
    	  //doors button
    	  this.doorButton = (Button)this.findViewById(R.id.doorButton);
    	  this.doorButton.setOnClickListener(new OnClickListener() {
    	    @Override
    	    public void onClick(View view) {
    	    	//featureNotEnabledMsg();
    	    	Intent myIntent = new Intent(view.getContext(), DoorActivity.class);
    	    	startActivity(myIntent);
    	    }
    	  });
    	  //security button
      	  this.securityButton = (Button)this.findViewById(R.id.securityButton);
      	  this.securityButton.setOnClickListener(new OnClickListener() {
      	    @Override
      	    public void onClick(View view) {
      	    	featureNotEnabledMsg();
      	    }
      	  });
      	//appliances button
      	  this.appliancesButton = (Button)this.findViewById(R.id.appliancesButton);
      	  this.appliancesButton.setOnClickListener(new OnClickListener() {
      	    @Override
      	    public void onClick(View view) {
      	    	featureNotEnabledMsg();
      	    }
    	  });
      	//settings button
      	  this.settingsButton = (Button)this.findViewById(R.id.settingsButton);
      	  this.settingsButton.setOnClickListener(new OnClickListener() {
      	    @Override
      	    public void onClick(View view) {
      	    	//featureNotEnabledMsg();
      	    	Intent myIntent = new Intent(view.getContext(), SettingsActivity.class);
    	    	startActivity(myIntent);
      	    }
    	  });
    }
    
    public void featureNotEnabledMsg(){
    	new AlertDialog.Builder(this)
        .setTitle("Function not yet implemented.")
        //.setMessage("")
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
                // do nothing, returns to the main menu
            }
         })
        /*
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
                // continue with delete
            }
         })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
                // do nothing
            }        
         })
         */
         .show();
    }
}