package com.csit.packages.mobilesurvey;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewCodeActivity extends Activity {
	
	
	TextView txtv1;
	TextView txtv2;
	TextView txtv3;
	TextView txtv4;
	TextView txtv5;
	Button btnExit;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_code);
		
		txtv1=(TextView)this.findViewById(R.id.codeTextview);
		
		txtv2=(TextView)this.findViewById(R.id.codeTextview1);
		txtv3=(TextView)this.findViewById(R.id.codeTextview2);
		txtv4=(TextView)this.findViewById(R.id.codeTextview3);
		txtv5=(TextView)this.findViewById(R.id.codeTextview4);
		btnExit=(Button)this.findViewById(R.id.btnCodeExit);
		
		String text="";
		String text1="00000";
		String text2="00000";
		String text3="00000";
		
		
		  
			  SharedPreferences getid = getSharedPreferences("deviceinfo", 0);
			  text=getid.getString("id", "0000000000");
		
		text1=text.substring(0, 5);
		text2=text.substring(5, 10);
		text3=text.substring(10, 15);
		
		
		
		
		txtv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		txtv1.setGravity(Gravity.CENTER);
		txtv1.setText(text1);
		txtv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		txtv2.setGravity(Gravity.CENTER);
		txtv2.setText("*  *  *");
		txtv3.setGravity(Gravity.CENTER);
		txtv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		txtv3.setText(text2);
		txtv4.setGravity(Gravity.CENTER);
		txtv4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		txtv4.setText("*  *  *");
		txtv5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		txtv5.setGravity(Gravity.CENTER);
		txtv5.setText(text3);
		
	}

	
	
	
	public void onClickCodeExit(View v) {
	     // TODO Auto-generated method stub
	
		finish();
	         
	     
	}
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_code, menu);
		return true;
	}

	
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
