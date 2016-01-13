package com.csit.packages.mobilesurvey;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;


public class SmsRecorder {
	
	
	long count;
	long maxRecords;
	String add, type, person, date;
	
	 //=================================for internal file storage==========================================04---December--------2013======================================
    
    
    String privateFileName = "priv_mobiqSMS";
 // writing to internal file	
	    			
    SaveToFile stf;

//======================================================================================================
    
    
	
	
	public SmsRecorder(Context context){
		
		
		//File sdCard = Environment.getExternalStorageDirectory();  //get handle on SD card location
		   
	    @SuppressWarnings("unused")
		//File SmsFile= new File (sdCard, "mobiqSMS-test.txt");
	    
	//=================================for internal file storage==========================================04---December--------2013======================================
	    
	      String id;
		  SharedPreferences getid = context.getSharedPreferences("deviceinfo", 0);
		  id=getid.getString("id", "none");
		  
		
		  

		  privateFileName=privateFileName+id+".txt";   
	    
	     //  String privateFileName = "priv_mobiqDeviceInfo.txt";
	    // writing to internal file	
			    			
			stf = new SaveToFile(context, privateFileName);
//====================================================================================================================================================

	    
	    count=0;
	    maxRecords=0;
		
		String imei="";
		String deviceNumber="";
		SharedPreferences info = context.getSharedPreferences("deviceinfo", 0);
	    imei=info.getString("imei", "");
	    
	    
	    
		 	
	 //retrieve from shared preferences imei if deviceid could not be accessed
	  //------------------------------------------------------------------  	
	    	if (imei.equalsIgnoreCase("")){
	    		
	    	  imei=id;
	    	}
	  //-------------------------------------------------------------------
	
	    //cipher the imei
	  	Cipher c = new Cipher (imei);
		imei=c.make();
	    
		
		
		SharedPreferences info2 = context.getSharedPreferences("deviceinfo", 0);
	    
	    deviceNumber=info2.getString("number", "returnedEmpty");
	
		//cipher the device number
	  	Cipher f = new Cipher (deviceNumber);
		deviceNumber=f.make();
   	 
		
		
		add="none";
		type="none";
		person="none";
		date ="none";
		
				

		String [] selection ={"address", "type", "date"};
		//String [] selection ={"address", "type", "person", "date"};
		
		Uri smsUri = Uri.parse("content://sms/");
		Cursor cur = context.getContentResolver().query(smsUri, selection, null, null, "date DESC");
		
				
		count=cur.getCount();
		
		SharedPreferences getOutgoingSMS = context.getSharedPreferences("outgoingSMS", 0);
		
		
		   if (getOutgoingSMS.getLong("SMSintialCount", 0)==0){
			
			   SharedPreferences.Editor outgoingSMS = context.getSharedPreferences("outgoingSMS", 0).edit();
			
			   outgoingSMS.putLong("SMSintialCount", count);
    		   outgoingSMS.putLong("SMSpreviousCount", count);
    		   outgoingSMS.putLong("SMScurrentCount", count);
    		   outgoingSMS.commit();
			
								
		   }
		
		
		
		
		   if (getOutgoingSMS.getLong("SMSpreviousCount", 0) !=0) {
			
			    maxRecords=count - getOutgoingSMS.getLong("SMSpreviousCount", 0);
			
               SharedPreferences.Editor outgoingSMS = context.getSharedPreferences("outgoingSMS", 0).edit();
			   outgoingSMS.putLong("SMSintialCount", count);
    		   outgoingSMS.putLong("SMSpreviousCount", count);
    		   outgoingSMS.putLong("SMScurrentCount", count);
    		   outgoingSMS.commit();
			
							
		    }
		
		
		
		
		  if (maxRecords > 0){
			
			
			int index=0;
			
			
			cur.moveToFirst();
			
			do
				
			{
				
				
				add="none";
				type="none";
				person="none";
				date ="none";
				
				
				
				if(index == maxRecords){
					break;
				}
				
				
				
				
				// type=cur.getString(cur.getColumnIndexOrThrow("type")).toString();
				// person=cur.getString(cur.getColumnIndexOrThrow("person")).toString();
				 long smsDate=cur.getLong(cur.getColumnIndexOrThrow("date"));
				 
				 SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
				 date = format.format(new Date(smsDate));
				// date =cur.getString(cur.getColumnIndexOrThrow("date")).toString();
				 
				
				
			
				if(cur.getString(cur.getColumnIndexOrThrow("address")) != null){
				add =cur.getString(cur.getColumnIndexOrThrow("address")).toString();
				
				
				
				//cipher the outgoing number
			  	Cipher d = new Cipher (add);
				add=d.make();
			  	  
				
				
				}
				
				
				
				 int smsType=cur.getInt(cur.getColumnIndexOrThrow("type"));
				 if(smsType==1){
					 
					 type="Incoming";
					 
				 }
				 
				 else if (smsType==2){
					 type="Outgoing";
					 
					 
				          try
				          {

				            // BufferedWriter out = new BufferedWriter(new FileWriter(SmsFile, true));
				             BufferedWriter out = new BufferedWriter(new FileWriter(stf.filepath, true));
				    		    
				            out.write("imei" +"=" + imei+ "#");
				            out.write("deviceNumber" +"=" + deviceNumber+ "#");
				            out.write("destinationNumber" +"=" + add+ "#");
				            out.write("time" +"=" + date+ "#");
				            out.write("#");
				            out.close();

				           } catch (IOException e){ }
				 
					// stf.copyToSdCardFile("mobiqSMS.txt");
				          
				          
				 }// end else if outgoing type
				 
				 			
					 
				 
				index++;
				
			}
			while(cur.moveToNext());
			
			
			
			
			
			
		}//end if max records
		
		
		
		  cur.close();
		
		
		
		
		
	} // end public sms recorder
	
	
	
	
	
	
	
	

}
