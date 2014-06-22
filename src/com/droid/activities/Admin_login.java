package com.droid.activities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.droid.utilities.Connection;
import com.droid.utilities.ConstantsFile;
import com.droid.validators.InputFieldValidations;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Admin_login extends Activity {	/** Called when the activity is first created. */
	public static String ID_Cache = null;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		// load up the layout
		setContentView(R.layout.activity_admin_login);

		// get the button resource in the xml file and assign it to a local variable of type Button
		Button launch = (Button) findViewById(R.id.admin_login_button);
		Button forgot = (Button) findViewById(R.id.password_button);

		
		//	When the login button is clicked, the username and password are fetched from the text fields and verified with the backend.
		//  If successfully matched, the corresponding screen is loaded up 

		launch.setOnClickListener(new OnClickListener() {

			public void onClick(View viewParam) {
				// this gets the resources in the xml file and assigns it to a
				// local variable of type EditText
				EditText usernameEditText = (EditText) findViewById(R.id.admin_username);
				EditText passwordEditText = (EditText) findViewById(R.id.admin_password);
				TextView lblResult = (TextView) findViewById(R.id.doctor_result);

				// the getText() gets the current value of the text box
				// the toString() converts the value to String data type,then assigns it to a variable of type String
				String sUserName = usernameEditText.getText().toString();
				String sPassword = passwordEditText.getText().toString();

				// if(sUserName.equals("doctor") && sPassword.equals("doctor")){
				ID_Cache = sUserName;
				/**validating the input fields**/
				if (InputFieldValidations.IsEmpty(sUserName)|| InputFieldValidations.IsEmpty(sPassword)) {
					Toast.makeText( Admin_login.this,
							"Enter username and password",
							Toast.LENGTH_SHORT).show();
					Log.i("Login",
							"Enter username and password");
				}
				else if(sUserName.length()<3||(sUserName.length()>15)){
					Toast.makeText(Admin_login.this,
							"Enter unsername of length greater than 3",
							Toast.LENGTH_SHORT).show();
					Log.i("Login",
							"Enter unsername of length greater than 3");
				}
				else if(sPassword.length()<3||(sPassword.length()>15)){
					Toast.makeText(Admin_login.this,
							"Enter password of length greater than 3",
							Toast.LENGTH_SHORT).show();
					Log.i("Login",
							"Enter password of length greater than 3");
				}
				
				else{
					/**validating if Admin is entering into application**/
					if (sUserName.substring(0, 3).equals("ADM")) {

						String result = login(sUserName, sPassword);
						if (result == "success") {

							try {
								ConstantsFile.foreignContext = createPackageContext(
										ConstantsFile.packageName,
										Context.CONTEXT_IGNORE_SECURITY
										| Context.CONTEXT_INCLUDE_CODE);
								ConstantsFile.adminClass = (Class<Admin>) ConstantsFile.foreignContext.getClassLoader().loadClass("com.droid.activities.Admin");
								Intent intent = new Intent(Admin_login.this, ConstantsFile.adminClass);
								startActivity(intent);
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NameNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						else if(result == "fail") {
							Toast.makeText(Admin_login.this,
									"The username or password you entered is incorrect",
									Toast.LENGTH_SHORT).show();
							Log.i("Login",
									"The username or password you entered is incorrect");

						}
						else{
							Toast.makeText(Admin_login.this,
									"Connection Error",
									Toast.LENGTH_SHORT).show();
							Log.i("Login",
									"Connection Error");
							
						}

					}
					

					else{
						Toast.makeText(Admin_login.this,
								"Invalid Username",
								Toast.LENGTH_SHORT).show();
						Log.i("Login",
								"Invalid Username");
					}
				}
			}
		}
		); // end of launch.setOnclickListener
		
		forgot.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
            //
				EditText usernameEditText = (EditText) findViewById(R.id.admin_username);
				
				String sUserName = usernameEditText.getText().toString();
				ID_Cache = sUserName;
				/**validating the input fields**/
				if (InputFieldValidations.IsEmpty(sUserName)) {
					Toast.makeText(Admin_login.this,
							"Enter a valid username",
							Toast.LENGTH_SHORT).show();
					Log.i("Login",
							"Enter a valid username");
				}
				else if(sUserName.length()<3||(sUserName.length()>15)){
					Toast.makeText(Admin_login.this,
							"Enter a valid username of length greater than 3",
							Toast.LENGTH_SHORT).show();
					Log.i("Login",
							"Enter a valid username of length greater than 3");
				}

				else{
					if (sUserName.substring(0, 3).equals("ADM")) {
						String result = Verify(sUserName);
						if (result == "success") {
				
				try {
					
					ConstantsFile.foreignContext = createPackageContext(
							ConstantsFile.packageName,
							Context.CONTEXT_IGNORE_SECURITY
									| Context.CONTEXT_INCLUDE_CODE);
					ConstantsFile.passwordDoctorClass = (Class<Password_Doctor>) ConstantsFile.foreignContext
							.getClassLoader().loadClass(ConstantsFile.password_doctor);
					Intent intent = new Intent(Admin_login.this,
							ConstantsFile.passwordDoctorClass);
					
					
					startActivity(intent);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						}
						else if (result == "fail") {
							Toast.makeText(Admin_login.this,
									"Username does not exist.",
									Toast.LENGTH_SHORT).show();
							Log.i("Login",
									"Username does not exist.");

						}
						else {
							Toast.makeText(Admin_login.this,
									"Connection Error.",
									Toast.LENGTH_SHORT).show();
							Log.i("Login",
									"Connection Error");
						}
					}
					else{
						Toast.makeText(Admin_login.this,
								"Username does not exist.",
								Toast.LENGTH_SHORT).show();
						Log.i("Login",
								"Username does not exist.");
					}
				}
			}
		});

	}

	
// Login function to send username and password for backend verification
	public static String login(String username, String password) {
		String cmd = "login";
		Log.i("Connection", "Sending message");
		URLConnection urlConnection = null;
		String output = "";
		// System.out.println("inside login function ---->>>>> ");
		try {
			urlConnection = Connection.connect();
			Log.i("Connection", "Encoding message: Username was: " + username
					+ " and password was: " + password);
			// Encode the string combination into a url to send to the php page
			String data = URLEncoder.encode("username", "UTF-8") + "="
			+ URLEncoder.encode(username, "UTF-8") + "&"
			+ URLEncoder.encode("password", "UTF-8") + "="
			+ URLEncoder.encode(password, "UTF-8") + "&"
			+ URLEncoder.encode("cmd", "UTF-8") + "="
			+ URLEncoder.encode(cmd, "UTF-8");
			// System.out.println("data from the login function---> " + data);
			// System.out.println("urlConnection.getOutputStream()--->"+urlConnection.getOutputStream());
			 Log.i("Connection", "Sending message: " + data);
			OutputStreamWriter wr = new OutputStreamWriter(
					urlConnection.getOutputStream());
			// System.out.println("after OutputStreamWriter--->>>>");
			wr.write(data);
			// System.out.println("afte write");
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
	
	//Verify function to send username backend verification	for Forgot Password
		public static String Verify(String username){
			String cmd = "fgPassword";
			Log.i("Connection", "Sending message");
			URLConnection urlConnection = null;
			String output = "";
			// System.out.println("inside login function ---->>>>> ");
			try {
				urlConnection = Connection.connect();
				Log.i("Connection", "Encoding message: Username was: " + username);
				// Encode the string combination into a url to send to the php page
				String data = URLEncoder.encode("username", "UTF-8") + "="
				+ URLEncoder.encode(username, "UTF-8") + "&"
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
