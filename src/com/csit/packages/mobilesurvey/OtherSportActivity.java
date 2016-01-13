package com.csit.packages.mobilesurvey;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OtherSportActivity extends Activity {

	
	Button mButton;  
	EditText mEdit;  
	TextView mText;
	String times_for_other = "none";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input_text_submit);
		
		
		  Intent intent=getIntent();
	      times_for_other=intent.getStringExtra("times_for_other");
		  
		
		
		    mButton = (Button)findViewById(R.id.buttonOtherInput);
		    mEdit = (EditText) this.findViewById(R.id.editTextPlainTextInput);
			//final EditText  mEdit = (EditText) this.findViewById(R.id.editTextPlainTextInput);
			 mText = (TextView)findViewById(R.id.otherInputTextview);
	         mText.setText("Please type in the non listed sport activity done " + times_for_other+ " times in the last week");
			
			 
	         
			
			
			mButton.setOnClickListener(new View.OnClickListener() {
			            public void onClick(View view) {
			           
			            	String input=mEdit.getText().toString();
				               
			            	if (!input.equalsIgnoreCase("")){
			                	
			                	
			            		
			            		SharedPreferences.Editor non_listed_activities = getSharedPreferences("nonListedActivities", 0).edit();
								non_listed_activities.putString("otherSportsName", input);
								non_listed_activities.commit();
			            		
			            		
			                	
			                	
			                	SharedPreferences getResults=getSharedPreferences("sportsClubResults", 0);
				            	
				            	if(getResults.getBoolean("anyClubs", true)){
				            	
				            	     Intent intent = new Intent(getApplicationContext(), ClubsListActivity.class);   
				            	     startActivity(intent);
				            	}
				            	
				            	else if(getResults.getBoolean("anyNonClubs", true)){
				            		 Intent intent = new Intent(getApplicationContext(), NonClubsListActivity.class);   
				            	     startActivity(intent);
				            	}
				            	else{  //upload results
				            		
				            		 Intent intent2 = new Intent(getApplicationContext(), ThursdayQHttpService.class);
				         				
			            		     startService(intent2);
			            		     
			            		     
			            		     
			            		     //checking user selection mode shared preferences
			   				         Boolean adminMode=false;
			   				   		
			   				   		SharedPreferences getUserMode = getSharedPreferences("userMode", 0);
			   				   		
			   				   		adminMode=getUserMode.getBoolean("Admin", false);
			   				   		
			   				   		if(adminMode){
			   				   			//send intent to weekly sports and club activities
			   				   			
			   				   		 //    ResetResults rr = new ResetResults();
			   						//    rr.resetWeeklySurveys(getApplicationContext());
			   					    
			   					             //  Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
			   						
			   						          //  startActivity(intent3);
			   						            
			   	//Start camera activity=============================================================
					   	    				   		
						   	    				  // 	Intent camintent = new Intent(getApplicationContext(), CameraActivity.class);
						   	    			   		
						   	    			       // startActivity(camintent); 
			   				   		Toast.makeText(getApplicationContext(), "Thank you for taking part. Remember you can upload a photo as part of the mobiQ study.", Toast.LENGTH_LONG).show();
			   				   			
			   				   		          } //end if admin mode
			   				   		
			            		     
			   				   		else  {
			   				   		Toast.makeText(getApplicationContext(), "Thank you for taking part. Remember you can upload a photo as part of the mobiQ study.", Toast.LENGTH_LONG).show();	
			   				   		//====================================================================================================
		   	    				   		
			   	    				   	//Start camera activity========================================
			   	    				   		
			   	    				      //	Intent camintent = new Intent(getApplicationContext(), CameraActivity.class);
			   	    			   		
			   	    			          //  startActivity(camintent); 
			   	    			       
			   	    			  //===========================================================================
			   				   	  	                   }//end else if admin mode
			   				   		
			            		     
			            		     
				            		
				            	}
			                	
			                	
			                	
			                	
				            	
			                	
			                	finish();
			                }
			                else {
			                	
			                	 Toast.makeText(getApplicationContext(), "Please enter the activity in the text box", Toast.LENGTH_LONG).show();
			                }
			               
			            
			            }
			});
	
			
			
			
			

		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.other_sport, menu);
		return true;
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
