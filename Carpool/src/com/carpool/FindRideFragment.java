package com.carpool;

import android.app.Fragment;
import android.content.Intent;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FindRideFragment extends Fragment{
	
	public FindRideFragment(){}
	
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		View rootView = inflater.inflate(R.layout.fragment_findride, container, false);
	     
		Intent myIntent = new Intent(getActivity(), RequestActivity.class);
        getActivity().startActivity(myIntent); 
       
        return rootView;
    }

 
	

	

	
	
	




	
	
}
