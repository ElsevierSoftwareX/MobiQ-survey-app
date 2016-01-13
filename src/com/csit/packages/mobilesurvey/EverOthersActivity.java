package com.csit.packages.mobilesurvey;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class EverOthersActivity extends Activity {
	
	
	
	
	public String drugsQuestions[] = {
			                         "Ecstacy",
			                         "Speed",
			                         "Mephedrone",
			                         "Legal ecstacy or other 'upper' pills",
	                                 "Legal smoking mixtures"};
	
	
	Boolean everEcstacy=false;
	Boolean everSpeed=false;
	Boolean everMeph=false;
	Boolean everPills=false;
	Boolean everMix=false;
	
	static Boolean AllOthersfalse=true;
	
	
	
	TextView  main_question;
	TextView questionsTextView[];
	RadioButton radioButtons[][];
	RadioGroup radioGroups[];
	Boolean Allanswered=false;
	Boolean selected[];
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ever_main);
		
		
		Allanswered=false;
		AllOthersfalse=true;
		
		
		
		main_question = new TextView(this);
		//question.setText(R.string.hello_world);
		main_question.setText("Have you ever used?");
		main_question.setTextSize(25);
		main_question.setId(100);
		main_question.setLayoutParams(new LayoutParams (LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		
			
		 LinearLayout linearLayout = (LinearLayout) findViewById (R.id.linear_layout);
			
	     linearLayout.setBackgroundColor(Color.TRANSPARENT);
	     
	     linearLayout.addView(main_question);
	    
	
			
			questionsTextView = new TextView  [drugsQuestions.length];
			radioButtons = new RadioButton [drugsQuestions.length][2];
			radioGroups = new RadioGroup [drugsQuestions.length];
			selected = new Boolean [drugsQuestions.length];
			
			
			
			
			
			SharedPreferences getResults = getSharedPreferences("everResults", 0);
			everEcstacy=getResults.getBoolean("everEcstacy", false);
			everSpeed=getResults.getBoolean("everSpeed", false);
			everMeph=getResults.getBoolean("everMeph", false);
			everPills=getResults.getBoolean("everPills", false);
			everMix=getResults.getBoolean("everMix", false);
			
			
			
		
			if (everEcstacy){
				selected[0]=false;
			}
			else {selected[0]=true; }
			
			if (everSpeed){
				selected[1]=false;
			}
			else {selected[1]=true; }
			
			if (everMeph){
				selected[2]=false;
			}
			else {selected[2]=true; }
			
			if (everPills){
				selected[3]=false;
			}
			else {selected[3]=true; }
			
			if (everMix){
				selected[4]=false;
			}
			else {selected[4]=true; }
			
			
			
			      for (int i=0; i<drugsQuestions.length; i++){
				
			    	  
			    	      questionsTextView[i] = new TextView(this);
			    	
				          questionsTextView[i].setText(drugsQuestions[i]) ;
				          questionsTextView[i].setTextSize(15);
				         
				          questionsTextView[i].setId(i+27);                    //check uniqueness of this ID
				          
				          questionsTextView[i].setLayoutParams(new LayoutParams (LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				
			      }
			
			
						      
			      for (int i=0; i<drugsQuestions.length; i++){
						
			    	  
			    	  radioButtons[i][0] = new RadioButton(this);
			    	  radioButtons[i][1]= new RadioButton(this);
			    	  radioButtons[i][0].setText("yes");
			    	  radioButtons[i][1].setText("no"); 
			    	 // radioButtons[i][1].setChecked(true);
			  
		       }
			      
			      
			      
			
			      for (int i=0; i<drugsQuestions.length; i++){
						
			    	  radioGroups[i]=new RadioGroup(this);
			    	  radioGroups[i].setOrientation(RadioGroup.HORIZONTAL) ;
			          radioGroups[i].addView(radioButtons[i][0]);
			    	  radioGroups[i].addView(radioButtons[i][1]);
			    	  
			    	  
		      }
		
		   
			      for (int i=0; i<drugsQuestions.length; i++){
			    	  
			    	if  (selected[i]){
						
			    	  linearLayout.addView(questionsTextView[i]);
			    	  linearLayout.addView(radioGroups[i]);
			    	
			            }
			    
		           } // end for
			
	
	}
	  




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ever, menu);
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
	
	
public void onClickSubmit (View view){
		
	
	  //check
	   Allanswered=true;
	
	  for (int i=0; i<drugsQuestions.length; i++){
		  
		  if (selected[i]==true){
			          if (!(radioButtons[i][0].isChecked() || radioButtons[i][1].isChecked())) {       // no selection?
	    	                Allanswered=false;
	    		            }
		  }
			          
		 	 
     
          }// end for
        
        
    
	  
	  
	  
	  
	       if (Allanswered==false){
	  	    	   
	    	      Toast.makeText(getApplicationContext(), "Please answer all the questions", Toast.LENGTH_LONG).show();
	  	  
	      }
	  
	        
	       
	       
	            else if (Allanswered==true){
	            	
	           	
	            	
	            	
	                
	            	//==================================================================  
	            	  
	            	  
	            	 if (radioButtons[0][0].isChecked()){
	              	  	everEcstacy=true;
	              	  	AllOthersfalse=false;
	              	  SharedPreferences.Editor results = getSharedPreferences("everResults", 0).edit();
	            		results.putBoolean("everEcstacy", everEcstacy);
	            		
	            		results.putBoolean("allOthersFalse", AllOthersfalse);
	            		
	            		results.commit();
	              	
	              	  	
	              	  	
	                	}
	                if (radioButtons[1][0].isChecked()){
	              	  everSpeed=true;
	              	AllOthersfalse=false;
	              	
	              	SharedPreferences.Editor results = getSharedPreferences("everResults", 0).edit();
	            	results.putBoolean("everSpeed",everSpeed);
	            	
	            	results.putBoolean("allOthersFalse", AllOthersfalse);
	            	
	            	results.commit();
	            	
	                }
	                if (radioButtons[2][0].isChecked()){
	              	  everMeph=true;
	              	AllOthersfalse=false;
	              	
	              	SharedPreferences.Editor results = getSharedPreferences("everResults", 0).edit();
	            	results.putBoolean("everMeph",everMeph);
	            	results.putBoolean("allOthersFalse", AllOthersfalse);
	            	
	            	results.commit();
	            	
	                }
	                if (radioButtons[3][0].isChecked()){
	              	  everPills=true;
	              	AllOthersfalse=false;
	              	SharedPreferences.Editor results = getSharedPreferences("everResults", 0).edit();
	            	results.putBoolean("everPills",everPills);
	            	results.putBoolean("allOthersFalse", AllOthersfalse);
	            	results.commit();
	            	
	              	
	                }  
	                if (radioButtons[4][0].isChecked()){
	                	  everMix=true;
	                	  AllOthersfalse=false;
	                	  SharedPreferences.Editor results = getSharedPreferences("everResults", 0).edit();
	            		   results.putBoolean("everMix", everMix);
	            			results.putBoolean("allOthersFalse", AllOthersfalse);
	            			results.commit();
	                  	
	                	  
	                  }    


	            	//======================================================================  
	            	
	            	
	            	
	            	
	            	
	            	
	            	//SharedPreferences.Editor results = getSharedPreferences("everResults", 0).edit();
	    			//results.putBoolean("everEcstacy", everEcstacy);
	    		//	results.putBoolean("everSpeed",everSpeed);
	    		//	results.putBoolean("everMeph",everMeph);
	    		//	results.putBoolean("everPills",everPills);
	    		//	results.putBoolean("everMix", everMix);
	    		//	results.putBoolean("allOthersFalse", AllOthersfalse);
	    			
	    		//	results.commit();
	            	
	            	
	            	
	    			SharedPreferences getResults=getSharedPreferences("everResults", 0);
	    			
  	    			
  	    			if((getResults.getBoolean("everEcstacy", false)) && (getResults.getBoolean("everSpeed", false)) && 
  	    					
  	    					
  	    		 (getResults.getBoolean("everMeph", false)) && (getResults.getBoolean("everPills", false))
  	    		 
  	    		&& (getResults.getBoolean("everMix", false))){
  	    						
  	    						
  	    				SharedPreferences.Editor results = getSharedPreferences("everResults", 0).edit();
		    			results.putBoolean("askEverOther",false);
		    			results.commit();
  	    				
  	    						
  	    					}
	    			
	    	
  	    			
  	    			
  	   //----------------------------------------------------------^^^^^^^^^^^^^^^^^^^^^^^^^
  	    			
  	    			
  	    	 	  
			    	  
			  //----------------------------------------------------------^^^^^^^^^^^^^^^^^^^^^^^^^		
	    			
	    			
	    			if((getResults.getBoolean("allFalse", false)) && (getResults.getBoolean("allOthersFalse", false))){
	    			
	    				//To do
	    				// store results
	    				//
	    				//
	    				//
	    				
	    				//upload results
				    	  

			    		Intent intent = new Intent(this, MondayQHttpService.class);
			    			
			    		startService(intent);
  			
	    				
	    				
	    				  //checking user selection mode shared preferences
  				        Boolean adminMode=false;
  				   		
  				   		SharedPreferences getUserMode = getSharedPreferences("userMode", 0);
  				   		
  				   		adminMode=getUserMode.getBoolean("Admin", false);
  				   		
  				   		if(adminMode){
  				   			//send intent to weekly sports and club activities
  				   			
  				   		          //  ResetResults rr = new ResetResults();
  						           // rr.resetWeeklySurveys(getApplicationContext());
  					    
  				   		  Intent intent2 = new Intent(this, ThursdayDialog.class);
  			 			
 			             startActivity(intent2);     
  				   			                               
  				   		                     //Start camera activity
  	    				   		
	   	    				   //	Intent camintent = new Intent(getApplicationContext(), CameraActivity.class);
	   	    			   		
	   	    			       //  startActivity(camintent); 
 			            Toast.makeText(getApplicationContext(), "Thank you for taking part. Remember you can upload a photo as part of the mobiQ study.", Toast.LENGTH_LONG).show();	
  						     finish();         						            
  				   			
  				   		          } //end if admin mode
  				   		
  				   		
  				   		else{
	    				
	    				
  	//====================================================================================================
   				   		
	    	//Start camera activity=================================
	    				   		
	    				  // 	Intent camintent = new Intent(this, CameraActivity.class);
	    			   		
	    			      // startActivity(camintent); 
	    			       
	    		//===========================================================================
	    				
  				   		 }
  				   		
  				   		
  				   	 //start show thank you for install screen shared preferences
	 	            		
	 	            		SharedPreferences getThankyouScreenShow = getApplicationContext().getSharedPreferences("thankyouScreenShow", 0);
	 	            		
	 	            		if(getThankyouScreenShow.getBoolean("show", true)){
	 	            			
	 	    	   			
	    				   		Intent thankYouintent = new Intent(this, ThankyouActivity.class);
   	    			   		
		   	    			    startActivity(thankYouintent); 	
	 	            			
	 	            		
	 	            		
		   	    			    SharedPreferences.Editor thankyouScreenShow = getApplicationContext().getSharedPreferences("thankyouScreenShow", 0).edit();
		   	    			    thankyouScreenShow.putBoolean("show", false);
		   	    			    thankyouScreenShow.commit();
	 	            		}
	 	            		    else{
	 	            		    	Toast.makeText(getApplicationContext(), "Thank you for taking part. Remember you can upload a photo as part of the mobiQ study.", Toast.LENGTH_LONG).show();
	 	            		         }
  				   		
  				   		
	    				
	    				
	    				finish();
   	 			     
	    			}
	    			
	    			
	    			         else{

	    		    	   	     Intent weeklyIntent = new Intent(this, WeeklyActivity.class);
	    	   	 			     startActivity(weeklyIntent);
	    	   	 			     finish();
	    				
	    			        }
                   }
	    	        
	      
	    	
	       
	          
		
	  }
    
    
   
	
	
}
