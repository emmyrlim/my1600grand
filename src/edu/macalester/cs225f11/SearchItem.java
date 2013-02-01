package edu.macalester.cs225f11;

import android.text.Html;
import android.text.Spanned;

/**
 * A section of search from either groups, faculty, students or departments
 * @author Emmy
 *
 */
public class SearchItem extends Item{

	private String searchResults;
	
	public SearchItem(String searchResults, String body) {
		super(body);
		this.searchResults = searchResults;
	}
	
	/**
	 * @return a span that keeps formatting that 
	 * contains all the results from that section
	 */
	public Spanned getSearchSection(){
		return Html.fromHtml(body.trim());
	}
	
	/**
	 * Gets the string that contains how many results there are from the section.
	 * @return
	 */
	public String getSearchResults(){
		return (Html.fromHtml(searchResults.trim())).toString().split("\n")[0];
	}

}
