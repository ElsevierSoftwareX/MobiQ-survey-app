package com.csit.packages.mobilesurvey;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class UserInterfaceActivity extends Activity {
	
	
	String locFile="";
	
	//File sdCard = Environment.getExternalStorageDirectory();  //get handle on SD card location
	   // File myFile = new File(sdCard, "mobiQSurveylocation.txt");
	    //File myLogFile= new File (sdCard, "mobiQlog.txt");
	  //  File myLocFile= new File (sdCard, "mobiQloc.txt");
	
	
	  Button btnReport;
	 // Button btnPause;
	  Button btnPicture;
	  Button btnSelectMode;
	  Button btnClose;
	 Context context;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_interface);
		
		
		 btnReport=(Button)this.findViewById(R.id.btnReport);
	//	 btnPause=(Button)this.findViewById(R.id.btnPause);
		 btnPicture=(Button)this.findViewById(R.id.btnTakePic);
		 btnSelectMode=(Button)this.findViewById(R.id.btnSelectMode);
		 btnClose=(Button)this.findViewById(R.id.btnExitUserInt);
		
		
		context=getApplicationContext();
		
		/*
			
			// Get the device id using telephony manager	
				  
		 	   String id="";
		 	  TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
		 	  id=tm.getDeviceId();
               
		 	    SharedPreferences.Editor info = getSharedPreferences("deviceInfo", 0).edit();
		 		info.putString("imei", id);
		 		info.commit();
				
		*/		
		 		
		 		//set location file to owner only writable
		 	//	myLocFile.setWritable(false, true);
		 		//locFile=myLocFile.toString();
		 		
				 
				 
		 	//	ResetResults r = new ResetResults();
			//	r.CancelAlarms(getApplicationContext());
				
				
			
		 		//user selection mode shared preferences
				
				SharedPreferences.Editor userMode = getSharedPreferences("userMode", 0).edit();
				userMode.putBoolean("Admin", false);
				userMode.commit();
				
		 		
			//get welcome screen shared preferences
				
						SharedPreferences getwelcomeScreenShow = getSharedPreferences("welcomeScreenShow", 0);
						if(getwelcomeScreenShow.getBoolean("show", true) ){
							
							
							Intent intent = new Intent(this, WelcomeActivity.class);
					      	startActivity(intent); 
					      	finish();
							
						}
		 
		 
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_interface, menu);
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
	
	
	
	
	

	public void onClickReport(View v) {
	     // TODO Auto-generated method stub
		
		
		Intent intent = new Intent(this, ReportProblemActivity.class);
		startActivity(intent);
		finish();
	         
	     
	}
	
	
/*
	public void onClickShowCode(View v) {
	     // TODO Auto-generated method stub
	     	    
		Intent intent = new Intent(this, ViewCodeActivity.class);
		startActivity(intent);
		finish(); 
		
		//  finish();
	         
	     
	}
	
	*/
	
	public void onClickPicture(View v) {
	     // TODO Auto-generated method stub
	     	    
		  Intent intent = new Intent(context, CameraActivity.class);
		  startActivity(intent); 
		  finish();
	         
	     
	}
	
	
	public void onClickSelectMode(View v) {
	     // TODO Auto-generated method stub
	     	    
		  Intent intent = new Intent(context, ModeSelectActivity.class);
		  startActivity(intent); 
		  finish();
	         
	     
	}
	
	public void onClickExit(View v) {
	     // TODO Auto-generated method stub
	     	    
	           finish();
	         
	     
	}
	
	
	
	

}


