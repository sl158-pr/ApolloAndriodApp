package com.droid.activities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.droid.activities.R;
import com.droid.utilities.Connection;
import com.droid.utilities.ConstantsFile;
import com.droid.validators.InputFieldValidations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Password_Doctor extends Activity{
    /** Called when the activity is first created. */
    @Override
   
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        /** load up the layout**/
        setContentView(R.layout.doctorpasswordupdate);
       
        /**get the button resource in the xml file and assign it to a local variable of type Button**/
        /*
        final Button launch = (Button)findViewById(R.id.ok_button);
        launch.setEnabled(false);
        */
        //
        
        final Button launch = (Button)findViewById(R.id.ok_button);
        launch.setEnabled(true);
        Button btnCancel = (Button)findViewById(R.id.cancel_button);
        Button btnSubmit = (Button)findViewById(R.id.submitbutton);
        
      //   final EditText oldPwdEditText = (EditText) findViewById(R.id.txt_oldpwd);
     //   oldPwdEditText.setEnabled(false);
        
        final EditText newpwdEditText = (EditText) findViewById(R.id.txt_newpwd);
        newpwdEditText.setEnabled(false);
        final EditText confirmpwdEditText = (EditText) findViewById(R.id.txt_confirm);
        confirmpwdEditText.setEnabled(false);
         final TextView lblResult = (TextView)findViewById(R.id.result);
        
        /*
        final EditText newpwdEditText = (EditText) findViewById(R.id.txt_newpwd);
        newpwdEditText.setEnabled(true);
        final EditText confirmpwdEditText = (EditText) findViewById(R.id.txt_confirm);
        confirmpwdEditText.setEnabled(true);
         final TextView lblResult = (TextView)findViewById(R.id.result);
         */
         
         /**This is the action listener for Submit button**/
         btnSubmit.setOnClickListener( new OnClickListener()
         {

 			public void onClick(View arg0) {
 				// TODO Auto-generated method stub
 				//write code to check the correctness of entered answers
 				//disbaling the questions
 				/**getting the values of edit text**/
 				EditText queryText1 = (EditText) findViewById(R.id.txt_ans1);
 				 EditText queryText2 = (EditText) findViewById(R.id.txt_ans2);
 		        String sQueryText1 = queryText1.getText().toString();
 				String sQueryText2 = queryText2.getText().toString();
 				
 				newpwdEditText.setEnabled(false);
 				confirmpwdEditText.setEnabled(false);
 				
 				 
 				/*
 				newpwdEditText.setEnabled(true);
 				 confirmpwdEditText.setEnabled(true);
 				 */
 				
 				if (InputFieldValidations.IsEmpty(sQueryText1)|| InputFieldValidations.IsEmpty(sQueryText2)) {
 					Toast.makeText( Password_Doctor.this,
							"Enter the Answers",
							Toast.LENGTH_SHORT).show();
					Log.i("Forgot Password",
							"Enter the Answers");
 				}
 				else{
 			        queryText1.setEnabled(false);
 			     
 			        queryText2.setEnabled(false);
 			        //
 			       
 			        
 			        //String result = Queryverify(sQueryText1, sQueryText2);
 			       // if (result == "success") {
 			        //enabling the passwords text
 				//oldPwdEditText.setEnabled(true);
 				 newpwdEditText.setEnabled(true);
 				 confirmpwdEditText.setEnabled(true);
 				 launch.setEnabled(true);
 			       // }
 			      //  else if (result == "fail") {
 					//	lblResult
 					//	.setText("Password update failed. Querys does not exist.");
 					//}
 			       // else {
 					//	lblResult
 					//	.setText("!");
 				//	}
 			        
 				}
 				
 			}
         	
         });
        /**This is the action listener for ok button**/
         
        launch.setOnClickListener( new OnClickListener()
        {
               
                public void onClick(View viewParam)
                {
                        // this gets the resources in the xml file and assigns it to a local variable of type EditText
               
                
                // the getText() gets the current value of the text box
                // the toString() converts the value to String data type
                // then assigns it to a variable of type String
            //    String sOldPwd = oldPwdEditText.getText().toString();
                String sNewPwd = newpwdEditText.getText().toString();
                String sConfirmPwd = confirmpwdEditText.getText().toString();
            //    if (InputFieldValidations.IsEmpty(sOldPwd)|| InputFieldValidations.IsEmpty(sNewPwd)||InputFieldValidations.IsEmpty(sConfirmPwd)) {
			//		lblResult.setText("must enter all the fields and default password is doctor");
			//	}
                if ( InputFieldValidations.IsEmpty(sNewPwd)||InputFieldValidations.IsEmpty(sConfirmPwd)) {
			
                	Toast.makeText( Password_Doctor.this,
							"All Fields Must be Entered",
							Toast.LENGTH_SHORT).show();
					Log.i("Forgot Password",
							"All Fields Must be Entered");
                	
				}
                else{
             //  if(sOldPwd.equals("doctor")){
                	EditText queryText1 = (EditText) findViewById(R.id.txt_ans1);
    				 EditText queryText2 = (EditText) findViewById(R.id.txt_ans2);
    		        String sQueryText1 = queryText1.getText().toString();
    				String sQueryText2 = queryText2.getText().toString();
                  if(sNewPwd.equals(sConfirmPwd)){
            	  
            	   System.out.println("Update password.. initiate.."); 
            	   String result = UpdatePassword(sNewPwd, sQueryText1, sQueryText2);
            	   if(result=="success"){
            		   Toast.makeText( Password_Doctor.this,
   							"Password Updated Successfully",
   							Toast.LENGTH_SHORT).show();
   					Log.i("Forgot password",
   							"Password Updated Successfully");
            		   
            		 System.out.println("Password update successful..."); 
            		 
            		 
            	   }
            	   else if (result == "fail") {
            		   Toast.makeText( Password_Doctor.this,
   							"Password Update Failed",
   							Toast.LENGTH_SHORT).show();
   					Log.i("Forgot password",
   							"Password Update Failed");
					}
                	           
                } 
           
                else {
 
                	Toast.makeText( Password_Doctor.this,
							"Password did not Match",
							Toast.LENGTH_SHORT).show();
					Log.i("Forgot password",
							"Password did not Match");
                	
                	        }
             //  }
                
                }
              
        }
        }
       
        ); // end of launch.setOnclickListener
        btnCancel.setOnClickListener(new OnClickListener() {
        	 	   
               	    public void onClick(View v) {
        	
               	    	
     		try {
     			ConstantsFile.foreignContext = createPackageContext(ConstantsFile.packageName, Context.CONTEXT_IGNORE_SECURITY | Context.CONTEXT_INCLUDE_CODE);
     			ConstantsFile.doctorLoginClass = (Class<Doctor_Login>) ConstantsFile.foreignContext.getClassLoader().loadClass(ConstantsFile.doctor_login);
     			Intent intent = new Intent(Password_Doctor.this, ConstantsFile.doctorLoginClass);
             	   startActivity(intent);
     		} catch (ClassNotFoundException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}catch (NameNotFoundException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
      
               	    }
        });
        
    }
   
 // Query Verify function to send Query1 and Query2 for backend verification
  	public static String UpdatePassword(String sNewPwd,String sQueryText1, String sQueryText2) {
  		String cmd = "updatePassword";
  		Log.i("Connection", "Sending message");
  		URLConnection urlConnection = null;
  		String output = "";
  		// System.out.println("inside login function ---->>>>> ");
  		try {
  			urlConnection = Connection.connect();
  			Log.i("Connection", "Encoding message: New Password: " + sNewPwd);
  			// Encode the string combination into a url to send to the php page
  			String data = URLEncoder.encode("myID", "UTF-8") + "="
 		 			+ URLEncoder.encode(Doctor_Login.ID_Cache, "UTF-8") + "&"             //
 		 			+ URLEncoder.encode("sQueryText1", "UTF-8") + "="
 			+ URLEncoder.encode(sQueryText1, "UTF-8") + "&"
 			+ URLEncoder.encode("sQueryText2", "UTF-8") + "="
 			+ URLEncoder.encode(sQueryText2, "UTF-8") + "&"
 		 			+ URLEncoder.encode("sNewPwd", "UTF-8") + "="
  			+ URLEncoder.encode(sNewPwd, "UTF-8") + "&"
  			+ URLEncoder.encode("cmd", "UTF-8") + "="
  			+ URLEncoder.encode(cmd, "UTF-8");
  			// System.out.println("data from the login function---> " + data);
  			// System.out.println("urlConnection.getOutputStream()--->"+urlConnection.getOutputStream());
  			 Log.i("Connection", "Sending message: " + data);
  			OutputStreamWriter wr = new OutputStreamWriter(
  					urlConnection.getOutputStream());
  			// System.out.println("after OutputStreamWriter--->>>>");
  			wr.write(data);
  			// System.out.println("after write");
  			wr.flush();
  			Log.i("Connection", "Sending message to web");
  			String buffer;

  			BufferedReader rd = new BufferedReader(new InputStreamReader(
  					urlConnection.getInputStream()));
  			System.out.println("rd value ::: " + rd.readLine());
  			while ((buffer = rd.readLine()) != null) {
  				Log.i("Connection", "Retrieving message buffer is " + buffer);
  				if (buffer.equals("Pass."))
  					output = "success";
  				else if (buffer.equals("Fail."))
  					output = "fail";
  				else
  					;
  			}
  			wr.close();
  			rd.close();

  		} catch (Exception ex) {
  			Log.i("Connection", "Exception: " + ex);
  			return "bad";
  			// System.out.println(ex.toString());
  		}
  		return output;
  	}
    
}