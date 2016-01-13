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

public class EverActivity extends Activity {
		
	public String drugsQuestions[] = {"Tobacco ",
			
			                         "Alcohol",
			                         "Cannabis",
			                         "Any other drugs?"
			                         };
	
	
	Boolean everTobacco=false;
	Boolean everAlcohol=false;
	Boolean everCannabis=false;
	Boolean everOther=false;
	
	
	TextView  main_question;
	TextView questionsTextView[];
	RadioButton radioButtons[][];
	RadioGroup radioGroups[];
	Boolean Allanswered=false;
	Boolean selected[];
	

	static Boolean Allfalse=true;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ever_main);
		
		Allanswered=false;
		Allfalse=true;
		
			
		SharedPreferences getResults = getSharedPreferences("everResults", 0);
		everTobacco=getResults.getBoolean("everTobacco", false);
		everAlcohol=getResults.getBoolean("everAlcohol", false);
		everCannabis=getResults.getBoolean("everCannabis", false);
		everOther=getResults.getBoolean("everOther", false);
		
		
		
		
		
		main_question = new TextView(this);
		//question.setText(R.string.hello_world);
		main_question.setText("Have you ever used?");
		main_question.setTextSize(25);
		
		main_question.setId(100);     //check for conflict in Id?????????/
		
		
		main_question.setLayoutParams(new LayoutParams (LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		
			
		 LinearLayout linearLayout = (LinearLayout) findViewById (R.id.linear_layout);
			
	     linearLayout.setBackgroundColor(Color.TRANSPARENT);
	     
	     linearLayout.addView(main_question);
	    
	
			
			questionsTextView = new TextView  [drugsQuestions.length];
			radioButtons = new RadioButton [drugsQuestions.length][2];
			radioGroups = new RadioGroup [drugsQuestions.length];
			selected = new Boolean [drugsQuestions.length];
			
			
			
			//select the questions to show
			if (everTobacco){
				selected[0]=false;
			}
			else {selected[0]=true; }
			
			if (everAlcohol){
				selected[1]=false;
			}
			else {selected[1]=true; }
			
			if (everCannabis){
				selected[2]=false;
			}
			else {selected[2]=true; }
			
			if (everOther){
				selected[3]=false;
			}
			else {selected[3]=true; }
			
			
			
			
			      for (int i=0; i<drugsQuestions.length; i++){
				
			    	  
			    	      questionsTextView[i] = new TextView(this);
			    	
				          questionsTextView[i].setText(drugsQuestions[i]) ;
				          questionsTextView[i].setTextSize(15);
				          questionsTextView[i].setId(i+27);
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
			    	
			    
		           }
		      
				
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
	             	  	everTobacco=true;
	             	  	Allfalse=false;
	             	  	
	             	  	
	             	  	SharedPreferences.Editor results = getSharedPreferences("everResults", 0).edit();
		    			results.putBoolean("everTobacco", everTobacco);
		    			results.putBoolean("allFalse", Allfalse);
		    			results.commit();
	               	}
	               if (radioButtons[1][0].isChecked()){
	             	  everAlcohol=true;
	             	  Allfalse=false;
	             	  
	             	 SharedPreferences.Editor results = getSharedPreferences("everResults", 0).edit();
		    			results.putBoolean("everAlcohol",everAlcohol);
		    			results.putBoolean("allFalse", Allfalse);
		    			results.commit();
	               }
	               if (radioButtons[2][0].isChecked()){
	             	  everCannabis=true;
	             	  Allfalse=false;
	             	 SharedPreferences.Editor results = getSharedPreferences("everResults", 0).edit();
		    			results.putBoolean("everCannabis",everCannabis);
		    			results.putBoolean("allFalse", Allfalse);
		    			results.commit();
	               }
	               if (radioButtons[3][0].isChecked()){
	             	  everOther=true;
	             	  Allfalse=false;
	             	  
	             	 SharedPreferences.Editor results = getSharedPreferences("everResults", 0).edit();
		    			results.putBoolean("everOther",everOther);
		    			results.putBoolean("allFalse", Allfalse);
		    			results.commit();
	             	  //                     
	               }    

	           	//======================================================================  
	            	
	            	
	               SharedPreferences getResults=getSharedPreferences("everResults", 0);
 	    			
 	    			if((getResults.getBoolean("everAlcohol", false)) && (getResults.getBoolean("everCannabis", false)) && 
 	    					
 	    					
 	    		 (getResults.getBoolean("everTobacco", false)) && (getResults.getBoolean("everOther", false))){
 	    						
 	    						
 	    				SharedPreferences.Editor results = getSharedPreferences("everResults", 0).edit();
		    			results.putBoolean("askEver",false);
		    			results.commit();
 	    				
 	    						
 	    					}
	       //-------------------------------------------------------------------------------------
 	    			
 	    			
 	    			
 	    			 SharedPreferences getResults2=getSharedPreferences("everResults", 0);
  	    			
  	    			if(!(getResults2.getBoolean("everAlcohol", false)) && (!getResults2.getBoolean("everCannabis", false)) && 
  	    					
  	    					
  	    		 (!getResults2.getBoolean("everTobacco", false)) && (getResults2.getBoolean("everOther", false))){
  	    						
  	    						
  	    				SharedPreferences.Editor results = getSharedPreferences("everResults", 0).edit();
 		    			results.putBoolean("allFalse",true);
 		    			results.commit();
  	    				
  	    						
  	    					}
 	    			
 	    			
 	    			
 	    			
 	    //---------------------------------------------------------------------------------			
	    	  	    			
  	    			
  	     //-------------------------------------------------------------------------------------
 	    			
 	    			
 	    			
	    			 	    			
 	    			if((getResults2.getBoolean("everEcstacy", false)) && (getResults2.getBoolean("everSpeed", false)) && 
 	    					
 	    					
 	    		 (getResults2.getBoolean("everMeph", false)) && (getResults2.getBoolean("everPills", false)) &&
 	    		 
 	    					
 	    		(getResults2.getBoolean("everMix", false)) && (getResults2.getBoolean("everOther", false))){
 	    						
 	    						
 	    				everOther=false;			
 	    						
 	    					}
	    			
	    			
	    			
	    			
	    //---------------------------------------------------------------------------------			
	    	   	     
	    	   	    			
	    	   	     
	    	 	//	SharedPreferences.Editor results = getSharedPreferences("everResults", 0).edit();
	    			//results.putBoolean("everTobacco", everTobacco);
	    		//	results.putBoolean("everAlcohol",everAlcohol);
	    		//	results.putBoolean("everCannabis",everCannabis);
	    		//	results.putBoolean("everOther",everOther);
	    		//	results.putBoolean("allFalse", Allfalse);
	    		//	results.commit();
	    			
	    			
	    			
	    			if(everOther==true) {
	    			   Intent intent = new Intent(this, EverOthersActivity.class);
		    	   	     //if (anySports=true)
	   	 			     startActivity(intent);
	   	 			     finish();
	   	 			     
	    			             }
	    			else{
	    				
	    				      if((getResults.getBoolean("allFalse", false))){
	    				    	  
	    				    	  
	    				    	  
	    				    	  //upload results
	    				    	  

	    				    		Intent intent = new Intent(this, MondayQHttpService.class);
	    				    			
	    				    		startService(intent);
	    	    			
      	 			     
   	    				              
   	    				              
   	    				     // if in  admin mode then go to next set of questions
   	    				              
   	    				              
   	    				      //checking user selection mode shared preferences
   	    				         Boolean adminMode=false;
   	    				   		
   	    				   		SharedPreferences getUserMode = getSharedPreferences("userMode", 0);
   	    				   		
   	    				   		adminMode=getUserMode.getBoolean("Admin", false);
   	    				   		
   	    				   		if(adminMode){
   	    				   			//send intent to weekly sports and club activities
   	    				   			
   	    				   		    //  ResetResults rr = new ResetResults();
   	    						   //  rr.resetWeeklySurveys(getApplicationContext());
   	    					    
   	    				   	  Intent intent2 = new Intent(this, ThursdayDialog.class);
   				 			
   				             startActivity(intent2);    
   				             
   				          Toast.makeText(getApplicationContext(), "Thank you for taking part. Remember you can upload a photo as part of the mobiQ study.", Toast.LENGTH_LONG).show();
   	    				   			
   				             finish();
   	    				   		                 //Start camera activity
		   	    				   		
			   	    				  // 	Intent camintent = new Intent(this, CameraActivity.class);
			   	    			   		
			   	    			      //   startActivity(camintent); 
   	    						         
   	    						         
   	    						      	
   	    						 	  
   	    				   		          } //end if admin mode
   	    				   		
   	    				      
   	    				   		else{
   	    				   		
   	    				   		
   	    				   	//====================================================================================================
   	    				   		
   	    			//Start camera activity==========================================
   	    				   		
   	    				       //	Intent camintent = new Intent(this, CameraActivity.class);
   	    			   		
   	    			            //  startActivity(camintent); 
   	    				   			
   	    				   			
   	    				   		 //start show thank you for install screen shared preferences
   	    	 	            		
   	    	 	            		SharedPreferences getThankyouScreenShow = getApplicationContext().getSharedPreferences("thankyouScreenShow", 0);
   	    	 	            		
   	    	 	            		if(getThankyouScreenShow.getBoolean("show", true)){
   	    	 	            			
   	    	 	    	   			
   	    	    				   		Intent thankYouintent = new Intent(this, ThankyouActivity.class);
   	 	   	    			   		
   	    		   	    			    startActivity(thankYouintent); 	
   	    	 	            			
   	    	 	            		
   	    	 	            		
   	    		   	    			    SharedPreferences.Editor thankyouScreenShow = getApplicationContext().getSharedPreferences("thankyouScreenShow", 0).edit();
   	    		   	    			    thankyouScreenShow.putBoolean("show", false);
   	    		   	    			    thankyouScreenShow.commit();
   	    		   	    			    
   	    		   	    			    finish();
   	    	 	            		}
   	    	 	            				else {
   	    	 	            				Toast.makeText(getApplicationContext(), "Thank you for taking part. Remember you can upload a photo as part of the mobiQ study.", Toast.LENGTH_LONG).show();
   	    	 	            				}//end else thankyouscreenshow
   	    	    				   		
   	    				   			
   	    			
   	    			       
   	    			       //===========================================================================
   	    			       
   	    				   		       }//end else if admin mode
   	    				   		
   	    				   		
   	    				   		
   	    				   		
   	    				   
   	    				   		
   	    				   		
   	    				   		
   	    				   		
   	    				   

	    				             finish();
   	    			           }
   	    			
   	    			
   	    			         else{

   	    		    	   	     Intent intent = new Intent(this, WeeklyActivity.class);
   	    	   	 			     startActivity(intent);
   	    	   	 			     //to do store results
   	    	   	 			     //
   	    	   	 			     //
   	    	   	 			     //
   	    	   	 			     
   	    	   	 			     finish();
   	    				
   	    			                }
	    			
	    			}
   	 			           
	    			
	    			
	    			
	    			finish();
              
	            
	            }  // end else if all answered
	    	        
	       
	              
   
	   
	          
		
	  }
    
    
   
}
