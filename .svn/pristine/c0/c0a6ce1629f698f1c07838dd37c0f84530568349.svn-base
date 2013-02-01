package edu.macalester.cs225f11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.macalester.edu.R;

import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.text.Html;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class CampusTools extends GrandMenu {
	
	private static HttpHelper helper;
	private static String directory;
	private List<SearchItem> searchResults;
	private Map<String, SearchItem> searchItems;
	private Map<String, ArrayList<String>> matchStrings;
	private final String NO_RESULTS = "No matches. Please try searching for a different name.";
	private TextView searchResult;
	private TextView deptHeader;
	private TextView groupHeader;
	private TextView facultyHeader;
	private TextView studentsHeader;
	private int selected = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.directory);
		helper = new HttpHelper(new DefaultHttpClient());
		currentActivity = "directory";
		directory = getIntent().getStringExtra("DIRECTORY");
		final EditText txtUrl = (EditText)findViewById(R.id.search_query);
		final ImageButton btnFetch = (ImageButton)findViewById(R.id.make_search);
		deptHeader = (TextView) findViewById(R.id.dept_header);
		groupHeader = (TextView) findViewById(R.id.group_header);
		facultyHeader = (TextView) findViewById(R.id.faculty_header);
		studentsHeader = (TextView) findViewById(R.id.students_header);
		searchResult = (TextView)findViewById(R.id.search_result_items);
		final TextView searchPrompt = (TextView)findViewById(R.id.search_prompt);
		
		btnFetch.setOnClickListener(new Button.OnClickListener(){	
		 	public void onClick(View v){
		 		searchPrompt.setVisibility(View.GONE);
		 		String searchString = txtUrl.getText().toString();
		 		if (searchString.endsWith(" ") || searchString.startsWith(" ")){
		 			trimOrNot(searchString);
		 		} else {
		 			directorySearchAndDisplay(searchString);
		 		}
		 	}
		});
	}
	
	/**
	 * makes the search, and then displays it
	 * @param searchString the search query
	 */
	private void directorySearchAndDisplay(String searchString){
		String htmlResult = makeSearch(searchString);
 		parseSearch(htmlResult);
 		deptHeader.setText(searchItems.get("in Departments/Offices:").getSearchResults());
 		deptHeader.setVisibility(View.VISIBLE);
 		groupHeader.setText(searchItems.get("in Groups/Organizations:").getSearchResults());
 		groupHeader.setVisibility(View.VISIBLE);
 		facultyHeader.setText(searchItems.get("in Faculty/Staff:").getSearchResults());
 		facultyHeader.setVisibility(View.VISIBLE);
 		studentsHeader.setText(searchItems.get("in Students:").getSearchResults());
 		studentsHeader.setVisibility(View.VISIBLE);
 		if (selected > -1){
 			switch(selected){
 			case 0:
 				searchResult.setText(searchItems.get("in Departments/Offices:").getSearchSection());
 				break;
 			case 1:
 			 	searchResult.setText(searchItems.get("in Groups/Organizations:").getSearchSection());
 				break;
 			case 2:
 			 	searchResult.setText(searchItems.get("in Faculty/Staff:").getSearchSection());
 				break;
 			case 3:
 			 	searchResult.setText(searchItems.get("in Students:").getSearchSection());
 				break;
 				default:
 			}
 		}
	}
	
	/**
	 * make a post request into the directory
	 * @param query the search term
	 * @return the html response
	 */
	public String makeSearch(String query){
		//For testing purposes, directory is directly defined here
//		directory = "http://webapps.macalester.edu/directory/portal-search.cfm"; 
    	List<NameValuePair> searchPairs = new ArrayList<NameValuePair>();
		searchPairs.add(new BasicNameValuePair("Name", query));
		searchPairs.add(new BasicNameValuePair("search.x", "25"));
		searchPairs.add(new BasicNameValuePair("search.y", "11"));
		String result=helper.postRequest(directory, searchPairs);
		result=result.substring(980, result.length());
		return result;
    }

	/**
	 * Parses the search result into searchItems
	 * @param result the html result from the search
	 */
	private void parseSearch(String result){
		matchStrings = createMatchStrings();
		String[] lines = result.split("\n");
		if (lines[3].trim().contains(NO_RESULTS)){
			return;
		}
		Set<String> matchKeys = matchStrings.keySet();
		String startFrom = "null";
		for (int i=3; i<lines.length; i++){
			String line = lines[i].trim();
			for (String key: matchKeys){
				if (line.contains(key)){
					startFrom = key;
					break;
				} 
			}
			matchStrings.get(startFrom).add(line);
		}
		searchItems = new HashMap<String, SearchItem>();
		for (String key: matchStrings.keySet()){
			String keyString = "No results "+key;
			String searchResults = "No matches found "+key;;
			List<String> itemList  = matchStrings.get(key);
			if (itemList.size() > 0){
				keyString = "";
				for (int i =0; i< itemList.size(); i++ ){
					if (i == 0){
						searchResults = itemList.get(i); 
					} else {
						keyString = keyString + itemList.get(i);
					}
				}
			}
			searchItems.put(key, new SearchItem(searchResults, keyString));
		}
	}
	
	/**
	 * setps up the matchStrings which will be used in searching
	 * @return
	 */
	private Map<String, ArrayList<String>> createMatchStrings(){
		Map<String, ArrayList<String>> matchStrings = new LinkedHashMap<String, ArrayList<String>>();
		matchStrings.put("null", new ArrayList<String>());
		matchStrings.put("in Departments/Offices:", new ArrayList<String>());
		matchStrings.put("in Groups/Organizations:", new ArrayList<String>());
		matchStrings.put("in Faculty/Staff:",new ArrayList<String>());
		matchStrings.put("in Students:",new ArrayList<String>());
		matchStrings.put("About the Directory/FAQ",new ArrayList<String>());
		return matchStrings;
	}
	
	/**
	 * Called when the dept tab-line is pressed
	 * @param v
	 */
	public void showDepartmentResults(View v){
		selected = 0;
		v.setBackgroundColor(Color.parseColor("#333377"));
		groupHeader.setBackgroundColor(Color.BLACK);
		facultyHeader.setBackgroundColor(Color.BLACK);
		studentsHeader.setBackgroundColor(Color.BLACK);
	 	searchResult.setText(searchItems.get("in Departments/Offices:").getSearchSection());
	}
	
	/**
	 * Called when the group tab-line is pressed
	 * @param v
	 */
	public void showGroupResults(View v){
		selected = 1;
		v.setBackgroundColor(Color.parseColor("#333377"));
		deptHeader.setBackgroundColor(Color.BLACK);
		facultyHeader.setBackgroundColor(Color.BLACK);
		studentsHeader.setBackgroundColor(Color.BLACK);
	 	searchResult.setText(searchItems.get("in Groups/Organizations:").getSearchSection());
	}
	
	/**
	 * Called when the faculty tab-line is pressed
	 * @param v
	 */
	public void showFacultyResults(View v){
		selected = 2;
		v.setBackgroundColor(Color.parseColor("#333377"));
		deptHeader.setBackgroundColor(Color.BLACK);
		groupHeader.setBackgroundColor(Color.BLACK);
		studentsHeader.setBackgroundColor(Color.BLACK);
	 	searchResult.setText(searchItems.get("in Faculty/Staff:").getSearchSection());
	}
	
	/**
	 * Called when the student tab-line is pressed
	 * @param v
	 */
	public void showStudentsResults(View v){
		selected = 3;
		v.setBackgroundColor(Color.parseColor("#333377"));
		deptHeader.setBackgroundColor(Color.BLACK);
		groupHeader.setBackgroundColor(Color.BLACK);
		facultyHeader.setBackgroundColor(Color.BLACK);
	 	searchResult.setText(searchItems.get("in Students:").getSearchSection());
	}
	
	/**
	 * creates a dialog that allows the user to decide whether she wants to text trimmed or not
	 * @param searchString the query of the search
	 * @param searchPrompt t
	 */
	private void trimOrNot(final String searchString) {
		 AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
		 helpBuilder.setTitle("Please Note:");
		 helpBuilder.setMessage("Your search query contains spaces in front of behind it. This might affect your results; would you like to remove these spaces? ");
		 helpBuilder.setPositiveButton("Yes",
		 new DialogInterface.OnClickListener() {
			 public void onClick(DialogInterface dialog, int which) {
				 EditText editText = (EditText) findViewById(R.id.search_query);
				 editText.setText(searchString.trim());
				 directorySearchAndDisplay(searchString.trim());
		     	}
			 });
	
			 helpBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			 @Override
			 public void onClick(DialogInterface dialog, int which) {
				 directorySearchAndDisplay(searchString);
			 }
		 });
		 AlertDialog helpDialog = helpBuilder.create();
		 helpDialog.show();

		}
}
