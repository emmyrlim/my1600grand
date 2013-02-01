package edu.macalester.cs225f11;

import org.apache.http.impl.client.DefaultHttpClient;
import org.macalester.edu.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CafeMacMenu extends GrandMenu {
	private static HttpHelper helper;
	private static String cafemac;
//	private static String[] body=new String[7];
	private CafeMacItem[] menuList; 
	private static final int NUM_DAYS = 7;
//	private String[] daysWeek={"Mon,","Tue,","Wed,","Thu,","Fri,","Sat,","Sun,"};

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cafemac);
		currentActivity = "cafemac";
		helper = new HttpHelper(new DefaultHttpClient());
		cafemac = getIntent().getStringExtra("CAFEMACSITE");
		int today = getToday();
		String result = getMenu();
		System.out.println("//////////////////before parsemenu//////////////////////");
		if (result != "error"){
			menuList = parseMenu(getMenu());
		} else {
			menuList = new CafeMacItem[1];
			menuList[0] = new CafeMacItem("error", "error");
		}
		final HorizontalPager pager = (HorizontalPager) findViewById(R.id.pager);
		for (int i=today; i < NUM_DAYS; i++){
			if (i == today) {
				createCurrentMenu(menuList[i], "Today", pager);
			} else {
				createCurrentMenu(menuList[i], menuList[i].getDay(), pager);
			}
		}
	}
	
	/**
	 * creates the layout for a specific day and adds it to cafemac.xml
	 * @param menu the cafemacitem to be added
	 * @param day the day of the cafemacitem
	 * @param pager the horizontal pager to be added
	 */
	public void createCurrentMenu(CafeMacItem menu, String day, HorizontalPager pager){
		View cafeMacItem = getLayoutInflater().inflate(R.layout.cafemac_item, null);
		TextView text = null; 
		text = (TextView) cafeMacItem.findViewById(R.id.day_header);
		text.setText(day);
		text = (TextView) cafeMacItem.findViewById(R.id.lunch_text);
		text.setText(menu.getFoodString("lunch"));
		text = (TextView) cafeMacItem.findViewById(R.id.dinner_text);
		text.setText(menu.getFoodString("dinner"));
		text.setWidth(getWindowManager().getDefaultDisplay().getWidth()-26);
		pager.addView(cafeMacItem);
	}
	
	/**
	 * Gets the menu from the CafeMac site and returns it in string form. 
	 */

	public String getMenu(){
		cafemac = "http://www.cafebonappetit.com/rss/menu/159";
		String result = helper.getRequest(cafemac);
		return Html.fromHtml(Html.fromHtml(result).toString()).toString();
	}
	
	/**
	 * Parses the menu into the menuList
	 * @param results string to be parsed
	 */
	public CafeMacItem[] parseMenu(String results){
		String[] daysWeek={"Mon, ","Tue, ","Wed, ","Thu, ","Fri, ","Sat, ","Sun, "};
		CafeMacItem[] menuList = new CafeMacItem[NUM_DAYS];
		int[] days=new int[NUM_DAYS];
		for (int i=0;i<NUM_DAYS;i++){
			days[i]=results.indexOf(daysWeek[i]);
		}
		for (int i=0;i<NUM_DAYS -1;i++){
			int nextIndex = days[i+1];
			if (nextIndex == -1){
				for (int j=i+1; j<NUM_DAYS; j++){
					if (days[j] != -1){
						nextIndex = days[j];
					} else {
						nextIndex = results.length();
					}
				}
			}
			String body=results.substring(days[i],nextIndex);
			String day=daysWeek[i].substring(0,daysWeek[i].length()-1);
			menuList[i]=new CafeMacItem(day,body);
		}
		if (days[NUM_DAYS-1] != -1){
			String body = results.substring(days[6],results.length());
			menuList[NUM_DAYS-1]=new CafeMacItem("Sun", body);
		} else {
			menuList[NUM_DAYS-1]=new CafeMacItem("Sun", "");
		}
		return menuList;
	}
	
	
	/**
	 * @return list of menu items
	 */
	public CafeMacItem[] getMenuList() {
		return menuList;
	}
	
	/**
	 * Since android's time system starts from Sunday, we have to format it to our own time system. 
	 * Constant time operation.
	 * @return day int of current day according to format monday-sunday (where mon=0, and sun=6)
	 */
	public int getToday(){
		Time now = new Time();
		now.setToNow();
		if (now.weekDay == 0){
			return 6;
		} else {
			return now.weekDay-1;
		}
	}
}
