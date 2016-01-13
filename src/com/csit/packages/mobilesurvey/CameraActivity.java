package com.csit.packages.mobilesurvey;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CameraActivity extends Activity {
	
	static final int FILE_TYPE_IMAGE = 1;
	static final int FILE_TYPE_TXT = 2;
	static final int CAPTURE_IMAGE_ACTIVITY_REQ = 100;
	static final int SELECT_GALLERY_ACTIVITY_REQ = 200;
	static final int MAX_FILE_SIZE = 1800000;
	
	static final String lineEnd ="\r\n";
	static final String twoHyphens = "--";
	static final String boundary = "*****";
	
	static String picturePath = null;
	static String commentPath = null;
	static String serverDir = "CamUpload";
	
	static String serverUrlImg ="";
	static String serverUrlTxt = "";
	static String locationString = "";
	static String photoInfoUrl ="";
	static String phototag ="";

	
	// fileUriTemp is needed if the user aborts the camera, so the old picture can be retrieved
	static String picturePathTemp = null;
	// this is needed to check later if a copy of the image was created and needs therefore to be deleted 
	static boolean imageFromCam = true;
	
	LocationManager locationManager = null;
	Location location = null;
	
	ProgressDialog progDialog = null;
	
	static boolean uploadButtonVisible = false;
	
	String timeStamp;
	String timeDate;
	String latitude ="0";
	String longitude = "0";
	String comments = "none";
	List<NameValuePair> nvp;
	String postResponse;
	String id="";
	
	
	Button uploadButton;
	TextView output;
	
	
	private Handler mHandler;    // handler to send message to a toast in a new thread
	
	
	protected void toastText(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
	
	
	
	
	private class DisplayToast implements Runnable{
		  String mText;
		  public DisplayToast(String text){
		    mText = text;
		  }
		  public void run(){
		     Toast.makeText(getApplicationContext(), mText, Toast.LENGTH_SHORT).show();
		  }
		}

private StringBuilder inputStreamToString(InputStream is){
		
		String line =" ";
		StringBuilder total = new StringBuilder();
		
		//wrap a buffered reader around the input stream
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		//read response until end
		try {
			while ((line = rd.readLine())!= null){
				total.append(line);
				
						}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return full string
		
		return total;
	
   } // end of inputStringtoString method 
	
	
	
	
	
	
	private File getOutputFile(int type) {
		String SdStatus = Environment.getExternalStorageState();
		if (SdStatus.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
			toastText("SD card can only be read");
			return null;
		}
		
		if (!SdStatus.equals(Environment.MEDIA_MOUNTED)) {
			toastText("SD card seems not to be mounted");
			return null;
		}
		
		File newFile = null;
		
		if (type == FILE_TYPE_IMAGE) {
			//File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CameraActivityDir");
			File storageDir = new File(Environment.getExternalStorageDirectory(), "CameraActivityDir");
			
			if (!storageDir.exists()) {
				if (!storageDir.mkdir()) {
					if (!storageDir.mkdirs()) {
						return null;
					}
				}
			}
				
					
			
			SharedPreferences deviceinfo = getSharedPreferences("deviceinfo", 0);
			id = deviceinfo.getString("id", "ID_INACCESSIBLE");
			
			
			
			
			// create a media file name
			timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.UK).format(new Date());
			
			 Calendar cal = Calendar.getInstance(); 
			 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	 timeDate = df.format(cal.getTime());
			
			
			newFile = new File(storageDir.getPath()+File.separator+"IMAG_"+timeStamp+ "_" + id +".jpg");
			
			phototag="IMAG_"+timeStamp+ "_" + id +".jpg";
			
			
		} else if (type == FILE_TYPE_TXT) {
			if (picturePath == null) {
				return null;
			}
			int lastPoint = picturePath.lastIndexOf(".");
			if (lastPoint < 0) {
				return null;
			}
            String fileName = picturePath.substring(0, lastPoint);
			newFile = new File(fileName+".txt");
			
		    EditText editText = ((EditText)findViewById(R.id.editTextCamera));
		    comments = editText.getText().toString();
		   
		    if (comments.length() <= 0){
		    	
		    	comments="none";
		    }
		    
			FileOutputStream foStream = null;
			
			try {
				foStream = new FileOutputStream(newFile);
				foStream.write(comments.getBytes());
			} catch (Exception e) {
				newFile = null;
			} finally {
				if (foStream != null) {
					try {
						foStream.close();
					} catch (Exception e) {}
				}
			}
		} 
		
		return newFile;
	}
	
	
	
	
	
	private void showPhoto(String photoPath) {
		try {
			File photoFile = new File(photoPath);
			if (photoFile.exists()) {
				recyclePicture();
				Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
				BitmapDrawable bitmapDrawable = new BitmapDrawable(this.getResources(), bitmap);
				//photoImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
				ImageView photoImage = (ImageView)findViewById(R.id.photo_image);
				photoImage.setImageDrawable(bitmapDrawable);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	protected void buttonVisible() {
		uploadButtonVisible = true;
		Button uploadButton = (Button)findViewById(R.id.btnUpload);
		//uploadButton.setVisibility(View.VISIBLE);
		TextView output = (TextView)findViewById(R.id.output);
		//output.setText(locationString);
	}
	
	
	
	
	protected void fileDeletion(String filePath) {
		if (filePath != null) {
			File deleteFile = new File(filePath);
			if (deleteFile.exists()) {
				deleteFile.delete();
			}
		}
	}
	
	
	
	
	protected void recyclePicture() {
		ImageView photoImage = (ImageView)findViewById(R.id.photo_image);
		Drawable oldDrawable = photoImage.getDrawable();
		if (oldDrawable != null) {
			((BitmapDrawable)oldDrawable).getBitmap().recycle();
		}
	}
	
	
	
	
	protected void addLocationText() {
		locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		
		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Toast.makeText(this, "GPS is not activated", Toast.LENGTH_LONG).show();
		} else {
			location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null) {
				//TextView output = (TextView)findViewById(R.id.output);
				locationString = "latitude: "+location.getLatitude()+"\nlongitude: "+location.getLongitude();
				latitude=Double.toString(location.getLatitude());
				longitude=Double.toString(location.getLongitude());
				
				//output.setText(locationString);
			}
		}
	}
	
	
		
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			// delete old copy of image if previous image was taken by cam
			if (imageFromCam) {
				fileDeletion(picturePath);
			}
			
			if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQ) {
				picturePath = picturePathTemp;
				imageFromCam = true;
			} else if (requestCode == SELECT_GALLERY_ACTIVITY_REQ) {
				picturePath = getPathFromUri(data.getData());
				imageFromCam = false;
			} else {
				return;
			}
			
			//buttonVisible();
			uploadButton.setVisibility(View.VISIBLE);
			
			showPhoto(picturePath);
			addLocationText();
		} else if (resultCode == RESULT_CANCELED) {
		} else {
			toastText("Could not retrieve picture. Unknown Error");
		}
	}
	
	
	
	public String getPathFromUri(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	
	
	
	
	public void onClickCallCam(View v) {
		File file = getOutputFile(FILE_TYPE_IMAGE);
		if (file != null) {
			picturePathTemp = file.getAbsolutePath();
			
			Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			i.putExtra(MediaStore.EXTRA_MEDIA_TITLE, "PicTitle");
			i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQ);
			
			//uploadButton.setVisibility(View.VISIBLE);
			
		}
	}
	
	
	
	
	public void onClickGallery(View v) {
		Intent i = new Intent();
		i.setType("image/*");
		i.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(i, SELECT_GALLERY_ACTIVITY_REQ);
	}
	
	
	
	
	public void onClickContinue(View v) {
		fileDeletion(picturePath);
		fileDeletion(commentPath);
		
		
		
		
		/*
		//checking user selection mode shared preferences
	         Boolean adminMode=false;
	   		
	   		SharedPreferences getUserMode = getSharedPreferences("userMode", 0);
	   		
	   		adminMode=getUserMode.getBoolean("Admin", false);
	   		
	   		if(adminMode){
	   			//send intent to weekly sports and club activities
	   			
	   		   //  ResetResults rr = new ResetResults();
			 //   rr.resetWeeklySurveys(getApplicationContext());
		    
		       //    Intent intent3 = new Intent(this, MainActivity.class);
			
			     //  startActivity(intent3);
	   			
	   			
	   		          } //end if admin mode
		
		*/
		
		
		
		
		finish();		
	}
	
	
	
	
	public void onClickUploadPic(View v) {
		
		//suol
		ConnectionManager cm = new ConnectionManager(getApplicationContext());
		if(!cm.isNetworkAvailable()){
			
			
			Toast.makeText(getApplicationContext(), "This operation requires Internet connection, please check your connection", Toast.LENGTH_LONG).show();
			
		}
		
		else{
		
	
			/*
			progDialog = ProgressDialog.show(CameraActivity.this, "", "Uploading file...", true);
		
		//UploadTask task = new UploadTask();
		//task.execute();
		if (picturePath == null) {
			return;
		}
		commentPath = getOutputFile(FILE_TYPE_TXT).getAbsolutePath();
		if (commentPath == null) {
			return;
		}

		new Thread(new Runnable() {
			public void run() {
				uploadHttpFile(FILE_TYPE_IMAGE);
			//	uploadHttpFile(FILE_TYPE_TXT);
				runOnUiThread(new Runnable() {
					public void run() {
						if (progDialog.isShowing()) {
							progDialog.dismiss();
						}
					}
				});
				fileDeletion(picturePath);
				fileDeletion(commentPath);
				finish();
			}
		}).start();
		
		
		*/
			
			commentPath = getOutputFile(FILE_TYPE_TXT).getAbsolutePath();
			if (commentPath == null) {
				return;
			}
		
			
			//get all values
			
			 nvp= new ArrayList<NameValuePair>();
   	   	 
			 
			 
			 
			 
//**********************************************************************************************	          
	    	 //cipher the imei
	    	  Cipher c = new Cipher (id);
	    	  id=c.make();
			  
//**********************************************************************************************	
	  			 
	    	
  	   	  
		     nvp.add(new BasicNameValuePair("imei", id));
	    	 nvp.add(new BasicNameValuePair("time", timeDate));
	    	 nvp.add(new BasicNameValuePair("latitude", latitude));
	    	 nvp.add(new BasicNameValuePair("longitude", longitude));
	    	 nvp.add(new BasicNameValuePair("phototag", phototag));
	    	 nvp.add(new BasicNameValuePair("comments", comments));
			
			
			
			
			new Thread(new Runnable() {
				
				
				
				public void run() {
					FileUploader fu = new FileUploader(getApplicationContext());
					fu.uploadFile(picturePath, serverUrlImg);
					if (fu.serverResponseCode==200){
						
						fileDeletion(picturePath);		
						
						runOnUiThread(new Runnable() {
								public void run() {
									toastText("Your photo has been uploaded ........");
								   }
						  });
						
						
						//post imei, location, timestamp and comments
						 post(photoInfoUrl, nvp);
						
						
						
						
						
						
					}
					
					fu.uploadFile(commentPath, serverUrlTxt);
					
                     if (fu.serverResponseCode==200){
                    	 
                    	 						
						fileDeletion(commentPath);				
											
					}
					
					
				}
			}).start();
			
			
			
			
			//Intent intent = new Intent(this, UserInterfaceActivity.class);
				
			//startActivity(intent);
		
			
			//checking user selection mode shared preferences
	        
			/*
			Boolean adminMode=false;
	   		
	   		SharedPreferences getUserMode = getSharedPreferences("userMode", 0);
	   		
	   		adminMode=getUserMode.getBoolean("Admin", false);
	   		
	   		if(adminMode){
	   			//send intent to weekly sports and club activities
	   			
	   		   //  ResetResults rr = new ResetResults();
			 //   rr.resetWeeklySurveys(getApplicationContext());
		    
		        //   Intent intent3 = new Intent(this, MainActivity.class);
			
			    //   startActivity(intent3);
	   			
	   			
	   		          } //end if admin mode
			
		*/
			finish();
			
		
		}
	}
	
	
	
	

 public void post (String url, List<NameValuePair> nameValuePairs){
		 
		 HttpClient httpclient = new DefaultHttpClient();
	     HttpPost httppost = new HttpPost(url);
	     postResponse = "Empty";
		 
	  		 
	try {
		 
		 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
		 HttpResponse response=httpclient.execute(httppost);
		 
		 StringBuilder resp =inputStreamToString(response.getEntity().getContent());
			
			if (resp!= null){
			
			postResponse=resp.toString();
			
			//mHandler.post(new DisplayToast("HTTP RESPONSE for photo upload:" + postResponse));
			
			runOnUiThread(new Runnable() {
				public void run() {
					//toastText(postResponse);
				   }
			});
			
			
			}
			
			else 
				{
				postResponse="No Response!!!";
				   
				
				//mHandler.post(new DisplayToast("NO HTTP RESPONSE RECEIVED for photo upload"));
				runOnUiThread(new Runnable() {
					public void run() {
						//toastText(postResponse);
					   }
				});
				}
		 
		 
	       } catch (ClientProtocolException e){ }	
	        catch (IOException e) {   }
		 
		 
	 } // end of post method
	 
	 
	
	
	
	
	
	
	
	public boolean uploadHttpFile(int type) {
		String filePath = null;
		if (type == FILE_TYPE_IMAGE) {
			filePath = picturePath;
		} else {
			filePath = commentPath;
		}
		
		String serverUrl = null;
		if (type == FILE_TYPE_IMAGE) {
			serverUrl = serverUrlImg;
		} else {
			serverUrl = serverUrlTxt;
		}
		
		Log.d("HTTP", "Uploading file: "+filePath);
		
		serverUrl += "?directory="+serverDir;
		
		DataOutputStream dataOutStream = null;
		FileInputStream fileInStream = null;
		HttpURLConnection con = null;
		
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1024*1024;
		
		File uploadFile = new File(filePath);
		
		if (!uploadFile.exists()) {
			runOnUiThread(new Runnable() {
				public void run() {
					toastText("Uploading failed");
				}
			});
			return false;
		}
		
		try {
			fileInStream = new FileInputStream(uploadFile);
			if (fileInStream.getChannel().size() > MAX_FILE_SIZE) {
				runOnUiThread(new Runnable() {
					public void run() {
						toastText("File size is too large for uploading");
					}
				});
				fileInStream.close();
				return false;
			}
			URL url = new URL(serverUrl);
			con = (HttpURLConnection)url.openConnection();
			
			// allow inputs & outputs
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			
            //int lastSlash = filePath.lastIndexOf("/");
            //String fileName = filePath.substring(lastSlash+1);        
            
			// enable POST method
			con.setRequestMethod("POST");
			
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("ENCTYPE", "multipart/form-data");
			con.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			con.setRequestProperty("file", filePath);
			
			dataOutStream = new DataOutputStream( con.getOutputStream() );
			
			dataOutStream.writeBytes(twoHyphens + boundary + lineEnd);
			dataOutStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + filePath +"\"" + lineEnd);
			dataOutStream.writeBytes(lineEnd);
			
			bytesAvailable = fileInStream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];		
			
			// Read file
			bytesRead = fileInStream.read(buffer, 0, bufferSize);
			//bytesRead = 0;
		
			while (bytesRead > 0)
			{
				dataOutStream.write(buffer, 0, bufferSize);
				bytesAvailable = fileInStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInStream.read(buffer, 0, bufferSize);
			}
			
			dataOutStream.writeBytes(lineEnd);
			dataOutStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// Responses from the server (code and message)
			int serverResponseCode = con.getResponseCode();
			String serverResponseMessage = con.getResponseMessage();	
			Log.d("HTTP", serverResponseMessage+" - code: "+serverResponseCode);     
			
			dataOutStream.flush();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.toString());
		} finally {
			try {
				if (fileInStream != null) {
					fileInStream.close();
					Log.d("HTTP", "close 1"); 
				}
				if (dataOutStream != null) {
					dataOutStream.close();
					Log.d("HTTP", "close 2"); 
				}
				if (con != null) {
					con.disconnect();
					Log.d("HTTP", "close 3"); 
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.toString());
			}
		}
	
		return true;
	}

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		/*
		PackageManager packMan = getPackageManager();
		if (packMan.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
		}
		*/
		
		
		 serverUrlImg = getResources().getString(R.string.picture_url);
		 serverUrlTxt = getResources().getString(R.string.picture_text_url);
		
		 photoInfoUrl = getResources().getString(R.string.photo_info_url);
		
		
		
		uploadButton = (Button)findViewById(R.id.btnUpload);
		//uploadButton.setVisibility(View.VISIBLE);
		output = (TextView)findViewById(R.id.output);
		
	//	if (uploadButtonVisible) {
		//	buttonVisible();
	//	}
		
		
	
		
	//	if (picturePath != null) {
		//	showPhoto(picturePath);
		//}
	
		
		
		
		
		
		
	}
	
	@Override
	public void onDestroy() {
		//recyclePicture();
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
