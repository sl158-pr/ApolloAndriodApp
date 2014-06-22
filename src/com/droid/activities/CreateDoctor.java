package com.droid.activities;

// Creation of doctor record

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

public class CreateDoctor extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// load up the layout
		setContentView(R.layout.createdoctor);

		// get the button resource in the xml file and assign it to a local
		// variable of type Button

		Button btnBack = (Button) findViewById(R.id.canceldoc);
		EditText doctorid = (EditText) findViewById(R.id.txt_docid);
		doctorid.setVisibility(4);
		TextView docid = (TextView) findViewById(R.id.docid);
		docid.setVisibility(4);
		Button btnCreate = (Button) findViewById(R.id.okbutton);

		Button logout_view = (Button) findViewById(R.id.doctor_logout);

		// When the Back button is clicked, load up the Admin screen

		btnBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				try {
					ConstantsFile.foreignContext = createPackageContext(
							ConstantsFile.packageName,
							Context.CONTEXT_IGNORE_SECURITY
									| Context.CONTEXT_INCLUDE_CODE);
					ConstantsFile.adminClass = (Class<Admin>) ConstantsFile.foreignContext
							.getClassLoader().loadClass(ConstantsFile.admin);

					Intent intent = new Intent(CreateDoctor.this,
							ConstantsFile.adminClass);
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

		// When the Create button is clicked, load up the screen where the
		// doctor's details can be entered for record creation
		btnCreate.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			public void onClick(View v) {

				EditText DocName = (EditText) findViewById(R.id.txt_docname);
				EditText Address = (EditText) findViewById(R.id.txt_address);
				EditText officeHrs = (EditText) findViewById(R.id.txt_officehrs);
				EditText email = (EditText) findViewById(R.id.txt_email);
				EditText Details = (EditText) findViewById(R.id.txt_details);
				EditText DoctorType = (EditText) findViewById(R.id.txt_doctype);

				String sDocName = DocName.getText().toString();
				String sAddress = Address.getText().toString();
				String sOfficeHrs = officeHrs.getText().toString();
				String sEmail = email.getText().toString();
				String sDetails = Details.getText().toString();
				String sDoctorType = DoctorType.getText().toString();

				String isValid = InputFieldValidations.validateDoctorDetails(
						sDocName, sAddress, sOfficeHrs, sEmail, sDetails,
						sDoctorType);
				if (!(isValid.equals("valid"))) {
					Toast.makeText(CreateDoctor.this,
							isValid,
							Toast.LENGTH_SHORT).show();
					Log.i("Create patient",
							isValid);
				} else {
					/*
					 * ConstantsFile.foreignContext =
					 * createPackageContext(ConstantsFile.packageName,
					 * Context.CONTEXT_IGNORE_SECURITY |
					 * Context.CONTEXT_INCLUDE_CODE); ConstantsFile.adminClass =
					 * (Class<Admin>)
					 * ConstantsFile.foreignContext.getClassLoader()
					 * .loadClass(ConstantsFile.admin);
					 * 
					 * Intent intent = new Intent(CreateDoctor.this,
					 * ConstantsFile.adminClass); startActivity(intent); }
					 */

					System.out.println("DocName:" + sDocName);
					System.out.println("Address:" + sAddress);
					System.out.println("OfficeHrs:" + sOfficeHrs);
					System.out.println("Email:" + sEmail);
					System.out.println("Details:" + sDetails);
					System.out.println("DoctorType:" + sDoctorType);

					Log.i("Create Doctor", "Invoking createDoctorRecord");
					String result = createDoctorRecord(sDocName, sAddress,
							sOfficeHrs, sEmail, sDetails, sDoctorType);

					if (result == "success") {
						Toast.makeText(CreateDoctor.this,
								"New doctor record successfully created!",
								Toast.LENGTH_SHORT).show();
						Log.i("DoctorRecordCreate",
								"Doctor record created successfully.. ");

						finish();

						/*
						 * try { ConstantsFile.foreignContext =
						 * createPackageContext( ConstantsFile.packageName,
						 * Context.CONTEXT_IGNORE_SECURITY |
						 * Context.CONTEXT_INCLUDE_CODE);
						 * ConstantsFile.doctorClass = (Class<Doctor>)
						 * ConstantsFile.foreignContext
						 * .getClassLoader().loadClass(ConstantsFile.doctor);
						 * Intent intent = new Intent(CreateDoctor.this,
						 * ConstantsFile.doctorClass); startActivity(intent); }
						 * catch (ClassNotFoundException e) { // TODO
						 * Auto-generated catch block e.printStackTrace(); }
						 * catch (NameNotFoundException e) { // TODO
						 * Auto-generated catch block e.printStackTrace(); }
						 */
					} else {
						// Toast.makeText(PatientDetails_Doctor.this,"Problem creating patient record.. Please check that all fields are filled in.",
						// Toast.LENGTH_SHORT).show();
						Log.i("DoctorRecordCreate",
								"Failed to create doctor record.. ");
					}

				}

			}
		});

		// When the Logout button is clicked, log out by loading up the login
		// screen

		logout_view.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("unchecked")
			public void onClick(View v) {

				try {
					System.out.println("Logout initiated..");

					ConstantsFile.foreignContext = createPackageContext(
							ConstantsFile.packageName,
							Context.CONTEXT_IGNORE_SECURITY
									| Context.CONTEXT_INCLUDE_CODE);

					ConstantsFile.adminLoginClass = (Class<Admin_login>) ConstantsFile.foreignContext
							.getClassLoader().loadClass(ConstantsFile.admin_login);

					Intent intent = new Intent(CreateDoctor.this,
							ConstantsFile.adminLoginClass);
					startActivity(intent);

					finish();

					// moveTaskToBack(true);

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

	// Function for passing on the doctor's details to the php script

	public static String createDoctorRecord(String DoctorName, String Address,
			String OfficeHrs, String Email, String Details, String DoctorType) {
		String cmd = "createDoctorRecord";
		Log.i("Connection from createDoctorRecord", "Sending message");
		URLConnection urlConnection = null;

		String output = "";
		// System.out.println("inside login function ---->>>>> ");
		try {
			urlConnection = Connection.connect();

			Log.i("Connection from createDoctorRecord", "Encoding message: ");
			// Encode the string combination into a url to send to the php page
			String data = URLEncoder.encode("DoctorName", "UTF-8") + "="
					+ URLEncoder.encode(DoctorName, "UTF-8") + "&"
					+ URLEncoder.encode("address", "UTF-8") + "="
					+ URLEncoder.encode(Address, "UTF-8") + "&"
					+ URLEncoder.encode("OfficeHrs", "UTF-8") + "="
					+ URLEncoder.encode(OfficeHrs, "UTF-8") + "&"
					+ URLEncoder.encode("Email", "UTF-8") + "="
					+ URLEncoder.encode(Email, "UTF-8") + "&"
					+ URLEncoder.encode("Details", "UTF-8") + "="
					+ URLEncoder.encode(Details, "UTF-8") + "&"
					+ URLEncoder.encode("DoctorType", "UTF-8") + "="
					+ URLEncoder.encode(DoctorType, "UTF-8") + "&"
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