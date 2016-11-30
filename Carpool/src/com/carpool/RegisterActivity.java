package com.carpool;

import org.json.JSONException;
import java.io.FileInputStream;
import java.io.IOException;
import org.json.JSONObject;

import com.carpool.library.DatabaseHandler;
import com.carpool.library.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity 
{
	Button btnRegister;
	Button btnLinkToLogin;
	EditText inputFullName;
	EditText inputEmail;
	EditText inputPassword;
	EditText inputPhno;
	EditText inputAddr;
	TextView registerErrorMsg;
	public int flag1=0,flag2=0,flag3=0,flag4=0;
	public String ipString;
	
	public static String pin1="1234";
	
	public static String status="false";
	public static int registerflag=0;
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_STAT = status;
	private static String KEY_PHNO = "phno";
	private static String KEY_ADDR = "address";
	private static String KEY_CREATED_AT = "created_at";

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		// Importing all assets like buttons, text fields
		inputFullName = (EditText) findViewById(R.id.registerName);
		inputEmail = (EditText) findViewById(R.id.registerEmail);
		inputPassword = (EditText) findViewById(R.id.registerPassword);
		inputPhno = (EditText) findViewById(R.id.registerPhno);
		inputAddr = (EditText) findViewById(R.id.registerAddr);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		//btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
		registerErrorMsg = (TextView) findViewById(R.id.register_error);
		
		//get ip address
		
		WifiManager wifiManager= (WifiManager)getSystemService(WIFI_SERVICE);
		
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ip= wifiInfo.getIpAddress();
		ipString= String.format("%d.%d.%d.%d",(ip & 0xff),(ip >>8 & 0xff),(ip >>16 & 0xff),(ip >>24 & 0xff));
		
		
		// Register Button Click event
		btnRegister.setOnClickListener(new View.OnClickListener() 
		{			
		public void onClick(View view)
		{
				String name = inputFullName.getText().toString();
				
				String email = inputEmail.getText().toString();
				//validation for email
						
				String password = inputPassword.getText().toString();
				String phno = inputPhno.getText().toString();
				String address = inputAddr.getText().toString();
			    //String ipString1=ipString;
				
				
				
				try 
				{   
					if(!email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
					{

						Toast.makeText(getApplicationContext(), "Enter valid email like abc@xyz.com",3).show();
						flag1=1;
					}
					if(phno.length()<10||phno.length()>14)
					{
						flag2=1;
						Toast.makeText(getApplicationContext(), "Enter valid number",3).show();
					}

					 if(password.length()<6)
					{
						flag3=1;
						Toast.makeText(getApplicationContext(), "Enter 6 character password",3).show();
					}

					 if(!name.matches("^[_A-Za-z-]+(\\.[_A-Za-z-]+)*$"))
					{
						flag4=1;
						Toast.makeText(getApplicationContext(), "Enter valid email like abc@xyz.com",3).show();
					}
	      			if(flag1==0&&flag2==0&&flag3==0&&flag4==0)
					{	
					
					final GMailSender sender = new GMailSender("nupur1.kulkarni@gmail.com", "kul123nup");
                    sender.sendMail("Verification", "1234" ,"nupur1.kulkarni@gmail.com","samruddhi9369@gmail.com");
                	// starting background task to update product
    				Intent i = new Intent(getApplicationContext(), MainActivity.class);
    					
    				startActivity(i);
    					// Close Registration Screen
    				finish();
					}
				} 
            	catch (Exception e) 
            	{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			

				UserFunctions userFunction = new UserFunctions();
				JSONObject json = userFunction.registerUser(name, email,phno,address,ipString, password);
				
				
				
				
				// check for login response
				try {
					if (json.getString(KEY_SUCCESS) != null) {
						registerErrorMsg.setText("");
						String res = json.getString(KEY_SUCCESS); 
						if(Integer.parseInt(res) == 1){
							// user successfully registered
							// Store user details in SQLite Database
							status="true";
							registerflag=1;
							DatabaseHandler db = new DatabaseHandler(getApplicationContext());
							JSONObject json_user = json.getJSONObject("user");
							
							
							
					         
					       
							
							
							//JSONObject json_user1 = json.getJSONObject("user1");
							// Clear all previous data in database
							userFunction.logoutUser(getApplicationContext());
							db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), /*json_user.getString(KEY_PHNO),json_user.getString(KEY_ADDR),*/json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));	
							//db.addUser1( email,status);						
							// Launch Dashboard Screen
							Intent dashboard = new Intent(getApplicationContext(), GmailVerify.class);
							// Close all views before launching Dashboard
							dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(dashboard);
							// Close Registration Screen
							finish();
						}
						else
						{
							// Error in registration
							registerErrorMsg.setText("Error occured in registration");
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				
				
				
				
				
			}
		});

	
	}
}