package com.carpool;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.carpool.library.JSONParser;

public class SelectOffer extends Activity 
{

	TextView txtSource;
	TextView txtDestination;
	TextView txtDate;
	TextView txtEmail;
	TextView txtTime;
	TextView txtSeats;
	Button btnSave;
	Button btnDelete;
	String det;
	String pid;
	
	RequestActivity r=new RequestActivity();//for email
	ViewOffersActivity v=new ViewOffersActivity();
	// Progress Dialog
	private ProgressDialog pDialog;
		// JSON parser class
	JSONParser jsonParser = new JSONParser();

		// single product url
	private static final String url_product_details = "http://192.168.16.1/android_connect/get_product_details.php";
	private static final String url_decseat_count = "http://192.168.16.1/android_connect/decseatcount.php";
	
		// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCT = "offer";
	private static final String TAG_PID = "pid";
		//private static final String TAG_NAME = "name";
	private static final String TAG_SOURCE = "source";
	private static final String TAG_DESTINATION = "dest";
	private static final String TAG_DATE = "date";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_TIME = "time";
	private static final String TAG_SEATS = "seats";
	
		
		@Override
	public void onCreate(Bundle savedInstanceState) 
	{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.viewdetails);

			// save button
			btnSave = (Button) findViewById(R.id.btnSave);
			//btnDelete = (Button) findViewById(R.id.btnDelete);

			// getting product details from intent
			Intent i = getIntent();
			
			// getting product id (pid) from intent
			pid = i.getStringExtra(TAG_PID);

			
			// Getting complete product details in background thread
			new GetProductDetails().execute();
			
			// save button click event
			btnSave.setOnClickListener(new View.OnClickListener() 
			{

				@Override
				public void onClick(View arg0) 
				{
					
					//send mail to offer
					
					try 
					{   
	                    final GMailSender sender = new GMailSender("nupur1.kulkarni@gmail.com", "kul123nup");
	                    sender.sendMail("request", r.det ,"nupur1.kulkarni@gmail.com","samruddhi9369@gmail.com");
	                	
	                    new decseatcount().execute();
	                    // starting background task to update product
	                    
	    				Intent i = new Intent(getApplicationContext(), MainActivity.class);
	    					
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

		/**
		 * Background Async Task to Get complete product details
		 * */
		class GetProductDetails extends AsyncTask<String, String, String> 
		{

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() 
			{
				super.onPreExecute();
				pDialog = new ProgressDialog(SelectOffer.this);
				pDialog.setMessage("Loading offer details. Please wait...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			/**
			 * Getting product details in background thread
			 * */
			protected String doInBackground(String... params)
			{

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
							params.add(new BasicNameValuePair("pid", pid));
							
							
							// getting product details by making HTTP request
							// Note that product details url will use GET request
							JSONObject json = jsonParser.makeHttpRequest(url_product_details, "GET", params);

							// check your log for json response
							Log.d("Single offer Details", json.toString());
							
							// json success tag
							success = json.getInt(TAG_SUCCESS);
							if (success == 1) 
							{
								// successfully received product details
								JSONArray productObj = json.getJSONArray(TAG_PRODUCT); // JSON Array
								
								// get first product object from JSON Array
								JSONObject product = productObj.getJSONObject(v.flg);

								
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
								txtTime.setText(product.getString(TAG_TIME));
								txtTime.invalidate();
								txtSeats.setText(product.getString(TAG_SEATS));
								txtSeats.invalidate();
								
								
								

							}
							else
							{
								// offer not found
								
								
								
							}
						} 
						catch (JSONException e) 
						{
							e.printStackTrace();
						}
					}
				});

				return null;
			}


			/**
			 * After completing background task Dismiss the progress dialog
			 * */
			protected void onPostExecute(String file_url) 
			{
				// dismiss the dialog once got all details
				pDialog.dismiss();
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
							params.add(new BasicNameValuePair("pid",pid));
							JSONObject json = jsonParser.makeHttpRequest(url_decseat_count, "GET", params);
						}
						catch(Exception e){}
					}
				});
				
				return null;
			}
		}
}
		



