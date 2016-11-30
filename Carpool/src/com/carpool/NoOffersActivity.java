package com.carpool;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NoOffersActivity extends Activity
{
	Button btok;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oops);
		btok = (Button) findViewById(R.id.btok);
		
		btok.setOnClickListener(new View.OnClickListener() 
        {

        @Override
        public void onClick(View view) 
        {
        		// creating new product in background thread
        	Intent i1 = new Intent(NoOffersActivity.this, MainActivity.class);
		    startActivity(i1);
        }
        });
		
		
	}
	
	public void onBackPressed() 
	{
		//super.onBackPressed();
		Intent i1 = new Intent(NoOffersActivity.this, MainActivity.class);
	    startActivity(i1);
		//finish();
	}
	
}
