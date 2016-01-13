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

public class ThursdayQHttpService extends IntentService{
	

	
	private Context context;
	//private Handler mHandler;    // handler to send message to a toast in a new thread

	String postUrl;
	String fileUploadUrl;
	
	//File sdCard = Environment.getExternalStorageDirectory();  //get handle on SD card location
    
    //File myLocFile=new File (sdCard, "mobiqThursday.txt");
    
    
 //=================================for internal file storage==========================================04---December--------2013======================================
    
    
    String privateFileName = "priv_mobiqThursday";
 // writing to internal file	
	    			
    SaveToFile stf;
	



//====================================================================================================================================================

    
   

	public ThursdayQHttpService() {
		super("ThursdayQHttpService");
		
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
	private List<NameValuePair>  getThursdayQandA(){
		
			
			
			
			// sports and activities results
			
						SharedPreferences weeklySportsClubsResults = getSharedPreferences("sportsClubResults", 0);
						String anySport=toYesNo(weeklySportsClubsResults.getBoolean("anySports", false));
						String anyClubs=toYesNo(weeklySportsClubsResults.getBoolean("anyClubs", false));
						String anyNonClubs=toYesNo(weeklySportsClubsResults.getBoolean("anyNonClubs", false));
						
						

				// sports selection results	 
				
					    SharedPreferences sports_results = getSharedPreferences("regularSportsResults", 0);
						String sports_data [];
						String sports_value [];
						sports_data = getResources().getStringArray(R.array.sports_array_label);
						sports_value =new String[sports_data.length];
						
						for (int i =0; i<sports_data.length; i++){
												 			 
							sports_value [i]=toYesNo(sports_results.getBoolean("sports"+i, false));
				      	  
						}
						
				
						
						// sports number of times results	 
						SharedPreferences sports_results2 = getSharedPreferences("numberOfTimesSport", 0);
						
						String sports_frequency_value [];
						sports_frequency_value =new String[sports_data.length];
					
						for (int i =0; i<sports_data.length; i++){
							
							     sports_frequency_value [i]=sports_results2.getString("sports"+i, "none");
						      	  
								}		
						
				
						
						
						
				// Clubs selection results	 
				
							
						SharedPreferences clubs_results = getSharedPreferences("regularClubsResults", 0);
						String clubs_data [];
						String clubs_value [];
						clubs_data = getResources().getStringArray(R.array.club_array_label);
						clubs_value=new String [clubs_data.length];
						for (int i =0; i< clubs_data.length; i++){
				
				       	          clubs_value[i]=toYesNo(clubs_results.getBoolean("clubs"+i, false));
				       	 
						}		
								

				// Clubs number of times results	 
					SharedPreferences num_times_results = getSharedPreferences("numberOfTimesClubs", 0);
					String clubs_frequency_value []=new String [clubs_data.length];
					
						for (int i =0; i< clubs_data.length; i++){
					
							clubs_frequency_value [i]=num_times_results.getString("clubs"+i, "none");
				    
						}
						

				// Non  Clubs selection results	 
				
						SharedPreferences non_clubs_results = getSharedPreferences("regularNonClubsResults", 0);
						String non_clubs_data [];
						
						non_clubs_data = getResources().getStringArray(R.array.non_club_array_label);
						
						String non_clubs_value [] = new String [non_clubs_data.length];
						for (int i =0; i< non_clubs_data.length; i++){
						
							non_clubs_value [i]=toYesNo(non_clubs_results.getBoolean("non_clubs"+i, false));  
					
						}
						
				// Non Clubs number of times results	
				
						 SharedPreferences num_times_results2 = getSharedPreferences("numberOfTimesNonClubs", 0);
						 String non_clubs_frequency_value [] = new String [non_clubs_data.length];
						 for (int i =0; i< non_clubs_data.length; i++){
									
						 non_clubs_frequency_value [i]=num_times_results2.getString("non_clubs"+i, "none");  
						}
						
						
			
			
			
			
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
    	
    	     nvp.add(new BasicNameValuePair("imei", imei));
    		 nvp.add(new BasicNameValuePair("time", formattedDate));
	    	
 		   //  nvp.add(new BasicNameValuePair("Day", "Thursday"));
 		     
 		     nvp.add(new BasicNameValuePair("anySport", anySport));
	    	 nvp.add(new BasicNameValuePair("anyClubs", anyClubs));
	    	 nvp.add(new BasicNameValuePair("anyNonClubs", anyNonClubs));
	    	 
	    	// for(int i=0; i< sports_data.length; i++){
	    		 
	    	//	 nvp.add(new BasicNameValuePair(sports_data [i]+"Bool", sports_value [i]));
	    	// }
	    	 
           for(int i=0; i< sports_data.length; i++){
	    		 
	    		 nvp.add(new BasicNameValuePair(sports_data [i], sports_frequency_value [i]));
	    	 }
	    	 
           SharedPreferences non_listed_activities = getSharedPreferences("nonListedActivities", 0);
		   String activity=non_listed_activities.getString("otherSportsName", "none");
		   nvp.add(new BasicNameValuePair("OtherSportsName", activity));
           
           
         //  for(int i=0; i< clubs_data.length; i++){
	    //		 
	    		// nvp.add(new BasicNameValuePair(clubs_data [i]+"Bool", clubs_value [i]));
	    	// }
	    	 
         for(int i=0; i< clubs_data.length; i++){
	    		 
	    		 nvp.add(new BasicNameValuePair(clubs_data [i], clubs_frequency_value [i]));
	    	 }
         
         
         non_listed_activities = getSharedPreferences("nonListedActivities", 0);
		 activity=non_listed_activities.getString("otherClubsName", "none");
		 nvp.add(new BasicNameValuePair("OtherClubsName", activity));
         
         
         
       //  for(int i=0; i< non_clubs_data.length; i++){
    		 
    	//	 nvp.add(new BasicNameValuePair(non_clubs_data [i]+"Bool", non_clubs_value [i]));
    	// }
    	 
       for(int i=0; i< non_clubs_data.length; i++){
    		 
    		 nvp.add(new BasicNameValuePair(non_clubs_data [i], non_clubs_frequency_value [i]));
    	 }
       
      non_listed_activities = getSharedPreferences("nonListedActivities", 0);
	  activity=non_listed_activities.getString("otherNonClubsName", "none");
	  nvp.add(new BasicNameValuePair("OtherNonClubsName", activity));
	    	 
	    	// nvp.add(new BasicNameValuePair("time", formattedDate));
	    	// nvp.add(new BasicNameValuePair("imei", imei));
	 
	    	 return	 nvp; 
	}  // end of getCurrentLocation Method
	
	
	@Override
	public void onCreate(){
		super.onCreate();
		//mHandler = new Handler();
		context=getApplicationContext();
		
		
	//	myLocFile.setWritable(false, true);
		
		postUrl=getResources().getString(R.string.thursday_post_url);
		fileUploadUrl=getResources().getString(R.string.thursday_file_url);
		
		
//=================================for internal file storage==========================================04---December--------2013======================================
	    
	    
          String privateFileName = "priv_mobiqThursday";
       
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
		// mHandler.post(new DisplayToast("Saving thursday survey results........"));             
				PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
				PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
				//Acquire the lock
				wl.acquire();	
		
			
				
		           ConnectionManager connection = new ConnectionManager(context);
					
					if(connection.isNetworkAvailable()){   // if network detected
						
				//check if pending bulk data exists on file then add current value and upload file. 
				
						
						
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
								
								//clear file contents
							        
							      	 stf.clear();      // clear internal file
									// stf.clearExternalFile("mobiqThursday.txt");  // clear local file
							           
							           
								
							         }  // end if it containsData
											
											
					//---------------------------------------------------------------------------------------------------------------------							
											
														
						
						
						
						// insert code for post method here===========>
						 List<NameValuePair> locationNVP = getThursdayQandA();
						 post(postUrl, locationNVP);
						
				
					// insert code for local storage method here===========>	
						
						// mHandler.post(new DisplayToast("NETWORK is AVAILABLE for MondayQHttpService"));
						 saveLocalFile(locationNVP, true);
					//	 mHandler.post(new DisplayToast("Local storage location: " + myFile.toString())); 
					
				    	
				    	       }//  end if
						
						//==========================
						
								
					
					 else    // if network is not available
					         {
						 
							
						
						    // Show network not available message in DisplayToast
						   // mHandler.post(new DisplayToast("NO NETWORK AVAILABLE for MondayQHttpService"));
						    
						 // insert code for local storage method here===========>	
						    
						    List<NameValuePair> locationNVP = getThursdayQandA();;
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
			
			
			
			//mHandler.post(new DisplayToast("HTTP RESPONSE for ThursdayQHttpService:" + postResponse));
			
			   
			
			}
			
			else 
				{
				   
				
				//mHandler.post(new DisplayToast("NO HTTP RESPONSE RECEIVED for ThursdayQHttpService"));
				
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
		   
		   
		 //  stf.copyToSdCardFile("mobiqThursday.txt");
		   
		 }//end if not network available

	          


	} // end of saveLocalFiles
	
	

}
