package com.carpool;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LocationFragment extends Fragment {
	
	public LocationFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        Intent myIntent = new Intent(getActivity(), MapActivity.class);
        getActivity().startActivity(myIntent); 
		
        return rootView;
    }
}
