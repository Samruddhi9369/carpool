
package com.carpool;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.carpool.library.JSONParser;
import com.carpool.library.UserFunctions;

public class DashboardActivity extends Activity {
	UserFunctions userFunctions;
	Button btnLogout;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /**
         * Dashboard Screen for the application
         * */        
        // Check login status in database
        userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())){
        	setContentView(R.layout.fragment_logout);
        	btnLogout = (Button) findViewById(R.id.button1);
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
			String seats="hello1";
			params.add(new BasicNameValuePair("seats", seats));
        	
			
        	btnLogout.setOnClickListener(new View.OnClickListener() {
    			
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				userFunctions.logoutUser(getApplicationContext());
    				Intent login = new Intent(getApplicationContext(), LoginActivity.class);
    	        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	        	startActivity(login);
    	        	// Closing dashboard screen
    	        	finish();
    			}
    		});
        	
        }else{
        	// user is not logged in show login screen
        	Intent login = new Intent(getApplicationContext(), LoginActivity.class);
        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(login);
        	// Closing dashboard screen
        	finish();
        }
        
        
        
        
    }
    
    public void logout(View view){
        SharedPreferences sharedpreferences = getSharedPreferences
        (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        moveTaskToBack(true); 
        DashboardActivity.this.finish();
     }
     public void exit(View view){
        moveTaskToBack(true); 
        DashboardActivity.this.finish();
     }

}