package edu.macalester.cs225f11;


import java.util.ArrayList;
import java.util.List;


import org.macalester.edu.R;

import edu.macalester.cs225f11.CampusTools;
import edu.macalester.cs225f11.CustomMenu.OnMenuItemSelectedListener;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

/**
 * The parent activity of all activities that give us pretty custom menus.
 * @author Emmy
 *
 */
public class GrandMenu extends Activity implements OnMenuItemSelectedListener {
	private final static String CAFEMACSITE = "http://www.cafebonappetit.com/rss/menu/159";
	private final static String DIRECTORY = "http://webapps.macalester.edu/directory/portal-search.cfm";
	private final static String CAMPUSEVENTS = "http://events.macalester.edu/index.cfm?cal=Campus+Events";
	private static final int ID_CAMPUS_EVENTS = 1;
	private static final int ID_CAFE_MAC   = 2;
	private QuickAction quickActionList;
	private static final int ITEMS_PER_ROW_PORTRAIT = 4;
	private static final int ITEMS_PER_ROW_LANDSCAPE = 8;
	public static List<ImageView> placeholderImages;
    ArrayList<CustomMenuItem> menuItems = null;
    protected String currentActivity="";
	
	/**
	 * Some global variables.
	 */
	private CustomMenu mMenu;
	public static final int MENU_ITEM_LIST = 1;
	public static final int MENU_ITEM_TOOLS = 2;
	public static final int MENU_ITEM_PREFERENCES = 3;
	
    /**
     * Always called when an Android activity launches.
     */
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        //create the view
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		//Add action item    	 
        ActionItem searchItem 	= new ActionItem(ID_CAMPUS_EVENTS, "Campus Events", getResources().getDrawable(R.drawable.campus_events_icon));
        ActionItem infoItem 	= new ActionItem(ID_CAFE_MAC, "Cafe Mac", getResources().getDrawable(R.drawable.cafe_mac_menu_icon));
    	
		//create QuickAction. Use QuickAction.VERTICAL or QuickAction.HORIZONTAL param to define layout 
        //orientation
		quickActionList = new QuickAction(this, QuickAction.VERTICAL);
		
		//add action items into QuickAction;
        quickActionList.addActionItem(searchItem);
        quickActionList.addActionItem(infoItem);
		
        
        //initialize the menu
        mMenu = new CustomMenu(this, this, getLayoutInflater());
        mMenu.setHideOnSelect(false);
        mMenu.setItemsPerLineInPortraitOrientation(ITEMS_PER_ROW_PORTRAIT);
        mMenu.setItemsPerLineInLandscapeOrientation(ITEMS_PER_ROW_LANDSCAPE);
        //load the menu items
        loadMenuItems();
        
        //Set listener for action item clicked
		quickActionList.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
			@Override
			public void onItemClick(QuickAction source, int pos, int actionId) {				
				ActionItem actionItem = quickActionList.getActionItem(pos);
	            Intent intent;   
				//here we can filter which action item was clicked with pos or actionId parameter
				if (actionId == ID_CAMPUS_EVENTS) {
					if (!currentActivity.equals("campusevents")){
						intent = new Intent(getApplicationContext(), CampusEvents.class);
						intent.putExtra("CAMPUSEVENTS", CAMPUSEVENTS);
						startActivity(intent);
					}
				} else if (actionId == ID_CAFE_MAC) {
					if (!currentActivity.equals("cafemac")){
						intent = new Intent(getApplicationContext(), CafeMacMenu.class);
			        	intent.putExtra("CAFEMACSITE", CAFEMACSITE);
			        	startActivity(intent);
					}
				} else {
					Toast.makeText(getApplicationContext(), actionItem.getTitle() + " selected", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		//set listener for on dismiss event, this listener will be called only if QuickAction dialog was dismissed
		//by clicking the area outside the dialog.
		quickActionList.setOnDismissListener(new QuickAction.OnDismissListener() {			
			@Override
			public void onDismiss() {
			}
		});
        

	}

	/**
     * Load up our menu.
     */
	private void loadMenuItems() {
		placeholderImages = new ArrayList<ImageView>();
		//This is kind of a tedious way to load up the menu items.
		//Am sure there is room for improvement.
		menuItems = new ArrayList<CustomMenuItem>();
		CustomMenuItem cmi = new CustomMenuItem();
		cmi.setCaption("Mac Lists");
		cmi.setImageResourceId(R.drawable.mac_icon_2);
		cmi.setId(MENU_ITEM_LIST);
		menuItems.add(cmi);
		cmi = new CustomMenuItem();
		cmi.setCaption("Campus Tools");
		cmi.setImageResourceId(R.drawable.mac_tools);
		cmi.setId(MENU_ITEM_TOOLS);
		menuItems.add(cmi);
		cmi = new CustomMenuItem();
		cmi.setCaption("Preferences");
		cmi.setImageResourceId(R.drawable.mac_preferences);
		cmi.setId(MENU_ITEM_PREFERENCES);
		menuItems.add(cmi);
		cmi = new CustomMenuItem();	
		if (!mMenu.isShowing())
		try {
			mMenu.setMenuItems(menuItems);
		} catch (Exception e) {
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Egads!");
			alert.setMessage(e.getMessage());
			alert.show();
		}
	}
	
	/**
	 * For each item in menuitems, create a imageview at the bottom with the name "imageview_1", 
	 * "imageview_2"..."imageview_x", where x is the id of the menu item
	 * then it can be called later by taking the menu item's id, and taking it onto "imageview_"
	 * @param amount
	 */
	public void createBackgroundImages(int amount){
		boolean isLandscape = false;
		Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		if (display.getWidth() > display.getHeight()) isLandscape = true;
		
		int divisor = ITEMS_PER_ROW_PORTRAIT;
        if (isLandscape) divisor = ITEMS_PER_ROW_LANDSCAPE;
        int remainder = 0;
        int rows = 0;
        if (amount < divisor) {
        	rows = 1;
        	remainder = amount;
        } else {
        	rows = (amount / divisor);
        	remainder = amount % divisor;
        	if (remainder != 0) rows++;
        }
        TableLayout table = (TableLayout) findViewById(R.id.menu_table);
        table.removeAllViews();
		TableRow row = null;
		ImageView placeholder = null;
		for (int i = 0; i < rows; i++){
    		//create headers
    		row = new TableRow(this);
    		row.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
    		for (int j=0; j< divisor; j++) {
    			if (i*divisor+j >= amount) break;
    			placeholder = new ImageView(this);
    			placeholder.setImageResource(R.drawable.placeholder_image);
    			row.addView(placeholder);
    			placeholderImages.add(placeholder);
    		}
    		table.addView(row);
		}
	}
	
	/**
     * Snarf the menu key.
     */
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
	    if (keyCode == KeyEvent.KEYCODE_MENU) {
	    	doMenu();
	    	return true; //always eat it!
	    } 
		return super.onKeyDown(keyCode, event); 
	} 
	
	/**
     * Toggle our menu on user pressing the menu key.
     */
	private void doMenu() {
		if (mMenu.isShowing()) {
			mMenu.hide();
		} else {
			//Note it doesn't matter what widget you send the menu as long as it gets view.
			mMenu.show(findViewById(R.id.menu_table));
		}
	}
	

	/**
     * For the demo just toast the item selected.
     */
	@Override
	public void MenuItemSelectedEvent(CustomMenuItem selection) {
		Intent intent;
		if (selection.getId() == MENU_ITEM_LIST) {
			quickActionList.show(findViewById(R.id.maclists_placeholder));
		} else {
			switch (selection.getId()) {
			case MENU_ITEM_TOOLS:
				if (!currentActivity.equals("directory")){
					intent = new Intent(this, CampusTools.class);
					intent.putExtra("DIRECTORY", DIRECTORY);
					startActivity(intent);
				}
	        	break;
			case MENU_ITEM_PREFERENCES:
				intent = new Intent(this, Preferences.class);
	        	startActivity(intent);
	        	break;
			default:
			}
		}
	}
	
	
}