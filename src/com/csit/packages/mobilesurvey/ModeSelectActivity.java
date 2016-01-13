package com.csit.packages.mobilesurvey;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ModeSelectActivity extends Activity {
	
	
	
	  Button btnAdminSelect;
	  Button btnUserMode;
	  Button btnExitModeSelect;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mode_select);
		
		btnAdminSelect=(Button)this.findViewById(R.id.btnAdminSelect);
		btnUserMode=(Button)this.findViewById(R.id.btnUserMode);
		btnExitModeSelect=(Button)this.findViewById(R.id.btnExitModeSelect);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mode_select, menu);
		return true;
	}
	
	
	

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode==KeyEvent.KEYCODE_HOME)
	    {
	    	//showToast();
	    }
	    return super.onKeyDown(keyCode, event);
	}
	


	public void onClickAdminSelect(View v) {
	     // TODO Auto-generated method stub
	     	    
	          
		  Intent intent = new Intent(getApplicationContext(), AdminLoginActivity.class);
   		
		  startActivity(intent);
		
		  finish();
	         
	     
	}
	
	
	public void onClickUserSelect(View v) {
	     // TODO Auto-generated method stub
		
		
		   Intent intent = new Intent(getApplicationContext(), UserInterfaceActivity.class);
   		
		   startActivity(intent);
	     	    
	        finish();
	         
	     
	}
	
	
	public void onClickExitModeSelect(View v) {
	     // TODO Auto-generated method stub
	     	    
	           finish();
	         
	     
	}
	
	

}
