package edu.macalester.cs225f11;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * Redacted Login Controller - This will be used in future when we implement login functions
 * @author Emmy
 *
 */
public class LoginController {
	
	private static String loginSite;
	private static HttpHelper helper;
	
	public LoginController(HttpHelper helper, String loginSite){
		this.helper = helper;
		this.loginSite = loginSite;
	}
	
	public void portalLogin(String user, String pass){
    	List<NameValuePair> loginPairs = new ArrayList<NameValuePair>();
    	loginPairs.add(new BasicNameValuePair("user", user));
    	loginPairs.add(new BasicNameValuePair("pass", pass));
    	loginPairs.add(new BasicNameValuePair("uuid", "0xACA021"));
    	helper.postRequest(loginSite, loginPairs);
    }

}
