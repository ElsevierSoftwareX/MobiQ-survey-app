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

public class DeviceInfoHttpService extends IntentService{
	
	String id="", 
	number = "",
	osName = "",
	osVer = "",
	release= "",
	model = "",
	cpu = "",
	api = "";
			
	
	
	
	
	
	private Context context;
	//private Handler mHandler;    // handler to send message to a toast in a new thread

	String postUrl;
	String fileUploadUrl;
	
	//File sdCard = Environment.getExternalStorageDirectory();  //get handle on SD card location
   // File myFile = new File(sdCard, "mobiQlocation.txt");
   // File myLocFile = new File (sdCard, "mobiqDeviceInfo.txt");
    
    
//=================================for internal file storage==========================================04---December--------2013======================================
    
    
    String privateFileName = "priv_mobiqDeviceInfo";
 // writing to internal file	
	    			
    SaveToFile stf;

//====================================================================================================================================================
	
    
    
    
   

	public DeviceInfoHttpService() {
		super("DeviceInfoHttpService");
		
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
	
	

	private List<NameValuePair>  getDeviceInfo(){
		
		SharedPreferences deviceinfo = getSharedPreferences("deviceinfo", 0);
		id = deviceinfo.getString("id", id);
		number = deviceinfo.getString("number", number);
		osName = deviceinfo.getString("osName", osName);
		osVer = deviceinfo.getString("osVer", osVer);
		release= deviceinfo.getString("release", release);
		model = deviceinfo.getString("model", model);
		cpu = deviceinfo.getString("cpu", cpu);
		api = deviceinfo.getString("api", api);
		
      Calendar cal = Calendar.getInstance();
  	  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  	  String formattedDate = df.format(cal.getTime());
		
		
  	 //cipher the imei and imsi
  	  Cipher c = new Cipher (id);
  	  id=c.make();
  	  
  	Cipher d = new Cipher (number);
	number=d.make();
  	  
  	  
    	List<NameValuePair> nvp= new ArrayList<NameValuePair>();
    	    	   	 
    	
    	   	  
 		     nvp.add(new BasicNameValuePair("id", id));
	    	 nvp.add(new BasicNameValuePair("number", number));
	    	 nvp.add(new BasicNameValuePair("osname", osName));
	    	 nvp.add(new BasicNameValuePair("osver", osVer));
	    	 nvp.add(new BasicNameValuePair("osrelease", release));
	    	 nvp.add(new BasicNameValuePair("model", model));
	    	 nvp.add(new BasicNameValuePair("cpu", cpu));
	    	 nvp.add(new BasicNameValuePair("api", api));
	    	 nvp.add(new BasicNameValuePair("time", formattedDate));
	    	 
	    	 return	 nvp; 
	}  // end of getCurrentLocation Method
	
	
	@Override
	public void onCreate(){
		super.onCreate();
	//	mHandler = new Handler();
		context=getApplicationContext();
		
		
	//=================================for internal file storage==========================================04---December--------2013======================================
	    // writing to internal file	
		 String id;
		 SharedPreferences getid = getSharedPreferences("deviceinfo", 0);
		 id=getid.getString("id", "none");

		  privateFileName=privateFileName+id+".txt";   
			    			
		  stf = new SaveToFile(context, privateFileName);
//====================================================================================================================================================

		  postUrl=getResources().getString(R.string.device_info_post_url);
		  fileUploadUrl=getResources().getString(R.string.device_info_file_url);
	
		
		
	};
	
	
	
	//public void onDestroy(){
		
	//	super.onDestroy();
		//	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
					
		//  mHandler.post(new DisplayToast("Starting ModayQ http service........"));
		// mHandler.post(new DisplayToast("Saving device information........"));             
				PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
				PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
				//Acquire the lock
				wl.acquire();	
		
						
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
			// stf.clearExternalFile("mobiqDeviceInfo.txt");  // clear local file
			
		         }  // end if it containsData
						
						
//---------------------------------------------------------------------------------------------------------------------							
						
						
		else{
						
						
		// insert code for post method here===========>
						 List<NameValuePair> locationNVP = getDeviceInfo();
						 post(postUrl, locationNVP);
						
				
	// insert code for local storage method here===========>	
						
						// mHandler.post(new DisplayToast("NETWORK is AVAILABLE for MondayQHttpService"));
						// saveLocalFile(myFile, locationNVP, true);
					
		}  // end else if contains file data			 
						 
						 
			//	 mHandler.post(new DisplayToast("Local storage location: " + myFile.toString())); 
					
				    	
				    	       }//  end if   network is available
						
						//==========================
						
								
					
					 else    // if network is not available
					         {
						 
							
						
			// Show network not available message in DisplayToast
						   // mHandler.post(new DisplayToast("NO NETWORK AVAILABLE for MondayQHttpService"));
						    
			// insert code for local storage method here===========>	
						    
						    List<NameValuePair> locationNVP = getDeviceInfo();;
						    saveLocalFile(locationNVP, false);            //false: there is no network connectivity
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
			
			//mHandler.post(new DisplayToast("HTTP RESPONSE for DeviceInfoHttpService:" + postResponse));
			
			
			}
			
			else 
				{
				   
				
			//	mHandler.post(new DisplayToast("NO HTTP RESPONSE RECEIVED for DeviceInfoHttpService"));
				
				}
		 
		 
	       } catch (ClientProtocolException e){ }	
	        catch (IOException e) {   }
		 
		 
	 } // end of post method
	 
	 
	
	 
	 public void saveLocalFile(List<NameValuePair> locationNVP, Boolean isNetworkAvailable){
		 
		 
		 
		 Boolean networkAvailable=isNetworkAvailable;          // is there a network connection?
				 
		 
		 
if(!networkAvailable){        // if there was no network connection

		 try
	     {

	 //    BufferedWriter out = new BufferedWriter(new FileWriter(myLocFile, true));
	     BufferedWriter out = new BufferedWriter(new FileWriter(stf.filepath, true));
	    
	     
	     for (NameValuePair nvp :locationNVP){ 
	     	   //	out.write(outputValues + "\n");
	     	 out.write(nvp.getName() +"=" + nvp.getValue()+ "#");
	     	}
	     

	     out.write("#");
	     out.close();

	  } catch (IOException e){ }
		 
		 
		// stf.copyToSdCardFile("mobiqDeviceInfo.txt");
		 
		 
		 
	} // end if network available
		 
		 
		 
		 
		 
		 
		 
		 

	          
	  } // end of saveLocalFiles
	
	

}
