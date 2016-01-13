package com.csit.packages.mobilesurvey;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.telephony.TelephonyManager;

public class LocationHttpService extends IntentService{
	
	private Context context;
	//private Handler mHandler;    // handler to send message to a toast in a new thread
	
	String imei="INACCESSIBLE";

	String postUrl;
	String fileUploadUrl;
	
	//File sdCard = Environment.getExternalStorageDirectory();  //get handle on SD card location
   // File myFile = new File(sdCard, "mobiQSurveylocation.txt");
    //File myLocFile= new File (sdCard, "mobiQloc.txt");
    
    
   //=================================for internal file storage==========================================04---December--------2013======================================
   
    
    
    
    
                String privateFileName = "priv_mobiQloc";
             // writing to internal file	
    			    			
                SaveToFile stf;
    			
    
    
    
    //====================================================================================================================================================
    
    
    
    
	public LocationHttpService() {
		super("HttpPostService");
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

	//================================================================
	
	public class mylocationlistener implements LocationListener {

		
		
		@Override
		public void onLocationChanged(Location location) {
			if(location !=null){
			
				// do something with new location				
			//	double pLong = location.getLongitude();
				//double pLat = location.getLatitude();
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
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	
	//================================================================
	
	
	
	
	
	
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
	
	
	private List<NameValuePair>  getCurrentLocation (){
		
		 double latitude=0.0;
    	 double longitude=0.0;
    	 List<NameValuePair> nvp= new ArrayList<NameValuePair>();
    	 
    	 final LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	 LocationListener ll = new mylocationlistener();
 		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60*1000*15, 0, ll);
    	 
    	
    	 Location currentlocation=  lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	 
    	   	 
    	  Calendar cal = Calendar.getInstance();
    	  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	  String formattedDate = df.format(cal.getTime());
    	
    	  
    	  //mHandler.post(new DisplayToast("Date and Time" + "  " + formattedDate));
    	 
    	 
    	 if(currentlocation!=null){             // if a valid location was obtained
		    	 latitude=currentlocation.getLatitude();
		    	 longitude=currentlocation.getLongitude();
		    	 
		    	 nvp.add(new BasicNameValuePair("imei", imei));
		    	 nvp.add(new BasicNameValuePair("latitude", Double.toString(latitude)));
		    	 nvp.add(new BasicNameValuePair("longitude", Double.toString(longitude)));
		    	 nvp.add(new BasicNameValuePair("time", formattedDate));
		    	 
		    	 
		         return	 nvp;
    	 
    	 } // end if 
    	 
    	 
    	 else{
    		 
    		 
    		// mHandler.post(new DisplayToast("NO CURRENT LOCATION"));
    		 
    		 nvp.add(new BasicNameValuePair("imei", imei));
    		 nvp.add(new BasicNameValuePair("latitude", "0"));
	    	 nvp.add(new BasicNameValuePair("longitude", "0"));
	    	 nvp.add(new BasicNameValuePair("time", formattedDate));
	 
	    	 return	 nvp;
	 
    		//return null;
    		
    	 }
		
	}  // end of getCurrentLocation Method
	
	
	
	
	@Override
	public void onCreate(){
		
		
		super.onCreate();
		//mHandler = new Handler();
		context=getApplicationContext();
		//myLocFile.setWritable(false, true);
		
		
		postUrl=getResources().getString(R.string.location_post_url);
		fileUploadUrl=getResources().getString(R.string.location_file_url);
		
		 //=================================for internal file storage==========================================04---December--------2013======================================
	    
	    
        // writing to internal file	
		
		
		
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
	
	@Override
	protected void onHandleIntent(Intent intent) {
					
		 // mHandler.post(new DisplayToast("Starting location http service.........."));
		//  mHandler.post(new DisplayToast("updating location co-ordinates.........."));
		             
				
		  
		  
		        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
				PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
				//Acquire the lock
				wl.acquire();	
		
				
		       // String number;
		    	TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		    	imei=tm.getDeviceId();	
		    	
		    	
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
		    	  
		    	  
		    	ConnectionManager connection = new ConnectionManager(context);
					
					if(connection.isNetworkAvailable()){   // if network detected
						
						//---------------------------------------------------------------------------------------------------------------------					
										//	String inputString=null;
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
								//stf.clearExternalFile("mobiQloc.txt");  // clear local file
								
								
								
							         }  // end if it containsData
											
											
//---------------------------------------------------------------------------------------------------------------------							
					
						
						
					// insert code for post method here===========>
						 List<NameValuePair> locationNVP = getCurrentLocation();
						 post(postUrl, locationNVP);
						
				
					// insert code for local storage method here===========>	
						
					//	 mHandler.post(new DisplayToast("NETWORK is AVAILABLE for LocationHttpService"));
						 saveLocalFile(locationNVP, true);
					//	 mHandler.post(new DisplayToast("Local storage location: " + myFile.toString())); 
					
				    	
				    	       }//  end if
						
						//==========================
						
								
					
					 else    // if network is not available
					         {
						 
							
						
						    // Show network not available message in DisplayToast
						//    mHandler.post(new DisplayToast("NO NETWORK AVAILABLE for LocationHttpService"));
						    
						 // insert code for local storage method here===========>	
						    
						    List<NameValuePair> locationNVP = getCurrentLocation();
						    saveLocalFile(locationNVP, false);
						
						
					        
					        }// end else
					
				
							
					
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
			
		//	mHandler.post(new DisplayToast("HTTP RESPONSE for LocationHttpService:" + postResponse));
			
			
			}
			
			else 
				{
				   
				
			//	mHandler.post(new DisplayToast("NO HTTP RESPONSE RECEIVED for LocationHttpService"));
				
				}
		 
		 
	       } catch (ClientProtocolException e){ }	
	        catch (IOException e) {   }
		 
		 
	 } // end of post method
	 
	 
	
	 
	 public void saveLocalFile(List<NameValuePair> locationNVP, Boolean isNetworkAvailable){
		 
		Boolean networkAvailable=isNetworkAvailable;
		 
		 
		 
		 
		 
	if(!networkAvailable){	 
		 

		 try
	     {

	    // BufferedWriter out = new BufferedWriter(new FileWriter(myLocFile, true));
	     
	     BufferedWriter out = new BufferedWriter(new FileWriter(stf.filepath, true));
	    
	     
	     for (NameValuePair nvp :locationNVP){ 
	     	   //	out.write(outputValues + "\n");
	     	 out.write(nvp.getName() +"=" + nvp.getValue()+ "#");
	     	}
	     

	     out.write("#");
	     out.close();

	   } catch (IOException e){ }
		 
		 
		 
		// stf.copyToSdCardFile("mobiQloc.txt");
		 

		 
	} //end if not networkAvailable	 
		 
		 
	  } // end of saveLocalFiles
	 
	 
	 
	 
	 



	 
	 
	 
	 
	 
}
