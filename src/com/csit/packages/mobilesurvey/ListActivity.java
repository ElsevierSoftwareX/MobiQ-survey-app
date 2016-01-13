package com.csit.packages.mobilesurvey;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ListActivity extends Activity {
	
	
	
	private LinearLayout checkboxLayout;
    private String[] data;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);
		
		
		checkboxLayout = (LinearLayout) findViewById(R.id.Checkbox_Layout);
        data = getResources().getStringArray(R.array.data_array);

        for (int i = 0; i < data.length; i++) {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(data[i]);
            cb.setTextColor(Color.BLUE);
            checkboxLayout.addView(cb);
        }

		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}
	
	
	
	
	
	
	


public void button_click(View view) {

        for (int i = 0; i < checkboxLayout.getChildCount(); i++) {
            if (checkboxLayout.getChildAt(i) instanceof CheckBox) {
                CheckBox cb = (CheckBox) checkboxLayout.getChildAt(i);
                if (cb.isChecked()) {
                    Toast.makeText(getApplicationContext(),
                            cb.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }


}



}
