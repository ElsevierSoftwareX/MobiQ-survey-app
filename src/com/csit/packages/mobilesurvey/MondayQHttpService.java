package com.csit.packages.mobilesurvey;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;

public class MondayQHttpService extends IntentService{
	

	
	private Context context;
	//private Handler mHandler;    // handler to send message to a toast in a new thread

	String postUrl;
	String fileUploadUrl;
	
		
	//File sdCard = Environment.getExternalStorageDirectory();  //get handle on SD card location
   // File myLocFile= new File (sdCard, "mobiqMonday.txt");
    
    
    
    //=================================for internal file storage==========================================04---December--------2013======================================
    
    
    String privateFileName = "priv_mobiqMonday";
 // writing to internal file	
	    			
    SaveToFile stf;
	
    Boolean file_uploaded=false;


//====================================================================================================================================================

   

	public MondayQHttpService() {
		super("MondayQHttpService");
		
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
		     Toast.makeText(getApplicationContext(), mText, Toast.LENGTH_SHORT).show();
		  }
		}
		
		*/

private StringBuilder inputStreamToString(InputStream is){
		
		String line =" ";
		StringBuilder total = new StringBuilder();
		
		//wrap a buffered reader around the input stream
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		//read response until end
		try {
			while ((line = rd.readLine())!= null){
				total.append(line);
				
						}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return full string
		
		return total;
	
     } // end of inputStringtoString method 
	
	
public String toYesNo(boolean yon ){
	String yesorno;
	if (yon)
		yesorno="yes";
	else if (!yon)
		yesorno="no";
	else
		yesorno=null;
	return yesorno;
}
	private List<NameValuePair>  getMondayQandA(){
		
		SharedPreferences getResults = getSharedPreferences("everResults", 0);
		
		String everTobacco=toYesNo(getResults.getBoolean("everTobacco", false));
		String everAlcohol=toYesNo(getResults.getBoolean("everAlcohol",false));
		String everCannabis=toYesNo(getResults.getBoolean("everCannabis",false));
		String everEcstacy=toYesNo(getResults.getBoolean("everEcstacy", false));
		String everSpeed=toYesNo(getResults.getBoolean("everSpeed",false));
		String everMeph=toYesNo(getResults.getBoolean("everMeph",false));
		String everPills=toYesNo(getResults.getBoolean("everPills",false));
		String everMix=toYesNo(getResults.getBoolean("everMix", false));
		
		

		
		
		
		// weekly results
		
		
		 SharedPreferences weeklyResults = getSharedPreferences("weeklyResults", 0);
			
		    String weeklyTobacco=toYesNo(weeklyResults.getBoolean("weeklyTobacco", false));
			String weeklyAlcohol=toYesNo(weeklyResults.getBoolean("weeklyAlcohol",false));
			String weeklyCannabis=toYesNo(weeklyResults.getBoolean("weeklyCannabis",false));
			String weeklyEcstacy=toYesNo(weeklyResults.getBoolean("weeklyEcstacy", false));
			String weeklySpeed=toYesNo(weeklyResults.getBoolean("weeklySpeed",false));
			String weeklyMeph=toYesNo(weeklyResults.getBoolean("weeklyMeph",false));
			String weeklyPills=toYesNo(weeklyResults.getBoolean("weeklyPills",false));
			String weeklyMix=toYesNo(weeklyResults.getBoolean("weeklyMix", false));
		
			SharedPreferences info = getSharedPreferences("deviceInfo", 0);
	 		String imei=info.getString("imei", "");
		

	    	 //retrieve from shared preferences imei if deviceid could not be accessed
		  	  //------------------------------------------------------------------  	
		  	    	if (imei.equalsIgnoreCase("")){
		  	    		
		  	    		 SharedPreferences getid = getSharedPreferences("deviceinfo", 0);
		  	   		     imei=getid.getString("id", "INACCESSIBLE");	
		  	    	}
		  	  //-------------------------------------------------------------------
		    	

	 		
	 //**********************************************************************************************	          
	    	 //cipher the imei
	    	  Cipher c = new Cipher (imei);
	    	  imei=c.make();
			  
//**********************************************************************************************	
	  
		
				
		
		
		List<NameValuePair> nvp= new ArrayList<NameValuePair>();
    	    	   	 
    	  Calendar cal = Calendar.getInstance();
    	  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	  String formattedDate = df.format(cal.getTime());
    	  
    	  
    	   // String id="";
    		//TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    	//	id=tm.getDeviceId();

    	   	  
 		     //nvp.add(new BasicNameValuePair("Day", "Monday"));
    	 
	    	 nvp.add(new BasicNameValuePair("imei", imei));
	    	 nvp.add(new BasicNameValuePair("time", formattedDate));
	    	 nvp.add(new BasicNameValuePair("EverTobacco", everTobacco));
	    	 nvp.add(new BasicNameValuePair("EverAlcohol", everAlcohol));
	    	 nvp.add(new BasicNameValuePair("EverCannabis", everCannabis));
	    	 nvp.add(new BasicNameValuePair("EverXTC", everEcstacy));
	    	 nvp.add(new BasicNameValuePair("EverSpeed", everSpeed));
	    	 nvp.add(new BasicNameValuePair("EverMeph", everMeph));
	    	 nvp.add(new BasicNameValuePair("EverLegalPills", everPills));
	    	 nvp.add(new BasicNameValuePair("EverlegalSmoke", everMix));
	    	 nvp.add(new BasicNameValuePair("LastWeekTobacco", weeklyTobacco));
	    	 nvp.add(new BasicNameValuePair("LastWeekAlcohol", weeklyAlcohol));
	    	 nvp.add(new BasicNameValuePair("LastWeekCannabis", weeklyCannabis));
	    	 nvp.add(new BasicNameValuePair("LastWeekXTC", weeklyEcstacy));
	    	 nvp.add(new BasicNameValuePair("LastWeekSpeed", weeklySpeed));
	    	 nvp.add(new BasicNameValuePair("LastWeekMeph", weeklyMeph));
	    	 nvp.add(new BasicNameValuePair("LastWeekLegalPills", weeklyPills));
	    	 nvp.add(new BasicNameValuePair("LastWeeklegalSmoke", weeklyMix));
	    	 
	    	 
	    	
	 
	    	 return	 nvp; 
	}  // end of getCurrentLocation Method
	
	
	@Override
	public void onCreate(){
		super.onCreate();
		//mHandler = new Handler();
		context=getApplicationContext();
		
		postUrl=getResources().getString(R.string.monday_post_url);
		fileUploadUrl=getResources().getString(R.string.monday_file_url);
		
		
		
	 //=================================for internal file storage==========================================04---December--------2013======================================
	    
	    
          String privateFileName = "priv_mobiqMonday";
        
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
					
		//  mHandler.post(new DisplayToast("Starting ModayQ http service........"));
		    //    mHandler.post(new DisplayToast("Saving Monday survey results........"));             
				PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
				PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
				//Acquire the lock
				wl.acquire();	
		
						
		           ConnectionManager connection = new ConnectionManager(context);
					
					if(connection.isNetworkAvailable()){   // if network detected
			
						
						//---------------------------------------------------------------------------------------------------------------------					
						
						                 
											Boolean containsData=false;
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
								if (fu.serverResponseCode==200){
								  //  mHandler.post(new DisplayToast("Mondayfileuploaded  code 200 ok " ));
								    file_uploaded=true;
									
								}
								
								
								
							      }  // end if it containsData
							
							
							
							
							   if(file_uploaded){
								 stf.clear();      // clear internal file
								// stf.clearExternalFile("mobiqMonday.txt");  // clear local file
							   }	
								
								
								
					//---------------------------------------------------------------------------------------------------------------------							
											
											
					
						
						
					// insert code for post method here===========>
						 List<NameValuePair> locationNVP = getMondayQandA();
						 post(postUrl, locationNVP);
						
				
					     saveLocalFile(locationNVP, true);
					//	 mHandler.post(new DisplayToast("Local storage location: " + myFile.toString())); 
					
				    	
				    	       }//  end if
						
						//==========================
						
								
					
					 else    // if network is not available
					         {
						 
							
						
						    // Show network not available message in DisplayToast
						   // mHandler.post(new DisplayToast("NO NETWORK AVAILABLE for MondayQHttpService"));
						    
						 // insert code for local storage method here===========>	
						    
						    List<NameValuePair> locationNVP = getMondayQandA();
						    saveLocalFile(locationNVP, false);
					        }
					
							
							wl.release();
	}
	
	 public void post (String url, List<NameValuePair> nameValuePairs){
		 
		 
		 HttpClient httpclient = new DefaultHttpClient();
	     HttpPost httppost = new HttpPost(url);
	     try {
		 
		 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
		 HttpResponse response=httpclient.execute(httppost);
		
		 
		 StringBuilder resp =inputStreamToString(response.getEntity().getContent());
			
			if (resp!= null){
			
						
			//mHandler.post(new DisplayToast("HTTP RESPONSE for MondayQHttpService:" + postResponse));
			}
			
			else 
				{
				   
				
				//mHandler.post(new DisplayToast("NO HTTP RESPONSE RECEIVED for MondayQHttpService"));
				
				}
		 
		 
	       } catch (ClientProtocolException e){ }	
	        catch (IOException e) {   }
		 
		 
	 } // end of post method
	 
	 
	
	 
	 public void saveLocalFile(List<NameValuePair> locationNVP, Boolean isNetworkAvailable){
		 
		 Boolean networkAvailable=isNetworkAvailable;
		
		 
		 
		 
if(!networkAvailable){
		 try
	     {

	    
			  BufferedWriter out = new BufferedWriter(new FileWriter(stf.filepath, true));
	    
	     
	     for (NameValuePair nvp :locationNVP){ 
	     	   //	out.write(outputValues + "\n");
	     	 out.write(nvp.getName() +"=" + nvp.getValue()+ "#");
	     	}
	     

	     out.write("#");
	     out.close();

	  } catch (IOException e){ }
		 
		 
		// stf.copyToSdCardFile("mobiqMonday.txt");

	          
}//end if not network available

} // end of saveLocalFiles
	
	

}
