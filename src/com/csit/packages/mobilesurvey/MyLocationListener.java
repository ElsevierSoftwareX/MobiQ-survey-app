package com.csit.packages.mobilesurvey;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class MyLocationListener implements LocationListener {
	public static double latitude;
	public static double longitude;
	
	@Override
	public void onLocationChanged(Location loc) {
		latitude = loc.getLatitude();
		longitude = loc.getLongitude();
		Log.d("GPS","new lat: "+latitude);
		Log.d("GPS","new longi: "+longitude);
	}

	@Override
	public void onProviderDisabled(String arg0) {
		System.out.println(arg0+" is disabled");
	}

	@Override
	public void onProviderEnabled(String arg0) {
		System.out.println(arg0+" is enabled");
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
}
