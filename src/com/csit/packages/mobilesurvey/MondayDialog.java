package com.csit.packages.mobilesurvey;




import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MondayDialog extends Activity {
	
	
	int duration=10;  //10 mins
	boolean proceed=false;
	boolean postpone = false;
	 
	 
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.monday_dialog);
		
		WindowManager.LayoutParams params = getWindow().getAttributes();  
	     //  params.height =  ViewGroup.LayoutParams.WRAP_CONTENT;  
	    params.height =  ViewGroup.LayoutParams.MATCH_PARENT;  
	    params.width =  ViewGroup.LayoutParams.MATCH_PARENT;  
	    this.getWindow().setAttributes(params); 
	    
	     TextView text = (TextView)findViewById(R.id.text);
		 text.setVisibility(View.VISIBLE);
	
		  text.setText("Your weekly survey is about to start");
			
			

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// set the title of the Alert Dialog

            alertDialogBuilder.setTitle("Mobi-Q Survey");

            alertDialogBuilder.setMessage("Starting weekly quick survey");
			alertDialogBuilder.setCancelable(false)
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
						
				    startSurvey();	
				    MondayDialog.this.finish();
			    	dialog.cancel();	
			    	
			    }
			  })
			
			  /*
			  .setNegativeButton("Remind me later",
			   new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				
			    	
			    	//To do postpone section
			    	//postpone();
			    	
			    	//MondayDialog.this.finish();
			    	//dialog.cancel();
			    }
			  })
			  
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
		
				proceed=true;
				
				ResetResults rr = new ResetResults();
				rr.resetWeeklySurveys(getApplicationContext());
			
				//============================================================================
				
				//check for operation mode and make sure it is not admin
					
				//checking user selection mode shared preferences
			         Boolean adminMode=false;
			   		
			   		SharedPreferences getUserMode = getSharedPreferences("userMode", 0);
			   		
			   		adminMode=getUserMode.getBoolean("Admin", false);
			   		
			   		if(adminMode){
			   			    
					   //reset admin mode back to false if it is set to true
					     	    						  	
					 // 	SharedPreferences.Editor userMode = getSharedPreferences("userMode", 0).edit();
					  //	userMode.putBoolean("Admin", false);
					  //	userMode.commit();   					  	
					              		   	    						         
			   			   			
			   		          } //end if admin mode
				
				
			    
			    //============================================================================
			    
			
			    
			    SharedPreferences getResults = getSharedPreferences("everResults", 0);
			       
			       if(getResults.getBoolean("askEver", true)==true){
					
					     Intent intent = new Intent(this, EverActivity.class);
					
					     startActivity(intent);
					
			       }
			       
			       else if(getResults.getBoolean("askEverOther", true)==true){
			   		
					     Intent intent = new Intent(this, EverOthersActivity.class);
					
					     startActivity(intent);
					
			     }
			       
			       else {
			    	       Intent intent = new Intent(this, WeeklyActivity.class);
			   		
					       startActivity(intent);
			    	   
			       }
			    
			    
			    
			    
		
	}
	
	
	
	
	
	
	public void postpone(){
		
		
//checking user selection mode shared preferences
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
		
		
		// if((postpone || proceed)){
			
			
	//	}
		// else{
			 
			// quickRestart();
		// }
		
		//check if the survey has been taken if not schedule and postpone again
		
			super.onStop();
	}
	
	
	
	
	
	public void quickRestart(){
		//proceed=true;
		Intent intent = new Intent(this, MondayDialog.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}
	
}
