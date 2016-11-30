package  com.carpool;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.carpool.SessionManager;
import  com.carpool.library.DatabaseHandler;

import com.carpool.library.JSONParser;
import  com.carpool.library.UserFunctions;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class LoginActivity extends Activity 
{
	
	Button btnLogin,btnLinkToRegister;
	EditText inputEmail,inputPassword,e1;
	TextView loginErrorMsg;
	DatagramSocket s;
	private NotificationManager mNotificationManager;
	public static int notificationID = 100, numMessages = 0;
	int flag=1;
	String mess="",text="";
	JSONParser jsonParser = new JSONParser();
	public static String email11;
	//show in prof fragment
	public static String username="";
	//SessionManager session;
	
	public static final String MyPREFERENCES = "MyPrefs" ;
	public static final String name = "nameKey"; 
	public static final String pass = "passwordKey"; 
	SharedPreferences sharedpreferences;


	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_PHNO = "phno";
	private static String KEY_ADDR = "address";
	private static String KEY_CREATED_AT = "created_at";
	List<NameValuePair> params;
	
	private static String url_sock_client = "http://192.168.16.1/android_connect/SocketClient.php";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	
			setContentView(R.layout.login);
		
		
			params = new ArrayList<NameValuePair>();
			String seats="hello1";
			params.add(new BasicNameValuePair("seats", seats));
		
			// Importing all assets like buttons, text fields
			inputEmail = (EditText) findViewById(R.id.loginEmail);
			inputPassword = (EditText) findViewById(R.id.loginPassword);
			btnLogin = (Button) findViewById(R.id.btnLogin);
			btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
			loginErrorMsg = (TextView) findViewById(R.id.login_error);

			// Login button Click Event
			btnLogin.setOnClickListener(new View.OnClickListener() 
			{

				public void onClick(View view) 
				{
							
					String email = inputEmail.getText().toString();
					email11=email;
					String password = inputPassword.getText().toString();
					UserFunctions userFunction = new UserFunctions();
					Log.d("Button", "Login");
					JSONObject json = userFunction.loginUser(email, password);

					// check for login response
					try 
					{
						if (json.getString(KEY_SUCCESS) != null) 
						{
							loginErrorMsg.setText("");
							String res = json.getString(KEY_SUCCESS); 
							if(Integer.parseInt(res) == 1)
							{
								// user successfully logged in
								// Store user details in SQLite Database
								DatabaseHandler db = new DatabaseHandler(getApplicationContext());
								JSONObject json_user = json.getJSONObject("user");
							
								// Clear all previous data in database
								userFunction.logoutUser(getApplicationContext());
								db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));
								
								username=json_user.getString(KEY_NAME);
								
								
								Intent in = new Intent(getApplicationContext(),OfferActivity.class);
								// sending pid to next activity
								in.putExtra(KEY_EMAIL, email);
	
							
								Intent dashboard = new Intent(getApplicationContext(), MainActivity.class);
							
								// Close all views before launching Dashboard
								dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(dashboard);
						
								// Close Login Screen
								finish();
							}
							else
							{
								// Error in login
								loginErrorMsg.setText("Incorrect username/password");
							}
						}//if
					
					}//try
					catch (JSONException e) 
					{
						e.printStackTrace();
					}
					finally
					{
						new hello().execute();
						System.out.println("Foxpur");
					}
				}//onclick
			});

			// Link to Register Screen
			btnLinkToRegister.setOnClickListener(new View.OnClickListener() 
			{

				public void onClick(View view) 
				{
					Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
					startActivity(i);
					finish();
				}
		
			});
			
		
	}//oncreate
	
		@Override
		protected void onResume()
		{
	      sharedpreferences=getSharedPreferences(MyPREFERENCES, 
	      Context.MODE_PRIVATE);
	      if (sharedpreferences.contains(name))
	      {
	    	  if(sharedpreferences.contains(pass))
	    	  {
	    		  Intent i = new Intent(this,MainActivity.class);
	    		  startActivity(i);
	    	  }
	      }
	      super.onResume();
	   }

		public void login(View view)
		{
	      Editor editor = sharedpreferences.edit();
	      String u = inputEmail.getText().toString();
	      String p = inputPassword.getText().toString();
	      editor.putString(name, u);
	      editor.putString(pass, p);
	      editor.commit();
	      Intent i = new Intent(this,MainActivity.class);
	      startActivity(i);
	   }

	
		class hello extends AsyncTask<String, String, String> 
		{

			@Override
			protected String doInBackground(String... arg0) 
			{
			// TODO Auto-generated method stub
				try
				{
					byte[] message = new byte[8000]; 
					while(true)
					{ 
						s = new DatagramSocket(5050);
						DatagramPacket p = new DatagramPacket(message, message.length);		
						System.out.println("here.....");
		            
						s.receive(p);
			  
						System.out.println("here");						
				    	text = new String(message, 0, p.getLength());	
				    	System.out.println("Udp tutorial message jiggy:" + text);
				    	updateNotification();
		          
		            
					}
				}
				catch(Exception e)
				{
		    	 System.out.println(e);
				}
				return null;
			}
	
		}//helloend
	
	protected void updateNotification() 
	{
	      Log.i("Update", "notification");

	      /* Invoking the default notification service */
	      NotificationCompat.Builder  mBuilder = 
	      new NotificationCompat.Builder(this);	

	      mBuilder.setContentTitle("New offer matched");
	      mBuilder.setContentText("Click to view details");
	      mBuilder.setTicker(text);
	      mBuilder.setSmallIcon(R.drawable.ic_launcher);

	     /* Increase notification number every time a new notification arrives */
	      mBuilder.setNumber(++numMessages);
	      
	      /* Creates an explicit intent for an Activity in your app */
	     // Intent resultIntent = new Intent(this, NotificationView.class);
	      
	      Intent resultIntent = new Intent(this, NotificationView.class);
	      
	      TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	      stackBuilder.addParentStack(NotificationView.class);

	      /* Adds the Intent that starts the Activity to the top of the stack */
	      stackBuilder.addNextIntent(resultIntent);
	      PendingIntent resultPendingIntent =
	         stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

	      mBuilder.setContentIntent(resultPendingIntent);

	      mNotificationManager =
	      (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

	      /* Update the existing notification using same notification ID */
	      mNotificationManager.notify(notificationID, mBuilder.build());
	      //startActivity(resultIntent);
	   }

	   protected void cancelNotification() 
	   {
		      Log.i("Cancel", "notification");
		      mNotificationManager.cancel(notificationID);
	   }
	   
	   
	   public void onBackPressed() 
		{
			super.onBackPressed();
			moveTaskToBack(true);
			finish();
		}
	

	
}
