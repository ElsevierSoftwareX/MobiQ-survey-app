package com.csit.packages.mobilesurvey;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;


public class MobiQBroadcastReceiver extends BroadcastReceiver {
	
	
	public static final String BOOT_ACTION_NAME = "android.intent.action.BOOT_COMPLETED";
	public static final String BOOT_ACTION_NAME_QUICK ="android.intent.action.QUICKBOOT_POWERON";
	public static long callLogtime;
	public static long smsLogtime;
	public static long currentTime;
	public static long callReferenceTime, smsReferenceTime, locationReferenceTime, tempMondayReference, tempThursdayReference, fileCheckReference;
	PendingIntent pintent1, pintent2, pintent3, PImonintent, PIthurintent, pendingSubstancesIntent;
    public static AlarmManager am;
   public static Boolean mondayScheduled=false;
   public static Boolean thursdayScheduled=false;
   public static Boolean sendingSMS=false;
   public static Boolean sendingCalls=false;
   
  
   
   File privateFile;
   String nameOfFile;
   String filepath;
	
   
	//File sdCard = Environment.getExternalStorageDirectory();  //get handle on SD card location
   
    //File myLocFile= new File (sdCard, "mobiQloc.txt");
   
   
   

	@Override
	public void onReceive(final Context context, Intent intent) {
		
		
		//String locationURL=context.getResources().getString(R.string.location_file_url);
		String mondayURL=context.getResources().getString(R.string.monday_file_url);
		String thursdayURL=context.getResources().getString(R.string.thursday_file_url);
		String callsURL=context.getResources().getString(R.string.calls_file_url);
		String smsURL=context.getResources().getString(R.string.sms_file_url);
		String deviceInfoURL=context.getResources().getString(R.string.device_info_file_url);
		String problemURL=context.getResources().getString(R.string.problem_file_url);
		
		
		
		if (BOOT_ACTION_NAME.equals(intent.getAction())||BOOT_ACTION_NAME_QUICK.equals(intent.getAction()))
				{
			
			//scheduler sleep/active state shared preferences
			
			SharedPreferences getSchedulerState = context.getSharedPreferences("schedulerState", 0);
		                   if(getSchedulerState.getBoolean("Active", true)){
								
			                     //cancel existing alarm first before you set a new one
			                 CancelAlarm(context);
			                 setAlarm(context);
		                     }// end if scheduler state active
			
				}
		else{		
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
		//Acquire the lock
		wl.acquire();
		
		
		currentTime=System.currentTimeMillis();
		sendingSMS=false;
		sendingCalls=false;
		//check for connectivity and prompt user to upload pending data?????

		//DELETE FILES AFTER UPLOADING SUCCESSFULLY
		
		
		  String id;
		  SharedPreferences getid = context.getSharedPreferences("deviceinfo", 0);
		  id=getid.getString("id", "none");

		  	  
		  FilesChecker fc;
		 
		/*
		
		 nameOfFile="priv_mobiQloc";
		 nameOfFile=nameOfFile+id+".txt";  
		 FilesChecker fc= new FilesChecker(context, nameOfFile);
		 if(fc.checkConnectionAndFile()){
											
         	fc.uploadFiles(locationURL);
         	fc.clearExternalFile("mobiQloc.txt");
		}
		
		
		
		*/
		
	if((currentTime-fileCheckReference) >= ((1000*60*58))){	
		fileCheckReference=System.currentTimeMillis();
		
		 //myLocFile= new File (sdCard, "mobiqMonday.txt");
		 nameOfFile="priv_mobiqMonday";
		 nameOfFile=nameOfFile+id+".txt";  
		 //fc= new FilesChecker(context, nameOfFile, "mobiqMonday.txt");
		 fc= new FilesChecker(context, nameOfFile, "none");
		if(fc.checkConnectionAndFile()){
			
			fc.uploadFiles(mondayURL);
			
		}
		
		
		
		
		nameOfFile="priv_mobiqThursday";
		nameOfFile=nameOfFile+id+".txt";  
		//fc= new FilesChecker(context, nameOfFile, "mobiqThursday.txt");
		fc= new FilesChecker(context, nameOfFile, "none");
		if(fc.checkConnectionAndFile()){
			
			fc.uploadFiles(thursdayURL);
				
		}
		
			
		
		nameOfFile="priv_mobiqDeviceInfo";
		nameOfFile=nameOfFile+id+".txt";  
		//fc= new FilesChecker(context, nameOfFile, "mobiqDeviceInfo.txt" );
		fc= new FilesChecker(context, nameOfFile, "none");
		if(fc.checkConnectionAndFile()){
			
			fc.uploadFiles(deviceInfoURL);
			
					
		}
		
		
		
		nameOfFile="priv_mobiqCalls";
		nameOfFile=nameOfFile+id+".txt";  
		//fc= new FilesChecker(context, nameOfFile,"mobiqCalls.txt");
		fc= new FilesChecker(context, nameOfFile, "none");
		if(fc.checkConnectionAndFile()){
			
			fc.uploadFiles(callsURL);		
			sendingCalls=true;
			
		}
			
		
		
		nameOfFile="priv_mobiqSMS";
		nameOfFile=nameOfFile+id+".txt";  
		//fc= new FilesChecker(context, nameOfFile, "mobiqSMS.txt");
		fc= new FilesChecker(context, nameOfFile, "none");
		if(fc.checkConnectionAndFile()){
				
				fc.uploadFiles(smsURL);
				sendingSMS=true;
				
			
			}
			
		
	
		nameOfFile="priv_mobiQproblem";
		nameOfFile=nameOfFile+id+".txt";  
		//fc= new FilesChecker(context, nameOfFile, "mobiQproblem.txt");
		fc= new FilesChecker(context, nameOfFile, "none");
		if(fc.checkConnectionAndFile()){
				
				fc.uploadFiles(problemURL);
							
								
			}
		
		
	
	}//end if filesCheckReference
		
		
		
//check if surveys are up to date, if not prompt again. use shared preferences to store the state.
		//
		//
		//
		//
		//   To do......................................
		
		
		
		

		am =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		
		
		if((currentTime-locationReferenceTime)>= (1000*60*18)){
			locationReferenceTime=currentTime;
		Intent intent1 = new Intent(context, LocationHttpService.class);				
		pintent1 = PendingIntent.getService(context, 0, intent1, 0);
		//am.set(AlarmManager.RTC_WAKEUP, currentTime+ 1000*60*15, pintent1);
		 am.set(AlarmManager.RTC_WAKEUP, currentTime, pintent1);
		}
		
		
		
		
		
		// check conditions for polling Call log information	
		
		if(((currentTime-callReferenceTime)>= (1000*60*36)) && (!sendingCalls)){
			
			callReferenceTime=currentTime;
					
			Intent intent2 = new Intent(context, CallLogHttpService.class);				
			pintent2 = PendingIntent.getService(context, 0, intent2, 0);
		//am.set(AlarmManager.RTC_WAKEUP, currentTime+ 1000*60*60*5, pintent2);
			am.set(AlarmManager.RTC_WAKEUP, currentTime, pintent2);
			sendingCalls=false;
				
		   }
		
		
		
		// check conditions for polling Sms log information
		if(((currentTime-smsReferenceTime)>= (1000*60*36)) && (!sendingSMS)){
			smsReferenceTime=currentTime;
			Intent intent3 = new Intent(context, SmsLogHttpService.class);
			pintent3 = PendingIntent.getService(context, 0, intent3, 0);
			//am.set(AlarmManager.RTC_WAKEUP, currentTime+ 1000*60*60*6, pintent3);	
			am.set(AlarmManager.RTC_WAKEUP, currentTime, pintent3);	
			sendingSMS=false;
		  }
		
		
		
		// Temporary monday and thursday scheduling
		
		/*	
		
				if((currentTime-tempMondayReference)> (1000*60*5)){
					tempMondayReference=currentTime;
							
					
					//  Toast.makeText(context, "Scheduling Monday........" + (currentTime-tempMondayReference) + " seconds later" , Toast.LENGTH_LONG).show();
					
					 Intent monintent = new Intent(context, MondayDialog.class);
			     		//intent.putExtra("monday", false);
			     		monintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			     		monintent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
			     	//	monintent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			     		context.startActivity(monintent);
				
				   }
				
				
				// ------------------------------------------------------------	
				
				if((currentTime-tempThursdayReference)> (1000*60*10)){
					tempThursdayReference=currentTime;

					// Toast.makeText(context, "Scheduling Thursday........" + (currentTime-tempThursdayReference) + " seconds later" , Toast.LENGTH_LONG).show();	
				   	  Intent thurintent = new Intent(context, ThursdayDialog.class);
			  		//intent.putExtra("thursday", false);
			  		thurintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			  		thurintent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
			  	//	thurintent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			  		context.startActivity(thurintent);
				   }
				
		// ------------------------------------------------------------	-----------------------
		
	*/
		
		
		
	
		
		
		Calendar time = Calendar.getInstance();

	
		//if(Calendar.MONDAY==time.get(Calendar.DAY_OF_WEEK)&& ((time.get(Calendar.HOUR_OF_DAY)==17)))
		//if(Calendar.WEDNESDAY==time.get(Calendar.DAY_OF_WEEK)&& ((time.get(Calendar.HOUR_OF_DAY)==23)))
		// if((time.get(Calendar.HOUR_OF_DAY)==12))
		//if((Calendar.MONDAY==time.get(Calendar.DAY_OF_WEEK)&& ((time.get(Calendar.HOUR_OF_DAY)==22) || (time.get(Calendar.HOUR_OF_DAY)==23) ||(time.get(Calendar.HOUR_OF_DAY)==24))) || ((Calendar.MONDAY==time.get(Calendar.DAY_OF_WEEK)&&(time.get(Calendar.AM_PM)==Calendar.PM)) &&((time.get(Calendar.HOUR)==10) || (time.get(Calendar.HOUR)==11) || (time.get(Calendar.HOUR)==12))))
		
		if((Calendar.MONDAY==time.get(Calendar.DAY_OF_WEEK)&& ((time.get(Calendar.HOUR_OF_DAY)==17) || (time.get(Calendar.HOUR_OF_DAY)==18) ||(time.get(Calendar.HOUR_OF_DAY)==21))) || ((Calendar.MONDAY==time.get(Calendar.DAY_OF_WEEK)&&(time.get(Calendar.AM_PM)==Calendar.PM)) &&((time.get(Calendar.HOUR)==5) || (time.get(Calendar.HOUR)==6) || (time.get(Calendar.HOUR)==9))))
		 {
			 
			
			
			SharedPreferences.Editor thursdaySurveyTaken = context.getSharedPreferences("thursdaySurveyTaken", 0).edit();
			thursdaySurveyTaken.putBoolean("taken", false);
			thursdaySurveyTaken.commit();
			

			SharedPreferences mondaySurvey = context.getSharedPreferences("mondaySurveyTaken", 0);
			mondayScheduled=mondaySurvey.getBoolean("taken", false);
			
						
			 if (!mondayScheduled){
				 
				 mondayScheduled=true;
				 
				 
				 
					//reset survey state
					
					SharedPreferences.Editor mondaySurveyTaken = context.getSharedPreferences("mondaySurveyTaken", 0).edit();
					mondaySurveyTaken.putBoolean("taken", true);
					mondaySurveyTaken.commit();
				 
			       
					
					
					
					//  Toast.makeText(context, "Scheduling Monday questions........", Toast.LENGTH_LONG).show();
			           Intent monintent = new Intent(context, MondayDialog.class);
		        		//intent.putExtra("monday", false);
		        		monintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        		monintent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
		        	//	monintent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		        		context.startActivity(monintent);			          
			  		 
			 }
			 
			 		 
			 
		 }else{
			 
			 		mondayScheduled=false;
			 }
		
	
		 
		 
		
//check for thursday scheduled questions		
		 
//if((Calendar.THURSDAY==time.get(Calendar.DAY_OF_WEEK)&& ((time.get(Calendar.HOUR_OF_DAY)==17))) || (Calendar.THURSDAY==time.get(Calendar.DAY_OF_WEEK)&&((time.get(Calendar.AM_PM)==Calendar.PM)) &&((time.get(Calendar.HOUR)==5))))
		if((Calendar.THURSDAY==time.get(Calendar.DAY_OF_WEEK)&& ((time.get(Calendar.HOUR_OF_DAY)==17) || (time.get(Calendar.HOUR_OF_DAY)==18) ||(time.get(Calendar.HOUR_OF_DAY)==21))) || ((Calendar.THURSDAY==time.get(Calendar.DAY_OF_WEEK)&&(time.get(Calendar.AM_PM)==Calendar.PM)) &&((time.get(Calendar.HOUR)==5) || (time.get(Calendar.HOUR)==6) || (time.get(Calendar.HOUR)==9))))
	//if(Calendar.WEDNESDAY==time.get(Calendar.DAY_OF_WEEK)&& ((time.get(Calendar.HOUR_OF_DAY)==23)))
	// if((time.get(Calendar.HOUR_OF_DAY)==18))
	 
	         {
			 
	SharedPreferences.Editor mondaySurveyTaken = context.getSharedPreferences("mondaySurveyTaken", 0).edit();
	mondaySurveyTaken.putBoolean("taken", false);
	mondaySurveyTaken.commit();
	

	SharedPreferences thursdaySurvey = context.getSharedPreferences("thursdaySurveyTaken", 0);
	thursdayScheduled=thursdaySurvey.getBoolean("taken", false);
	
	
	
						 
			 if (!thursdayScheduled){
				 
				 thursdayScheduled=true;
				 
				 
				 
				  SharedPreferences.Editor thursdaySurveyTaken = context.getSharedPreferences("thursdaySurveyTaken", 0).edit();
					thursdaySurveyTaken.putBoolean("taken", true);
					thursdaySurveyTaken.commit();
				 
				 
				 
				 
				 
				 
			         // Toast.makeText(context, "Scheduling thursday questions........", Toast.LENGTH_LONG).show();
			          
			          Intent thurintent = new Intent(context, ThursdayDialog.class);
		        		//intent.putExtra("thursday", false);
		        		thurintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        		thurintent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
		        	//	thurintent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		        		context.startActivity(thurintent);
		        		
		        		
		        	//	new Handler().postDelayed(new Runnable() {
		             //         @Override
		            //          public void run() {
		                          
		                 //   	  Intent thurintent = new Intent(context, ThursdayDialog.class);
		  		        		
		  		        	//	thurintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		  		        	//	thurintent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
		  		        	
		  		        	//	context.startActivity(thurintent);
		  		                 // }
		                //  }, 30000);
		        		
		        	
			 }
			 
			 
			 
			 
		 }else{
			 
			 		thursdayScheduled=false;
			 }
		
		
		StringBuilder msgStr = new StringBuilder();	
		//msgStr.append("MobiQ repeating alarm received at ");	
		msgStr.append("MobiQ scheduler operation at ");		
		Format formatter = new SimpleDateFormat ("hh:mm:ss a ");
		msgStr.append(formatter.format(new Date()));		
	    //Toast.makeText(context, msgStr, Toast.LENGTH_SHORT).show();
		
		//release the lock
		wl.release();
	}  // end else
	
	} // end onReceive
	
	
	
	//==========================================================================================================================================
	
	public  void setAlarm(Context context)
	{
		
		callReferenceTime=System.currentTimeMillis();
		smsReferenceTime=System.currentTimeMillis();
		locationReferenceTime=System.currentTimeMillis();
		tempMondayReference=System.currentTimeMillis();
		tempThursdayReference=System.currentTimeMillis();
		fileCheckReference=System.currentTimeMillis();;
		
		
		//scheduler sleep/active state shared preferences
		
		SharedPreferences.Editor schedulerState = context.getSharedPreferences("schedulerState", 0).edit();
		schedulerState.putBoolean("Active", true);
		schedulerState.commit();
		
		
		
		
		//check for scheduling of thursday and mondday?
		
		am =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent (context, MobiQBroadcastReceiver.class);
		pendingSubstancesIntent = PendingIntent.getBroadcast(context, 0, intent,0);
		//repeat after every 10 minutes
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+1000*60*2, 1000*60*20, pendingSubstancesIntent);
		
	}

	
//=========================================================================================================================================
	
	
	public void CancelAlarm(Context context)
	{
		
		Intent intent = new Intent(context, MobiQBroadcastReceiver.class);
		pendingSubstancesIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		am.cancel(pendingSubstancesIntent);
			
		
		//scheduler sleep/active state shared preferences
		
				SharedPreferences.Editor schedulerState = context.getSharedPreferences("schedulerState", 0).edit();
				schedulerState.putBoolean("Active", false);
				schedulerState.commit();
		
		
		
		
		//Toast.makeText(context, "Sending MobiQ scheduler to sleep..............", Toast.LENGTH_SHORT).show();
	}
	

}
