package com.capstonecontrol;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class DoorActivity extends BarActivity {

    private MjpegView mv;
    private static final int MENU_QUIT = 1;
    public View doorVideo;

    /* Creates the menu items */
    public boolean onCreateOptionsMenu(Menu menu) {    
    menu.add(0, MENU_QUIT, 0, "Quit");
    return true;
    }

    /* Handles item selections */
    public boolean onOptionsItemSelected(MenuItem item) {    
        switch (item.getItemId()) {
            case MENU_QUIT:
                finish();
                return true;    
            }    
        return false;
    }

    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE); 
    	super.onCreate(savedInstanceState);
    	this.setContentView(R.layout.door);
    	//enable bar
    	enableBar();
        //sample public cam    
    	doorVideo = (View) findViewById(R.id.doorVideo);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        mv = new MjpegView(this);
         
        new Thread(new Runnable() {
            public void run() {
            	String URL = "http://192.168.0.125:8080/?action=stream";
                mv.setSource(MjpegInputStream.read(URL));
                mv.setDisplayMode(MjpegView.SIZE_BEST_FIT);
                mv.showFps(false);
                doorVideo = mv;
            }
          }).start();

    }

    public void onPause() {
        super.onPause();
        mv.stopPlayback();
    }
}