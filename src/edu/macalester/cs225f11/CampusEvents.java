package edu.macalester.cs225f11;

import org.apache.http.impl.client.DefaultHttpClient;
import org.macalester.edu.R;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CampusEvents extends GrandMenu {
	
	private static HttpHelper helper;
	private static String campusEvents;
	public List<CampusEventsItem>events;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.campusevents);
		currentActivity = "campusevents";
		helper = new HttpHelper(new DefaultHttpClient());
		campusEvents = getIntent().getStringExtra("CAMPUSEVENTS");
		getDailyCalendar();
        TableLayout campusEventsTable = (TableLayout) findViewById(R.id.campus_events_table);
        createEvents(campusEventsTable);
	}
	
	/**
	 * This method lays out the campusEventsItems of the day into a table
	 * @param table The table in which the campus events would be layed out
	 */
	public void createEvents(TableLayout table){
		for (int i = 0; i < events.size(); i++){
        	TableRow event = new TableRow(this);
        	View campusEventsItem = getLayoutInflater().inflate(R.layout.campus_event_item, null);

        	final ImageView leftWall = (ImageView) campusEventsItem.findViewById(R.id.campus_events_item_middle_left);
        	final ImageView rightWall = (ImageView) campusEventsItem.findViewById(R.id.campus_events_item_middle_right);
        	final TextView tv = (TextView) campusEventsItem.findViewById(R.id.campus_events_item_text);
        	tv.setText(events.get(i).getTitle()+"\n\nWhere: " + events.get(i).getLocation() +"\nTime: "+events.get(i).getTime());
        	tv.setWidth(getWindowManager().getDefaultDisplay().getWidth()-26);
        	
            ViewTreeObserver vto = tv.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    leftWall.setMinimumHeight(tv.getHeight());
                    rightWall.setMinimumHeight(tv.getHeight());
                    ViewTreeObserver obs = tv.getViewTreeObserver();
                    obs.removeGlobalOnLayoutListener(this);
                }
            });
        	
            // Add the new view to the campus events table
        	event.addView(campusEventsItem);
    		table.addView(event);
    		

        }
	}
	/**
	 * Gets the calendar from macalester.edu, and stores the data in an ArrayList
	 */
	public void getDailyCalendar(){
		events=new ArrayList<CampusEventsItem>();
    	campusEvents = "http://events.macalester.edu/index.cfm?cal=Campus+Events";
		String result=Html.fromHtml(helper.getRequest(campusEvents)).toString();
		parseCalendar(result);
	}
	
	/**
	 * Creates the campusEventItems from the string pulled from the site
	 * @param result the string pulled from the site
	 */
	public void parseCalendar(String result){
		int[] index=new int[7];
		int indexMin=result.length();
    	int day = -1;		
    	String[] daysWeek={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
		for(int i=0;i<7;i++){
			index[i]=result.indexOf(daysWeek[i]);
			if (index[i]< indexMin){
				day=i;
				indexMin=index[i];
			}
		}
		if (day==6){
			result=result.substring(index[day], index[0]);
		} else {
			result=result.substring(index[day], index[day+1]);
		}
		String[] delims=result.split("\t");
		for(int i=0; i<delims.length;i++){
			if (delims[i].equals("")){
			}else if (delims[i].contains("All Day")){
				String time=delims[i];
				String body=delims[i+8];
				CampusEventsItem eventItem=new CampusEventsItem(time,body);
				events.add(eventItem);
				i=i+8;
			}else if (delims[i].indexOf("-")==-1){
			}else if(delims[i-5].length()==0){
			}else if(!isNumeric(delims[i-5].substring(0, 1))){
			}else{
				String time=delims[i-5]+delims[i];
				String body=delims[i+4];
				CampusEventsItem eventItem=new CampusEventsItem(time,body);
				events.add(eventItem);
				i++;
			}
		}
	}
	
	
	/**
	 * Checks to see if a string passed in is made up of numbers
	 */
	public boolean isNumeric(String s) {  
	    return java.util.regex.Pattern.matches("\\d+", s); 
	}

	public List<CampusEventsItem> parseCalendarTester(String result,
			int[] index, int day) {
		/** 
		 * Method made to test parseCalendar, adding in an input for day so that I can look at individual days in the result.
		 */
		List<CampusEventsItem> calendar;
		calendar = new ArrayList<CampusEventsItem>();
		int indexMin=result.length();
    	int thisDay = -1;		
    	String[] daysWeek={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
		for(int i=0;i<7;i++){
			index[i]=result.indexOf(daysWeek[i]);
		}
		thisDay = day; //for testing purposes
		if (day==6){
			result=result.substring(index[day], index[0]);
		} else {
			result=result.substring(index[day], index[day+1]);
			System.out.println("Result = " + result);
		}
		String[] delims=result.split("\t");
		for(int i=0; i<delims.length;i++){
			if (delims[i].equals("")){
			}
			else if (delims[i].indexOf("-")==-1){
			}else if(delims[i-5].length()==0){
			}else if(!isNumeric(delims[i-5].substring(0, 1))){
			}else{
				String time=delims[i-5]+delims[i];
				String body=delims[i+4];
				CampusEventsItem eventItem=new CampusEventsItem(time,body);
				calendar.add(eventItem);
				i++;
			}
		}
		return calendar;
	}  

}