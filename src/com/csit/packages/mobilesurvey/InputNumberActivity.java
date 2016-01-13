package com.csit.packages.mobilesurvey;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InputNumberActivity extends Activity {
	
	
	Button mButton;  
	EditText mEdit;
	TextView mText;
	Context context;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_number);
		
		context=getApplicationContext();
		mButton = (Button)findViewById(R.id.buttonNumberSubmit);
		mEdit = (EditText) findViewById(R.id.number_editText);
		mText = (TextView)findViewById(R.id.numberTextview);
		
		
	}

	
	
	public void onClickNumberSubmit (View view){    
		
		

		String input=mEdit.getText().toString();
		
		
		if (!input.equalsIgnoreCase("")){
    		
    		    		
    		
			  SharedPreferences.Editor putid = getSharedPreferences("deviceinfo", 0).edit();
    		  putid.putString("number", input);	
			  putid.commit(); 
				
			 
			  
			  
			  Intent intent0 = new Intent(this, DeviceInfoHttpService.class);
			  startService(intent0);
			  
			  
			  
			  
			  SharedPreferences getResults = getSharedPreferences("everResults", 0);
              
              if(getResults.getBoolean("askEver", true)==true){
       		
       		     Intent intent = new Intent(getApplicationContext(), EverActivity.class);
       		
       		     startActivity(intent);
       		
              }
              
              else if(getResults.getBoolean("askEverOther", true)==true){
          		
       		     Intent intent = new Intent(getApplicationContext(), EverOthersActivity.class);
       		
       		     startActivity(intent);
       		
            }
              
              else {
           	       Intent intent = new Intent(getApplicationContext(), WeeklyActivity.class);
          		
       		       startActivity(intent);
           	   
              }
			  
			  
			  
	    	//Intent intent = new Intent(this, UserInterfaceActivity.class);
	    	//startActivity(intent);
	  	   	finish();
			
			
		
			
							
		}
    	
    	else{
    		
    		Toast.makeText(getApplicationContext(), "Please enter your phone number in the input text box", Toast.LENGTH_LONG).show();
    		
    		 		
    		
    	       }
		
		
	
		
	  
		
	}
	
	
	@Override
	 public void onBackPressed () {
		//showToast();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode==KeyEvent.KEYCODE_HOME)
	    {
	    	//showToast();
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
