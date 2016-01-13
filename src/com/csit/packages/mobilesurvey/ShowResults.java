package com.csit.packages.mobilesurvey;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ShowResults extends Activity {
	

	Context context;
    TextView mainText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_results);
		
		
		context=getApplicationContext();
		
		mainText = (TextView)findViewById(R.id.mainText);
		mainText.setText("");

		
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_results, menu);
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
	
	
	
	
	
	
public void onClickShow (View view){
		
	
	
	StringBuilder collectResults = new StringBuilder();
	
	SharedPreferences getResults = getSharedPreferences("everResults", 0);
	 collectResults.append("***************************" + "\n");
	collectResults.append("Ever Used Results" + "\n");
	collectResults.append("***************************" + "\n");
	collectResults.append("everTobacco" + "  ");
	collectResults.append(getResults.getBoolean("everTobacco", false));
	collectResults.append("\n");
	collectResults.append("everAlcohol" + "  ");
	collectResults.append(getResults.getBoolean("everAlcohol",false));
	collectResults.append("\n");
	collectResults.append("everCannabis" + "  ");
	collectResults.append(getResults.getBoolean("everCannabis",false));
	collectResults.append("\n");
	collectResults.append("everOther" + "  ");
	collectResults.append(getResults.getBoolean("everOther",false));
	collectResults.append("\n");
	collectResults.append("everEcstacy" + "  ");
	collectResults.append(getResults.getBoolean("everEcstacy", false));
	collectResults.append("\n");
	collectResults.append("everSpeed" + "  ");
	collectResults.append(getResults.getBoolean("everSpeed",false));
	collectResults.append("\n");
	collectResults.append("everMeph" + "  ");
	collectResults.append(getResults.getBoolean("everMeph",false));
	collectResults.append("\n");
	collectResults.append("everPills" + "  ");
	collectResults.append(getResults.getBoolean("everPills",false));
	collectResults.append("\n");
	collectResults.append("everMix" + "  ");
	collectResults.append(getResults.getBoolean("everMix", false));
	collectResults.append("\n");
	

	
	
	
// weekly results
	
	
	 SharedPreferences weeklyResults = getSharedPreferences("weeklyResults", 0);
	 collectResults.append("***************************" + "\n");
	 collectResults.append("Weekly Used Results" + "\n");
	 collectResults.append("***************************" + "\n");
	 collectResults.append("weeklyTobacco" + "  ");
	 collectResults.append(weeklyResults.getBoolean("weeklyTobacco", false) + "\n");
	 collectResults.append("weeklyAlcohol" + "  ");
	 collectResults.append(weeklyResults.getBoolean("weeklyAlcohol", false) + "\n");
	 collectResults.append("weeklyCannabis" + "  ");
	 collectResults.append(weeklyResults.getBoolean("weeklyCannabis", false) + "\n");
	 collectResults.append("weeklyOther" + "  ");
	 collectResults.append(weeklyResults.getBoolean("weeklyOther", false) + "\n");	
	 collectResults.append("weeklyEcstacy" + "  ");
	 collectResults.append(weeklyResults.getBoolean("weeklyEcstacy", false) + "\n");
	 collectResults.append("weeklySpeed" + "  ");
	 collectResults.append(weeklyResults.getBoolean("weeklySpeed", false) + "\n");
	 collectResults.append("weeklyMeph" + "  ");
	 collectResults.append(weeklyResults.getBoolean("weeklyMeph", false) + "\n");
	 collectResults.append("weeklyPills" + "  ");
	 collectResults.append(weeklyResults.getBoolean("weeklyPills", false) + "\n");
	 collectResults.append("weeklyMix" + "  ");
	 collectResults.append(weeklyResults.getBoolean("weeklyMix", false) + "\n");	
	 
		
// sports and activities results

		
		SharedPreferences weeklySportsClubsResults = getSharedPreferences("sportsClubResults", 0);
		 collectResults.append("***************************" + "\n");
		collectResults.append("Weekly sports and activities results" + "\n");
		collectResults.append("***************************" + "\n");
		 collectResults.append("anySports" + "  ");
		 weeklySportsClubsResults = getSharedPreferences("sportsClubResults", 0);
		 collectResults.append(weeklySportsClubsResults.getBoolean("anySports", false) + "\n");
		collectResults.append("anyClubs" + "  ");
		weeklySportsClubsResults = getSharedPreferences("sportsClubResults", 0);
		collectResults.append(weeklySportsClubsResults.getBoolean("anyClubs", false) + "\n");
		collectResults.append("anyNonClubs" + "  ");
		weeklySportsClubsResults = getSharedPreferences("sportsClubResults", 0);
		collectResults.append(weeklySportsClubsResults.getBoolean("anyNonClubs", false) + "\n");
		
		

// sports selection results	 
		 collectResults.append("***************************" + "\n");
		collectResults.append("Weekly sports" + "\n");
		collectResults.append("***************************" + "\n");
	    SharedPreferences sports_results = context.getSharedPreferences("regularSportsResults", 0);
		String data [];
		data = getResources().getStringArray(R.array.sports_array);
		for (int i =0; i<data.length; i++){
			
	 
	     collectResults.append(data [i] + "  ");
	   //  sports_results.getBoolean("sports"+i, false);
	     collectResults.append(sports_results.getBoolean("sports"+i, false) + " \n");
      	  
		}
		
// sports number of times results	 
		 collectResults.append("***************************" + "\n");		
		 collectResults.append("Weekly sports frequency" + "\n");
		 collectResults.append("***************************" + "\n");
		SharedPreferences sports_results2 = context.getSharedPreferences("numberOfTimesSport", 0);
		data = getResources().getStringArray(R.array.sports_array);
		for (int i =0; i<data.length; i++){
					
			     
			     collectResults.append(data [i] + "  ");
			    // sports_results2.getString("sports"+i, "none");
			     collectResults.append(sports_results2.getString("sports"+i, "none") + " \n");
		      	  
				}		
		
	
// Clubs selection results	 
		 collectResults.append("***************************" + "\n");
		collectResults.append("Weekly clubs" + "\n");
		collectResults.append("***************************" + "\n");				
			
		SharedPreferences clubs_results = context.getSharedPreferences("regularClubsResults", 0);
		String data1 [];
		data1 = getResources().getStringArray(R.array.club_array);
		for (int i =0; i<data1.length; i++){
			
		
		collectResults.append(data1 [i] + "  ");
      // 	clubs_results.getBoolean("clubs"+i, false);
       	collectResults.append(clubs_results.getBoolean("clubs"+i, false) + " \n");
       	 
		}		
				

// Clubs number of times results	 
		 collectResults.append("***************************" + "\n");
		collectResults.append("Weekly clubs frequency" + "\n");
		collectResults.append("***************************" + "\n");
		SharedPreferences num_times_results = context.getSharedPreferences("numberOfTimesClubs", 0);
		data1 = getResources().getStringArray(R.array.club_array);
		for (int i =0; i<data1.length; i++){
		collectResults.append(data1 [i] + "  ");	
	   // num_times_results.getString("clubs"+i, "none");
		collectResults.append(num_times_results.getString("clubs"+i, "none") + " \n");
    
		}
		

// Non  Clubs selection results	 
		 collectResults.append("***************************" + "\n");
		collectResults.append("Weekly non clubs" + "\n");
		collectResults.append("***************************" + "\n");
		SharedPreferences non_clubs_results = context.getSharedPreferences("regularNonClubsResults", 0);
		String data2 [];
		data2 = getResources().getStringArray(R.array.non_club_array);
		for (int i =0; i<data2.length; i++){
		collectResults.append(data2 [i] + "  ");	
		//non_clubs_results.getBoolean("non_clubs"+i, false);
		collectResults.append(non_clubs_results.getBoolean("non_clubs"+i, false) + " \n");  
	
		}
		
// Non Clubs number of times results	
		 collectResults.append("***************************" + "\n");
		collectResults.append("Weekly non clubs frequency" + "\n");
		collectResults.append("***************************" + "\n");	
		 SharedPreferences num_times_results2 = context.getSharedPreferences("numberOfTimesNonClubs", 0);
		data2 = getResources().getStringArray(R.array.non_club_array);
		for (int i =0; i<data2.length; i++){
			collectResults.append(data2 [i] + "  ");	
	   	//	num_times_results2.getString("non_clubs"+i, "none");
	   		collectResults.append(num_times_results2.getString("non_clubs"+i, "none")+ " \n");  
		}
		
		
		mainText.setText(collectResults);		

	
	}
	
	
	public void onClick (View view){
		
		
		Intent intent= new Intent(this, MainActivity.class);
		startActivity(intent);
		
		finish();
		
	}
	
	
//================================================================================================================	
	void postResults(){
		
		
		
		
		
	}

}
