package com.csit.packages.mobilesurvey;

import android.content.SharedPreferences;

import android.content.Context;


public class ResetResults {
	
	
	
	public ResetResults(){
		
	}
	
	
	public void resetWeeklySurveys(Context context)

	{
		
		
		// weekly questions shared preferences
		
		SharedPreferences.Editor weeklyResults = context.getSharedPreferences("weeklyResults", 0).edit();
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
			
			SharedPreferences.Editor weeklySportsClubsResults = context.getSharedPreferences("sportsClubResults", 0).edit();
			weeklySportsClubsResults.putBoolean("anySports", false);
			weeklySportsClubsResults.putBoolean("anyClubs",false);
			weeklySportsClubsResults.putBoolean("anyNonClubs",false);
			weeklySportsClubsResults.commit();

		// sports activity selection
			
			
			String data [];
			data = context.getResources().getStringArray(R.array.sports_array);
			for (int i =0; i<data.length; i++){
				
		     SharedPreferences.Editor sports_results = context.getSharedPreferences("regularSportsResults", 0).edit();
		     sports_results.putBoolean("sports"+i, false);
		     sports_results.commit();   
			}
			
	//clubs activity selection
			
			
			String data1 [];
			data1 = context.getResources().getStringArray(R.array.club_array);
			for (int i =0; i<data1.length; i++){
				
			SharedPreferences.Editor clubs_results = context.getSharedPreferences("regularClubsResults", 0).edit();
	    	clubs_results.putBoolean("clubs"+i, false);
	    	clubs_results.commit();  
			}
			
	//non clubs activity selection
			
			
			String data2 [];
			data2 = context.getResources().getStringArray(R.array.non_club_array);
			for (int i =0; i<data2.length; i++){
				
				SharedPreferences.Editor non_clubs_results = context.getSharedPreferences("regularNonClubsResults", 0).edit();
				non_clubs_results.putBoolean("non_clubs"+i, false);
				non_clubs_results.commit();   
			}


	//number of times sports	
			data = context.getResources().getStringArray(R.array.sports_array);
			for (int i =0; i<data.length; i++){
				
				 SharedPreferences.Editor num_times_results = context.getSharedPreferences("numberOfTimesSport", 0).edit();
				 num_times_results.putString("sports"+i, "none");
				 num_times_results.commit(); 
			}	
			
	//number of times clubs	
						data = context.getResources().getStringArray(R.array.club_array);
						for (int i =0; i<data.length; i++){
							
							 SharedPreferences.Editor num_times_results = context.getSharedPreferences("numberOfTimesClubs", 0).edit();
			        		 num_times_results.putString("clubs"+i, "none");
				    		 num_times_results.commit(); 
						}	
	//number of times non clubs 	
						data = context.getResources().getStringArray(R.array.non_club_array);
						for (int i =0; i<data.length; i++){
							
							 SharedPreferences.Editor num_times_results = context.getSharedPreferences("numberOfTimesNonClubs", 0).edit();
			        		 num_times_results.putString("non_clubs"+i, "none");
				    		 num_times_results.commit(); 
						}	
		
		
						
//names of other non listed activities
						
						SharedPreferences.Editor non_listed_activities = context.getSharedPreferences("nonListedActivities", 0).edit();
						non_listed_activities.putString("otherSportsName", "none");
						non_listed_activities.putString("otherClubsName", "none");
						non_listed_activities.putString("otherNonClubsName", "none");
						non_listed_activities.commit();					
		
		
	}  // end reset weekly surveys
	
	
	public void resetAllSurveys(Context context)

	{
		
		
		 //===========clear  private files=============================================================
		 
		 
		 String id;
		  SharedPreferences getid = context.getSharedPreferences("deviceinfo", 0);
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
		 
		
		
		 
		 
		 
		 
		 //initialize survey states
 		SharedPreferences.Editor mondaySurveyTaken = context.getSharedPreferences("mondaySurveyTaken", 0).edit();
 		mondaySurveyTaken.putBoolean("taken", false);
 		mondaySurveyTaken.commit();
 		
 		SharedPreferences.Editor thursdaySurveyTaken = context.getSharedPreferences("thursdaySurveyTaken", 0).edit();
 		thursdaySurveyTaken.putBoolean("taken", false);
 		thursdaySurveyTaken.commit();
		
		
		
		
		//user selection mode shared preferences
		
		SharedPreferences.Editor userMode = context.getSharedPreferences("userMode", 0).edit();
		userMode.putBoolean("Admin", false);
		userMode.commit();
		
		
		
		
		//start welcome screen shared preferences
		
		SharedPreferences.Editor welcomeScreenShow = context.getSharedPreferences("welcomeScreenShow", 0).edit();
		welcomeScreenShow.putBoolean("show", true);
		welcomeScreenShow.commit();
		
		
		
		// ever questions shared preferences
		
		  SharedPreferences.Editor everResults = context.getSharedPreferences("everResults", 0).edit();
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
		
		SharedPreferences.Editor weeklyResults = context.getSharedPreferences("weeklyResults", 0).edit();
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
			
			SharedPreferences.Editor weeklySportsClubsResults = context.getSharedPreferences("sportsClubResults", 0).edit();
			weeklySportsClubsResults.putBoolean("anySports", false);
			weeklySportsClubsResults.putBoolean("anyClubs",false);
			weeklySportsClubsResults.putBoolean("anyNonClubs",false);
			weeklySportsClubsResults.commit();

		// sports activity selection
			
			
			String data [];
			data = context.getResources().getStringArray(R.array.sports_array);
			for (int i =0; i<data.length; i++){
				
		     SharedPreferences.Editor sports_results = context.getSharedPreferences("regularSportsResults", 0).edit();
	   	 sports_results.putBoolean("sports"+i, false);
	   	 sports_results.commit();   
			}
			
	//clubs activity selection
			
			
			String data1 [];
			data1 = context.getResources().getStringArray(R.array.club_array);
			for (int i =0; i<data1.length; i++){
				
				SharedPreferences.Editor clubs_results = context.getSharedPreferences("regularClubsResults", 0).edit();
	    	clubs_results.putBoolean("clubs"+i, false);
	    	 clubs_results.commit();  
			}
			
	//non clubs activity selection
			
			
			String data2 [];
			data2 = context.getResources().getStringArray(R.array.non_club_array);
			for (int i =0; i<data2.length; i++){
				
				SharedPreferences.Editor non_clubs_results = context.getSharedPreferences("regularNonClubsResults", 0).edit();
	   	non_clubs_results.putBoolean("non_clubs"+i, false);
	   	 non_clubs_results.commit();   
			}


	//number of times sports	
			data = context.getResources().getStringArray(R.array.sports_array);
			for (int i =0; i<data.length; i++){
				
				 SharedPreferences.Editor num_times_results = context.getSharedPreferences("numberOfTimesSport", 0).edit();
	 		 num_times_results.putString("sports"+i, "none");
	 		 num_times_results.commit(); 
			}	
			
	//number of times clubs	
						data = context.getResources().getStringArray(R.array.club_array);
						for (int i =0; i<data.length; i++){
							
							 SharedPreferences.Editor num_times_results = context.getSharedPreferences("numberOfTimesClubs", 0).edit();
			        		 num_times_results.putString("clubs"+i, "none");
				    		 num_times_results.commit(); 
						}	
	//number of times non clubs 	
						data = context.getResources().getStringArray(R.array.non_club_array);
						for (int i =0; i<data.length; i++){
							
							 SharedPreferences.Editor num_times_results = context.getSharedPreferences("numberOfTimesNonClubs", 0).edit();
			        		 num_times_results.putString("non_clubs"+i, "none");
				    		 num_times_results.commit(); 
						}	
		
//names of other non listed activities
						
						SharedPreferences.Editor non_listed_activities = context.getSharedPreferences("nonListedActivities", 0).edit();
						non_listed_activities.putString("otherSportsName", "none");
						non_listed_activities.putString("otherClubsName", "none");
						non_listed_activities.putString("otherNonClubsName", "none");
						non_listed_activities.commit();				
		
		
	}  // end reset all surveys
	
	
	
	//===========================================================================================================================	
	
	
	public void CancelAlarms(Context context){
		
		
		WelcomeActivity.myMobiQreceiver.CancelAlarm(context);
		
		
	//scheduler sleep/active state shared preferences
		
		SharedPreferences.Editor schedulerstate = context.getSharedPreferences("schedulerState", 0).edit();
		schedulerstate.putBoolean("Active", false);
		schedulerstate.commit();
		
		
	}
	
	//===========================================================================================================================	
}
