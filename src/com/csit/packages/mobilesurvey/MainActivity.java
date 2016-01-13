package com.csit.packages.mobilesurvey;

import java.io.File;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	
//	Alarm alarm=new Alarm();

	String locFile="";
	
	File sdCard = Environment.getExternalStorageDirectory();  //get handle on SD card location
	File myLocFile= new File (sdCard, "mobiQloc.txt");
	    
	 Context context;
	 
	 String databaseViewUrl;
	    
	public static boolean isSleeping=false;    //scheduler sleep state
	    
		 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
				
		
		context=getApplicationContext();
		
		 databaseViewUrl=getResources().getString(R.string.database_view_url);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	//===========================================================================================================================		
	
	
	public void onClickButtonStart (View view){            //starting the application manually
		
      
		
		resetWeeklySurveys();

	
       Intent intent = new Intent(this,MondayDialog.class);
  		
       startActivity(intent);
       
	
       
       finish();
		
	}
	
	
	

//===========================================================================================================================	
	
	
	
public void onClickButtonReset (View view){         //reset the application
		
   //reset application
	
/*	
	 File myLocFile= new File (sdCard, "mobiQloc.txt");
	// myLocFile.setWritable(false, true);
	 try
     {

     BufferedWriter out = new BufferedWriter(new FileWriter(myLocFile, false));
    
     
     
     out.close();

  } catch (IOException e){ }
	 
	 
	 
	 
	 
	 myLocFile= new File (sdCard, "mobiqMonday.txt");
	// myLocFile.setWritable(false, true);
	 try
     {

     BufferedWriter out = new BufferedWriter(new FileWriter(myLocFile, false));
    
     
     
     out.close();

  } catch (IOException e){ }
	 
	 
	 myLocFile= new File (sdCard, "mobiqThursday.txt");
	// myLocFile.setWritable(false, true);
	 try
     {

     BufferedWriter out = new BufferedWriter(new FileWriter(myLocFile, false));
    
     
     
     out.close();

  } catch (IOException e){ }
	
	
	 
	 myLocFile= new File (sdCard, "mobiqCalls.txt");
	
	 try
     {

     BufferedWriter out = new BufferedWriter(new FileWriter(myLocFile, false));
    
     
     
     out.close();

  } catch (IOException e){ }
	 
	 
	 
	 myLocFile= new File (sdCard, "mobiqSMS.txt");
	
	 try
     {

     BufferedWriter out = new BufferedWriter(new FileWriter(myLocFile, false));
    
     
     
     out.close();

  } catch (IOException e){ }
	 
	 
	 myLocFile= new File (sdCard, "mobiqDeviceInfo.txt");
	

	 
	 
	 try
     {

     BufferedWriter out = new BufferedWriter(new FileWriter(myLocFile, false));
    
     
     
     out.close();

  } catch (IOException e){ }
	 
	 
	 
	 myLocFile= new File (sdCard, "mobiQproblem.txt");
	
	 try
     {

     BufferedWriter out = new BufferedWriter(new FileWriter(myLocFile, false));
    
     
     
     out.close();

  } catch (IOException e){ }
	 
*/	
	 
	//===========================================================================================end of clear files======
	
	 //===========clear  private files=============================================================
	 
	 
	 String id;
	  SharedPreferences getid = getSharedPreferences("deviceinfo", 0);
	  id=getid.getString("id", "none");
	 
	 SaveToFile stf = new SaveToFile(context, "priv_mobiQloc"+id+".txt");
	 stf.clear();
	 SaveToFile stf2 = new SaveToFile(context, "priv_mobiqMonday"+id+".txt");
	 stf2.clear();
	 SaveToFile stf3 = new SaveToFile(context, "priv_mobiqThursday"+id+".txt");
	 stf3.clear();
	 SaveToFile stf4 = new SaveToFile(context, "priv_mobiqCalls"+id+".txt");
	 stf4.clear();
	 SaveToFile stf5 = new SaveToFile(context, "priv_mobiqSMS"+id+".txt");
	 stf5.clear();
	 SaveToFile stf6 = new SaveToFile(context, "priv_mobiqDeviceInfo"+id+".txt");
	 stf6.clear();
	 SaveToFile stf7 = new SaveToFile(context, "priv_mobiQproblem"+id+".txt");
	 stf7.clear();
	 
	
	 
	//cancel alarms
	ResetResults r = new ResetResults();
	r.CancelAlarms(getApplicationContext());
	
	
	//start welcome screen shared preferences
	
	SharedPreferences.Editor welcomeScreenShow = getSharedPreferences("welcomeScreenShow", 0).edit();
	welcomeScreenShow.putBoolean("show", true);
	welcomeScreenShow.commit();
	
	
	//start show thank you for install screen shared preferences
	
	SharedPreferences.Editor thankyouScreenShow = context.getSharedPreferences("thankyouScreenShow", 0).edit();
	thankyouScreenShow.putBoolean("show", true);
	thankyouScreenShow.commit();
	
	
	//user selection mode shared preferences
	
	SharedPreferences.Editor userMode = getSharedPreferences("userMode", 0).edit();
	userMode.putBoolean("Admin", false);
	userMode.commit();
	
	
	// ever questions shared preferences
	
	  SharedPreferences.Editor everResults = getSharedPreferences("everResults", 0).edit();
	  everResults.putBoolean("everTobacco", false);
		everResults.putBoolean("everAlcohol",false);
		everResults.putBoolean("everCannabis",false);
		everResults.putBoolean("everOther",false);
	    everResults.putBoolean("everEcstacy", false);
	    everResults.putBoolean("everSpeed",false);
	    everResults.putBoolean("everMeph",false);
	    everResults.putBoolean("everPills",false);
	    everResults.putBoolean("everMix", false);
	    everResults.putBoolean("allFalse", true);
	    everResults.putBoolean("allOthersFalse", true);
	    everResults.putBoolean("askEver", true);
	    everResults.putBoolean("askEverOther", true);
	    
		everResults.commit();
		
		
		
	// weekly questions shared preferences
		
		   SharedPreferences.Editor weeklyResults = getSharedPreferences("weeklyResults", 0).edit();
			weeklyResults.putBoolean("weeklyTobacco", false);
			weeklyResults.putBoolean("weeklyAlcohol",false);
			weeklyResults.putBoolean("weeklyCannabis",false);
			weeklyResults.putBoolean("weeklyOther",false);
			weeklyResults.putBoolean("weeklyEcstacy", false);
			weeklyResults.putBoolean("weeklySpeed",false);
			weeklyResults.putBoolean("weeklyMeph",false);
			weeklyResults.putBoolean("weeklyPills",false);
			weeklyResults.putBoolean("weeklyMix", false);
			
			weeklyResults.commit();   

			//sports/club shared preferences
			
			SharedPreferences.Editor weeklySportsClubsResults = getSharedPreferences("sportsClubResults", 0).edit();
			weeklySportsClubsResults.putBoolean("anySports", false);
			weeklySportsClubsResults.putBoolean("anyClubs",false);
			weeklySportsClubsResults.putBoolean("anyNonClubs",false);
			weeklySportsClubsResults.commit();
	
	// sports activity selection
			
			
			String data [];
			data = getResources().getStringArray(R.array.sports_array);
			
			for (int i =0; i<data.length; i++){
			SharedPreferences.Editor sports_results = getSharedPreferences("regularSportsResults", 0).edit();
			 sports_results.putBoolean("sports"+i, false);
          	 sports_results.commit();   
			}
			 
			
//clubs activity selection
			
			
			String data1 [];
			data1 = getResources().getStringArray(R.array.club_array);
			for (int i =0; i<data1.length; i++){
				SharedPreferences.Editor clubs_results = getSharedPreferences("regularClubsResults", 0).edit();
				clubs_results.putBoolean("clubs"+i, false);
            	clubs_results.commit();  
			}
			
//non clubs activity selection
			
			
			String data2 [];
			data2 = getResources().getStringArray(R.array.non_club_array);
			
			for (int i =0; i<data2.length; i++){
			 SharedPreferences.Editor non_clubs_results = getSharedPreferences("regularNonClubsResults", 0).edit();
			 non_clubs_results.putBoolean("non_clubs"+i, false);
          	 non_clubs_results.commit();   
			}
		 

// number of times sports	
			data = getResources().getStringArray(R.array.sports_array);
			for (int i =0; i<data.length; i++){
				
				 SharedPreferences.Editor num_times_results = getSharedPreferences("numberOfTimesSport", 0).edit();
        		 num_times_results.putString("sports"+i, "none");
	    		 num_times_results.commit(); 
			}	
			
// number of times clubs	
						data = getResources().getStringArray(R.array.club_array);
						for (int i =0; i<data.length; i++){
							
							 SharedPreferences.Editor num_times_results = getSharedPreferences("numberOfTimesClubs", 0).edit();
			        		 num_times_results.putString("clubs"+i, "none");
				    		 num_times_results.commit(); 
						}	
// number of times non clubs 	
						data = getResources().getStringArray(R.array.non_club_array);
						for (int i =0; i<data.length; i++){
							
							 SharedPreferences.Editor num_times_results = getSharedPreferences("numberOfTimesNonClubs", 0).edit();
			        		 num_times_results.putString("non_clubs"+i, "none");
				    		 num_times_results.commit(); 
						}	

						
		
						
	//names of other non listed activities
						
						SharedPreferences.Editor non_listed_activities = getSharedPreferences("nonListedActivities", 0).edit();
						non_listed_activities.putString("otherSportsName", "none");
						non_listed_activities.putString("otherClubsName", "none");
						non_listed_activities.putString("otherNonClubsName", "none");
						non_listed_activities.commit();
						
						
	//restart the main activity					
				//	Intent intent= new Intent(this, MainActivity.class);
				//	startActivity(intent);
						
						
			finish();
		
	}



//===========================================================================================================================	


public void onClickButtonResults (View view){   // show current survey results
	
	
	
	Intent intent= new Intent(this, ShowResults.class);
	startActivity(intent);
	
	 finish();
	
	
}


//===========================================================================================================================	


public void onClickButtonClose (View view){   // cancel all current alarms and close application
	
	 
	//ResetResults r = new ResetResults();
	//r.CancelAlarms(getApplicationContext());
	
	
/*
 * 
 * 
 * 
 
	ResetResults r = new ResetResults();
	r.CancelAlarms(getApplicationContext());
	
	
	
	Intent intent = new Intent(this, MondayQHttpService.class);
		
	  startService(intent);
	
		     
	 Intent intent2 = new Intent(this, ThursdayQHttpService.class);
			
	     startService(intent2);
	     
	     
	     */
	
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
			
			
	
		 /*  		
		   		//check the alarm status
		   	
		   		if(WelcomeActivity.myMobiQreceiver!=null){
		   		//if (MobiQBroadcastReceiver.am !=null){
		   			
		   		 Toast.makeText(context, "scheduler active", Toast.LENGTH_SHORT).show();
		   			
		   			
		   		}
		   		else{
		   			
		   		 Toast.makeText(context, "scheduler at sleep", Toast.LENGTH_SHORT).show();
		   		}
		   		
	
	*/
	     
	 finish();
	
}


//===========================================================================================================================	

public void onClickButtonView (View view){           //view the web based query of the application database table
	
	
	ConnectionManager cm = new ConnectionManager(this);
	
	if(cm.isNetworkAvailable()){
	
	   
	    Uri uri = Uri.parse(databaseViewUrl);
	  //  Uri uri = Uri.parse("http://suol2suol.netii.net/survey/mobiqdata.html");
		   Intent intent2 = new Intent(Intent.ACTION_VIEW, uri); 
		   startActivity(intent2);	 
		   
		 //close application
		finish();
		   
	}
	
	else{ // if there is no network connectivity
		
		   Toast.makeText(context, "No network connection: enable Wi-Fi or mobile data", Toast.LENGTH_LONG).show();
	}
		   

	
}

//===========================================================================================================================	

public void onClickButtonSleep (View view){          

   // put the app to sleep by canceling the pending alarms 
	
	
	if(isSleeping){
		
		Toast.makeText(context, "scheduler is already suspended", Toast.LENGTH_SHORT).show();
	}
	
	else{
		
		ResetResults r = new ResetResults();
		r.CancelAlarms(getApplicationContext());
		
		isSleeping=true;
	}
	
	
		
	//finish();

}



//===========================================================================================================================	

//===========================================================================================================================	


public void onClickButtonResume (View view){   // cancel all current alarms and close application
	
	 
//check the alarm status
	
	
if(!isSleeping){
		
		Toast.makeText(context, "scheduler is already active", Toast.LENGTH_SHORT).show();
	}
	
	else{
		
		WelcomeActivity.myMobiQreceiver.setAlarm(context);
			
     Toast.makeText(context, "scheduler is now reactivated", Toast.LENGTH_SHORT).show();
     
     
     
     
     isSleeping=false;
	}
	
	
    
	
	
}


//===========================================================================================================================




public void resetWeeklySurveys()

{
	
	
	// weekly questions shared preferences
	
	   SharedPreferences.Editor weeklyResults = getSharedPreferences("weeklyResults", 0).edit();
		weeklyResults.putBoolean("weeklyTobacco", false);
		weeklyResults.putBoolean("weeklyAlcohol",false);
		weeklyResults.putBoolean("weeklyCannabis",false);
		weeklyResults.putBoolean("weeklyOther",false);
		weeklyResults.putBoolean("weeklyEcstacy", false);
		weeklyResults.putBoolean("weeklySpeed",false);
		weeklyResults.putBoolean("weeklyMeph",false);
		weeklyResults.putBoolean("weeklyPills",false);
		weeklyResults.putBoolean("weeklyMix", false);
		
		weeklyResults.commit();   

		//sports/club shared preferences
		
		SharedPreferences.Editor weeklySportsClubsResults = getSharedPreferences("sportsClubResults", 0).edit();
		weeklySportsClubsResults.putBoolean("anySports", false);
		weeklySportsClubsResults.putBoolean("anyClubs",false);
		weeklySportsClubsResults.putBoolean("anyNonClubs",false);
		weeklySportsClubsResults.commit();

	// sports activity selection
		
		
		String data [];
		data = getResources().getStringArray(R.array.sports_array);
		for (int i =0; i<data.length; i++){
			
	     SharedPreferences.Editor sports_results = getSharedPreferences("regularSportsResults", 0).edit();
   	 sports_results.putBoolean("sports"+i, false);
   	 sports_results.commit();   
		}
		
//clubs activity selection
		
		
		String data1 [];
		data1 = getResources().getStringArray(R.array.club_array);
		for (int i =0; i<data1.length; i++){
			
			SharedPreferences.Editor clubs_results = getSharedPreferences("regularClubsResults", 0).edit();
    	clubs_results.putBoolean("clubs"+i, false);
    	 clubs_results.commit();  
		}
		
//non clubs activity selection
		
		
		String data2 [];
		data2 = getResources().getStringArray(R.array.non_club_array);
		for (int i =0; i<data2.length; i++){
			
			SharedPreferences.Editor non_clubs_results = getSharedPreferences("regularNonClubsResults", 0).edit();
   	non_clubs_results.putBoolean("non_clubs"+i, false);
   	 non_clubs_results.commit();   
		}


//number of times sports	
		data = getResources().getStringArray(R.array.sports_array);
		for (int i =0; i<data.length; i++){
			
			 SharedPreferences.Editor num_times_results = getSharedPreferences("numberOfTimesSport", 0).edit();
 		 num_times_results.putString("sports"+i, "none");
 		 num_times_results.commit(); 
		}	
		
//number of times clubs	
					data = getResources().getStringArray(R.array.club_array);
					for (int i =0; i<data.length; i++){
						
						 SharedPreferences.Editor num_times_results = getSharedPreferences("numberOfTimesClubs", 0).edit();
		        		 num_times_results.putString("clubs"+i, "none");
			    		 num_times_results.commit(); 
					}	
//number of times non clubs 	
					data = getResources().getStringArray(R.array.non_club_array);
					for (int i =0; i<data.length; i++){
						
						 SharedPreferences.Editor num_times_results = getSharedPreferences("numberOfTimesNonClubs", 0).edit();
		        		 num_times_results.putString("non_clubs"+i, "none");
			    		 num_times_results.commit(); 
					}	
	
//names of other non listed activities
					
					SharedPreferences.Editor non_listed_activities = getSharedPreferences("nonListedActivities", 0).edit();
					non_listed_activities.putString("otherSportsName", "none");
					non_listed_activities.putString("otherClubsName", "none");
					non_listed_activities.putString("otherNonClubsName", "none");
					non_listed_activities.commit();
					
					
	
	
}




}
