package com.carpool;
import com.carpool.library.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.carpool.library.UserFunctions;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ViewRequestsActivity extends ListActivity {

	Button btnok;
	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> productsList;

	// url to get all products list
	private static String url_all_products = "http://192.168.16.1/android_connect/get_all_requests.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "request";
	//private static final String TAG_PID = "pid";
	private static final String TAG_SOURCE = "source";
	private static final String TAG_NAME = "email";
	private static final String TAG_TIME = "time";
	private static final String TAG_SEATS = "seats";
	

	// products JSONArray
	JSONArray request = null;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewrides);
		
		
		UserFunctions userFunction = new UserFunctions();
		
		JSONObject json = userFunction.createOffer(OfferActivity.vardest);

		

		// Hashmap for ListView
		productsList = new ArrayList<HashMap<String, String>>();

		// Loading products in Background Thread
		new LoadAllProducts().execute();

		// Get listview
		ListView lv = getListView();

		// on seleting single product
		// launching Edit Product Screen
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String pid = ((TextView) view.findViewById(R.id.pid)).getText()
						.toString();

				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						SelectOffer.class);
				// sending pid to next activity
				in.putExtra(TAG_SOURCE, id);
				
				// starting new activity and expecting some response back
				startActivityForResult(in, 100);
			}
		});
		
		btnok = (Button) findViewById(R.id.button1);
		btnok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				
				Intent dashboard = new Intent(getApplicationContext(), ProfileFragmant.class);
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
	 * Background Async Task to Load all product by making HTTP Request
	 * */
	class LoadAllProducts extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewRequestsActivity.this);
			pDialog.setMessage("Loading Requests. Please wait...");
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
		
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
			
			// Check your log cat for JSON reponse
			Log.d("All Products: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					request = json.getJSONArray(TAG_PRODUCTS);

					// looping through All Products
					for (int i = 0; i < request.length(); i++) {
						JSONObject c = request.getJSONObject(i);

						// Storing each json item in variable
						String id = c.getString(TAG_SOURCE);
						String name = c.getString(TAG_NAME);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_SOURCE, id);
						map.put(TAG_NAME, name);

						// adding HashList to ArrayList
						productsList.add(map);
					}
				} else {
					// no products found
					// Launch Add New product Activity
					Intent i = new Intent(getApplicationContext(),
							OfferActivity.class);
					// Closing all previous activities
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
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
							ViewRequestsActivity.this, productsList,
							R.layout.list_item, new String[] { TAG_SOURCE,
									TAG_NAME},
							new int[] { R.id.pid, R.id.name });
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
}