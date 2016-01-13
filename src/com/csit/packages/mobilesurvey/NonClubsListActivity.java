package com.csit.packages.mobilesurvey;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

public class NonClubsListActivity extends Activity {
	
	private LinearLayout checkboxLayout;
    private String[] data;
    Boolean other_selected=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);
		checkboxLayout = (LinearLayout) findViewById(R.id.Checkbox_Layout);
        data = getResources().getStringArray(R.array.non_club_array);

        for (int i = 0; i < data.length; i++) {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(data[i]);
            cb.setTextColor(Color.BLUE);
            checkboxLayout.addView(cb);
        }

		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
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
	
	
	
	


public void button_click(View view) {

	Boolean checked=false;

	 for (int i = 0; i < checkboxLayout.getChildCount(); i++) {
        if (checkboxLayout.getChildAt(i) instanceof CheckBox) {
            CheckBox cb = (CheckBox) checkboxLayout.getChildAt(i);
            if (cb.isChecked()) {
                checked=true;
            	
            	if (cb.getText().toString().contains("Other")){
            		
            		other_selected=true;
            		
            	}
            	
            	SharedPreferences.Editor non_clubs_results = getSharedPreferences("regularNonClubsResults", 0).edit();
            	non_clubs_results.putBoolean("non_clubs"+i, true);
            	non_clubs_results.commit();   
            }
            else{
            	SharedPreferences.Editor non_clubs_results = getSharedPreferences("regularNonClubsResults", 0).edit();
            	non_clubs_results.putBoolean("non_clubs"+i, false);
            	non_clubs_results.commit();   
            }
        }
    }//end for

  
	 
   if(checked==true){
   
// Intent intent = new Intent(this, NonClubsListActivity.class);
 Intent intent = new Intent(this, NonClubsRegularActivity.class);	     
 
 if (other_selected){
	  intent.putExtra("other", true);
	  
 }
 else {intent.putExtra("other", false);}
 
 
 
 startActivity(intent);
		 
		 finish();
		    //Todo
 		    
 		    // store results
		 
     }
   
   else{
   	
   	  Toast.makeText(getApplicationContext(), "please select one or more options", Toast.LENGTH_SHORT).show();
   	
   }


}




}
