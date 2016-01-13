package com.csit.packages.mobilesurvey;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.PowerManager;

public class CallLogHttpService extends IntentService{
	
	private Context context;
	//String imei="";

	
	String fileUploadUrl;
	
	
	//File sdCard = Environment.getExternalStorageDirectory();  //get handle on SD card location
    //File myFile = new File(sdCard, "mobiQSurveylocation.txt"); 
    //File myLocFile = new File(sdCard, "mobiqCalls-test.txt");
    
//=================================for internal file storage==========================================04-December-2013======================================
    
    
    String privateFileName = "priv_mobiqCalls";
 // writing to internal file	
	    			
    SaveToFile stf;
	



//====================================================================================================================================================
	

	public CallLogHttpService() {
		super("CallLogHttpService");
		
		}

	       

	@Override
	public void onCreate(){
		
		
		super.onCreate();
		new Handler();
		context=getApplicationContext();
		
	
		fileUploadUrl=getResources().getString(R.string.calls_file_url);
		
//=================================for internal file storage==========================================04---December--------2013======================================
	
		
		  String id;
		  SharedPreferences getid = getSharedPreferences("deviceinfo", 0);
		  id=getid.getString("id", "none");
		  

		  privateFileName=privateFileName+id+".txt";  
			    			
		  stf = new SaveToFile(context, privateFileName);
			



//====================================================================================================================================================

		
		
	};
	
	
	
	//public void onDestroy(){
		
	//	super.onDestroy();
		//	}
	
	@SuppressLint("Wakelock")
	@Override
	protected void onHandleIntent(Intent intent) {
					
		//  mHandler.post(new DisplayToast("Starting Call Log http service.........."));
		         
				PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
				PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
				//Acquire the lock
				wl.acquire();	
		
		/*	
		    	TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		    	imei=tm.getDeviceId();		
		    	
		    	
		    	
		    	 //retrieve from shared preferences imei if deviceid could not be accessed
		  	  //------------------------------------------------------------------  	
		  	    	if (imei.equalsIgnoreCase("")){
		  	    		
		  	    		 SharedPreferences getid = getSharedPreferences("deviceinfo", 0);
		  	   		     imei=getid.getString("id", "INACCESSIBLE");	
		  	    	}
		  	  //-------------------------------------------------------------------
		    	
		    	
		  */  	
		    	
		    	
		         
				ConnectionManager connection = new ConnectionManager(context);
					
					if(connection.isNetworkAvailable()){   // if network detected
						
						//---------------------------------------------------------------------------------------------------------------------					
							
											boolean containsData=false;
											String contents="";										
									
																		
							
//=============================for internal file storage==================04---December 2013================================================================			

								             contents=stf.read();	
								             if (contents.contains("#") || contents.contains("##")){
													
										           containsData=true;
										           
									         }	
								             
											
//=============================for internal file storage==================04---December 2013================================================================			
													
							
							if(containsData){
								
								//upload file to database server
								
								FileUploader fu = new FileUploader(context);							
								        
								fu.uploadFile(stf.filepath, fileUploadUrl);	
								stf.clear();      // clear internal file
							//	stf.clearExternalFile("mobiqCalls.txt");  // clear local file
																						
							         }  // end if it containsData
											
											
//---------------------------------------------------------------------------------------------------------------------							
		
						
						// mHandler.post(new DisplayToast("NETWORK is AVAILABLE for CallLogHttpService"));
						// saveLocalFile(myFile, locationNVP, true);
						// mHandler.post(new DisplayToast("Local storage location: " + myFile.toString())); 
					
				    	
				    	       }//  end if network connection available
						
						//==========================
						
								
					
					 else    // if network is not available
					         {
						 
									
						  // Show network not available message in DisplayToast
						  //  mHandler.post(new DisplayToast("NO NETWORK AVAILABLE for CallLogHttpService"));
					
					 
					        }// end else				
					wl.release();
	}

	
	

}
