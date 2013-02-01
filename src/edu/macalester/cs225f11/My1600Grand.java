package edu.macalester.cs225f11;

/**
 * My1600Grand is the first activity called when the app starts up
 */



import org.apache.http.impl.client.DefaultHttpClient;
import org.macalester.edu.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;


public class My1600Grand extends GrandMenu {
	
	private static HttpHelper helper;
	private final static String CAFEMACSITE = "http://www.cafebonappetit.com/rss/menu/159";
	private final static String DIRECTORY = "http://webapps.macalester.edu/directory/portal-search.cfm";
	private final static String CAMPUSEVENTS = "http://events.macalester.edu/index.cfm?cal=Campus+Events";
	// These Strings are sites for the SPO and login, to use when we incorporate these functions again.
	private final static String SPOSITE = "https://ssb-linux.banner.macalester.edu:9006/pls/prod/zwskopob.P_DisplayPobox";
	private final static String LOGIN = "https://1600grand4.macalester.edu/cp/home/login";
	
    /** 
     * Called when the activity is first created. 
     **/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (isOnline()){
            // Create objects
            helper = new HttpHelper(new DefaultHttpClient());
            
            // START default action
        	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        	String screen = prefs.getString("listPref", "1");
            startDefaultScreen(screen); 
        }
        

    }
    
    /**
     * This method checks to see if user is connected
     * @return
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }
    
    /**
     * Controls which screen to start
     * @param screen the number of the screen to start
     * @return
     */
    public boolean startDefaultScreen(String screen) {
    	Intent intent;

	    if (screen.equals("1") == true) {
			intent = new Intent(this, CampusEvents.class);
	    	intent.putExtra("CAMPUSEVENTS", CAMPUSEVENTS);
	    	startActivity(intent);
			return true;
	    }
		else if (screen.equals("2") == true) {
			intent = new Intent(this, CampusTools.class);
	    	intent.putExtra("DIRECTORY", DIRECTORY);
	    	startActivity(intent);
			return true;
		}
		else if (screen.equals("3") == true) {
			intent = new Intent(this, CafeMacMenu.class);
	    	intent.putExtra("CAFEMACSITE", CAFEMACSITE);
	    	startActivity(intent);
			return true;
	    }
		else if (screen.equals("4") == true) {
			intent = new Intent(getBaseContext(), Preferences.class);
			startActivity(intent);
			return true;
		}
		else {
			intent = new Intent(this, CampusEvents.class);
	    	intent.putExtra("CAMPUSEVENTS", CAMPUSEVENTS);
	    	startActivity(intent);
			return true;
		}
	}
    
}