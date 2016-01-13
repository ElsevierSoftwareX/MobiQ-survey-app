package com.csit.packages.mobilesurvey;

public class CreateIMEI {
	
	long imei= 999999999;
	
	public CreateIMEI(){
		
					
		
	}
	
	
	public long   Create(){
		
		imei=0;
		
		for (int i=0; i< 10; i++){
			
			long multiplier=(long) Math.pow(10, i);
			double r=Math.random();
			
			
			double r_10=10*r;
			
			if (r_10 <1.0){
				r_10=3.0;
			}
			
			
			imei=imei+(long)r_10*multiplier;
			
		}
		
		return imei;
		
	}

}
