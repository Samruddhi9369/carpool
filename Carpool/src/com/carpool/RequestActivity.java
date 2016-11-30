package com.carpool;

import com.carpool.library.*;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;


import android.app.TimePickerDialog;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class RequestActivity extends Activity implements OnItemSelectedListener
{

	// Progress Dialog

	private AutoCompleteTextView autoComplete, autoComplete1;
	public static String det="";	
	private ProgressDialog pDialog;
	public static String vardest;
	public static int gid=10;
	JSONParser jsonParser = new JSONParser();
	public static String dateString11;
	public static String typeString12;
	public static String seats1;
	private int yr,mon,dy;
	private int hr;
	private int min;
	public String label1,label2,label3,label4;
	private TextView dispDate, dispTime;
	public int flag=0;
	private String email;
	private ArrayAdapter<String> adapter,adapter1;
	public String source1,dest1;
	TextView inputSource;
	TextView inputDest;
	//TextView inputDate;
	//TextView inputTime;
	public String inputTime,inputDate;
	//TextView inputSeats;
	public Spinner spinnerseat,spinnerto,spinnerfrom;
	LoginActivity l=new LoginActivity();
	
	// url to create new product
	private static String url_create_request = "http://192.168.16.1/android_connect/create_request.php";

	// JSON Node names
	private static final String TAG_details = "details";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_EMAIL = "email";
	


	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_findride);
		spinnerseat = (Spinner) findViewById(R.id.spinnerseats);
		spinnerfrom = (Spinner) findViewById(R.id.spinnerfrom);
		spinnerto = (Spinner) findViewById(R.id.spinnerto);
	        
	        
	     // Spinner element
	        
	        
	        // Spinner click listener
	        spinnerfrom.setOnItemSelectedListener(this);
	        
	        // Spinner Drop down elements
	        List<String> fromplace = new ArrayList<String>();
	        fromplace.add("Abhinav Vidyalaya");
	        fromplace.add("Ahilyadevi High School");
	        fromplace.add("All Saints High School");
	        fromplace.add("Aundh");
	        fromplace.add("Balshikshan");
	        fromplace.add("Bharat English School");
	        fromplace.add("Bharatiya Vidya Bhavan");
	        fromplace.add("City International School");
	        fromplace.add("Crescent High School");
	        fromplace.add("Delhi Public School");
	        fromplace.add("Deccan Gymkhana");
	        fromplace.add("Dhankawadi");
	        fromplace.add("Dr.Kalmadi Shamrao High School");
	        fromplace.add("D.S.K. School");
	        fromplace.add("Gujrat Colony");
	        fromplace.add("Gurukul School");
	        fromplace.add("Holy Angels Convent School");
	        fromplace.add("Hutchings High School");
	        fromplace.add("Ideal Colony");
	        fromplace.add("Indus International School");
	        fromplace.add("Jog Educational Trust School");
	        fromplace.add("Jnana Prabodhini Prashala");
	        fromplace.add("Karvenagar");
	        fromplace.add("Kasba Peth");
	        fromplace.add("Katraj");
	        fromplace.add("Kendriya Vidyalaya");
	        fromplace.add("Khadaki");
	        fromplace.add("Khadakwasala");
	        fromplace.add("Kothrud");
	        fromplace.add("Laxmanrao Apte Prashala");
	        fromplace.add("Lohgaon");
	        fromplace.add("Loyola High School");
	        fromplace.add("Malwadi");
	        fromplace.add("Mangalwar peth");
	        fromplace.add("Market Yard");
	        fromplace.add("Mayur Colony");
	        fromplace.add("Model Colony");
	        fromplace.add("Modern High School");
	        fromplace.add("Mount Carmel Public School");
	        fromplace.add("Navasahyadri");
	        fromplace.add("New English School ");
	        fromplace.add("New India School");
	        fromplace.add("Parvati");
	        fromplace.add("Pashan");
	        fromplace.add("Range Hill");
	        fromplace.add("Rasta peth");
	        fromplace.add("S.P. College Road");
	        fromplace.add("Shivaji Hsg Society");
	        fromplace.add("Shivajinagar");
	        fromplace.add("Sinhagad Springdale School");
	        fromplace.add("Swargate");
	        fromplace.add("The Bishop's School");
	        fromplace.add("Wadgaon Budruk");
	        fromplace.add("Wanawadi");
	        fromplace.add("Warje");
	        fromplace.add("Yerawada");
	   
	        
	        // Creating adapter for spinner
	     	ArrayAdapter<String> dataAdapterfrom = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fromplace);
	     		
	     	// Drop down layout style - list view with radio button
	     	dataAdapterfrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	     		
	     	// attaching data adapter to spinner
	    	spinnerfrom.setAdapter(dataAdapterfrom);
	    	
	    	
	    	
	    	
	    	// Spinnerto click listener
	    	spinnerto.setOnItemSelectedListener(this);
	    	// Creating adapter for spinner
	     	ArrayAdapter<String> dataAdapterto = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fromplace);
	     		
	     	// Drop down layout style - list view with radio button
	     	dataAdapterto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	     		
	     	// attaching data adapter to spinner
	    	spinnerto.setAdapter(dataAdapterto);
	    	
	    	// get the defined string-array
		
		dispDate=(TextView)findViewById(R.id.textViewDate);
		dispTime= (TextView)findViewById(R.id.time);
		
		
		
         
          	//code for Date picker
          		
		Button dateButton= (Button) findViewById(R.id.datebt);
        final Calendar c= Calendar.getInstance();
     	yr=c.get(Calendar.YEAR);
        mon=c.get(Calendar.MONTH);
        dy=c.get(Calendar.DATE);
        			
        dateButton.setOnClickListener(new OnClickListener() 
        {
						
        	public void onClick(View v) 
        	{
			// TODO Auto-generated method stub
							
        		new DatePickerDialog(RequestActivity.this, dateListener,yr, mon, dy).show();
			}
		});
              		
        Button timeButton=(Button)findViewById(R.id.timebt);
        final Calendar c1= Calendar.getInstance();
        hr=c1.get(Calendar.HOUR_OF_DAY);
        min=c1.get(Calendar.MINUTE);
        timeButton.setOnClickListener(new OnClickListener() 
        {
    		@Override
    		public void onClick(View v) {
    		
    		// TODO Auto-generated method stub
    			new TimePickerDialog(RequestActivity.this, timeListener, hr, min, true).show();
    		}
    	});
        
        
        
     // Spinner element
        
        
        // Spinner click listener
        spinnerseat.setOnItemSelectedListener(this);
        
        // Spinner Drop down elements
        List<String> seatno = new ArrayList<String>();
        seatno.add("1");
        seatno.add("2");
        seatno.add("3");
        seatno.add("4");
        seatno.add("5");
        seatno.add("6");
        seatno.add("7");
        seatno.add("8");
        
        // Creating adapter for spinner
     	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, seatno);
     		
     	// Drop down layout style - list view with radio button
     	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     		
     	// attaching data adapter to spinner
    	spinnerseat.setAdapter(dataAdapter);

        
       // seats1=spinnerseat.toString();
         		
         Button request = (Button) findViewById(R.id.searchbt);
         // button click event
         request.setOnClickListener(new View.OnClickListener() 
         {

         @Override
         public void onClick(View view) 
         {
         		// creating new product in background thread
         		new CreateRequest().execute();
         		//displayNotification();
         }
         });
              
          		

	}//OnCreate

	private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() 
	{

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) 
		{
			yr = selectedYear;
			mon = selectedMonth;
			dy = selectedDay;


			// set selected date into datepicker also
			view.init(yr, mon, dy, null);
			dispDate.setText("Enter Date: "+(mon+1)+"-"+dy+"-"+yr);
			inputDate=(mon+1)+"-"+dy+"-"+yr;

		}
	};
	
	
	private TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() 
	{

		// when dialog box is closed, below method will be called.
		public void onTimeSet(TimePicker view1, int hour, int minute) 
		{
			hr = hour;
			min = minute;

			dispTime.setText("Time: "+hr+":"+min);
			inputTime=hr+":"+min;

		}
	};
	/**
	 * Background Async Task to Create new offer
	 * */
	class CreateRequest extends AsyncTask<String, String, String> 
	{

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			pDialog = new ProgressDialog(RequestActivity.this);
			pDialog.setMessage("Creating Request..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating request
		 * */
		protected String doInBackground(String... args) 
		{
			String source = spinnerfrom.getSelectedItem().toString();
			String dest = spinnerto.getSelectedItem().toString();
			String date = inputDate;

			String email1 = LoginActivity.email11;
            String time = inputTime;
			String seats = spinnerseat.getSelectedItem().toString();

			
			//email
			det= "Details of the requester\n\n"+"Source:"+source+"\n"+"Destination:"+dest+"\n"+"Date:"+date+"\n"+"Email:"+email1+"\n"+"Time:"+time+"\n"+"Number of seats:"+seats;
		    //det=details;
			            

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("source", source));
			params.add(new BasicNameValuePair("dest", dest));
			params.add(new BasicNameValuePair("date", date));
			params.add(new BasicNameValuePair("email", email1));
            params.add(new BasicNameValuePair("time", time));
			params.add(new BasicNameValuePair("seats", seats));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_request,"POST", params);
			
			// check log cat from response
			Log.d("Create Response", json.toString());

			// check for success tag
			try 
			{
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1)
				{
					
					
					//JSONObject json1 = jsonParser.makeHttpRequest(url_sock_client, "GET", params);
					
					// successfully created product
					Intent i1 = new Intent(getApplicationContext(), ViewOffersActivity.class);
					startActivity(i1);					
					// closing this screen
					finish();
				}
				else
				{
					// failed to create product
					Intent i2 = new Intent(RequestActivity.this, NoOffersActivity.class);
					startActivity(i2);
					finish();
					
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
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// On selecting a spinner item
		String item = parent.getItemAtPosition(position).toString();
		
		// Showing selected spinner item
		//Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onBackPressed() 
	{
		//super.onBackPressed();
		Intent i1 = new Intent(RequestActivity.this, MainActivity.class);
	    startActivity(i1);
		//finish();
	}

	
}

