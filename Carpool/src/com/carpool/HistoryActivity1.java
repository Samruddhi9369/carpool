package com.carpool;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.carpool.library.JSONParser;

public class HistoryActivity1 extends ListActivity {

	Button btnok;
	// Progress Dialog
	private ProgressDialog pDialog;
	LoginActivity r=new LoginActivity();
	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> productsList;

	// url to get all products list
	private static String url_offer_history = "http://192.168.16.1/android_connect/histoffer.php";
	
	

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_OFFER = "offer";
	private static final String TAG_EMAIL = "email";
	
	private static final String TAG_DATE = "date";
	
	
	public static String email;
	public static int flg,flg1;

	// products JSONArray
	JSONArray offer = null;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewhistory1);
		
		
		// Hashmap for ListView
				productsList = new ArrayList<HashMap<String, String>>();

				// Loading products in Background Thread
				//new LoadAllRequest().execute();
				
				new LoadAllOffer().execute();
				// Get listview
				//ListView lv1 = getListView();
		
				ListView lv1= getListView();
				lv1.setOnItemClickListener(new OnItemClickListener() 
				{

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// getting values from selected ListItem
						
						email = ((TextView) view.findViewById(R.id.email11)).getText().toString();
						
						flg1=position;

						// Starting new intent
						Intent in = new Intent(getApplicationContext(),SelectOffer.class);
						// sending pid to next activity
						in.putExtra(TAG_EMAIL, email);
						
						// starting new activity and expecting some response back
						startActivityForResult(in, 100);
					}
				});
				// Get listview
				
				
				
				btnok = (Button) findViewById(R.id.button1);
				btnok.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View view) {
						
						Intent dashboard = new Intent(getApplicationContext(), MainActivity.class);
						// Close all views before launching Dashboard
						dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(dashboard);
						// Close Registration Screen
						finish();
					}
				});

	}
	
	// Response from Edit Product Activity
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			// if result code 100
			if (resultCode == 100) {
				// if result code 100 is received 
				// means user edited/deleted product
				// reload this screen again
				Intent intent = getIntent();
				finish();
				startActivity(intent);
			}

		}

		
		
		

		/**
		 * Background Async Task to Load all offer by making HTTP Request
		 * */
		class LoadAllOffer extends AsyncTask<String, String, String> {

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(HistoryActivity1.this);
				pDialog.setMessage("Loading history. Please wait...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(false);
				pDialog.show();
			}

			/**
			 * getting All products from url
			 * */
			protected String doInBackground(String... args) {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				params.add(new BasicNameValuePair("email", email));
				
				
				
				// getting JSON string from URL
				JSONObject json = jParser.makeHttpRequest(url_offer_history, "GET", params);
				
				// Check your log cat for JSON reponse
				Log.d("History Offers: ", json.toString());

				try {
					// Checking for SUCCESS TAG
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) {
						// products found
						// Getting Array of Products
						offer = json.getJSONArray(TAG_OFFER);

						// looping through All Products
						for (int i = 0; i < offer.length(); i++) {
							JSONObject c = offer.getJSONObject(i);
							

							// Storing each json item in variable
							String email = c.getString(TAG_EMAIL);
							String date = c.getString(TAG_DATE);

							// creating new HashMap
							HashMap<String, String> map = new HashMap<String, String>();

							// adding each child node to HashMap key => value
							map.put(TAG_EMAIL, email);
							map.put(TAG_DATE, date);

							// adding HashList to ArrayList
							productsList.add(map);
						}
					} 
				} catch (JSONException e) {
					e.printStackTrace();
				}

				return null;
			}

			/**
			 * After completing background task Dismiss the progress dialog
			 * **/
			protected void onPostExecute(String file_url) {
				// dismiss the dialog after getting all products
				pDialog.dismiss();
				// updating UI from Background Thread
				runOnUiThread(new Runnable() {
					public void run() {
						/**
						 * Updating parsed JSON data into ListView
						 * */
						ListAdapter adapter = new SimpleAdapter(
								HistoryActivity1.this, productsList,
								R.layout.list_item2, new String[] { TAG_EMAIL,TAG_DATE},
								new int[] { R.id.email11,R.id.date11 });
						// updating listview
						setListAdapter(adapter);
					}
				});

			}

		}
}
