package com.droid.activities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.droid.activities.R;
import com.droid.utilities.Connection;
import com.droid.utilities.ConstantsFile;
import com.droid.utilities.CustomAdapter;
import com.droid.utilities.Device;
import com.droid.validators.InputFieldValidations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Admin extends Activity {
	String result="Success";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// load up the layout
		setContentView(R.layout.doctorprofile);

		// get the button resource in the xml file and assign it to a local
		// variable of type Button
		TextView admintxt = (TextView) findViewById(R.id.dr_txt);
		admintxt.setVisibility(4);

		Button btnViewDoctor = (Button) findViewById(R.id.viewdoc_button);
		
		  Button btnAdminBack = (Button) findViewById(R.id.back_doctor);
		  btnAdminBack.setVisibility(4);
		 
		TableRow row3 = (TableRow) findViewById(R.id.row5);
		row3.setVisibility(4);
		TableRow row4 = (TableRow) findViewById(R.id.row6);
		row4.setVisibility(4);
		Button btnViewPatient = (Button) findViewById(R.id.view_button);

		Button btnAdminCreate = (Button) findViewById(R.id.create_patient);
		Button btnAdmViewMySchedule = (Button) findViewById(R.id.ViewMySchedule);
		btnAdmViewMySchedule.setVisibility(4);
		Button btnAdminCreateDoc = (Button) findViewById(R.id.create_doctor);
		Button btnSearchPatient = (Button) findViewById(R.id.searchby);
		Button btnSearchDoctor = (Button) findViewById(R.id.searchdoc);
		Button btnSearchBy = (Button) findViewById(R.id.search_doc);
		TextView logout_view = (TextView) findViewById(R.id.logout);

		// When the Search button is clicked, load up the corresponding
		// patient's profile screen

		btnSearchPatient.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("unchecked")
			public void onClick(View v) {
				EditText idtext = (EditText) findViewById(R.id.idname);
				String sPid = idtext.getText().toString();

				if (InputFieldValidations.IsEmpty(sPid)) {
					Toast.makeText( Admin.this,
							"Enter a valid Patient ID",
							Toast.LENGTH_SHORT).show();
					Log.i("Search",
							"Enter a valid Patient ID");
				} else {
					/**
					 * code to search for the patient details and then go to
					 * patient details page
					 **/
					searchPatient(sPid);
					System.out
							.println("Successful in fetching patients' list..");

					

				}
			}
		});
		// searching doctor details
		btnSearchDoctor.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("unchecked")
			public void onClick(View v) {
				EditText idtext = (EditText) findViewById(R.id.doc_idname);
				String docname = idtext.getText().toString();

				if (InputFieldValidations.IsEmpty(docname)) {
					Toast.makeText( Admin.this,
							"Enter the doctors Id or Name",
							Toast.LENGTH_SHORT).show();
					Log.i("Login",
							"Enter the doctors Id or Name");
				} else {
					/**
					 * code to search for the patient details and then go to
					 * patient details page
					 **/

					searchDoctor(docname);
					System.out
							.println("Successful in fetching doctor's details..");
					if(result=="Success")
					{displayDoctorsList();
					}
					
					 


				}
			}
		});
		// When the Back button is clicked, go back to the Login screen

		/*
		 * btnAdminBack.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * 
		 * Intent intent = new Intent(v.getContext(), ConstantsFile.loginClass);
		 * startActivityForResult(intent, 0);
		 * 
		 * } });
		 */

		// When the Create Patient button is clicked, load up the screen where
		// the patient details can be entered for creation of the patient record

		btnAdminCreate.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			public void onClick(View v) {

				
				try {
					
					Log.i("D_CreatePatientProfile", "Patient screen coming up");
					
					ConstantsFile.foreignContext = createPackageContext(
							ConstantsFile.packageName,
							Context.CONTEXT_IGNORE_SECURITY
									| Context.CONTEXT_INCLUDE_CODE);
					ConstantsFile.adminCreatePatientDetailsClass = (Class<AdminCreatePatientDetails>) ConstantsFile.foreignContext
							.getClassLoader().loadClass(
									ConstantsFile.adminCreatePatientDetails);
					Intent intent = new Intent(Admin.this,
							ConstantsFile.adminCreatePatientDetailsClass);
					startActivity(intent);

					

				}
				 catch (NameNotFoundException e) {
					//TODO Auto-generated catch block
					e.printStackTrace();
				}

				catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*Intent intent = new Intent(Admin.this,
						ConstantsFile.adminCreatePatientDetailsClass);
				//startActivity(intent);
				startActivityForResult(intent, 0);

				finish();*/
				/*
				 * Intent intent = new Intent(v.getContext(),
				 * ConstantsFile.adminCreatePatientDetailsClass);
				 * startActivityForResult(intent, 0);
				 */

			}
		});

		// When the Create Doctor button is clicked, load up the screen where
		// the doctor details can be entered for creation of the doctor record
		btnAdminCreateDoc.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			public void onClick(View v) {
				// TODO Auto-generated method stub

				System.out.println("createdoc initiated..");

				/*
				 * ConstantsFile.foreignContext = createPackageContext(
				 * ConstantsFile.packageName, Context.CONTEXT_IGNORE_SECURITY |
				 * Context.CONTEXT_INCLUDE_CODE);
				 * ConstantsFile.createDoctorClass = (Class<CreateDoctor>)
				 * ConstantsFile.foreignContext .getClassLoader().loadClass(
				 * ConstantsFile.createDoctor); Intent intent = new
				 * Intent(Admin.this, ConstantsFile.createDoctorClass);
				 * startActivity(intent);
				 */
				try {
					ConstantsFile.createDoctorClass = (Class<CreateDoctor>) ConstantsFile.foreignContext
							.getClassLoader().loadClass(
									ConstantsFile.createDoctor);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent = new Intent(v.getContext(),
						ConstantsFile.createDoctorClass);
				startActivityForResult(intent, 0);

				finish();

			}

		});
		btnViewDoctor.setOnClickListener(new OnClickListener() {

			
			public void onClick(View v) {
				fetchDoctorsList();
				displayDoctorsList();

			}
		});
		btnViewPatient.setOnClickListener(new OnClickListener() {

			
			public void onClick(View v) {
				fetchPatientsList();
				displayPatientsList();

			}
		});
		// When the Logout button is clicked, log out by loading up the first
		// login screen
		logout_view.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			public void onClick(View v) {

				Log.i("Admin", "Logout initiated..");

				try {
					ConstantsFile.foreignContext = createPackageContext(
							ConstantsFile.packageName,
							Context.CONTEXT_IGNORE_SECURITY
									| Context.CONTEXT_INCLUDE_CODE);

					ConstantsFile.adminLoginClass = (Class<Admin_login>) ConstantsFile.foreignContext
							.getClassLoader().loadClass(ConstantsFile.admin_login);

					Intent intent = new Intent(Admin.this,
							ConstantsFile.adminLoginClass);
					startActivity(intent);

				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				/*
				 * Intent intent = new Intent(v.getContext(),
				 * ConstantsFile.loginClass); startActivityForResult(intent, 0);
				 */
				// finish();

			}

		});

		// this is the action listener

	}

	Admin_login login = null;

	public void searchPatient(String PID) {

		String cmd = "searchpatient";
		Log.i("Connection", "Trying to retrieve the list of patients");
		URLConnection urlConnection = null;
		String output = "";
		try {

			// System.out.println("My ID IS :::: " + login.ID_Cache);
			urlConnection = Connection.connect();

			// Encode the string combination into a url to send to the php page
			String data = URLEncoder.encode("PID", "UTF-8") + "="
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
			StringBuffer sb = new StringBuffer();
			String buff = null;
			// System.out.println("rd value ::: " + rd.readLine());
			while ((buffer = rd.readLine()) != null) {
				// Log.i("Connection", "From buffer " + buffer);
				buff = buff + ":" + buffer;
				System.out.println("Buffer :: " + buffer);
			}
			System.out.println("Buff :: " + buff);
			if(!(buff.length()<6))
			{	patMinimalDetails(buff);
				displayPatientsList();
			}
			
			else 
			{ 
				Toast.makeText(Admin.this,
						"No patient record found",
						Toast.LENGTH_SHORT).show();
				Log.i("Search",
						"Enter a valid username");
			}
			wr.close();
			rd.close();

		} catch (Exception ex) {
			Log.i("Connection", "Exception: " + ex);
			ex.printStackTrace();
			// System.out.println(ex.toString());
		}

	}

	public void viewPatientsList() {

		String cmd = "viewMyPatientsList";
		Log.i("Admin: viewPatientsList", "Trying to view the list of patients");
		URLConnection urlConnection = null;
		// String output = "";
		try {
			login = new Admin_login();

			// System.out.println("My ID IS :::: " + login.ID_Cache);
			urlConnection = Connection.connect();

			// Encode the string combination into a url to send to the php
			// script
			String data = URLEncoder.encode("myID", "UTF-8") + "="
					+ URLEncoder.encode(Admin_login.ID_Cache, "UTF-8") + "&"
					+ URLEncoder.encode("cmd", "UTF-8") + "="
					+ URLEncoder.encode(cmd, "UTF-8");

			Log.i("Admin: viewPatientsList", "Sending message: " + data);
			OutputStreamWriter wr = new OutputStreamWriter(
					urlConnection.getOutputStream());
			wr.write(data);
			wr.flush();
			Log.i("Admin: viewPatientsList", "Sending message to web");
			// String buffer;

			wr.close();

		} catch (Exception ex) {
			Log.i("Admin: viewPatientsList", "Exception: " + ex);
			// System.out.println(ex.toString());
		}

	}

	public void fetchPatientsList() {

		String cmd = "fetchPatientsList";
		Log.i("Admin: fetchPatientsList",
				"Trying to retrieve the list of patients");
		URLConnection urlConnection = null;
		// String output = "";
		try {

			// System.out.println("My ID IS :::: " + login.ID_Cache);
			urlConnection = Connection.connect();

			// Encode the string combination into a url to send to the php page
			String data = URLEncoder.encode("myID", "UTF-8") + "="
					+ URLEncoder.encode(Admin_login.ID_Cache, "UTF-8") + "&"
					+ URLEncoder.encode("cmd", "UTF-8") + "="
					+ URLEncoder.encode(cmd, "UTF-8");

			Log.i("Admin: fetchPatientsList", "Sending message: " + data);
			OutputStreamWriter wr = new OutputStreamWriter(
					urlConnection.getOutputStream());
			wr.write(data);
			wr.flush();
			Log.i("Admin: fetchPatientsList", "Sending message to web");
			String buffer;

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String buff = null;
			// System.out.println("rd value ::: " + rd.readLine());
			while ((buffer = rd.readLine()) != null) {
				// Log.i("Connection", "From buffer " + buffer);
				buff = buff + ":" + buffer;
				// System.out.println("Buffer :: " + buffer);
			}
			
			 
			patMinimalDetails(buff);
			wr.close();
			rd.close();

		} catch (Exception ex) {
			Log.i("Admin: fetchPatientsList", "Exception: " + ex);
			ex.printStackTrace();
			// System.out.println(ex.toString());
		}

	}

	int DBnumOfRowsObt;
	String[] PatID = new String[100];
	String[] PatName = new String[100];
	String[] PatAge = new String[100];
	String[] PatSex = new String[100];
	StringBuffer showPatientsList = new StringBuffer();

	public void patMinimalDetails(String bufferVal) {
		System.out.println(" bufferVal is $$$$$$$$$$ " + bufferVal);
		String bufferValCopy = bufferVal.substring(bufferVal.indexOf("PAT"));
		System.out.println("bufferValCopy ###### " + bufferValCopy);

		String[] splitBuffer = bufferValCopy.split(":");

		System.out.println("splitBuffer length:" + splitBuffer.length);
		DBnumOfRowsObt = (splitBuffer.length) / 4;

		StringTokenizer st = new StringTokenizer(bufferValCopy, ":");
		for (int j = 0; j < DBnumOfRowsObt; j++)

		{
			System.out.println("hereugoooo"+j);
			while (st.hasMoreTokens())

			{
				PatID[j] = st.nextToken();
				PatName[j] = st.nextToken();
				PatAge[j] = st.nextToken();
				PatSex[j] = st.nextToken();
				showPatientsList.append(PatID[j] + "," + PatName[j] + ","
						+ PatSex[j] + "," + PatAge[j] + ".");

			}
		}

	}

	// Display patients List in Screen

	public void displayPatientsList() {

		final Context context = Admin.this;

		ListView ls2 = new ListView(context);

		// clear previous results in the LV
		ls2.setAdapter(null);
		// populate
		ArrayList m_Devices = new ArrayList();
		Device device;

		int i = 0;
		StringTokenizer stTemp = new StringTokenizer(
				showPatientsList.toString(), ".");

		while (stTemp.hasMoreTokens()) {
			StringTokenizer st = new StringTokenizer(stTemp.nextToken(), ",");
			while (st.hasMoreTokens()) {
				PatID[i] = st.nextToken();
				PatName[i] = st.nextToken();
				PatSex[i] = st.nextToken();
				PatAge[i] = st.nextToken();

				device = new Device(PatID[i], PatName[i] + ", " + PatSex[i]
						+ ", " + PatAge[i], 0, 0, 100);
				m_Devices.add(device);
			}
			i++;
		}

		final CustomAdapter lvAdapter = new CustomAdapter(context, m_Devices);
		ls2.setAdapter(lvAdapter);
		ls2.setOnItemClickListener(new OnItemClickListener() {

			
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// Get the item that was clicked
				/*
				 * Object o = arg0.getAdapter().getItem(arg2); String keyword =
				 * o.toString();
				 * 
				 * System.out.println("keyword:" + keyword);
				 */


				/*
				 * ProgressDialog progressDialog = ProgressDialog.show(
				 * Admin.this, "", " Authenticating user. Please wait ... ",
				 * true);
				 */


					System.out.println("Inside authorized>>>>>>>>>>");
					try {

						String bufferData = AdminUpdatePatientDetails
								.fetchPatientDetails(PatID[arg2]);

						ConstantsFile.foreignContext = createPackageContext(
								ConstantsFile.packageName,
								Context.CONTEXT_IGNORE_SECURITY
										| Context.CONTEXT_INCLUDE_CODE);
						ConstantsFile.adminUpdatePatientDetailsClass = (Class<AdminUpdatePatientDetails>) ConstantsFile.foreignContext
								.getClassLoader()
								.loadClass(
										ConstantsFile.adminUpdatePatientDetails);

						Intent intent = new Intent(Admin.this,
								ConstantsFile.adminUpdatePatientDetailsClass);

						Bundle b = new Bundle();

						b.putString("bufferDataString", bufferData);

						intent.putExtras(b);

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
		
		
		

		setContentView(ls2);
	}

	public void displayPatientDetails() {

		final Context context = Admin.this;

	}

	// function for displaying doctors list
	public void fetchDoctorsList() {

		String cmd = "fetchDoctorsList";
		Log.i("Admin: fetchDoctorsList",
				"Trying to retrieve the list of doctors");
		URLConnection urlConnection = null;
		// String output = "";
		try {

			// System.out.println("My ID IS :::: " + login.ID_Cache);
			urlConnection = Connection.connect();

			// Encode the string combination into a url to send to the php page
			String data = URLEncoder.encode("myID", "UTF-8") + "="
					+ URLEncoder.encode(Admin_login.ID_Cache, "UTF-8") + "&"
					+ URLEncoder.encode("cmd", "UTF-8") + "="
					+ URLEncoder.encode(cmd, "UTF-8");

			Log.i("Admin: fetchDoctorsList", "Sending message: " + data);
			OutputStreamWriter wr = new OutputStreamWriter(
					urlConnection.getOutputStream());
			wr.write(data);
			wr.flush();
			Log.i("Admin: fetchDoctorsList", "Sending message to web");
			String buffer;

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String buff = null;
			// System.out.println("rd value ::: " + rd.readLine());
			while ((buffer = rd.readLine()) != null) {
				// Log.i("Connection", "From buffer " + buffer);
				buff = buff + ":" + buffer;
				// System.out.println("Buffer :: " + buffer);
			}
			// System.out.println("Buff :: " + buff);
			docMinimalDetails(buff);
			wr.close();
			rd.close();

		} catch (Exception ex) {
			Log.i("Admin: fetchDoctorsList", "Exception: " + ex);
			ex.printStackTrace();
			// System.out.println(ex.toString());
		}

	}

	public  void searchDoctor(String DocID) {

		String cmd = "searchdoctor";
		Log.i("Connection", "Trying to retrieve the list of doctors.");
		URLConnection urlConnection = null;
		String output = "";
		try {

			// System.out.println("My ID IS :::: " + login.ID_Cache);
			urlConnection = Connection.connect();

			// Encode the string combination into a url to send to the php page
			String data = URLEncoder.encode("DID", "UTF-8") + "="
					+ URLEncoder.encode(DocID, "UTF-8") + "&"
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
			StringBuffer sb = new StringBuffer();
			String buff = null;
			// System.out.println("rd value ::: " + rd.readLine());
			while ((buffer = rd.readLine()) != null) {
				// Log.i("Connection", "From buffer " + buffer);
				buff = buff + ":" + buffer;
				System.out.println("Buffer :: " + buffer);
			}
			System.out.println("Buff :: " + buff);
			
			if(!(buff.length()<6)){  
				result="Success";
				docMinimalDetails(buff);
				}
			else{
				Toast.makeText(Admin.this,
						"No Doctor record found",
						Toast.LENGTH_SHORT).show();
				Log.i("Search Doctor",
						"No Doctor record found");
				result="Fail";
			}
			wr.close();
			rd.close();

		} catch (Exception ex) {
			Log.i("Connection", "Exception: " + ex);
			ex.printStackTrace();
			// System.out.println(ex.toString());
		}

	}

	int DBnumOfRowsObtd;
	String[] DID = new String[100];
	String[] DoctorName = new String[100];
	String[] DoctorDetails = new String[100];

	StringBuffer showDoctorsList = new StringBuffer();

	public void docMinimalDetails(String bufferVal) {
		System.out.println(" bufferVal is $$$$$$$$$$ " + bufferVal);
		String bufferValCopy = bufferVal.substring(bufferVal.indexOf("DOC"));
		System.out.println("bufferValCopy ###### " + bufferValCopy);

		String[] splitBuffer = bufferValCopy.split(":");

		System.out.println("splitBuffer length:" + splitBuffer.length);
		DBnumOfRowsObtd = (splitBuffer.length) / 3;

		StringTokenizer st = new StringTokenizer(bufferValCopy, ":");
		for (int j = 0; j < DBnumOfRowsObtd; j++)

		{
			
			
			while (st.hasMoreTokens())

			{
				DID[j] = st.nextToken();
				DoctorName[j] = st.nextToken();
				DoctorDetails[j] = st.nextToken();

				showDoctorsList.append(DID[j] + "," + DoctorName[j] + ","
						+ DoctorDetails[j] + ".");

			}
		}

	}

	// Display patients List in Screen

	public void displayDoctorsList() {

		final Context context = Admin.this;

		ListView ls2 = new ListView(context);

		// clear previous results in the LV
		ls2.setAdapter(null);
		// populate
		ArrayList m_Devices = new ArrayList();
		Device device;

		int i = 0;
		StringTokenizer stTemp = new StringTokenizer(
				showDoctorsList.toString(), ".");

		while (stTemp.hasMoreTokens()) {
			StringTokenizer st = new StringTokenizer(stTemp.nextToken(), ",");
			while (st.hasMoreTokens()) {
				DID[i] = st.nextToken();
				DoctorName[i] = st.nextToken();
				DoctorDetails[i] = st.nextToken();

				device = new Device(DID[i], DoctorName[i] + ", "
						+ DoctorDetails[i], 0, 0, 100);
				m_Devices.add(device);
			}
			i++;
		}

		final CustomAdapter lvAdapter = new CustomAdapter(context, m_Devices);
		ls2.setAdapter(lvAdapter);
		ls2.setOnItemClickListener(new OnItemClickListener() {

			
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// Get the item that was clicked
				/*
				 * Object o = arg0.getAdapter().getItem(arg2); String keyword =
				 * o.toString();
				 * 
				 * System.out.println("keyword:" + keyword);
				 */

				try {

					String bufferData = UpdateDoctorDetails
							.fetchDoctorDetails(DID[arg2]);

					ConstantsFile.foreignContext = createPackageContext(
							ConstantsFile.packageName,
							Context.CONTEXT_IGNORE_SECURITY
									| Context.CONTEXT_INCLUDE_CODE);
					ConstantsFile.updateDoctorDetailsClass = (Class<UpdateDoctorDetails>) ConstantsFile.foreignContext
							.getClassLoader().loadClass(
									ConstantsFile.updateDoctorDetails);

					Intent intent = new Intent(Admin.this,
							ConstantsFile.updateDoctorDetailsClass);

					Bundle b = new Bundle();

					b.putString("bufferDataString", bufferData);

					intent.putExtras(b);
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

		setContentView(ls2);

	}

	public void displayDoctorDetails() {

		final Context context = Admin.this;

	}

}