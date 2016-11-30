package com.carpool;

import org.json.JSONObject;

import com.carpool.library.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GmailVerify extends Activity 
{

	Button btnverify;
	EditText inputpin;
	EditText inputEmail;
	//String email,ippin;
	

	String reg=RegisterActivity.pin1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verify);
		
		

		// Importing all assets like buttons, text fields
		
		inputpin = (EditText) findViewById(R.id.verifyPin);
		btnverify = (Button) findViewById(R.id.btnVerify);
		
		// Register Button Click event
		btnverify.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View view) 
			{
				
				
				String pin= inputpin.getText().toString();
				
				
				if(reg.equals(pin))
				{
					// Launch Dashboard Screen
					Intent dashboard = new Intent(getApplicationContext(), LoginActivity.class);
					// Close all views before launching Dashboard
					dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(dashboard);
					// Close Registration Screen
					finish();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please Enter Correct Pin!!", Toast.LENGTH_LONG).show();
					
					Intent dashboard = new Intent(getApplicationContext(), GmailVerify.class);
					// Close all views before launching Dashboard
					dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(dashboard);
					// Close Registration Screen
					finish();
					
				}
				
				
				
				
				
			}
			
			
		});
	}
}
