package com.droid.activities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.utilities.Connection;
import com.droid.utilities.ConstantsFile;
import com.droid.validators.InputFieldValidations;

public class AdminUpdatePatientDetails extends Activity {

	
	
	
	
	private static EditText patientid;
	
	//patientid.setVisibility(4);
	
	private static EditText patientName;
	private static EditText Email;
	private static EditText patientAge;
	private static EditText bloodType;
	private static EditText gender;
	private static EditText bloodPressure;
	private static EditText allergies;
	private static EditText diagnosis;
	private static EditText primaryDoc;
	private static EditText secondaryDoc;
	

	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// load up the layout
		setContentView(R.layout.patientdetails);

		// get the button resource in the xml file and assign it to a local
		// variable of type Button

		Button btnBack = (Button) findViewById(R.id.back_button);
		
		
		
		Button btnUpdate = (Button) findViewById(R.id.Update_button);
		
		
		Button btnCreate = (Button) findViewById(R.id.Create_button);
		btnCreate.setVisibility(4);
		
		
		Button btnLogout = (Button) findViewById(R.id.Logout_button);
		
		patientid = (EditText) findViewById(R.id.txt_PID);
		patientName = (EditText) findViewById(R.id.txt_Name);
		Email = (EditText) findViewById(R.id.txt_Email);
		patientAge = (EditText) findViewById(R.id.txt_Age);
		bloodType = (EditText) findViewById(R.id.txt_BloodType);
		gender = (EditText) findViewById(R.id.txt_Gender);
		bloodPressure = (EditText) findViewById(R.id.txt_BP);
		allergies = (EditText) findViewById(R.id.txt_Allergies);
		diagnosis = (EditText) findViewById(R.id.txt_Diagnosis);
		primaryDoc = (EditText) findViewById(R.id.txt_PrimaryDoc);
		secondaryDoc = (EditText) findViewById(R.id.txt_SecondaryDoc);
		

		Bundle b = this.getIntent().getExtras();
		
		String bufferDataString = b.getString("bufferDataString");
		
		System.out.println("bufferDataString in oncreate:" + bufferDataString);
		
		
		String[] splitBuffer = bufferDataString.split(":");
		
		System.out.println("splitBuffer length:"+splitBuffer.length);
		
		for(int i=0;i<splitBuffer.length;i++)
		{
			System.out.println("splitBuffer:" + splitBuffer[i]);
		}
		
		patientid.setText(splitBuffer[0],TextView.BufferType.NORMAL);
		patientName.setText(splitBuffer[1],TextView.BufferType.NORMAL);
		Email.setText(splitBuffer[2],TextView.BufferType.NORMAL);
		patientAge.setText(splitBuffer[3],TextView.BufferType.NORMAL);
		bloodType.setText(splitBuffer[4],TextView.BufferType.NORMAL);
		gender.setText(splitBuffer[5],TextView.BufferType.NORMAL);
		bloodPressure.setText(splitBuffer[6],TextView.BufferType.NORMAL);
		allergies.setText(splitBuffer[7],TextView.BufferType.NORMAL);
		diagnosis.setText(splitBuffer[8],TextView.BufferType.NORMAL);
		primaryDoc.setText(splitBuffer[9],TextView.BufferType.NORMAL);
		secondaryDoc.setText(splitBuffer[10],TextView.BufferType.NORMAL);

		
		
		
		
		//TextView pid = (TextView) findViewById(R.id.PID);
		//pid.setVisibility(4);

		// TextView Logout = (TextView) findViewById(R.id.logout);

		// When the Back button is clicked, load up the Doctor's/Admin's profile
		// screen
		btnBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				System.out.println("Back initiated..");

				
				Intent intent = new Intent(v.getContext(), ConstantsFile.adminClass);
				startActivityForResult(intent, 0);

				
				
				
				//startActivity(intent);

			}
		});


		btnUpdate.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// this gets the resources in the xml file and assigns it to a
				// local variable of type EditText

				System.out.println("Update patient.. initiate..");

				Log.i("DocLoggedIn", "Update patient record.. Initiate..");
				
				EditText patientIDEditText = (EditText) findViewById(R.id.txt_PID);
				EditText patientNameEditText = (EditText) findViewById(R.id.txt_Name);
				System.out.println("1------------>");
				EditText ageEditText = (EditText) findViewById(R.id.txt_Age);
				System.out.println("2------------>");
				EditText primaryDocEditText = (EditText) findViewById(R.id.txt_PrimaryDoc);
				System.out.println("3------------>");
				EditText secondaryDocEditText = (EditText) findViewById(R.id.txt_SecondaryDoc);
				EditText emailEditText = (EditText) findViewById(R.id.txt_Email);
				EditText genderEditText = (EditText) findViewById(R.id.txt_Gender);
				EditText bloodTypeEditText = (EditText) findViewById(R.id.txt_BloodType);
				EditText bpEditText = (EditText) findViewById(R.id.txt_BP);
				EditText allergiesEditText = (EditText) findViewById(R.id.txt_Allergies);
				EditText diagnosisEditText = (EditText) findViewById(R.id.txt_Diagnosis);
				TextView lblResult = (TextView) findViewById(R.id.result);
				System.out.println("Update patient.. middle..");

				// the getText() gets the current value of the text box
				// the toString() converts the value to String data type
				// then assigns it to a variable of type String
				String sPatientID = patientIDEditText.getText().toString();
				String sPatientName = patientNameEditText.getText().toString();
				String sAge = ageEditText.getText().toString();
				String sPrimaryDoc = primaryDocEditText.getText().toString();
				String sSecondaryDoc = secondaryDocEditText.getText()
						.toString();
				String sEmail = emailEditText.getText().toString();
				String sGender = genderEditText.getText().toString();
				String sBloodType = bloodTypeEditText.getText().toString();
				String sBP = bpEditText.getText().toString();
				String sAllergies = allergiesEditText.getText().toString();
				String sDiagnosis = diagnosisEditText.getText().toString();

				String ifValid = InputFieldValidations.validatePatientDetails(
						sPatientName, sAge, sPrimaryDoc, sSecondaryDoc, sEmail,
						sGender, sBloodType, sBP, sAllergies, sDiagnosis);
				if (!(ifValid.equals("valid"))) {
					//lblResult.setText(ifValid);
					Toast.makeText(AdminUpdatePatientDetails.this,
							ifValid,
							Toast.LENGTH_SHORT).show();
					/*Log.i("DocViewUpdatePatientDetails", "Invoking updatePatientRecord again..");
					
					Intent intent = new Intent(v.getContext(), ConstantsFile.DocViewUpdatePatientDetailsClass);
					startActivityForResult(intent, 0);*/
					
				} else {
					System.out.println("PatientID:" + sPatientID);
					System.out.println("PatientName:" + sPatientName);
					System.out.println("Age:" + sAge);
					System.out.println("Primary Doc:" + sPrimaryDoc);
					System.out.println("sSecondaryDoc:" + sSecondaryDoc);
					System.out.println("sEmail:" + sEmail);
					System.out.println("sGender:" + sGender);
					System.out.println("sBloodType:" + sBloodType);
					System.out.println("sBP:" + sBP);
					System.out.println("sAllergies:" + sAllergies);
					System.out.println("sDiagnosis:" + sDiagnosis);

					Log.i("DocLoggedIn", "Invoking updatePatientRecord");
					String result = updatePatientRecord(sPatientID,sPatientName.trim(), sAge.trim(),

					sPrimaryDoc.trim(), sSecondaryDoc.trim(), sEmail.trim(), sGender.trim(), sBloodType.trim(),
							sBP.trim(), sAllergies.trim(), sDiagnosis.trim());

					if (result == "success") {
						Toast.makeText(AdminUpdatePatientDetails.this,
								"Patient record successfully updated!",
								Toast.LENGTH_SHORT).show();
						Log.i("PatientRecordUpdate",
								"Patient record updated successfully.. ");

						finish();

					} else {
						 Toast.makeText(AdminUpdatePatientDetails.this,"Problem creating patient record.. Please check that all fields are filled in.",
						 Toast.LENGTH_SHORT).show();
						Log.i("PatientRecordUpdate",
								"Failed to update patient record.. ");
					}

				}
			}
		});
		
	
		
		
		
		// Log out by loading up the Login screen

		btnLogout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				System.out.println("Logout initiated..");

/*					ConstantsFile.foreignContext = createPackageContext(
						ConstantsFile.packageName,
						Context.CONTEXT_IGNORE_SECURITY
								| Context.CONTEXT_INCLUDE_CODE);
				ConstantsFile.loginClass = (Class<Login>) ConstantsFile.foreignContext
						.getClassLoader().loadClass(ConstantsFile.login);
				Intent intent = new Intent(DocViewUpdatePatientDetails.this,
						ConstantsFile.loginClass);
				startActivity(intent);*/
				
				
				
				Intent intent = new Intent(v.getContext(), ConstantsFile.adminLoginClass);
				startActivityForResult(intent, 0);


				finish();

			}

		});

	}

	
	public static String fetchPatientDetails( String PID ) {

		String cmd = "fetchPatientDetails";
		Log.i("Connection", "Trying to retrieve the patient details");
		URLConnection urlConnection = null;
		String output = "";

		String bufferDataString = "";
		
		
		
		
		
		try {
			
			System.out.println("Patient ID is :::: " + PID);
			urlConnection = Connection.connect();

			// Encode the string combination into a url to send to the php page
			String data = URLEncoder.encode("PatientID", "UTF-8") + "="
					+ URLEncoder.encode(PID, "UTF-8") + "&"
					+ URLEncoder.encode("cmd", "UTF-8") + "="
					+ URLEncoder.encode(cmd, "UTF-8");

			Log.i("Connection", "Sending message: " + data);
			OutputStreamWriter wr = new OutputStreamWriter(
					urlConnection.getOutputStream());
			wr.write(data);
			wr.flush();
			Log.i("Connection", "Sending message to web");
			String buffer;

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			System.out.println("rd value ::: " + rd.readLine());
			StringBuffer bufferData = new StringBuffer(0);
			
			while ((buffer = rd.readLine()) != null) {
				
	
				bufferData.append(buffer + ":");
				//Log.i("Connection", "Retrieving message buffer is " + buffer);

			}
		
			
			
			bufferDataString = bufferData.toString();
			
			System.out.println("bufferDataString:" + bufferDataString);
			
			
			
			
			
			
			wr.close();
			rd.close();
			
		
			
			//return bufferDataString;
			

		} catch (Exception ex) {
			Log.i("fetchPatientDetails", "Exception: " + ex);
			// System.out.println(ex.toString());
		}
		return bufferDataString;

	}

	
	// Function for passing on the details to the php script
	public static String updatePatientRecord(String patientID, String patientName, String age,
			String primaryDoc, String secondaryDoc, String email,
			String gender, String bloodType, String BP, String allergies,
			String diagnosis) {
		String cmd = "updatePatientRecord";
		Log.i("Connection from updatePatientRecord", "Sending message");
		URLConnection urlConnection = null;

		String output = "";
		// System.out.println("inside login function ---->>>>> ");
		try {
			urlConnection = Connection.connect();

			Log.i("Connection from updatePatientRecord", "Encoding message: ");
			// Encode the string combination into a url to send to the php page
			String data = URLEncoder.encode("patientID", "UTF-8") + "="
					+ URLEncoder.encode(patientID, "UTF-8") + "&" 
					+ URLEncoder.encode("patientName", "UTF-8") + "="
					+ URLEncoder.encode(patientName, "UTF-8") + "&"
					+ URLEncoder.encode("age", "UTF-8") + "="
					+ URLEncoder.encode(age, "UTF-8") + "&"
					+ URLEncoder.encode("primaryDoc", "UTF-8") + "="
					+ URLEncoder.encode(primaryDoc, "UTF-8") + "&"
					+ URLEncoder.encode("secondaryDoc", "UTF-8") + "="
					+ URLEncoder.encode(secondaryDoc, "UTF-8") + "&"
					+ URLEncoder.encode("email", "UTF-8") + "="
					+ URLEncoder.encode(email, "UTF-8") + "&"
					+ URLEncoder.encode("gender", "UTF-8") + "="
					+ URLEncoder.encode(gender, "UTF-8") + "&"
					+ URLEncoder.encode("bloodType", "UTF-8") + "="
					+ URLEncoder.encode(bloodType, "UTF-8") + "&"
					+ URLEncoder.encode("BP", "UTF-8") + "="
					+ URLEncoder.encode(BP, "UTF-8") + "&"
					+ URLEncoder.encode("allergies", "UTF-8") + "="
					+ URLEncoder.encode(allergies, "UTF-8") + "&"
					+ URLEncoder.encode("diagnosis", "UTF-8") + "="
					+ URLEncoder.encode(diagnosis, "UTF-8") + "&"
					+ URLEncoder.encode("cmd", "UTF-8") + "="
					+ URLEncoder.encode(cmd, "UTF-8");
			// System.out.println("data from the login function---> " + data);
			System.out.println("urlConnection.getOutputStream()--->"
					+ urlConnection.getOutputStream());
			Log.i("Connection", "Sending message: " + data);
			OutputStreamWriter wr = new OutputStreamWriter(
					urlConnection.getOutputStream());
			System.out.println("after OutputStreamWriter--->>>>");
			wr.write(data);
			System.out.println("after write");
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