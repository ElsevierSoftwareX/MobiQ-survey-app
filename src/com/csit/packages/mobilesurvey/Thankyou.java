package com.csit.packages.mobilesurvey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class Thankyou extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thankyou);
		TextView text = (TextView)findViewById(R.id.text);
		text.append("\n\nDon't forget you can upload a photo to tell us about your social life.\n");
	}
	 public void onClick(View view){
		 Intent intent = new Intent(this, CameraActivity.class);
		 startActivity(intent);
	}
	 public void onClickOne(View view){
		 finish();
	}

}
