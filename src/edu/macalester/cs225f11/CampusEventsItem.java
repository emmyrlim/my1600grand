package edu.macalester.cs225f11;

public class CampusEventsItem extends Item{
	private String time;
	private String title;
	private String location;
	
	/**
	 * Constructor for campusEventItem
	 * @param time Time range that event happens
	 * @param body details about event
	 */
	public CampusEventsItem(String time, String body){
		super(body);
		this.time=time;
		String[] timeAndLocation = body.split("\n");
		this.title = timeAndLocation[0].trim();
		if (timeAndLocation.length == 2) {
			this.location = timeAndLocation[1].trim();
		} else {
			this.location = "no location";
		}
	}

	public String getTime() {
		return time;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getLocation() {
		return location;
	}

}
