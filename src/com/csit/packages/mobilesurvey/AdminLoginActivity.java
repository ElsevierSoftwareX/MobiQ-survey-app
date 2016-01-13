package com.csit.packages.mobilesurvey;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginActivity extends Activity {
	
	
	  EditText txtUserName;
	  EditText txtPassword;
	  Button btnLogin;
	  Button btnCancel;
	  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_login);
		
		txtUserName=(EditText)this.findViewById(R.id.txtUname);
        txtPassword=(EditText)this.findViewById(R.id.txtPwd);
        btnLogin=(Button)this.findViewById(R.id.btnLogin);
        btnCancel=(Button)this.findViewById(R.id.btnCancel);

				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_login, menu);
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
	
	
		
	
	
	public void onClickAdminLogin(View v) {
	     // TODO Auto-generated method stub
	     
	    if((txtUserName.getText().toString()).equals("csituser")  &&
	    		
	    		
	    (txtPassword.getText().toString()).equals("csituser")){
	    	
	            Toast.makeText(this, "Login Successful",Toast.LENGTH_LONG).show();
	            
	            
	            
	        	//user selection mode shared preferences
	    		
	    		SharedPreferences.Editor userMode = getSharedPreferences("userMode", 0).edit();
	    		userMode.putBoolean("Admin", true);            //set admin mode to true
	    		userMode.commit();
	    		
	         
	    		//send intent to next activity and close 
	            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        		
  		       startActivity(intent);
	            
	                     
	            finish();
	            
	       } else{
	            
	    	   Toast.makeText(this, "Invalid Login",Toast.LENGTH_LONG).show();
	    	   
	       }
	     
	}
	
	
	
	public void onClickAdminCancel(View v) {
	     // TODO Auto-generated method stub
	     	    
	           finish();
	         
	     
	}
	    
	    
	
	
	
	
	

}
