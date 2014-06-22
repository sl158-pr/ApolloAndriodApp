package com.droid.activities;

// View/Edit Patient's Details

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

public class DocViewPatientDetails extends Activity {

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
		btnUpdate.setVisibility(4);
		
		Button btnCreate = (Button) findViewById(R.id.Create_button);
		
		Button btnLogout = (Button) findViewById(R.id.Logout_button);
		EditText patientid = (EditText) findViewById(R.id.txt_PID);
		patientid.setVisibility(4);
		TextView pid = (TextView) findViewById(R.id.PID);
		pid.setVisibility(4);

		// TextView Logout = (TextView) findViewById(R.id.logout);

		// When the Back button is clicked, load up the Doctor's/Admin's profile
		// screen
		
		btnBack.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			
			public void onClick(View v) {

				try {
					System.out.println("Back initiated..");

					ConstantsFile.foreignContext = createPackageContext(
							ConstantsFile.packageName,
							Context.CONTEXT_IGNORE_SECURITY
									| Context.CONTEXT_INCLUDE_CODE);
					ConstantsFile.doctorClass = (Class<Doctor>) ConstantsFile.foreignContext
							.getClassLoader().loadClass(ConstantsFile.doctor);
					Intent intent = new Intent(DocViewPatientDetails.this,
							ConstantsFile.doctorClass);
					startActivity(intent);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		// When the Create button is clicked, capture all the details entered
		// and call the function that will pass on the details to the php script

		btnCreate.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// this gets the resources in the xml file and assigns it to a
				// local variable of type EditText

				System.out.println("Create patient.. initiate..");

				Log.i("DocLoggedIn", "Creating patient record.. Initiate..");

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
				System.out.println("Create patient.. middle..");

				// the getText() gets the current value of the text box
				// the toString() converts the value to String data type
				// then assigns it to a variable of type String
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
					Toast.makeText(DocViewPatientDetails.this,
							ifValid,
							Toast.LENGTH_SHORT).show();
					Log.i("Create patient",
							ifValid);
				} else {
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

					Log.i("DocLoggedIn", "Invoking createPatientRecord");
					String result = createPatientRecord(sPatientName.trim(), sAge.trim(),

					sPrimaryDoc.trim(), sSecondaryDoc.trim(), sEmail.trim(), sGender.trim(), sBloodType.trim(),
							sBP.trim(), sAllergies.trim(), sDiagnosis.trim());

					if (result == "success") {
						Toast.makeText(DocViewPatientDetails.this,
								"New patient record successfully created!",
								Toast.LENGTH_SHORT).show();
						Log.i("PatientRecordCreate",
								"Patient record created successfully.. ");

						/*
						 * foreignContext = createPackageContext("com.DaDroid",
						 * Context.CONTEXT_IGNORE_SECURITY |
						 * Context.CONTEXT_INCLUDE_CODE); doctor =
						 * (Class<Doctor>) foreignContext
						 * .getClassLoader().loadClass( "com.DaDroid.Doctor");
						 * Intent intent = new
						 * Intent(PatientDetails_Doctor.this, doctor);
						 * 
						 * startActivity(intent);
						 */

						finish();

					} else {
						// Toast.makeText(PatientDetails_Doctor.this,"Problem creating patient record.. Please check that all fields are filled in.",
						// Toast.LENGTH_SHORT).show();
						Log.i("PatientRecordCreate",
								"Failed to create patient record.. ");
					}

				}
			}
		});

		// Log out by loading up the Login screen

		btnLogout.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("unchecked")
			public void onClick(View v) {

				try {
					System.out.println("Logout initiated..");

					ConstantsFile.foreignContext = createPackageContext(
							ConstantsFile.packageName,
							Context.CONTEXT_IGNORE_SECURITY
									| Context.CONTEXT_INCLUDE_CODE);
					ConstantsFile.doctorLoginClass = (Class<Doctor_Login>) ConstantsFile.foreignContext
							.getClassLoader().loadClass(ConstantsFile.doctor_login);
					Intent intent = new Intent(DocViewPatientDetails.this,
							ConstantsFile.doctorLoginClass);
					startActivity(intent);

					finish();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

	}

	// Function for passing on the details to the php script
	public static String createPatientRecord(String patientName, String age,
			String primaryDoc, String secondaryDoc, String email,
			String gender, String bloodType, String BP, String allergies,
			String diagnosis) {
		String cmd = "createPatientRecord";
		Log.i("Connection from createPatientRecord", "Sending message");
		URLConnection urlConnection = null;

		String output = "";
		// System.out.println("inside login function ---->>>>> ");
		try {
			urlConnection = Connection.connect();

			Log.i("Connection from createPatientRecord", "Encoding message: ");
			// Encode the string combination into a url to send to the php page
			String data = URLEncoder.encode("patientName", "UTF-8") + "="
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