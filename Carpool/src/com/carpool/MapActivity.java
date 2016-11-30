package com.carpool;


import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
//import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.Polyline;
import android.graphics.Color;



public class MapActivity extends Activity {

	// Google Map
	private GoogleMap googleMap;
	double latitudek =18.551839;
    double longitudek =73.951441;
    double latitude = 17.385044;
	double longitude = 78.486671;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_location);

		try {
			// Loading map
			initilizeMap();
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			googleMap.setMyLocationEnabled(true);
			googleMap.getUiSettings().setZoomControlsEnabled(true);
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);
			googleMap.getUiSettings().setCompassEnabled(true);
			googleMap.getUiSettings().setRotateGesturesEnabled(true);
			googleMap.getUiSettings().setZoomGesturesEnabled(true);

			
			MarkerOptions marker = new MarkerOptions()
        	.position(new LatLng(latitude, longitude))
        	.title("Symantec Corporation ")
        	.snippet("Address: RMZ ICON, S. No. 3/8,Baner, Pune, Maharashtra ");

			MarkerOptions marker1 = new MarkerOptions()
        	.position(new LatLng(latitudek, longitudek))
        	.title("Symantec Corporation ")
        	.snippet("Address: EON Free Zone, Kharadi, Pune, Maharashtra"); 

			
			
				
				
					CameraPosition cameraPosition = new CameraPosition.Builder()
							.target(new LatLng(18.554579,
									73.797371)).zoom(35).build();

					googleMap.animateCamera(CameraUpdateFactory
							.newCameraPosition(cameraPosition));
					
				
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		}

	


	/**
	 * function to load map If map is not created it will create it for you
	 * */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	
	    // Fetches data from url passed
	    @Override
protected void onResume() {
	super.onResume();
	initilizeMap();
}
}