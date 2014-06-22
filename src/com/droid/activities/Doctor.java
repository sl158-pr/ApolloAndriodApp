package com.droid.activities;

// Doctor's profile

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.droid.utilities.Device;
import com.droid.utilities.CustomAdapter;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Doctor extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// load up the layout
		setContentView(R.layout.doctorprofile);

		// get the button resource in the xml file and assign it to a local
		// variable of type Button
		TextView admintxt= (TextView) findViewById(R.id.admin_txt);
		admintxt.setVisibility(4);
		TableRow row1 = (TableRow)findViewById(R.id.row3);
		row1.setVisibility(4);
		TableRow row2 = (TableRow)findViewById(R.id.row4);
		row2.setVisibility(4);
		TableRow row3 = (TableRow)findViewById(R.id.row5);
		row3.setVisibility(4);
		TableRow row4 = (TableRow)findViewById(R.id.row6);
		row4.setVisibility(4);
		Button btnCreateDoctor = (Button) findViewById(R.id.create_doctor);
		btnCreateDoctor.setVisibility(4);
		Button btnViewDoctor = (Button) findViewById(R.id.viewdoc_button);
		btnViewDoctor.setVisibility(4);
		
		
		Button btnBack = (Button) findViewById(R.id.back_doctor);
		btnBack.setVisibility(4);
		Button btnViewPatient = (Button) findViewById(R.id.view_button);

		Button btnCreate = (Button) findViewById(R.id.create_patient);
		Button btnSearch = (Button) findViewById(R.id.searchby);
		Button btnViewSchedule = (Button) findViewById(R.id.ViewMySchedule);
		
		Button logout_view = (Button) findViewById(R.id.logout);

		final TextView lblResult = (TextView) findViewById(R.id.txt_doctorprofile);

		// When the Search button is clicked, load up the profile of the patient
		// whose ID has been searched for
				btnSearch.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			EditText idtext = (EditText) findViewById(R.id.idname);
			String name = idtext.getText().toString();

			if (InputFieldValidations.IsEmpty(name)) {
				Toast.makeText( Doctor.this,
						"Enter a valid Patient ID",
						Toast.LENGTH_SHORT).show();
				Log.i("Search",
						"Enter a valid Patient ID");
			} else {
				/**
				 * code to search for the patient details and then go to
				 * patient details page
				 **/


				try {

					String bufferData = DocViewUpdatePatientDetails
							.fetchPatientDetails(name);
					String[] splitBuffer = bufferData.split(":");
					if(!(splitBuffer.length<3))
					{ ConstantsFile.foreignContext = createPackageContext(
							ConstantsFile.packageName,
							Context.CONTEXT_IGNORE_SECURITY
									| Context.CONTEXT_INCLUDE_CODE);
					ConstantsFile.docViewUpdatePatientDetailsClass = (Class<DocViewUpdatePatientDetails>) ConstantsFile.foreignContext
							.getClassLoader()
							.loadClass(
									ConstantsFile.docViewUpdatePatientDetails);

					Intent intent = new Intent(Doctor.this,
							ConstantsFile.docViewUpdatePatientDetailsClass);

					Bundle b = new Bundle();

					b.putString("bufferDataString", bufferData);

					intent.putExtras(b);

					startActivity(intent);
					}
					else 
					{
						Toast.makeText(Doctor.this,
								"No patient record found",
								Toast.LENGTH_SHORT).show();
						Log.i("Search",
								"Enter a valid username");
					}

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	});

				
				btnViewSchedule.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						try {
							fetchMySchedule();
							System.out.println("Successful in fetching my Appointments");
							displayMYSchedule();
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println("Error in dispplaying Available Schedule");
						}
					}	
				});

		// When the back button is clicked, load up the Login screen

		btnBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				
									try {
					ConstantsFile.foreignContext = createPackageContext(
							ConstantsFile.packageName,
							Context.CONTEXT_IGNORE_SECURITY
									| Context.CONTEXT_INCLUDE_CODE);
					ConstantsFile.doctorLoginClass = (Class<Doctor_Login>) ConstantsFile.foreignContext
							.getClassLoader().loadClass(ConstantsFile.doctor_login);

					Intent intent = new Intent(Doctor.this,
							ConstantsFile.doctorLoginClass);
					startActivity(intent);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			finish();			}
		});

		// When the View button is clicked, load up the patients' list under the
		// doctor's care

		btnViewPatient.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				try {
					fetchMyPatientsList();
					System.out.println("Successful in fetching patients' list..");
					displayMyPatientsList();
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Error in dispplaying patients records");
				}

			}
		});

		// When the Create patient button is clicked, load up the screen where
		// the patient details can be entered to create the patient profile
		btnCreate.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			public void onClick(View v) {

				try {
					Log.i("D_CreatePatientProfile", "Patient screen coming up");
					ConstantsFile.foreignContext = createPackageContext(
							ConstantsFile.packageName,
							Context.CONTEXT_IGNORE_SECURITY
									| Context.CONTEXT_INCLUDE_CODE);
					ConstantsFile.docViewPatientDetailsClass = (Class<DocViewPatientDetails>) ConstantsFile.foreignContext
							.getClassLoader().loadClass(
									ConstantsFile.docViewPatientDetails);
					Intent intent = new Intent(Doctor.this,
							ConstantsFile.docViewPatientDetailsClass);
					startActivity(intent);

					// createPatientLogin();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
					ConstantsFile.doctorLoginClass = (Class<Doctor_Login>) ConstantsFile.foreignContext
							.getClassLoader().loadClass(ConstantsFile.doctor_login);
					Intent intent = new Intent(Doctor.this,
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

		/*
		 * btnSetting.setOnClickListener(new OnClickListener() {
		 * 
		 * public void onClick(View v) {
		 * 
		 * Context foreignContext; Class<DoctorSettings> settings; try {
		 * foreignContext = createPackageContext("com.DaDroid",
		 * Context.CONTEXT_IGNORE_SECURITY | Context.CONTEXT_INCLUDE_CODE);
		 * settings = (Class<DoctorSettings>)
		 * foreignContext.getClassLoader().loadClass
		 * ("com.DaDroid.DoctorSettings"); Intent intent = new
		 * Intent(Doctor.this, settings); startActivity(intent); } catch
		 * (ClassNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }catch (NameNotFoundException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * 
		 * } });
		 */
		// this is the action listener

	}

	// Fetch the patients under the doctor's care

	public void fetchMyPatientsList() {

		String cmd = "fetchMyPatientsList";
		Log.i("Connection", "Trying to retrieve the list of patients");
		URLConnection urlConnection = null;
		String output = "";
		try {

			// System.out.println("My ID IS :::: " + login.ID_Cache);
			urlConnection = Connection.connect();

			// Encode the string combination into a url to send to the php page
			String data = URLEncoder.encode("myID", "UTF-8") + "="
					+ URLEncoder.encode(Doctor_Login.ID_Cache, "UTF-8") + "&"
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
				// System.out.println("Buffer :: " + buffer);
			}
			// System.out.println("Buff :: " + buff);
			patMinimalDetails(buff);
			wr.close();
			rd.close();

		} catch (Exception ex) {
			Log.i("Connection", "Exception: " + ex);
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
		DBnumOfRowsObt = (int) (splitBuffer.length) / 4;

		StringTokenizer st = new StringTokenizer(bufferValCopy, ":");
		for (int j = 0; j < DBnumOfRowsObt; j++)

		{
			// System.out.println("hereugoooo"+j);
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
		// for(int i=0;i<splitBuffer.length;i++)
		// {
		// if (splitBuffer.length > 4) for (int j=1; j == DBnumOfRowsObt;j++)
		//
		// System.out.println("splitBuffer:" + splitBuffer[i]);
		// }
		// System.out.println(PatID[0] +" " +
		// PatName[0]+" " +
		// PatAge[0]+" " +
		// PatSex[0]);
		// System.out.println(PatID[1] +" " +
		// PatName[1]+" " +
		// PatAge[1]+" " +
		// PatSex[1]);
		// System.out.println(PatID[2] +" " +
		// PatName[2]+" " +
		// PatAge[2]+" " +
		// PatSex[2]);
	}

	// Display patients List in Screen

	public void displayMyPatientsList() {

		final Context context = Doctor.this;

		/*
		 * ListView ls1 = new ListView(context); ArrayAdapter<String> adapter =
		 * new ArrayAdapter<String>( context,
		 * android.R.layout.simple_list_item_1, new
		 * String[]{"item1","item2","item3","item4","item5","item6","item7"});
		 * ls1.setAdapter(adapter);
		 */

		ListView ls2 = new ListView(context);
		/*
		 * m_lv1.setClickable(true); m_lv1.setFastScrollEnabled(true);
		 * m_lv1.setItemsCanFocus(true);
		 */

		// clear previous results in the LV
		ls2.setAdapter(null);
		// populate
		ArrayList m_Devices = new ArrayList();
		Device device;

		// for (int i=0;i<DBnumOfRowsObt;i++) {
		// // device = new Device("DADroid "+i,"13:B4:5C:0D:AE:67", i%2,0, 100 +
		// i);
		// // System.out.println("PatID[i] ,PatName[i] is ++++++++ "
		// +showPatientsList.toString());
		//
		// device = new Device(PatID[i] ,PatName[i], 0,0, 100);
		//
		// m_Devices.add(device);
		// }
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
				//Toast.makeText(getBaseContext(), "You clicked on "+ PatID[arg2],
				//Toast.LENGTH_LONG).show();
				// Toast.makeText(getBaseContext(),
				// "You clicked on "+" Patient's name"+arg2,
				// Toast.LENGTH_LONG).show();
				
				/*
				System.out.println("arg0:"+arg0);
				System.out.println("arg1:"+arg1);
				System.out.println("arg2:"+arg2);
				System.out.println("arg3:"+arg3);*/
				
				/*arg0.getAdapter();
				String data = lvAdapter.getItem(arg2);*/
				
				// Get the item that was clicked
				/*
				 * CustomAdapter adapter = (CustomAdapter) arg0.getAdapter();
				 * Object row = adapter.getItem(arg2);
				 * 
				 * String row1 = row.toString();
				 * System.out.println("row1:"+row1);
				 */
				
				try {

					String bufferData = DocViewUpdatePatientDetails
					.fetchPatientDetails(PatID[arg2]);

			ConstantsFile.foreignContext = createPackageContext(
					ConstantsFile.packageName,
					Context.CONTEXT_IGNORE_SECURITY
							| Context.CONTEXT_INCLUDE_CODE);
			ConstantsFile.docViewUpdatePatientDetailsClass = (Class<DocViewUpdatePatientDetails>) ConstantsFile.foreignContext
					.getClassLoader()
					.loadClass(
							ConstantsFile.docViewUpdatePatientDetails);

			Intent intent = new Intent(Doctor.this,
					ConstantsFile.docViewUpdatePatientDetailsClass);

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

	
	
	public void fetchMySchedule() {

		String cmd = "fetchMySchedule";
		Log.i("Connection", "Trying to retrieve the list of Appointments");
		URLConnection urlConnection = null;
		String output = "";
		try {

			// System.out.println("My ID IS :::: " + login.ID_Cache);
			urlConnection = Connection.connect();

			// Encode the string combination into a url to send to the php page
			String data = URLEncoder.encode("myID", "UTF-8") + "="
					+ URLEncoder.encode(Doctor_Login.ID_Cache, "UTF-8") + "&"
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
				//System.out.println("Buffer :: " + buffer);
			}
			// System.out.println("Buff :: " + buff);
			myAppointmentMinimalDetails(buff);
			wr.close();
			rd.close();

		} catch (Exception ex) {
			Log.i("Connection", "Exception: " + ex);
			ex.printStackTrace();
			// System.out.println(ex.toString());
		}

	}

	int MYDBnumOfRowsObt;
	String[] MySlotID = new String[100];
	String[] MyPatName = new String[100];
	String[] MyDate = new String[100];
	String[] MySlot = new String[100];
	StringBuffer showMYAppointments = new StringBuffer();

	public void myAppointmentMinimalDetails(String bufferVal) {
		System.out.println(" bufferVal is $$$$$$$$$$ " + bufferVal);
		String bufferValCopy = bufferVal.substring(bufferVal.indexOf("SLOT"));
		System.out.println("bufferValCopy ###### " + bufferValCopy);

		String[] splitBuffer = bufferValCopy.split(":");

		System.out.println("splitBuffer length:" + splitBuffer.length);
		MYDBnumOfRowsObt = (int) (splitBuffer.length) / 4;

		StringTokenizer st = new StringTokenizer(bufferValCopy, ":");
		for (int j = 0; j < MYDBnumOfRowsObt; j++)

		{
			// System.out.println("hereugoooo"+j);
			while (st.hasMoreTokens())

			{
				MySlotID[j] = st.nextToken();
				MyPatName[j] = st.nextToken();
				MyDate[j] = st.nextToken();
				MySlot[j] = st.nextToken();
				showMYAppointments.append(MySlotID[j] + ","+ MyPatName[j] + "," + MyDate[j] + ","
						+ MySlot[j] +".");

			}
		}
	}

	// Display patients List in Screen

	public void displayMYSchedule() {

		final Context context = Doctor.this;


		ListView ls2 = new ListView(context);

		// clear previous results in the LV
		ls2.setAdapter(null);
		// populate
		ArrayList m_Devices = new ArrayList();
		Device device;

		int i = 0;
		StringTokenizer stTemp = new StringTokenizer(
				showMYAppointments.toString(), ".");

		while (stTemp.hasMoreTokens()) {
			StringTokenizer st = new StringTokenizer(stTemp.nextToken(), ",");
			while (st.hasMoreTokens()) {
				MySlotID[i] = st.nextToken();
				MyPatName[i] = st.nextToken();
				MyDate[i] = st.nextToken();
				MySlot[i] = st.nextToken();

				device = new Device(MySlotID[i], MyPatName[i] + ", " + MyDate[i]
						+ ", " + MySlot[i], 0, 0, 100);
				m_Devices.add(device);
			}
			i++;
		}

		final CustomAdapter lvAdapter = new CustomAdapter(context, m_Devices);
		ls2.setAdapter(lvAdapter);
		setContentView(ls2);
		
	
	
	}

	// Display patients List in Screen

	public void displayPatientDetails() {

		final Context context = Doctor.this;

	}

}
