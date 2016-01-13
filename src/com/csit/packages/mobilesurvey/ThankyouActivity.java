package com.csit.packages.mobilesurvey;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThankyouActivity extends Activity {
	
	
	Button mButton;  
	TextView mText;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thank_you);
		
		
		context=getApplicationContext();
		mButton = (Button)findViewById(R.id.buttonThankyouClose);
		mText = (TextView)findViewById(R.id.thankyouTextview);
		
	}

	
	
	
	
	
	
	
	public void onClickThankyouClose (View view){    
		
	  finish();
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
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thankyou, menu);
		return true;
	}

}
