package edu.macalester.cs225f11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

public class HttpHelper extends Activity implements Parcelable{
	
	private static DefaultHttpClient client;
	
	/**
	 * Constructor
	 * @param client the httpClient being used
	 */
	public HttpHelper(DefaultHttpClient client){
		this.client = client;
	}

	/**
	 * Takes a response after a getRequest response
	 * and reads it and turns it into a string
	 * @param response the getRequest response
	 * @return the getRequest response String
	 */
	public static String request(HttpResponse response){
        String result = "";
        try{
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                str.append(line + "\n");
            }
            in.close();
            result = str.toString();
        }catch(Exception ex){
            result = "Error";
        }
        return result;
    }
	
	/**
	 * @return List of cookies in the site
	 */
	public List<Cookie> getCookies(){
		return client.getCookieStore().getCookies();
	}
	
	/**
	 * @return the httpClient
	 */
	public DefaultHttpClient getClient(){
		return client;
	}
	
	/**
	 * Makes a post request and then returns a String from the
	 * response
	 * @param url the url to post request
	 * @param nameValuePairs the name value pairs in the post request
	 * @return the response from the post request in string form
	 */
    public String postRequest(String url, List<NameValuePair> nameValuePairs){
    	HttpPost request = new HttpPost(url);
    	String result = null;
    	try {
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    		HttpResponse response = client.execute(request);
    		result = HttpHelper.request(response);
    	} catch (ClientProtocolException e){
    		result = "clientprotocolexception";
    	} catch (IOException e) {
    		result = "ioexception";
    	}
    	return result;
    }
    
    /**
     * Makes a get request to a URL
     * @param url url to get the get request to
     * @return returns the get request response in string form
     */
    public String getRequest(String url){
    	HttpGet request = new HttpGet(url);
    	String result = null;
    	try {
    		HttpResponse response = client.execute(request);
    		result = HttpHelper.request(response);
    		
    	} catch (Exception e){
    		System.err.println(e);
    		result = "error";
    	}
    	return result;
    }

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
}

