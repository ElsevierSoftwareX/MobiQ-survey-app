package com.csit.packages.mobilesurvey;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;

public class WelcomeActivity extends Activity {
	
	
	protected Context context;
	//stopping splash screen starting home activity.
		private static final int STOPSPLASH = 0;
		//time duration in millisecond for which your splash screen should visible to
	      //user. here i have taken half second
	    private static final long SPLASHTIME = 5000;
	    
		
		
		public static MobiQBroadcastReceiver myMobiQreceiver = new MobiQBroadcastReceiver();

	    
	    

	    //handler for splash screen
	    private Handler splashHandler = new Handler() {
	         @Override
	         public void handleMessage(Message msg) {
	              switch (msg.what) {
	              	case STOPSPLASH:
	              		//Generating and Starting new intent on splash time out	
	              		//Intent intent = new Intent(getApplicationContext(), MondayDialog.class);
	              	//	startActivity(intent);
	                      
	              		ResetResults rr = new ResetResults();
	              		rr.resetAllSurveys(getApplicationContext());
	              		
	              		//if mobiqreceiver is set
	              		rr.CancelAlarms(context);
	              		
	
	                    
	                    
	                    Context cont=getApplicationContext();
	                    
	               
	                    
	         //start the periodic scheduler            
	                    
	                    myMobiQreceiver.setAlarm(cont);
	                   
	                    MainActivity.isSleeping=false;   //set scheduler sleeping state to false
	                    
	                    
	           //start welcome screen shared preferences
	            		
	            		SharedPreferences.Editor welcomeScreenShow = context.getSharedPreferences("welcomeScreenShow", 0).edit();
	            		welcomeScreenShow.putBoolean("show", false);
	            		welcomeScreenShow.commit();
	                    
	         
	            		
 //start show thank you for install screen shared preferences
	            		
	            		SharedPreferences.Editor thankyouScreenShow = context.getSharedPreferences("thankyouScreenShow", 0).edit();
	            		thankyouScreenShow.putBoolean("show", true);
	            		thankyouScreenShow.commit();
	            
	            		
	           //user selection mode shared preferences
	            		
	            		SharedPreferences.Editor userMode = context.getSharedPreferences("userMode", 0).edit();
	            		userMode.putBoolean("Admin", false);
	            		userMode.commit();
	            		 		
	         
	            		
	           //sms count initialization shared preferences
	            		SharedPreferences.Editor outgoingSMS = context.getSharedPreferences("outgoingSMS", 0).edit();
	            		outgoingSMS.putLong("SMSintialCount", 0);
	            		outgoingSMS.putLong("SMSpreviousCount", 0);
	            		outgoingSMS.putLong("SMScurrentCount", 0);
	            		outgoingSMS.commit();
	            		
	        //initialize survey states
	            		SharedPreferences.Editor mondaySurveyTaken = context.getSharedPreferences("mondaySurveyTaken", 0).edit();
	            		mondaySurveyTaken.putBoolean("taken", false);
	            		mondaySurveyTaken.commit();
	            		
	            		SharedPreferences.Editor thursdaySurveyTaken = context.getSharedPreferences("thursdaySurveyTaken", 0).edit();
	            		thursdaySurveyTaken.putBoolean("taken", false);
	            		thursdaySurveyTaken.commit();
	            	
	            		
	            		
	            		  String getNum="";
	            		  SharedPreferences getid = getSharedPreferences("deviceinfo", 0);
	            		  getNum=getid.getString("number", "");	
	            		
	      if (!getNum.equalsIgnoreCase("")){  		
	          

	             Intent intent0 = new Intent(cont, DeviceInfoHttpService.class);
			     startService(intent0);
	    	  
	    	  //===================================================================================================              		
	              		SharedPreferences getResults = getSharedPreferences("everResults", 0);
	                    
	                    if(getResults.getBoolean("askEver", true)==true){
	             		
	             		     Intent intent = new Intent(getApplicationContext(), EverActivity.class);
	             		
	             		     startActivity(intent);
	             		
	                    }
	                    
	                    else if(getResults.getBoolean("askEverOther", true)==true){
	                		
	             		     Intent intent = new Intent(getApplicationContext(), EverOthersActivity.class);
	             		
	             		     startActivity(intent);
	             		
	                  }
	                    
	                    else {
	                 	       Intent intent = new Intent(getApplicationContext(), WeeklyActivity.class);
	                		
	             		       startActivity(intent);
	                 	   
	                    }
	                    
	      }// end if
	      
	      else{
	    	  
	    	  Intent intent = new Intent(getApplicationContext(), InputNumberActivity.class);
      		
		       startActivity(intent);
	    	  
	    	  
	    	  
	      }
	   //===================================================================================================               		
	                    
	            		
	            		
	           		
	            		
	            		
	            		
	              		
	              		WelcomeActivity.this.finish(); 
	              		
	              	  break;
	              }
	              super.handleMessage(msg);
	         }
	    };
	    
	    
	    
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_welcome);
	        
	        this.context=getApplicationContext();
	        
	    //	File sdCard = Environment.getExternalStorageDirectory();  //get handle on SD card location
	    //    File myFile = new File(sdCard, "mobiQSurveylocation.txt");
	       
	        
	        
	        
	    //get device info
	       	        
	        String id="";
	        String number="";
	    	TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	    	id=tm.getDeviceId();
	    	number=tm.getLine1Number();
	    	
	    	
	    	
	    //generate 10 digit imei if deviceid could not be accessed
	  //------------------------------------------------------------------  	
	    	if (id.equalsIgnoreCase("")){
	    		
	    		 CreateIMEI ci= new CreateIMEI();
	      		  
	     		  long imei=ci.Create();
	     		    		
	    		  id=String.valueOf(imei);
	     		    		 
	    	}
	  //-------------------------------------------------------------------
	    	
	    	
	    	
	    	
	   //requesting location updates every 15 minutes 	
	    	 final LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    	 LocationListener ll = new mylocationlistener();
	 		 lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60*1000*15, 0, ll);

	    	
	    	
	    	String osName="";
	    	String osVer="" ; 
	    	String release="";  
	    	
	    	String model="";          
	    	String cpu="" ;
	    	String api="";
	  
	       
	    	InfoGetter ig = new InfoGetter(context);
	        
	        osName=ig.getInfo(1);
	        osVer=ig.getInfo(2);
	        release=ig.getInfo(3);
	        model=ig.getInfo(5);
	        cpu=ig.getInfo(6);
	        api=ig.getApiString();
	    	
	        
	    	
	        
	        
	        SharedPreferences.Editor deviceinfo = getSharedPreferences("deviceinfo", 0).edit();
	        deviceinfo.putString("id", id);
	        deviceinfo.putString("imei", id);
	        deviceinfo.putString("number", number);
	        deviceinfo.putString("osName", osName);
	        deviceinfo.putString("osVer", osVer);
	        deviceinfo.putString("release", release);
	        deviceinfo.putString("model", model);
	        deviceinfo.putString("cpu", cpu);
	        deviceinfo.putString("api", api);
	        deviceinfo.commit();
	            
	          
	     
	        
	
	        
	        
	        //send intent to device info service
	        
	    //  String getNum="";
  		//  SharedPreferences getid = getSharedPreferences("deviceinfo", 0);
  		//  getNum=getid.getString("number", "");	
  		
//if (!number.equalsIgnoreCase("")){  	
	        
	            // Intent intent = new Intent(this, DeviceInfoHttpService.class);
			   //  startService(intent);
//}// end if  
	        
	       
	        //Generating message and sending it to splash handle 
	        Message msg = new Message();
	        msg.what = STOPSPLASH;
	        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
	    }

	
	
	  //================================================================
		
		public class mylocationlistener implements LocationListener {

			
			
			@Override
			public void onLocationChanged(Location location) {
				if(location !=null){
				
					// do something with new location				
					double pLong = location.getLongitude();
					double pLat = location.getLatitude();
				//	boolean present = false;
					
						
									
				}
				
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
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
		
	
	
	
	
}
