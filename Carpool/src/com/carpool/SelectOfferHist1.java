package com.carpool;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.carpool.library.JSONParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectOfferHist1 extends Activity
{
	
	TextView txtSource;
	TextView txtDestination;
	TextView txtDate;
	TextView txtEmail;
	TextView txtTime;
	TextView txtSeats;
	Button btnhok;
	Button btnhdelete;
	String det;
	String pid;
	String email;
	
	LoginActivity r=new LoginActivity();//for email
	HistoryActivity1 v=new HistoryActivity1();
	
	// Progress Dialog
	private ProgressDialog pDialog;
		// JSON parser class
	JSONParser jsonParser = new JSONParser();

		// single product url
	private static final String url_offerhist_details = "http://192.168.16.1/android_connect/get_offerhist_details.php";
	
		// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCT = "offer";
	private static final String TAG_PID = "pid";
		//private static final String TAG_NAME = "name";
	private static final String TAG_SOURCE = "source";
	private static final String TAG_DESTINATION = "dest";
	private static final String TAG_DATE = "date";
	private static final String TAG_EMAIL = "email1";
	private static final String TAG_TIME = "time";
	private static final String TAG_SEATS = "seats";
	
		
		@Override
	public void onCreate(Bundle savedInstanceState) 
	{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.viewofferdetails);

			// save button
			btnhok = (Button) findViewById(R.id.btnhok);
			btnhdelete = (Button) findViewById(R.id.btnhdelete);

			// getting product details from intent
			Intent i = getIntent();
			
			// getting product id (pid) from intent
			email= i.getStringExtra(TAG_EMAIL);

			
			// Getting complete product details in background thread
			new GetOfferHistoryDetails().execute();
			
			// save button click event
			btnhok.setOnClickListener(new View.OnClickListener() 
			{

				@Override
				public void onClick(View arg0) 
				{
					
					//send mail to offer
					
					try 
					{   
	                    // starting background task to update product
	    				Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
	    					
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

			// Delete button click event
			btnhdelete.setOnClickListener(new View.OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{
					// deleting product in background thread
					//new DeleteProduct().execute();
					Intent dashboard = new Intent(getApplicationContext(), HistoryActivity.class);
					// Close all views before launching Dashboard
					dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(dashboard);
					// Close Registration Screen
					finish();
				}
			});
		}

		/**
		 * Background Async Task to Get complete product details
		 * */
		class GetOfferHistoryDetails extends AsyncTask<String, String, String> 
		{

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() 
			{
				super.onPreExecute();
				pDialog = new ProgressDialog(SelectOfferHist1.this);
				pDialog.setMessage("Loading offer history details. Please wait...");
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
							
							email=r.email11;
							// Building Parameters
							List<NameValuePair> params = new ArrayList<NameValuePair>();
							params.add(new BasicNameValuePair("email", email));
							
							
							// getting product details by making HTTP request
							// Note that product details url will use GET request
							JSONObject json = jsonParser.makeHttpRequest(url_offerhist_details, "POST", params);

							// check your log for json response
							Log.d("Single offer Details", json.toString());
							
							// json success tag
							success = json.getInt(TAG_SUCCESS);
							if (success == 1) 
							{
								// successfully received product details
								JSONArray productObj = json.getJSONArray(TAG_PRODUCT); // JSON Array
								
								// get first product object from JSON Array
								JSONObject product = productObj.getJSONObject(v.flg1);

								
								txtSource = (TextView) findViewById(R.id.hsource);
								txtDestination = (TextView) findViewById(R.id.hdestination);
								txtDate = (TextView) findViewById(R.id.hdate);
								txtEmail = (TextView) findViewById(R.id.hemail);
								txtTime = (TextView) findViewById(R.id.htime);
								txtSeats = (TextView) findViewById(R.id.hseats);

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
								// request not found
								
								
								
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
	
	
	

}
