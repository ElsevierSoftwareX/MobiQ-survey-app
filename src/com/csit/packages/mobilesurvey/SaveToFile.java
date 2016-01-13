package com.csit.packages.mobilesurvey;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.os.Environment;

public class SaveToFile {
	
	
	public Context context;
	String nameOfFile;
	String filepath;
	String inputString;
	String readString="none";
	File privateFile;
	
	
	
	public SaveToFile(Context ctx, String filename){
		
		context=ctx;
		nameOfFile=filename;
		
		privateFile = new File(context.getFilesDir(), nameOfFile);
		filepath=privateFile.getAbsolutePath();
		
		
	
	}
	
	
	
	
	
	void append(String string){
		
		
		
		
		 try
	        {

	        BufferedWriter out = new BufferedWriter(new FileWriter(filepath, true));
	                 
	        out.write(string);
	        out.close();

	     } catch (IOException e){ }
		
	}
	
	
	
	public String read(){
		
		/*
		try {
			
			
			 BufferedReader br = new BufferedReader (new InputStreamReader((new BufferedInputStream (new FileInputStream(new File(filepath))))) );   
		
			 readString="";
						
			  while ((inputString=br.readLine())!=null){
														
				readString=readString+inputString;
			
			} // end while
			
		br.close();
		
	//		Toast.makeText(context, "contents: " + contents, Toast.LENGTH_LONG).show();
		
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		*/
		
		

		try {
			BufferedReader r = new BufferedReader(new FileReader(filepath));
			
			 readString="";
			
			while ((inputString = r.readLine())!=null){
						
				
				readString=readString+inputString;
				
			            }// end while
			
			r.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	
		
		return readString;
		
	}
	
	
	
	
	void clear(){        // clears the file 
		
		
		/*
		  try{
				
				
				FileOutputStream fos = context.openFileOutput(nameOfFile, Context.MODE_PRIVATE);
				
				fos.close();
				
				
			 } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		  */
		
		
		//clear file contents
        try
               {

               BufferedWriter out = new BufferedWriter(new FileWriter(filepath, false));

                out.close();

               } catch (IOException e){ }
	 
		  
		  
		  
	}
	
	
	
	
	void copyToSdCardFile(String name){
		
		File sdCard = Environment.getExternalStorageDirectory();  //get handle on SD card location
	    File sdCardFile = new File(sdCard, name);
	    
	    
	    try{
	    	
	              InputStream in = new FileInputStream(privateFile);
	              OutputStream out = new FileOutputStream(sdCardFile);

	               // Transfer bytes from source to destination
	                byte[] buf = new byte[1024];
	                int len;
	                while ((len = in.read(buf)) > 0) {
	                out.write(buf, 0, len);
	              }
	                in.close();
	                out.close();
	    
		
	    
	   } catch (IOException e){               }
	   
	    
		
	}  // end copyTo...
	
	
	
	
	void clearExternalFile (String name){
		
		File sdCard = Environment.getExternalStorageDirectory();  //get handle on SD card location
	    File sdCardFile = new File(sdCard, name);
	    
	    //String path=sdCardFile.getAbsolutePath();
	
		//clear file contents
        try
               {

            //   BufferedWriter out = new BufferedWriter(new FileWriter(path, false));
               BufferedWriter out = new BufferedWriter(new FileWriter(sdCardFile, false)); 
                out.close();

               } catch (IOException e){ }
	 
		
	
	}

}

