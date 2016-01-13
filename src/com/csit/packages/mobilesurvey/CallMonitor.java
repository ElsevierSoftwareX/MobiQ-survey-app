package com.csit.packages.mobilesurvey;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.Time;


public class CallMonitor  extends BroadcastReceiver{
	
	Context context;
	
	//File sdCard = Environment.getExternalStorageDirectory();  //get handle on SD card location
	   
    //File myLocFile= new File (sdCard, "mobiqCalls.txt");
    
    
    
   //=================================for internal file storage==========================================04---December--------2013======================================
    	    
    	    String privateFileName = "priv_mobiqCalls";
    	 // writing to internal file	
    		    			
    	    SaveToFile stf;

  //======================================================================================================
	
    
    String number, date, time, callType;
    String duration="";
    long calltime, startTime, endTime, totalTime;
    
    
    
    

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
	
//=================================for internal file storage==========================================04---December--------2013======================================
	    
		
		  String id;
		  String deviceNumber;
		  SharedPreferences getid =context.getSharedPreferences("deviceinfo", 0);
		  id=getid.getString("id", "returnedEmpty");
		  //deviceNumber=getid.getString("number", "none");
		
	
		  SharedPreferences info2 = context.getSharedPreferences("deviceinfo", 0);
		    
		   deviceNumber=info2.getString("number", "returnedEmpty");
			
		  

		  privateFileName=privateFileName+id+".txt";   
	     //  String privateFileName = "priv_mobiqDeviceInfo.txt";
	    // writing to internal file	
			    			
			stf = new SaveToFile(context, privateFileName);
//====================================================================================================================================================

			

	//Initialise shared preferences
		
		SharedPreferences.Editor outgoingCalls = context.getSharedPreferences("outgoingCall", 0).edit();
		
		Bundle bundle=intent.getExtras();
		
		if(bundle==null)
			return;
		
		number="INACCESSIBLE";
		callType="incoming";
		startTime=0;
		endTime=0;
		
		
		Time today = new Time (Time.getCurrentTimezone());
		today.setToNow();
		date=today.monthDay + "-" + (today.month +1) + "-" + today.year;
		time=today.format("%k:%M:%S");
						
		 Calendar cal = Calendar.getInstance();
	  	 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  	 String formattedDate = df.format(cal.getTime());
		
		
       //get incoming call details
		
		String state=bundle.getString(TelephonyManager.EXTRA_STATE);
		
		if((state !=null)  && (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING))){  //incoming call
			
					
			//number=bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
					
			//String msg = "Incoming call from " + number +" at: " + date + " " + time;
			//Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
			
			
			outgoingCalls.putString("type", "incoming");
			outgoingCalls.commit();
			
			
			
		}   //end if state = null
		
		
		
		else  if (state==null){   // it is an outgoing call
			  
			
			number=bundle.getString(Intent.EXTRA_PHONE_NUMBER);
			
			
			//cipher the outgoing number
			if(number==null){ 
				
				number="null";
			
			}
		  	
			Cipher d = new Cipher (number);
			number=d.make();
		  	  
			
			
			
			
			outgoingCalls.putString("number", number);
			outgoingCalls.putString("callTime", formattedDate);
			outgoingCalls.putString("type", "outgoing");
			outgoingCalls.commit();
			
			
			//String msg = "Outgoing call to " + number +" at: " + date + " " + time;
			//Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
			
			 	 
			 
			
			//get device info
		        
		       
		   TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		 //String telnumber=tm.getLine1Number();
				
		   String imei=tm.getDeviceId();	
			 
			 
		   		 	
	    	 //retrieve from shared preferences imei if deviceid could not be accessed
	  	  //------------------------------------------------------------------  	
	  	    	if (imei.equalsIgnoreCase("")){
	  	    		
	  	    	  imei=id;
	  	    	}
	  	  //-------------------------------------------------------------------
	    	
	  	    //cipher the imei
			  	Cipher c = new Cipher (imei);
				imei=c.make();
		   
			 
				
				
				
				//cipher the device number
			  	Cipher f = new Cipher (deviceNumber);
			  	deviceNumber=f.make();
				
			 
			 try
		     {

		   //  BufferedWriter out = new BufferedWriter(new FileWriter(myLocFile, true));
		     BufferedWriter out = new BufferedWriter(new FileWriter(stf.filepath, true));
		    
		       out.write("imei="+imei+ "#");
		       out.write("deviceNumber="+deviceNumber+ "#");
		   	   out.write("destinationNumber="+number+ "#");
		  
		   	   out.close();

		  } catch (IOException e){ }
			 
			
			 
			 
			 
			
		}  //end else if
		
		
		
		
		else if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK) ){
			
			
			
			SharedPreferences getlog = context.getSharedPreferences("outgoingCall", 0);
			
			//if (getlog.getString("number", null) != null){
			
			if (getlog.getString("type", null).equalsIgnoreCase("outgoing")){
				
				              
				
				       startTime = System.currentTimeMillis();
				
				        outgoingCalls.putLong("startTime", startTime);
				        outgoingCalls.putString("state", "OFFHOOK");
				        
				        outgoingCalls.commit();
				        
				        
				        
				        
				        
				        
				        try
					     {

					   //  BufferedWriter out = new BufferedWriter(new FileWriter(myLocFile, true));
					     BufferedWriter out = new BufferedWriter(new FileWriter(stf.filepath, true));
					    					    
					   	   out.write("time="+formattedDate+ "#");
					   	   out.close();

					  } catch (IOException e){ }
				
						
				        
				       
				        
				
			         }
			
			
		
			
		    } // end else if
		
		 else if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)){
			
           SharedPreferences getlog = context.getSharedPreferences("outgoingCall", 0);
           
           long start=getlog.getLong("startTime", System.currentTimeMillis());
           
           String previous_state=getlog.getString("state", "IDLE");
           
                    if (previous_state.equalsIgnoreCase("offhook")){
                    	
                    	endTime=System.currentTimeMillis();
                    	                    	
                    	outgoingCalls.putLong("endTime", endTime);
				        outgoingCalls.putString("state", "IDLE");
				        outgoingCalls.commit();
        	   
				        
				    	totalTime=endTime-start;
				        
				    	SimpleDateFormat df2 = new SimpleDateFormat("HH':'mm':'ss");
						df2.setTimeZone(TimeZone.getTimeZone("GMT+0"));
						duration=df2.format(new Date(totalTime));
				        
						
						
						//String msg = "Outgoing call ended " +" at: " + date + " " + time;
						//Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
						
						    
				 
		if (getlog.getString("type", null).equalsIgnoreCase("outgoing")){			  	  
						  	  
						  	 try
						     {

						   //  BufferedWriter out = new BufferedWriter(new FileWriter(myLocFile, true));
						     BufferedWriter out = new BufferedWriter(new FileWriter(stf.filepath, true));
						    
						      out.write("duration="+duration+ "#");
						      out.write("#");
						      out.close();

						  } catch (IOException e){ }
						  	  
						  	// stf.copyToSdCardFile("mobiqCalls.txt");
						      
		}  // end if
		
		
		
		
					outgoingCalls = context.getSharedPreferences("outgoingCall", 0).edit().clear();  //reset
						      
				        
                            } // end  if previous_state
			
			        
						
			
			
			 
			
		} // end else if
		
		
		
		
	}


	

}
