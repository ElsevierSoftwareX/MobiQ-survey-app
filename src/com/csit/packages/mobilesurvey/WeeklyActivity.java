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

public class WeeklyActivity extends Activity {
	
	
	
	
	public String drugsQuestions[] = {"Tobacco ",
			
			                         "Alcohol",
			                         "Cannabis",
			                         "Any other drugs?",
			                         "Ecstacy",
			                         "Speed",
			                         "Mephedrone",
			                         "Legal ecstacy or other 'upper' pills",
	                                 "Legal smoking mixtures"
	                            };
	
	
	
	
	Boolean everTobacco=false;
	Boolean everAlcohol=false;
	Boolean everCannabis=false;
	Boolean everOther=false;
	Boolean everEcstacy=false;
	Boolean everSpeed=false;
	Boolean everMeph=false;
	Boolean everPills=false;
	Boolean everMix=false;
	
	
	

	Boolean weeklyTobacco=false;
	Boolean weeklyAlcohol=false;
	Boolean weeklyCannabis=false;
	Boolean weeklyOther=false;
	Boolean weeklyEcstacy=false;
	Boolean weeklySpeed=false;
	Boolean weeklyMeph=false;
	Boolean weeklyPills=false;
	Boolean weeklyMix=false;
	
	
	
	
	TextView  main_question;
	TextView questions[];
	RadioButton radioButtons[][];
	RadioGroup radioGroups[];
	Boolean Allanswered=false;
	Boolean selected[];
	
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weekly);
		
		
		TextView  main_question = new TextView(this);
		//question.setText(R.string.hello_world);
		main_question.setText("In the Last week, have you used?");
		main_question.setTextSize(25);
		main_question.setId(100);
		main_question.setLayoutParams(new LayoutParams (LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		
			
		 LinearLayout linearLayout = (LinearLayout) findViewById (R.id.linear_layout);
			
	     linearLayout.setBackgroundColor(Color.TRANSPARENT);
	     
	     linearLayout.addView(main_question);
	    
	
	     
	     SharedPreferences results = getSharedPreferences("everResults", 0);
	     everTobacco= results.getBoolean("everTobacco", true);
	     everAlcohol= results.getBoolean("everAlcohol", true);
	     everCannabis= results.getBoolean("everCannabis", true);
	     everOther= results.getBoolean("everOther", true);
	     everEcstacy= results.getBoolean("everEcstacy", true);
	     everSpeed= results.getBoolean("everSpeed", true);
	     everMeph= results.getBoolean("everMeph", true);
	     everPills= results.getBoolean("everPills", true);
	     everMix= results.getBoolean("everMix", true);
	     
	     
			
	 	questions = new TextView  [drugsQuestions.length];
		radioButtons = new RadioButton [drugsQuestions.length][2];
		radioGroups = new RadioGroup [drugsQuestions.length];
		selected = new Boolean [drugsQuestions.length];
			
		
		
		
		
		if (everTobacco){
			selected[0]=true;
		}
		else {selected[0]=false; }
		
		if (everAlcohol){
			selected[1]=true;
		}
		else {selected[1]=false; }
		
		if (everCannabis){
			selected[2]=true;
		}
		else {selected[2]=false; }
		
		
		
		selected[3]=false;
		
		/*if (everOther){
			selected[3]=true;
		}
		else {selected[3]=false; }*/
		
		
		
		
		if (everEcstacy){
			selected[4]=true;
		}
		else {selected[4]=false; }
		
		if (everSpeed){
			selected[5]=true;
		}
		else {selected[5]=false; }
		
		if (everMeph){
			selected[6]=true;
		}
		else {selected[6]=false; }
		
		if (everPills){
			selected[7]=true;
		}
		else {selected[7]=false; }
		
		if (everMix){
			selected[8]=true;
		}
		else {selected[8]=false; }
		
		
		
		
		
			      for (int i=0; i<drugsQuestions.length; i++){
				
			    	 
			    	  questions[i] = new TextView(this);
			    	
				          questions[i].setText(drugsQuestions[i]) ;
				          questions[i].setTextSize(20);
				          questions[i].setId(i+27);
				          questions[i].setLayoutParams(new LayoutParams (LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			    	
				
			      }
			
			
						      
			      for (int i=0; i<drugsQuestions.length; i++){
						
			    	  
			    	  radioButtons[i][0] = new RadioButton(this);
			    	  radioButtons[i][1]= new RadioButton(this);
			    	  radioButtons[i][0].setText("yes");
			    	  radioButtons[i][1].setText("no"); 
			  
		       }
			      
			      
			      
			
			      for (int i=0; i<drugsQuestions.length; i++){
						
			    	  radioGroups[i]=new RadioGroup(this);
			    	  
			    	  radioGroups[i].setOrientation(RadioGroup.HORIZONTAL) ;
			          
			    	  radioGroups[i].addView(radioButtons[i][0]);
			    	  radioGroups[i].addView(radioButtons[i][1]);
		      }
		
			      
			      

			      for (int i=0; i<drugsQuestions.length; i++){
			    	  
			    	if  (selected[i]==true){
						
			    	  linearLayout.addView(questions[i]);
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
	    }//end for
	  
	  
	  
	  
	  if (Allanswered==false){
	    	   
	      Toast.makeText(getApplicationContext(), "Please answer all the questions", Toast.LENGTH_LONG).show();
	  
  }

    
   
   
   else{

	  //Todo code for checking if the selected displayed question is clicked yes or no
	   ///
	   ///
	   ///
	   ///
	   ///
	   
	   if (selected[0]) {
		   
		       if ((radioButtons[0][0].isChecked())){         //yes selected
		    	   weeklyTobacco =true; 
		          }
		    }  // end if
	  
	   if (selected[1]) {
		   
	       if ((radioButtons[1][0].isChecked())){         //yes selected
	    	   weeklyAlcohol =true; 
	          }
	    }  // end if
	   
	   if (selected[2]) {
		   
	       if ((radioButtons[2][0].isChecked())){         //yes selected
	    	   weeklyCannabis =true; 
	          }
	    }  // end if
	   
	   if (selected[3]) {
		   
	       if ((radioButtons[3][0].isChecked())){         //yes selected
	    	   weeklyOther =true; 
	          }
	    }  // end if
	   
	   if (selected[4]) {
		   
	       if ((radioButtons[4][0].isChecked())){         //yes selected
	    	   weeklyEcstacy =true; 
	          }
	    }  // end if
	   
	   if (selected[5]) {
		   
	       if ((radioButtons[5][0].isChecked())){         //yes selected
	    	   weeklySpeed=true; 
	          }
	    }  // end if
	   
	   if (selected[6]) {
		   
	       if ((radioButtons[6][0].isChecked())){         //yes selected
	    	   weeklyMeph =true; 
	          }
	    }  // end if
	   
	   if (selected[7]) {
		   
	       if ((radioButtons[7][0].isChecked())){         //yes selected
	    	   weeklyPills =true; 
	          }
	    }  // end if
	   
	   if (selected[8]) {
		   
	       if ((radioButtons[8][0].isChecked())){         //yes selected
	    	   weeklyMix =true; 
	          }
	    }  // end if
	   
	   
	   
	   
	   
	   SharedPreferences.Editor results = getSharedPreferences("weeklyResults", 0).edit();
		results.putBoolean("weeklyTobacco", weeklyTobacco);
		results.putBoolean("weeklyAlcohol", weeklyAlcohol);
		results.putBoolean("weeklyCannabis",weeklyCannabis);
		results.putBoolean("weeklyOther",weeklyOther);
		results.putBoolean("weeklyEcstacy", weeklyEcstacy);
		results.putBoolean("weeklySpeed",weeklySpeed);
		results.putBoolean("weeklyMeph",weeklyMeph);
		results.putBoolean("weeklyPills",weeklyPills);
		results.putBoolean("weeklyMix", weeklyMix);
		
		results.commit();   
	   
	   
		
		 
		   // Intent intent = new Intent(this, ListActivity.class);
		//Intent intent = new Intent(this, WeeklySportsClubActivity.class);
			
		//	startActivity(intent);
			
		
		
		
		
	//upload results
		Intent intent = new Intent(this, MondayQHttpService.class);
		
	    startService(intent);
		
			     
		
	   //checking user selection mode shared preferences
	         Boolean adminMode=false;
	   		
	   		SharedPreferences getUserMode = getSharedPreferences("userMode", 0);
	   		
	   		adminMode=getUserMode.getBoolean("Admin", false);
	   		
	   		if(adminMode){
	   			//send intent to weekly sports and club activities
	   			
	   		    
	   			//   ResetResults rr = new ResetResults();
			       // rr.resetWeeklySurveys(getApplicationContext());
		    
		               //  Intent intent2 = new Intent(this, WeeklySportsClubActivity.class);
			
			          //   startActivity(intent2);
	   			
			             Intent intent2 = new Intent(this, ThursdayDialog.class);
			 			
			             startActivity(intent2);      
			             
			             
			       //Start camera activity
				   		
  				        // 	Intent camintent = new Intent(this, CameraActivity.class);
  			   		
  			             //   startActivity(camintent); 
	   			
	   			
	   		          } //end if admin mode
	   		
	   		             else{       //if not admin mode
	   		            	 
	   		            	 
	   		          	//====================================================================================================
	    				   		
	   	    				   	//Start camera activity
	   	    				   		
	   	    				   	     //Intent camintent = new Intent(this, CameraActivity.class);
	   	    			   		
	   	    			          //   startActivity(camintent); 
	   	    			       
	   	    			       //===========================================================================
	   		        		
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
	    				   		
	   	 	            				
	   	    				   		
		   	    			    
		   	    			    
		   	    			    
	   			 
	   	                         	}
	 	
	   	//Toast.makeText(getApplicationContext(), "Thank you for taking part. Remember you can upload a photo as part of the mobiQ study.", Toast.LENGTH_LONG).show();
		finish();
		
		
          
      

        } // end if all answered

	  
	  
	  
}
    
    
   
	
	
	
	
}
