package com.carpool;

import android.annotation.TargetApi;


import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;

public class SearchFragment extends Fragment
{
	public SearchFragment(){}
	public static String dateString11;
	public static String typeString12;
	private int yr,mon,dy;
	private int hr;
	private int min;
	public String label;
	private TextView dispDate, dispTime;
	public int flag=0;
	
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		
		
		
		View rootView = inflater.inflate(R.layout.fragment_search, container, false);
		
		
		// Spinner element
        Spinner spinner = (Spinner)rootView.findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner)rootView.findViewById(R.id.spinnerplaces);
        
        Spinner spinnerto1 = (Spinner)rootView.findViewById(R.id.spinnerto);
        Spinner spinnerto21 = (Spinner)rootView.findViewById(R.id.spinnerplacesto);
        // Spinner click listener
       
        spinner.setOnItemSelectedListener((OnItemSelectedListener) this);
        spinner2.setOnItemSelectedListener((OnItemSelectedListener) this);
        
        spinnerto1.setOnItemSelectedListener((OnItemSelectedListener) this);
        spinnerto21.setOnItemSelectedListener((OnItemSelectedListener) this);
        
        
        TextView from=(TextView)rootView.findViewById(R.id.from);
        TextView fromplace=(TextView)rootView.findViewById(R.id.fromplace);
        // Spinner Drop down elements
        List<String> places = new ArrayList<String>();
        places.add("Abhinav Vidyalaya");
        places.add("Ahilyadevi High School");
        places.add("All Saints High School");
        places.add("Army Public School");
        places.add("Balshikshan");
        places.add("Bharat English School");
        places.add("Bharatiya Vidya Bhavan");
        places.add("Bhusari Colony");
        places.add("Balshikshan");
        places.add("The Bishop's School");
        places.add("City International School");
        places.add("Crescent High School");
        places.add("Delhi Public School");
        places.add("D.S.K. School");
        places.add("Gurukul School");
        places.add("Holy Angels Convent");
        places.add("Hutchings High School");
        places.add("Indus International School");
        places.add("Jnana Prabodhini Prashala");
        places.add("Jawahar Shikshan Mandal");
        places.add("Jog Educational Trust School");
        places.add("Dr.Kalmadi Shamrao High School");
        places.add("Kendriya Vidyalaya");
        places.add("Laxmanrao Apte Prashala");
        places.add("Loyola High School");
        places.add("Modern High School");
        places.add("Mount Carmel Public School");
        places.add("New English School");
        places.add("New India School");
		
        List<String> anyplaces = new ArrayList<String>();
 
        anyplaces.add("Aundh");
        anyplaces.add("Deccan Gymkhana");
        anyplaces.add("Dhankawadi");
        anyplaces.add("Gujrat Colony");
        anyplaces.add("Ideal Colony");
        anyplaces.add("Karvenagar");
        anyplaces.add("Kasba Peth");
        anyplaces.add("Katraj");
        anyplaces.add("Khadakwasala");
        anyplaces.add("Khadaki");
        anyplaces.add("Kothrud");
        anyplaces.add("Lohgaon");
        anyplaces.add("Mangalwar peth");
        anyplaces.add("Market Yard");
        anyplaces.add("Mayur Colony");
        anyplaces.add("Model Colony");
        anyplaces.add("Navasahyadri");
        anyplaces.add("Parvati");
        anyplaces.add("Pashan");
        anyplaces.add("Range Hill");
		anyplaces.add("Rasta peth");
        anyplaces.add("S.P. College Road");
        anyplaces.add("Shivaji Hsg Society");
        anyplaces.add("Shivajinagar");
        anyplaces.add("Swargate");
        anyplaces.add("Wadgaon Budruk");
        anyplaces.add("Wanawadi");
		
        List<String> listCombine = new ArrayList<String>();
        listCombine.addAll(places);
        listCombine.addAll(anyplaces);
        
        
        // Creating adapter for spinner from
    	   
        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_item,places);
    		// Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    		
    		// attaching data adapter to spinner
    		spinner.setAdapter(dataAdapter);
    		
    		
    		fromplace.setText(label);
    		
    		
		
    		  ArrayAdapter<String> dataAdapter1=new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_item,anyplaces);
      		// Drop down layout style - list view with radio button
          dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      		
      		// attaching data adapter to spinner
      		spinner2.setAdapter(dataAdapter1);
      	
      		
      	    // Creating adapter for spinner to
     	   
            ArrayAdapter<String> dataAdapter11=new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_item,places);
        		// Drop down layout style - list view with radio button
            dataAdapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		
        		// attaching data adapter to spinner
        		spinnerto1.setAdapter(dataAdapter11);
    		
        ArrayAdapter<String> dataAdapter12=new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_item,anyplaces);
          		// Drop down layout style - list view with radio button
              dataAdapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          		
          		// attaching data adapter to spinner
          		spinnerto21.setAdapter(dataAdapter12);
          		
          		
          		//code for Date radiogroup
          		int dateIndex;
          		RadioGroup rg;
          	    RadioButton rb;
          		rg=(RadioGroup) rootView.findViewById(R.id.radiogrp);
          		
          		dateIndex=rg.getCheckedRadioButtonId();
          		
          		rb=(RadioButton) rootView.findViewById(dateIndex);
          		if(rb.getText().toString().matches("DailyPool"))
          		{
          			dateString11="DailyPool";
          		}
        		else if(rb.getText().toString().matches("Today"))
        		{
        			dateString11="Today";
        		}
        		else if(rb.getText().toString().matches("Tomorrow"))
        		{
        			dateString11="Tomorrow";
        		}
        		else
        		{
        			dateString11="Other";
        			dispDate=(TextView)rootView.findViewById(R.id.textViewDate);
        			Button dateButton= (Button) rootView.findViewById(R.id.OtherDate);
        			final Calendar c= Calendar.getInstance();
        			yr=c.get(Calendar.YEAR);
        			mon=c.get(Calendar.MONTH);
        			dy=c.get(Calendar.DATE);
        			
        			dateButton.setOnClickListener(new OnClickListener() {
						
						public void onClick(View v) {
							// TODO Auto-generated method stub
							new DatePickerDialog(getActivity(), dateListener,yr, mon, dy).show();
						}
					});
        			
        			
        			
        		}

      			//seek bar code
          		
          	/*	SeekBar sb1=(SeekBar)rootView.findViewById(R.id.seekBar1);
          		
          		sb1.setMax(1);
          	    sb1.setMax(12);
          	    sb1.incrementProgressBy(1);
          	    
          	    
          	  sb1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

                 
                  public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                       if (progress < 3) {
                           progress = 3;
                           progress = progress + 2;
                       } else {
                           progress = progress + 2; // Add the minimum value (10)
                       }

                      TextView text = (TextView)findViewById(R.id.tvTaxitype);
                      text.setText(Integer.toString(progress));

                  }

                  @Override
                  public void onStartTrackingTouch(SeekBar seekBar) {}

                  @Override
                  public void onStopTrackingTouch(SeekBar seekBar) {}

              });
          	    
          		*/
          		
          		
          		dispTime=(TextView)rootView.findViewById(R.id.time);
          		Button timeButton=(Button)rootView.findViewById(R.id.setTime);
          		final Calendar c= Calendar.getInstance();
          		hr=c.get(Calendar.HOUR_OF_DAY);
          		min=c.get(Calendar.MINUTE);
          		
          		timeButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						new TimePickerDialog(getActivity(), timeListener, hr, min, true).show();
					}
				});
          		
          		
          	//code for AGE radiogroup
          		int typeIndex;
          		RadioGroup rg2;
          	    RadioButton rb2;
          		rg2=(RadioGroup) rootView.findViewById(R.id.radiogrp2);
          		
          		typeIndex=rg.getCheckedRadioButtonId();
          		
          		rb2=(RadioButton) rootView.findViewById(typeIndex);
          		if(rb2.getText().toString().matches("request"))
          		{
          			typeString12="Request";
          			flag=1;
          		}
        		else
        		{
        			typeString12="Offer";
        			flag=2;
        		}
        		

          		
          	//code for searchbt
          		/*Button search=(Button)rootView.findViewById(R.id.searchbt);
          		
          		search.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						if(flag==1)
						{
							RequestFragment rf = new RequestFragment();
						    FragmentManager fragmentManager = getFragmentManager();
						    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
						    fragmentTransaction.replace(android.R.id.content,rf);
						    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
						    fragmentTransaction.addToBackStack(null);
						    fragmentTransaction.commit();
							
							Intent myIntent = new Intent(getActivity(), Sample.class);
					        getActivity().startActivity(myIntent); 

							
							
						}
						if(flag==2)
						{
							OfferFragment of = new OfferFragment();
						    FragmentManager fragmentManager = getFragmentManager();
						    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
						    fragmentTransaction.add(R.id.offer, of, "offer");
						    //fragmentTransaction.replace(android.R.id.content,of);
						    fragmentTransaction.commit();
						}
						
					}
				});*/
          		
          		
       
          		
		class Search extends AsyncTask<String, String, String> 
		{

			@Override
			protected String doInBackground(String... params) 
			{
				// TODO Auto-generated method stub
				return null;
			}

		}
		
		
		
		return rootView;
	}//on create

	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		label = parent.getItemAtPosition(position).toString();
		// Showing selected spinner item
		
		Toast.makeText(parent.getContext(), "Selected: " + label, Toast.LENGTH_LONG).show();
		
		
		
	}



	

	private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			yr = selectedYear;
			mon = selectedMonth;
			dy = selectedDay;


			// set selected date into datepicker also
			view.init(yr, mon, dy, null);
			dispDate.setText("Enter Date: "+(mon+1)+"-"+dy+"-"+yr);

		}
	};
	
	
	private TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

		// when dialog box is closed, below method will be called.
		public void onTimeSet(TimePicker view1, int hour, int minute) {
			hr = hour;
			min = minute;

			dispTime.setText("Time: "+hr+":"+min);

		}
	};





}


