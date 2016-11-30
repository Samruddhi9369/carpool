package com.carpool;

import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.annotation.TargetApi;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ProfileFragmant extends Fragment
{
	Button button1,button2;
	TextView welname;
	LoginActivity l=new LoginActivity();
	public ProfileFragmant(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        
       welname= (TextView)rootView.findViewById(R.id.wel);
        
        welname.setText(l.username);
        
        
        return rootView;
    }
	
	
	  
	
}


