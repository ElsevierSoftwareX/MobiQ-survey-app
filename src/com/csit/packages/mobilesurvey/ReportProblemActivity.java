package com.csit.packages.mobilesurvey;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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



import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;





public class ReportProblemActivity extends Activity {
	
	
	Button mButton;  
	EditText mEdit;
	TextView mText;
	Context context;
	
	private Handler mHandler;
	
	
	File sdCard = Environment.getExternalStorageDirectory();  //get handle on SD card location
    File myLocFile= new File (sdCard, "mobiQproblem.txt");
    
    String postUrl;
	String fileUploadUrl;
    
    
//=================================for internal file storage==========================================04---December--------2013======================================
    
    
    String privateFileName = "priv_mobiQproblem";
 // writing to internal file	
	    			
    SaveToFile stf;

//====================================================================================================================================================
	
    
    private class DisplayToast implements Runnable{
		  String mText;

		  public DisplayToast(String text){
		    mText = text;
		  }

		  public void run(){
		     Toast.makeText(getApplicationContext(), mText, Toast.LENGTH_LONG).show();
		  }
		}	
    
    
    
    
    List<NameValuePair> problemNVP= new ArrayList<NameValuePair>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_problem);
		
		
		context=getApplicationContext();
		mButton = (Button)findViewById(R.id.buttonReportSubmit);
		mEdit = (EditText) findViewById(R.id.report_editText);
		mText = (TextView)findViewById(R.id.reportTextview);
		
		
		mHandler = new Handler();
		
		
		
		postUrl=getResources().getString(R.string.problem_post_url);
		fileUploadUrl=getResources().getString(R.string.problem_file_url);
		
		
		
		//get timestamp and location stamp if possible
		
		
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
 
 		
 		
 		
	   Calendar cal = Calendar.getInstance();
	  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String formattedDate = df.format(cal.getTime());
	
	    
	  
	  problemNVP.add(new BasicNameValuePair("imei", imei));
	  problemNVP.add(new BasicNameValuePair("time", formattedDate));
		
		
		
	//=================================for internal file storage==========================================04---December--------2013======================================
	    
	  
	  
	  String id;
	  SharedPreferences getid = getSharedPreferences("deviceinfo", 0);
	  id=getid.getString("id", "none");

	  privateFileName=privateFileName+id+".txt";  
			    			
			stf = new SaveToFile(context, privateFileName);
			
//====================================================================================================================================================

		
	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.report_problem, menu);
		return true;
	}
	
	
	
	
	
	public void onClickReportSubmit (View view){     
		
		
		
		String input=mEdit.getText().toString();
		
		
		problemNVP.add(new BasicNameValuePair("problem", input));
        
    	if (!input.equalsIgnoreCase("")){
    		
    		    		
    		ConnectionManager connection = new ConnectionManager(context);
			
			if(connection.isNetworkAvailable()){   // if network detected post to url
				
				
				new Thread(new Runnable() {                //thread needed as can't do network on main UI activity 
					public void run() {
						uploadHttp();
					}
				
				}).start();
				
							
			 }
			
			else{  //save file locally
				
				
				 try
			     {

			    // BufferedWriter out = new BufferedWriter(new FileWriter(myLocFile, true));
			     BufferedWriter out = new BufferedWriter(new FileWriter(stf.filepath, true));
			    
			     
			     for (NameValuePair nvp :problemNVP){ 
			     	   //	out.write(outputValues + "\n");
			     	 out.write(nvp.getName() +"=" + nvp.getValue()+ "#");
			     	}
			     

			     out.write("#");
			     out.close();

			   } catch (IOException e){ }
				 
				 
				// stf.copyToSdCardFile("mobiQproblem.txt");
				
			
				 Toast.makeText(getApplicationContext(), "No Internet connection available. Your comments will be sent later when connected to the Internet.", Toast.LENGTH_LONG).show();		 
				
				
			}
    		
    		
    		
    		
			 Intent intent = new Intent(this, UserInterfaceActivity.class);
				
		     startActivity(intent);
    		
    		
    		
    		finish();
    		
    		
    		
        	
    	}
    	
    	else{
    		
    		Toast.makeText(getApplicationContext(), "Please enter your comments in the input text box", Toast.LENGTH_LONG).show();
    		
    	       }
		
		
			
		
		
	}
	
	
	
	
    	
    	
    	
    public void uploadHttp(){
    	
    	
    	    	
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
				//stf.clearExternalFile("mobiQproblem.txt");  // clear local file
				
			         }  // end if it containsData
							
							
//---------------------------------------------------------------------------------------------------------------------							
	
	                 post (postUrl, problemNVP);
		
    
    	
    }
	

	
	
public void onClickReportBack (View view){    
	
	
    
    	Intent intent = new Intent(this, UserInterfaceActivity.class);
    	startActivity(intent);
  	   	finish();
		
	}
	
	
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
	
	
	
	
 public void post (String url, List<NameValuePair> nameValuePairs){
		 
		 HttpClient httpclient = new DefaultHttpClient();
	     HttpPost httppost = new HttpPost(url);
	    
	     @SuppressWarnings("unused")
		String postResponse = "Empty";
		 
	  		 
	try {
		 
		 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
		 HttpResponse response=httpclient.execute(httppost);
		 
		
		 
		 StringBuilder resp =inputStreamToString(response.getEntity().getContent());
			
			if (resp!= null){
			
			postResponse=resp.toString();
			
			//mHandler.post(new DisplayToast("HTTP RESPONSE for LocationHttpService:" + postResponse));
			mHandler.post(new DisplayToast("Your comments have been uploaded. Thank you."));
			
			//Toast.makeText(getApplicationContext(), "Your comments have been uploaded.Thank you.", Toast.LENGTH_LONG).show();
			
			}
			
			else 
				{
				postResponse="No Response!!!";
				   
				
			//	mHandler.post(new DisplayToast("NO HTTP RESPONSE RECEIVED for LocationHttpService"));
				
				}
		 
		 
	       } catch (ClientProtocolException e){ }	
	        catch (IOException e) {   }
		 
		 
	 } // end of post method
	
	
	
	
	
	
	
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
