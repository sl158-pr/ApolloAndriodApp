package com.droid.activities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.net.URLEncoder;

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

import com.droid.utilities.Connection;
import com.droid.utilities.ConstantsFile;
import com.droid.validators.InputFieldValidations;

public class UpdateDoctorDetails  extends Activity {
	
	private static EditText doctorid;
	private static EditText doctorName;
	private static EditText Email;
	private static EditText address;
	private static EditText doctorType;
	private static EditText details;
	private static EditText officeHrs;
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// load up the layout
		setContentView(R.layout.createdoctor);

		// get the button resource in the xml file and assign it to a local
		// variable of type Button
		doctorid = (EditText) findViewById(R.id.txt_docid);
		doctorName = (EditText) findViewById(R.id.txt_docname);
		Email = (EditText) findViewById(R.id.txt_email);
		address = (EditText) findViewById(R.id.txt_address);
		doctorType = (EditText) findViewById(R.id.txt_doctype);
		details = (EditText) findViewById(R.id.txt_details);
		officeHrs = (EditText) findViewById(R.id.txt_officehrs);
		
		

		Bundle b = this.getIntent().getExtras();
		
		String bufferDataString = b.getString("bufferDataString");
		
		System.out.println("bufferDataString in oncreate:" + bufferDataString);
		
		
		String[] splitBuffer = bufferDataString.split(",");
		
		System.out.println("splitBuffer length:"+splitBuffer.length);
		
		for(int i=0;i<splitBuffer.length;i++)
		{
			System.out.println("splitBuffer:" + splitBuffer[i]);
		}
		
		doctorid.setText(splitBuffer[0],TextView.BufferType.NORMAL);
		doctorName.setText(splitBuffer[1],TextView.BufferType.NORMAL);
		address.setText(splitBuffer[2],TextView.BufferType.NORMAL);
		officeHrs.setText(splitBuffer[3],TextView.BufferType.NORMAL);
		Email.setText(splitBuffer[4],TextView.BufferType.NORMAL);
		details.setText(splitBuffer[5],TextView.BufferType.NORMAL);
		doctorType.setText(splitBuffer[6],TextView.BufferType.NORMAL);
		
		
		
		
		
		
		Button btnBack = (Button) findViewById(R.id.canceldoc);

		Button btnCreate = (Button) findViewById(R.id.okbutton);
		//Button btnUpdate = (Button) findViewById(R.id.updatedoctor);
		btnCreate.setVisibility(4);
		Button logout_view = (Button) findViewById(R.id.doctor_logout);
		Button btnUpdateDoc=(Button) findViewById(R.id.updatedoc);
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

					Intent intent = new Intent(UpdateDoctorDetails.this,
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

		btnUpdateDoc.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				EditText DocId = (EditText) findViewById(R.id.txt_docid);
				EditText DocName = (EditText) findViewById(R.id.txt_docname);
			EditText Address = (EditText) findViewById(R.id.txt_address);
			EditText officeHrs = (EditText) findViewById(R.id.txt_officehrs);
			EditText email = (EditText) findViewById(R.id.txt_email);
			EditText Details = (EditText) findViewById(R.id.txt_details);
			EditText DoctorType = (EditText) findViewById(R.id.txt_doctype);
			String sDocId = DocId.getText().toString();
			String sDocName = DocName.getText().toString();
			String sAddress = Address.getText().toString();
			String sOfficeHrs = officeHrs.getText().toString();
			String sEmail = email.getText().toString();
			String sDetails = Details.getText().toString();
			String sDoctorType = DoctorType.getText().toString();
			TextView lblResult = (TextView) findViewById(R.id.create_result);

			String isValid = InputFieldValidations.validateDoctorDetails(
					sDocName, sAddress, sOfficeHrs, sEmail, sDetails,
					sDoctorType);
			if (!(isValid.equals("valid"))) {
				lblResult.setText(isValid);
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
				String result = updateDoctorRecord(sDocId,sDocName, sAddress,
						sOfficeHrs, sEmail, sDetails, sDoctorType);

				if (result == "success") {
					Toast.makeText(UpdateDoctorDetails.this,
							"New doctor record successfully created!",
							Toast.LENGTH_SHORT).show();
					Log.i("DoctorRecordCreate",
							"Doctor record created successfully.. ");

					finish();

					
				} else {
					// Toast.makeText(PatientDetails_Doctor.this,"Problem creating patient record.. Please check that all fields are filled in.",
					// Toast.LENGTH_SHORT).show();
					Log.i("DoctorRecordCreate",
							"Failed to create doctor record.. ");
				}

			}

		}		});

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
					ConstantsFile.doctorLoginClass = (Class<Doctor_Login>) ConstantsFile.foreignContext
							.getClassLoader().loadClass(ConstantsFile.doctor_login);
					Intent intent = new Intent(UpdateDoctorDetails.this,
							ConstantsFile.doctorLoginClass);
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

	//function for fetching doctor
	public static String fetchDoctorDetails( String DID ) {

		String cmd = "fetchDoctorDetails";
		Log.i("Connection", "Trying to retrieve the patient details");
		URLConnection urlConnection = null;
		String output = "";

		String bufferDataString = "";
			
		try {
			
			System.out.println("Doctor ID is :::: " + DID);
			urlConnection = Connection.connect();

			// Encode the string combination into a url to send to the php page
			String data = URLEncoder.encode("DID", "UTF-8") + "="
					+ URLEncoder.encode(DID, "UTF-8") + "&"
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
				
	
				bufferData.append(buffer + ",");
				//Log.i("Connection", "Retrieving message buffer is " + buffer);

			}
		
			bufferDataString = bufferData.toString();
			
			System.out.println("bufferDataString:" + bufferDataString);
				
			wr.close();
			rd.close();
			

		} catch (Exception ex) {
			Log.i("fetchDoctorDetails", "Exception: " + ex);
			// System.out.println(ex.toString());
		}
		return bufferDataString;

	}
	public static String updateDoctorRecord(String DID,String DoctorName,String Address,String
			OfficeHrs,String Email,String Details,String DoctorType) {
		String cmd = "updateDoctorRecord";
		Log.i("Connection from updatePatientRecord", "Sending message");
		URLConnection urlConnection = null;

		String output = "";
		// System.out.println("inside login function ---->>>>> ");
		try {
			urlConnection = Connection.connect();

			Log.i("Connection from updatePatientRecord", "Encoding message: ");
			// Encode the string combination into a url to send to the php page
			String data = URLEncoder.encode("DID", "UTF-8") + "="
			+ URLEncoder.encode(DID, "UTF-8") + "&"
			+ URLEncoder.encode("DoctorName", "UTF-8") + "="
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
