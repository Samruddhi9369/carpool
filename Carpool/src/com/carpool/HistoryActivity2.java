package com.carpool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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

public class HistoryActivity2 extends Activity {

	//Button btnok;
	Button btnRequest;
	Button btnOffer;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history2);
		
		// button
				btnRequest = (Button) findViewById(R.id.button1);
				btnOffer = (Button) findViewById(R.id.button2);

				// save button click event
				btnRequest.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent dashboard = new Intent(getApplicationContext(), HistoryActivity.class);
						
						// Close all views before launching Dashboard
						dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(dashboard);
				
						// Close Login Screen
						finish();
					}
				});


				
				btnOffer.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// Loading products in Background Thread
						Intent dashboard = new Intent(getApplicationContext(), HistoryActivity1.class);
						
						// Close all views before launching Dashboard
						dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(dashboard);
				
						// Close Login Screen
						finish();
					}
				});
				
				
				
				
	}
}
