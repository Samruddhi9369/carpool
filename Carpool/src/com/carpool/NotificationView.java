package com.carpool;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.carpool.SelectOffer.decseatcount;
import com.carpool.library.JSONParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NotificationView extends Activity 
{
	
	
	public static String det1;
	public static String det2;
	Button btnSave;
	Button btnDelete;
	private static String url_notification = "http://192.168.16.1/android_connect/notification.php";
	private static String url_notification1 = "http://192.168.16.1/android_connect/notification1.php";
	private static String url_notification3 = "http://192.168.16.1/android_connect/notification3.php";
	private static final String url_decseat_count = "http://192.168.16.1/android_connect/decseatcount1.php";
	
	JSONParser jsonParser = new JSONParser();
	
	//RequestActivity r=new RequestActivity();
	OfferActivity offerobj=new OfferActivity();
	TextView txtSource;
	TextView txtDestination;
	TextView txtDate;
	TextView txtEmail;
	TextView txtTime;
	TextView txtSeats;
	
	private static final String TAG_SOURCE = "source";
	private static final String TAG_DESTINATION = "dest";
	private static final String TAG_DATE = "date";
	private static final String TAG_TIME = "time";
	private static final String TAG_SEATS = "seats";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_EMAIL1 = "email";
	private static final String TAG_PRODUCT = "offer";
	private static final String TAG_PRODUCT1 = "request";
	private static final String TAG_PRODUCT2 = "users";
	
	private static final String TAG_NAME = "name";
	private static final String TAG_PHNO = "phno";
	private static String email;
	//private static String url_sock_client = "http://192.168.74.1/android_connect/SocketClient.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewdetails);	
		
		
		System.out.println("hi");
		// Intent myIntent = new Intent(NotificationView.this, ViewOffersActivity.class);
	       // startActivity(myIntent);
		
		btnSave = (Button) findViewById(R.id.btnSave);
		//btnDelete = (Button) findViewById(R.id.btnDelete);
		new GetProductDetails1().execute();
		
		btnSave.setOnClickListener(new View.OnClickListener() 
		{

			@Override
			public void onClick(View arg0) 
			{
				
				try 
				{   
					final GMailSender sender = new GMailSender("nupur1.kulkarni@gmail.com", "kul123nup");
					sender.sendMail("request", det1 ,"nupur1.kulkarni@gmail.com","samruddhi9369@gmail.com");
					// starting background task to update product
					new decseatcount().execute();

					Intent i = new Intent(getApplicationContext(), MainActivity.class);
					// Close all views before launching Dashboard
					//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					// Close Registration Screen
					finish();
            
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		

		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	class GetProductDetails1 extends AsyncTask<String, String, String> 
	{

		@Override
		protected String doInBackground(String... arg0) 
		{
			// TODO Auto-generated method stub
		
			// updating UI from Background Thread
			runOnUiThread(new Runnable() 
			{
				public void run() 
				{
					
					
					try 
					{
					// Check for success tag
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						
						//List<NameValuePair> params1 = new ArrayList<NameValuePair>();
						
						String source="hi";
						//params.add(new BasicNameValuePair("source", source));
						
						JSONObject json = jsonParser.makeHttpRequest(url_notification,"POST", params);
					
					    JSONArray productObj = json.getJSONArray(TAG_PRODUCT); // JSON Array
					
					// get first product object from JSON Array
					    JSONObject product = productObj.getJSONObject(0);
					
					
					
					//int success;
					txtSource = (TextView) findViewById(R.id.source);
					txtDestination = (TextView) findViewById(R.id.destination);
					txtDate = (TextView) findViewById(R.id.date);
					txtEmail = (TextView) findViewById(R.id.email);
					txtTime = (TextView) findViewById(R.id.time);
					txtSeats = (TextView) findViewById(R.id.seats);


// display product data in EditText
					txtSource.setText(product.getString(TAG_SOURCE));
					txtSource.invalidate();
					txtDestination.setText(product.getString(TAG_DESTINATION));
					txtDestination.invalidate();
					txtDate.setText(product.getString(TAG_DATE));
					txtDate.invalidate();
					txtEmail.setText(product.getString(TAG_EMAIL));
					txtEmail.invalidate();
					email=txtEmail.toString();
					txtTime.setText(product.getString(TAG_TIME));
					txtTime.invalidate();
					txtSeats.setText(product.getString(TAG_SEATS));
					txtSeats.invalidate();
					
					JSONObject json1 = jsonParser.makeHttpRequest(url_notification1,"GET", params);
					JSONArray productObj1 = json1.getJSONArray(TAG_PRODUCT1); // JSON Array
					
					
					// get first product object from JSON Array
				    JSONObject product1 = productObj1.getJSONObject(0);
				    det1="\nDetails of the request\n\n\nSource:\t"+product1.getString(TAG_SOURCE)+"\nDestination:\t"+product1.getString(TAG_DESTINATION)+"\nDate:\t"+product1.getString(TAG_DATE)+"\nEmail:\t"+product1.getString(TAG_EMAIL1)+"\nTime:\t"+product1.getString(TAG_TIME)+"\nNumber of seats:"+product1.getString(TAG_SEATS);
				    
					
					
					JSONObject json2 = jsonParser.makeHttpRequest(url_notification3,"GET", params);
					JSONArray productObj2 = json2.getJSONArray(TAG_PRODUCT2); // JSON Array
					
					
					    
					JSONObject product2 = productObj2.getJSONObject(0);
					    
					det2="\nDetails of requester\n\nPersonal Details:\n\nName:\t"+product2.getString(TAG_NAME)+"\nContact Number:\t"+product2.getString(TAG_PHNO)+det1;
					    
					det1=det2;
					} 
					catch (JSONException e) 
					{
						e.printStackTrace();
					}
				}
			});
			
			
			return null;
		}
		
	}
	
 
	 
	class decseatcount extends AsyncTask<String, String, String> 
	{

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
			// updating UI from Background Thread
			runOnUiThread(new Runnable()
			{
				public void run() 
				{
					// Check for success tag
					int success;
					try 
					{
						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("email",email));
						JSONObject json = jsonParser.makeHttpRequest(url_decseat_count, "GET", params);
					}
					catch(Exception e){}
				}
			});
			
			return null;
		}
	}

	
	

}

