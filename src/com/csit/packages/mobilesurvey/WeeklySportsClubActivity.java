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

public class WeeklySportsClubActivity extends Activity {
	
	
	
	
	public String sportsClubsQuestions[] = {"Played any sports?",
			
			                                 "Done any other school club activities?",
			                                 
			                                 "Done any other outside school activities?"
			                              };
	
	
	Boolean anySports=false;
	Boolean anyClubs=false;
	Boolean anyNonClubs=false;
		
	
	
	
	TextView  main_question;
	TextView questionsTextView[];
	RadioButton radioButtons[][];
	RadioGroup radioGroups[];
	Boolean Allanswered=false;
	
	

	static Boolean Allfalse=true;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ever_main);
		
		
		
		Allanswered=false;
		Allfalse=true;
		
		
		main_question = new TextView(this);
		//question.setText(R.string.hello_world);
		main_question.setText("In the last week, have you ...?");
		main_question.setTextSize(20);
		
		main_question.setId(100);     //check for conflict in Id?????????/
		
		
		main_question.setLayoutParams(new LayoutParams (LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		
			
		 LinearLayout linearLayout = (LinearLayout) findViewById (R.id.linear_layout);
			
	     linearLayout.setBackgroundColor(Color.TRANSPARENT);
	     
	     linearLayout.addView(main_question);
	    
	
			
			questionsTextView = new TextView  [sportsClubsQuestions.length];
			radioButtons = new RadioButton [sportsClubsQuestions.length][2];
			radioGroups = new RadioGroup [sportsClubsQuestions.length];
			
				
			      for (int i=0; i<sportsClubsQuestions.length; i++){
				
			    	  
			    	  questionsTextView[i] = new TextView(this);
			    	
				          questionsTextView[i].setText(sportsClubsQuestions[i]) ;
				          questionsTextView[i].setTextSize(15);
				          questionsTextView[i].setId(i+27);
				          questionsTextView[i].setLayoutParams(new LayoutParams (LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				
			      }
			
			
						      
			   
			      for (int i=0; i<sportsClubsQuestions.length; i++){
						
			    	  radioButtons[i][0] = new RadioButton(this);
			    	  radioButtons[i][1]= new RadioButton(this);
			    	 
			    	  radioButtons[i][0].setText("yes");
			    	  radioButtons[i][1].setText("no"); 
			    	 // radioButtons[i][1].setChecked(true);
			  
		       }
			      
			      
			      
			
			      for (int i=0; i<sportsClubsQuestions.length; i++){
						
			    	  radioGroups[i]=new RadioGroup(this);
			    	  radioGroups[i].setOrientation(RadioGroup.HORIZONTAL) ;
			          radioGroups[i].addView(radioButtons[i][0]);
			    	  radioGroups[i].addView(radioButtons[i][1]);
			    	  
			    	  
		      }
		
			      
			      
			      
			      
		   
			      for (int i=0; i<sportsClubsQuestions.length; i++){
			    	  
			    	
						
			    	  linearLayout.addView(questionsTextView[i]);
			    	  linearLayout.addView(radioGroups[i]);
			    	 
			    	
			    	
			    
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
	
	  for (int i=0; i<sportsClubsQuestions.length; i++){
		  
		   
			  
			          if (!(radioButtons[i][0].isChecked() || radioButtons[i][1].isChecked())) {       // no selection?
	    	                Allanswered=false;
	    		            }
			          
		         
	       	 
     
          }// end for
        
        
        
	//==================================================================  
	  
	  
	 if (radioButtons[0][0].isChecked()){
  	  	anySports=true;
  	  	Allfalse=false;
    	}
    if (radioButtons[1][0].isChecked()){
  	  anyClubs=true;
  	  Allfalse=false;
    }
    if (radioButtons[2][0].isChecked()){
    	  anyNonClubs=true;
    	  Allfalse=false;
      }

	//======================================================================  
	  
	  
	  
	  
	       if (Allanswered==false){
	  	    	   
	    	      Toast.makeText(getApplicationContext(), "Please answer all the questions", Toast.LENGTH_LONG).show();
	  	  
	      }
	  
	        
	       
	       
	            else if (Allanswered==true ){
	    	   	  
	    	   	     
	    	   	     
	    	   	     
	    	 		SharedPreferences.Editor results = getSharedPreferences("sportsClubResults", 0).edit();
	    			results.putBoolean("anySports", anySports);
	    			results.putBoolean("anyClubs",anyClubs);
	    			results.putBoolean("anyNonClubs",anyNonClubs);
	    			results.commit();
	    			
	    			
	    			
	    			
	    			//   Intent intent = new Intent(this, EverOthersActivity.class);
		    	   	     
	   	 			  //   startActivity(intent);
	    			
	    			
	    			if(anySports){
	    				
	    			    Intent intent = new Intent(this, SportsListActivity.class);
	    			    intent.putExtra("anyClubs", true);
		    	   	     
		   	 		    startActivity(intent);
		   	 		    
		   	 		    finish();
		   	 		    //Todo
		   	 		    
		   	 		    // store results
	    			}
	    			
	    			
	    			if (anyClubs){
	    				
	    				
	    				if (anySports==false){
	    				
	    				Intent intent = new Intent(this, ClubsListActivity.class);
		    	   	     
			   	 		 startActivity(intent);
			   	 		 
			   	 	    //Todo
			   	 		    
			   	 		    // store results
			   	 		 finish();
	    				}
	    				
	    			}
	    			
	    			
                     if (anyNonClubs){
	    				
	    				
	    				if (anySports==false && anyClubs==false){
	    				
	    				Intent intent = new Intent(this, NonClubsListActivity.class);
		    	   	     
			   	 		 startActivity(intent);
			   	 	    //Todo
			   	 		    
			   	 		    // store results
			   	 		 finish();
	    				}
	    				
	    			}
	    			
                    
                     
                     if ((!anySports) && (!anyClubs) && (!anyNonClubs)){    // upload results
                    	 
                    	 
                    	 Intent intent2 = new Intent(this, ThursdayQHttpService.class);
         				
            		     startService(intent2);
            		     
            		     
            		     
            		     
            		     
            		     //checking user selection mode shared preferences
   				         Boolean adminMode=false;
   				   		
   				   		SharedPreferences getUserMode = getSharedPreferences("userMode", 0);
   				   		
   				   		adminMode=getUserMode.getBoolean("Admin", false);
   				   		
   				   		if(adminMode){
   				   			//send intent to weekly sports and club activities
   				   			
   				   		  //   ResetResults rr = new ResetResults();
   						  //   rr.resetWeeklySurveys(getApplicationContext());
   					    
   					           // Intent intent3 = new Intent(this, MainActivity.class);
   						
   						       //  startActivity(intent3);
   				   		Toast.makeText(getApplicationContext(), "Thank you for taking part. Remember you can upload a photo as part of the mobiQ study.", Toast.LENGTH_LONG).show();
   				   			
   				   		//Start camera activity
  	    				   		
	   	    				   	//Intent camintent = new Intent(this, CameraActivity.class);
	   	    			   		
	   	    			        //startActivity(camintent); 
   				   			
   				   			
   				   		          } //end if admin mode
   				   		
   				   		else{
   				   			
   				   		Toast.makeText(getApplicationContext(), "Thank you for taking part. Remember you can upload a photo as part of the mobiQ study.", Toast.LENGTH_LONG).show();
   				   		//====================================================================================================
	    				   		
   	    				   	//Start camera activity
   	    				   		
   	    				      	//Intent camintent = new Intent(this, CameraActivity.class);
   	    			   		
   	    			             //startActivity(camintent); 
   	    			       
   	    			    //===========================================================================
   				   		                  }//end else if not admin mode
   				   		
            		     
            		     
            		
                    	 
                     }
                     
                     
                     //Todo
		   	 		    
		   	 		 // store results
                     //Toast.makeText(getApplicationContext(), "Thank you for taking part. Remember you can upload a photo as part of the mobiQ study.", Toast.LENGTH_LONG).show();
   	 			     finish();
                   }
	    	        
	       
	       
	      
	          
		
	  }
    
    
   	
	
	
}
