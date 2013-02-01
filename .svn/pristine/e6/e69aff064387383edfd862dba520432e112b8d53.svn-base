package edu.macalester.cs225f11;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.macalester.edu.R;

public class Preferences extends PreferenceActivity implements OnSharedPreferenceChangeListener{ 
	public static final String KEY_LIST_PREFERENCE = "listPref";
    private ListPreference mListPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the XML preferences file
        addPreferencesFromResource(R.xml.settings);

        // Get a reference to the preference
        mListPreference = (ListPreference)getPreferenceScreen().findPreference(KEY_LIST_PREFERENCE);
    }
    

    @Override
    protected void onResume() {
        super.onResume();     
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();        
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);    
    }

    /**
     * Controls the message displayed after a default screen is selected
     */
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals(KEY_LIST_PREFERENCE)) {
        	if (sharedPreferences.getString(key, "").equals("1")) {
        		mListPreference.setSummary("My1600Grand will start on Campus Events"); 
        	} else if (sharedPreferences.getString(key, "").equals("2")) {
        		mListPreference.setSummary("My1600Grand will start on Campus Tools");
        	} else if (sharedPreferences.getString(key, "").equals("3")) {
        		mListPreference.setSummary("My1600Grand will start on Cafe Mac Menu");
        	} else {
        		mListPreference.setSummary("Current value is " + sharedPreferences.getString(key, ""));
        	}
        }
    }
}