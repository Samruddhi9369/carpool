package com.carpool;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.annotation.TargetApi;

import android.os.Build;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class OfferFragment extends Fragment {
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_offer, container, false);
        
          
        Intent myIntent = new Intent(getActivity(), OfferActivity.class);
        getActivity().startActivity(myIntent); 
        return rootView;
    }
}

