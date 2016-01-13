package com.csit.packages.mobilesurvey;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;

public class SmsLogHttpService extends IntentService{
	
	private Context context;
	//private Handler mHandler;    // handler to send message to a toast in a new thread
	//String imei="";

	
	String fileUploadUrl;
	
	//File sdCard = Environment.getExternalStorageDirectory();  //get handle on SD card location
   // File myFile = new File(sdCard, "mobiQSurveylocation.txt");
   
  //  File myLocFile= new File (sdCard, "mobiqSMS-test.txt");
    
    
//=================================for internal file storage==========================================04---December--------2013======================================
    
    
    String privateFileName = "priv_mobiqSMS";
 // writing to internal file	
	    			
    SaveToFile stf;

//====================================================================================================================================================
	
    
    
	public SmsLogHttpService() {
		super("SmsLogHttpService");
		}

	//  protected void setParentContext (Context context) {
	  //      this.context = context;
	 //   }
       
/*
	private class DisplayToast implements Runnable{
		  String mText;

		  public DisplayToast(String text){
		    mText = text;
		  }

		  public void run(){
		     Toast.makeText(getApplicationContext(), mText, Toast.LENGTH_LONG).show();
		    
		  }
		}
		
	*/
	
	

	
	
	@Override
	public void onCreate(){
		
		
		super.onCreate();
		//mHandler = new Handler();
		context=getApplicationContext();
		
		fileUploadUrl=getResources().getString(R.string.sms_file_url);

		//=================================for internal file storage==========================================04---December--------2013======================================
	    
	
		  String id;
		  SharedPreferences getid = getSharedPreferences("deviceinfo", 0);
		  id=getid.getString("id", "none");

		  privateFileName=privateFileName+id+".txt";  
		
		
		
	    // writing to internal file	
			    			
			stf = new SaveToFile(context, privateFileName);
			



//====================================================================================================================================================

		
	};
	
	
	
	//public void onDestroy(){
		
	//	super.onDestroy();
		//	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
					
		//  mHandler.post(new DisplayToast("Starting Sms http service.........."));
		 // mHandler.post(new DisplayToast("Updating SMS info.........."));
		             
				PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
				PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
				//Acquire the lock
				wl.acquire();	
		
			/*
				TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		    	imei=tm.getDeviceId();		
		    	
		    	
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
								// stf.clearExternalFile("mobiqSMS.txt");  // clear local file
								
								
								
								
							           }  // end if it containsData
											
											
					//---------------------------------------------------------------------------------------------------------------------							
											
															
								 @SuppressWarnings("unused")
								SmsRecorder sr =new SmsRecorder(context);
						
								// stf.copyToSdCardFile("mobiqSMS.txt");
						
					
				    	
				    	       }//  end if  network available
						
						//==========================
						
								
					
					 else    // if network is not available
					         {
						 
				
						 
						 
						           @SuppressWarnings("unused")
								SmsRecorder sr =new SmsRecorder(context);
						           
						      	// stf.copyToSdCardFile("mobiqSMS.txt");
						       
						
					        }
					wl.release();
	   
	
	} // end on handle intent
	
	 
	 	 
	

	
} // end class
