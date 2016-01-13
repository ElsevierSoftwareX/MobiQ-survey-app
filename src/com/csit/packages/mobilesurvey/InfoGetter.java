package com.csit.packages.mobilesurvey;

import android.content.Context;
import android.telephony.TelephonyManager;

public class InfoGetter{
	
protected Context context;

public InfoGetter(Context context)	{
	this.context= context;
}




public String getId(){
	String id;
	TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	id=tm.getDeviceId();

	return id;
}


/*
public String getDate(){
	Calendar c = Calendar.getInstance(); 
	 Date cDate = new Date();
	cDate= c.getTime();
	String fDate= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cDate);
	return fDate;
}

*/
public String getInfo(int re){
	String osName = System.getProperty("os.name");
	String osVer = System.getProperty("os.version"); 
	String release = android.os.Build.VERSION.RELEASE;  
	int releaseCode = android.os.Build.VERSION.SDK_INT;      
	String model = android.os.Build.MODEL;          
	String cpu = android.os.Build.CPU_ABI;
	String manu = android.os.Build.MANUFACTURER;
	if(re==1)
		return osName;
	else if(re==2)
		return osVer;
	else if(re==3)
		return release;
	else if(re==4)
		return	Integer.toString(releaseCode);
	else if(re==5)
		return (manu+" "+model);
	else if(re==6)
		return cpu;
	else
		return null;
}



public String getApiString(){
	String api = getInfo(4);
	String apiString = null;
	int intA = Integer.parseInt(api);
	if (intA==1)
		apiString="BASE";
	else if (intA==2)
		apiString="BASE_1_1";
	else if (intA==3)
		apiString="CUPCAKE";
	else if (intA==4)
		apiString="DONUT";
	else if (intA==5)
		apiString="ECLAIR";
	else if (intA==6)
		apiString="ECLAIR_0_1";
	else if (intA==7)
		apiString="ECLAIR_MR1";
	else if (intA==8)
		apiString="FROYO";
	else if (intA==9)
		apiString="GINGERBREAD";
	else if (intA==10)
		apiString="GINGERBREAD_MR1";
	else if (intA==11)
		apiString="HONEYCOMB";
	else if (intA==12)
		apiString="HONEYCOMB_MR1";
	else if (intA==13)
		apiString="HONEYCOMB_MR2";
	else if (intA==14)
		apiString="ICE_CREAM_SANDWICH";
	else if (intA==15)
		apiString="ICE_CREAM_SANDWICH_MR1";
	else if (intA==16)
		apiString="JELLY_BEAN";
	else if (intA==17)
		apiString="JELLY_BEAN_MR1";
	else if (intA==18)
		apiString="JELLY_BEAN_MR2";
	else if(intA==10000)
		apiString="CUR_DEVELOPMENT";
	else
		apiString="UNKNOWN";
	return apiString;
}


}
