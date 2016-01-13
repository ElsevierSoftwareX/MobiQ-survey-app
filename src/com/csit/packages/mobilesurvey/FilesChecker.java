package com.csit.packages.mobilesurvey;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;

public class FilesChecker {
	
	
	
	Context context;
	//private String fpath;
	
	private boolean containsData=false;
	private String serverPath;
	
	String filepath;
	String nameOfFile;
	String nameOfExternalFile;
	File privateFile;
	Boolean file_uploaded=false;
	
	
	public FilesChecker(Context cnt, String filename, String externalFilename){
			
		context= cnt;			
		nameOfFile=filename;	
		nameOfExternalFile=externalFilename;
		privateFile = new File(context.getFilesDir(), nameOfFile);
		filepath=privateFile.getAbsolutePath();	
		
		
		
	}
	
	
	
	
	
	
	
	public boolean checkConnectionAndFile(){
		
		ConnectionManager connection = new ConnectionManager(context);
		
		if(connection.isNetworkAvailable()){   // if network detected
			
			//---------------------------------------------------------------------------------------------------------------------					
	
								String inputString=null;
								
								String contents="";
								
						
								
								try {
									BufferedReader br = new BufferedReader (new FileReader (filepath));   //note the change to string
								
									while ((inputString=br.readLine())!=null){
										
																											
										contents=contents+inputString;
										                                        						
							     		
									        if (contents.contains("#") || contents.contains("##")){
										
										           containsData= true;
										           
									         }	
									       
									
									
									} // end if
									
								br.close();
								
									
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				
							
								if(containsData){
									
									return true;
								}
								else
								{
									return false;
								}
				
		
		
		
	               }  // end if network connection
		              else{
		            	  
		            	  return false;
		              }
		
	
	} // end check connection
	
	
	
	
	
	public void uploadFiles(String uploadServer){
		
		serverPath = uploadServer;
		
		 //upload file to database server
		
		//FileUploader fu = new FileUploader(context);
		
			
		new Thread(new Runnable() {
			
			public void run() {
				FileUploader fu = new FileUploader(context);
				fu.uploadFile(filepath, serverPath);
				
	if (fu.serverResponseCode==200){
		
		file_uploaded=true;
			
		//clear file
				
				    try
                   {

                    BufferedWriter out = new BufferedWriter(new FileWriter(privateFile, false));

                       out.close();

                       } catch (IOException e){ }
				    
				    
				    
		if (nameOfExternalFile!="none"){
			
			
			  clearExternalFile(nameOfExternalFile);
			 
		}
				    
				    
				    
	 }	// end if fu.serverResponseCode	    
				
				
			}
		}).start();
		
		
		
		//fu.uploadFile(filepath, serverPath);
		
		
		//clear file contents
	         //  try
	                 // {

	                //  BufferedWriter out = new BufferedWriter(new FileWriter(filepath, false));
	  
	                 //  out.close();

	                 // } catch (IOException e){ }
		 
		
		
		
	}
	
	
	
	
	
void clearExternalFile (String name){
		
		File sdCard = Environment.getExternalStorageDirectory();  //get handle on SD card location
	    File sdCardFile = new File(sdCard, name);
	    
	//    String path=sdCardFile.getAbsolutePath();
	
		//clear file contents
        try
               {

            //   BufferedWriter out = new BufferedWriter(new FileWriter(path, false));
               BufferedWriter out = new BufferedWriter(new FileWriter(sdCardFile, false)); 
                out.close();

               } catch (IOException e){ }
	 
		
	
	}

	
		
		
}
