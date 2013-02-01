package edu.macalester.cs225f11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CafeMacItem extends Item{
	private String day;
	private Map<String, ArrayList<String>> foodMap;
	private List<String> foodItems;
	
	/**
	 * constructor
	 * @param day The day of the menu
	 * @param items all items of the day as extracted from the RSS feed
	 */
	public CafeMacItem(String day, String items){
		super(items);
		this.day=day;
		extractFoodItems();
	}

	/**
	 * Sorts the food into the different ArrayLists of the map.
	 * First it checks to see what format  the food is in (ie has 21
	 * itemson weekdays, 11 on sunday, etc.). It then places food
	 * in their respective categories.   
	 */
//	public void extractFoodItems(){
//		foodItems = new ArrayList<String>();
//		foodMap = new HashMap<String, ArrayList<String>>();
//		foodMap.put("soup", new ArrayList<String>());
//		foodMap.put("pasta", new ArrayList<String>());
//		foodMap.put("pizza", new ArrayList<String>());
//		foodMap.put("wok", new ArrayList<String>());
//		foodMap.put("grill", new ArrayList<String>());
//		foodMap.put("curry", new ArrayList<String>());
//		foodMap.put("wraps", new ArrayList<String>());
//		String[] items = body.split("\\[");
//		System.out.println("itemSize = " + items.length);
//		for (int i = 2; i < items.length; i = i + 2){
//			foodItems.add(items[i].substring(0,items[i].length()));
//			System.out.println("FOODITEM: " + items[i].substring(0,items[i].length()));
//		}
//		System.out.println("here");
//		for (int i=0; i<foodItems.size(); i++){
//			if (foodItems.size()== 21){
//				if (i == 0 || i == 1){
//					((List<String>) foodMap.get("soup")).add(foodItems.get(i));
//				} else if (i == 2 || i == 3){
//					((List<String>) foodMap.get("pasta")).add(foodItems.get(i));
//				} else if (i == 4){
//					((List<String>) foodMap.get("pizza")).add(foodItems.get(i));
//				} else if (i >= 5 && i <= 8){
//					((List<String>) foodMap.get("wok")).add(foodItems.get(i));
//				} else if (i >= 9 && i <= 11){
//					String foodItem = foodItems.get(i);
//					if (i == 9 && foodItem.substring(foodItem.length(), foodItem.length()).equals("Rice")){
//						((List<String>) foodMap.get("wok")).add(foodItem);
//					} else {
//						((List<String>) foodMap.get("grill")).add(foodItem);
//					}
//				} else if (i >= 12 && i <= 15){
//					((List<String>) foodMap.get("curry")).add(foodItems.get(i));
//				} else if (i >=16 && i <= 20){
//					((List<String>) foodMap.get("wraps")).add(foodItems.get(i));
//				}
//			} else if (foodItems.size() == 11){
//				if (i == 0 || i == 1){
//					((List<String>) foodMap.get("soup")).add(foodItems.get(i));
//				} else if (i == 2 || i == 3){
//					((List<String>) foodMap.get("pasta")).add(foodItems.get(i));
//				} else if (i == 4){
//					((List<String>) foodMap.get("pizza")).add(foodItems.get(i));
//				} else if (i >= 5 && i <= 7){
//					((List<String>) foodMap.get("wok")).add(foodItems.get(i));
//				} else if (i >= 8 && i <= 10){
//					((List<String>) foodMap.get("grill")).add(foodItems.get(i));
//				}
//			} else if (foodItems.size() == 17){
//				if (i == 0 || i == 1){
//					((List<String>) foodMap.get("soup")).add(foodItems.get(i));
//				} else if (i == 2 || i == 3){
//					((List<String>) foodMap.get("pasta")).add(foodItems.get(i));
//				} else if (i == 4){
//					((List<String>) foodMap.get("pizza")).add(foodItems.get(i));
//				} else if (i >= 5 && i <= 9){
//					((List<String>) foodMap.get("wok")).add(foodItems.get(i));
//				} else if (i >= 10 && i <= 12){
//					((List<String>) foodMap.get("grill")).add(foodItems.get(i));
//				} else if (i >= 13 && i <= 16){
//					((List<String>) foodMap.get("wraps")).add(foodItems.get(i));
//				}
//			} else if (foodItems.size() == 19){
//				if (i == 0 || i == 1){
//					((List<String>) foodMap.get("soup")).add(foodItems.get(i));
//				} else if (i == 2 || i == 3){
//					((List<String>) foodMap.get("pasta")).add(foodItems.get(i));
//				} else if (i == 4){
//					((List<String>) foodMap.get("pizza")).add(foodItems.get(i));
//				} else if (i >= 5 && i <= 8){
//					((List<String>) foodMap.get("wok")).add(foodItems.get(i));
//				} else if (i >= 9 && i <= 11){
//					((List<String>) foodMap.get("grill")).add(foodItems.get(i));
//				} else if (i == 12 || i== 13){
//					((List<String>) foodMap.get("curry")).add(foodItems.get(i));
//				} else if (i >=14 && i <= 18){
//					((List<String>) foodMap.get("wraps")).add(foodItems.get(i));
//				}
//			} else if (foodItems.size() == 12){
//				if (i == 0 || i == 1){
//					((List<String>) foodMap.get("soup")).add(foodItems.get(i));
//				} else if (i == 2 || i == 3){
//					((List<String>) foodMap.get("pasta")).add(foodItems.get(i));
//				} else if (i == 4){
//					((List<String>) foodMap.get("pizza")).add(foodItems.get(i));
//				} else if (i >= 5 && i <= 8){
//					((List<String>) foodMap.get("wok")).add(foodItems.get(i));
//				} else if (i >= 9 && i <= 11){
//					((List<String>) foodMap.get("grill")).add(foodItems.get(i));
//				}
//			} else if (foodItems.size() == 16){
//				if (i == 0 || i == 1){
//					((List<String>) foodMap.get("soup")).add(foodItems.get(i));
//				} else if (i == 2 || i == 3){
//					((List<String>) foodMap.get("pasta")).add(foodItems.get(i));
//				} else if (i == 4){
//					((List<String>) foodMap.get("pizza")).add(foodItems.get(i));
//				} else if (i >= 5 && i <= 8){
//					((List<String>) foodMap.get("wok")).add(foodItems.get(i));
//				} else if (i >= 9 && i <= 11){
//					((List<String>) foodMap.get("grill")).add(foodItems.get(i));
//				} else if (i >= 12 && i <= 15){
//					((List<String>) foodMap.get("wraps")).add(foodItems.get(i));
//				}
//			} else if (foodItems.size() == 18){
//				if (i == 0 || i == 1){
//					((List<String>) foodMap.get("soup")).add(foodItems.get(i));
//				} else if (i == 2 || i == 3){
//					((List<String>) foodMap.get("pasta")).add(foodItems.get(i));
//				} else if (i == 4){
//					((List<String>) foodMap.get("pizza")).add(foodItems.get(i));
//				} else if (i >= 5 && i <= 8){
//					((List<String>) foodMap.get("wok")).add(foodItems.get(i));
//				} else if (i >= 9 && i <= 11){
//					((List<String>) foodMap.get("grill")).add(foodItems.get(i));
//				} else if (i >= 12 && i <= 16){
//					((List<String>) foodMap.get("curry")).add(foodItems.get(i));
//				} else if (i == 17){
//					((List<String>) foodMap.get("wraps")).add(foodItems.get(i));
//				}
//			}
//		}
//	}
	
	public void extractFoodItems(){
		foodItems = new ArrayList<String>();
		foodMap = new HashMap<String, ArrayList<String>>();
		foodMap.put("lunch", new ArrayList<String>());
		foodMap.put("dinner", new ArrayList<String>());
		body = body.replace("\n", "").replace("\r", "");
		String[] lunchDinnerSplit = body.split("Dinner");
		String[] lunchItems = lunchDinnerSplit[0].split("\\[");
		String[] dinnerItems = lunchDinnerSplit[1].split("\\[");
		System.out.println("BODY IS: " + body);
		for (int i = 0; i < lunchItems.length; i++){
			foodItems.add(lunchItems[i]);
		}
		for (int i = 0; i < dinnerItems.length; i++){
			foodItems.add(dinnerItems[i]);
		}
		for (int i=0; i<foodItems.size(); i++){
			System.out.println("FOODITEM: " + foodItems.get(i));
		}
	}
	
	public String getDay() {
		return day;
	}
	
	public String toString(){
		return "DAY: "+day+"BODY: "+body;
	}
	
	/**
	 * Gets the food by type.
	 * @param foodType The type of food to be searched for
	 * @return List<String> of foods by type
	 */
	public List<String> getFood(String foodType){
		List<String> food = foodMap.get(foodType);
		if (food.size() == 0){
			food.add("No "+foodType+" today");
		}
		return foodMap.get(foodType);
	}
	
	public List<String> getSoup(){
		return getFood("soup");
	}
	
	public List<String> getPasta(){
		return getFood("pasta");
	}
	
	public List<String> getPizza(){
		return getFood("pizza");
	}
	
	public List<String> getWok(){
		return getFood("wok");
	}
	
	public List<String> getGrill(){
		return getFood("grill");
	}
	
	public List<String> getCurry(){
		return getFood("curry");
	}
	
	public List<String> getWraps(){
		return getFood("wraps");
	}
	
	/**
	 * Gets the by type, and returns a useable string.
	 * @param foodType Type of food to be searched for
	 * @return String of the food of a certain type 
	 */
	public String getFoodString(String foodType){
		String foodString = "";
		List<String> foodList = getFood(foodType);
		for (int i=0; i < foodList.size(); i++){
			if (i == 0) {
				foodString=foodList.get(i);
			} else {
				foodString=foodString+"\n"+foodList.get(i);
			}
		}
		return foodString;
	}
	
}
