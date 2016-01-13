package com.csit.packages.mobilesurvey;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ThankYouWeeklyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thank_you_weekly);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thank_you_weekly, menu);
		return true;
	}

}
