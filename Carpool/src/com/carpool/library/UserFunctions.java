
package com.carpool.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

public class UserFunctions {
	
	private JSONParser jsonParser;
	
	private static String loginURL = "http://192.168.16.1/ah_login_api/";
	private static String registerURL = "http://192.168.16.1/ah_login_api/";
	private static String OfferURL = "http://192.168.16.1/android_connect/get_all_offers.php";
	private static String RequestURL = "http://192.168.16.1/android_connect/get_all_requests.php";
	
	
	private static String login_tag = "login";
	private static String register_tag = "register";
	private static String offer_tag = "offer";
	private static String request_tag = "request";
	// constructor
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	
	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * */
	public JSONObject loginUser(String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	
	/**
	 * function make Login Request
	 * @param name
	 * @param email
	 * @param password
	 * */
	public JSONObject registerUser(String name, String email,String phno, String address, String ipString, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("phno", phno));
		params.add(new BasicNameValuePair("address", address));
		params.add(new BasicNameValuePair("ipString", ipString));
		params.add(new BasicNameValuePair("password", password));
		
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		// return json
		return json;
	}
	
	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if(count > 0){
			// user logged in
			return true;
		}
		return false;
	}
	
	/**
	 * Function to logout user
	 * Reset Database
	 * */
	public boolean logoutUser(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}
	
	
	public JSONObject createOffer(String dest){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("dest", dest));
		
		JSONObject json = jsonParser.getJSONFromUrl(OfferURL, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	
	public JSONObject createRequest(String dest){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("dest", dest));
		
		JSONObject json = jsonParser.getJSONFromUrl(RequestURL, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}

	
	
	
	
}