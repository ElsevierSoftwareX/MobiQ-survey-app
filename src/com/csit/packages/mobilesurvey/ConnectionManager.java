package com.csit.packages.mobilesurvey;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.Toast;

public class ConnectionManager {
	
	
  private Context context;
  public String connectionType = "unknown";
	

	
	public ConnectionManager (Context context){
		
		this.context = context;
	}
	
	
	
	
	public boolean isNetworkAvailable(){
		
		boolean info = false;
		
			
		
		try{
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		   // info = cm.getActiveNetworkInfo().isAvailable();
		   NetworkInfo [] allNetInfo  = cm.getAllNetworkInfo();
		   if (allNetInfo !=null) 
		     {
			 
			     for (int i =0; i < allNetInfo.length; i++){
			    	 if (allNetInfo[i].getState()==NetworkInfo.State.CONNECTED){
			    		 info = true;
			    	 }
			     }
			   
		     }
			   
	         
			}
		catch (Exception e) { 
			     //System.out.println(" CheckConnectivity Exception: " + e.getMessage());
		     }
		
			return info;
			
	}
	
	
	
	
	
	
	
	public NetworkInfo [] networkInformation(){
		
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo [] netInfo = cm.getAllNetworkInfo();
		
		return netInfo;
	}
	
	
	
	
	
	
	public void connectivityMessage(String msg){            
		
		Toast toast = Toast.makeText(this.context.getApplicationContext(), "", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setText(msg);
		toast.show();
	}
	
	
	
	
	
	
	public String getNetInfoType()
	{
		
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo().getTypeName();
	}
	

}  // end class connection manager
