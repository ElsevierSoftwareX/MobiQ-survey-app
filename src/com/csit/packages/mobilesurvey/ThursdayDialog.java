package com.csit.packages.mobilesurvey;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class ThursdayDialog extends Activity {
	
	
	boolean postpone=false;
	boolean proceed = false;
	
	 
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.thursday_dialog);
		
		WindowManager.LayoutParams params = getWindow().getAttributes();  
	 //params.height =  ViewGroup.LayoutParams.WRAP_CONTENT;  
	       params.height =  ViewGroup.LayoutParams.MATCH_PARENT;   
	       params.width =  ViewGroup.LayoutParams.MATCH_PARENT;  
	    this.getWindow().setAttributes(params); 
	    
	     	       
	       
            TextView text = (TextView)findViewById(R.id.text);
		
			text.setText("Your Thursday survey is about to start");
			
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);



			// set the title of the Alert Dialog

            alertDialogBuilder.setTitle("Mobi-Q Thursday Survey");

            alertDialogBuilder.setMessage("Starting thursday quick survey");
			alertDialogBuilder.setCancelable(false)
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				// To do
				
			    	 startSurvey();	 
			    	
			    		ThursdayDialog.this.finish();
				    	dialog.cancel();
			    	
			    }
			  })
			
			  
			  /*
			  .setNegativeButton("Remind me later",
			//.setNegativeButton("No!!! Get me out of here!",
			 
		
			new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				
			    	
			   //To do postpone section
			    	//postpone();
			    	
			    	//ThursdayDialog.this.finish();
			    	//dialog.cancel();
			    }			   
			    
			  }
			
					
					)
				*/	
					;

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
			
			
		
	}
	
	
	
	@Override
	public void onStart(){
		super.onStart();
		
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
	
	
	
	
public void startSurvey(){
		

	//Context context = getApplicationContext();
	
	
	proceed =true;
	
	
	
		 
	ResetResults rr = new ResetResults();
	rr.resetWeeklySurveys(getApplicationContext());
	
	
	//check for operation mode and make sure it is not admin
	
		//checking user selection mode shared preferences
		         Boolean adminMode=false;
		   		
		   		SharedPreferences getUserMode = getSharedPreferences("userMode", 0);
		   		
		   		adminMode=getUserMode.getBoolean("Admin", false);
		   		
		   		if(adminMode){
		   			    
				   //reset admin mode back to false if it is set to true
				    	    						  	
				  	SharedPreferences.Editor userMode = getSharedPreferences("userMode", 0).edit();
				  	userMode.putBoolean("Admin", false);
				  	userMode.commit();   					  	
				              		   	    						         
		   			   			
		   		          } //end if admin mode
			
			
				
    
    Intent intent = new Intent(this, WeeklySportsClubActivity.class);
	
	startActivity(intent);
	
	finish();
	
			    
			    
		
	}
	
	
	
	
	
	
	public void postpone(){
		

        Boolean adminMode=false;
  		
  		SharedPreferences getUserMode = getSharedPreferences("userMode", 0);
  		
  		adminMode=getUserMode.getBoolean("Admin", false);
  		
  		if(adminMode){
  			    
		   //reset admin mode back to false if it is set to true
		     	    						  	
		  	//SharedPreferences.Editor userMode = getSharedPreferences("userMode", 0).edit();
		  //	userMode.putBoolean("Admin", false);
		 // 	userMode.commit();   					  	
		              	
  			
  		     Intent intent = new Intent(this, MainActivity.class);
		     startActivity(intent);
		     
  			   			
  		          } //end if admin mode
  		
  	
	
   
   //===============================================================================================
		
			
					
	}
	
	
	
	
	
	
	
	
	
	@Override
	public void onStop(){
		
		
		//check if the survey has been taken if not schedule and postpone again
		
			super.onStop();
	}
	
	
	
	
	
	
	public void quickRestart(){
		//proceed=true;
		Intent intent = new Intent(this, ThursdayDialog.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}
	
	
	
}
