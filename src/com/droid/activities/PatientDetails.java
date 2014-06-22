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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PatientDetails extends Activity{
    /** Called when the activity is first created. */
 
    
    
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
    
    
	   @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // load up the layout
        setContentView(R.layout.detailspatientview);
       
        // get the button resource in the xml file and assign it to a local variable of type Button
        Button create=(Button) findViewById(R.id.Create_button);
        create.setVisibility(4);
        Button update=(Button) findViewById(R.id.Update_button);
        update.setVisibility(4);
        Button btnScheduler=(Button) findViewById(R.id.Scheduler_button);
        Button btnViewMyAppointment=(Button) findViewById(R.id.ViewMyAppointment);
        Button btnLogout = (Button) findViewById(R.id.Logout_button);
        Button btnADMBack = (Button)findViewById(R.id.back_admin);
        btnADMBack.setVisibility(4);
        Button Emergency =(Button) findViewById(R.id.emergency);
        
        Button btnBack = (Button)findViewById(R.id.back_button);
        btnBack.setVisibility(4);
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
        
       
		Emergency.setOnClickListener(new OnClickListener() {
			public void onClick(View viewParam) {
				Intent intent = new Intent(PatientDetails.this, Emergency.class);
				startActivity(intent);
			
			}
		});
		
		
		
		btnScheduler.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					fetchAvailableSchedule();
					System.out.println("Successful in fetching Available Schedule");
					displayAvailableSchedule();
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Error in dispplaying Available Schedule");
				}
			}	
		});
		
		btnViewMyAppointment.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					fetchMyAppiontments();
					System.out.println("Successful in fetching my Appointments");
					displayMYAppointments();
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Error in dispplaying Available Schedule");
				}
			}	
		});
     // Log out by loading up the Login screen

		btnLogout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				try {
					System.out.println("Logout initiated..");

					ConstantsFile.foreignContext = createPackageContext(
							ConstantsFile.packageName,
							Context.CONTEXT_IGNORE_SECURITY
									| Context.CONTEXT_INCLUDE_CODE);
					ConstantsFile.patientLoginClass = (Class<Patient_Login>) ConstantsFile.foreignContext
							.getClassLoader().loadClass(ConstantsFile.patient_login);
					Intent intent = new Intent(PatientDetails.this,
							ConstantsFile.patientLoginClass);
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
	   
	// Fetch the Schedule for appointment

		public void fetchAvailableSchedule() {

			String cmd = "fetchAvailableSchedule";
			String availablity_Value = "YES";
			Log.i("Connection", "Trying to retrieve the available appointments");
			URLConnection urlConnection = null;
			String output = "";
			try {

				// System.out.println("My ID IS :::: " + login.ID_Cache);
				urlConnection = Connection.connect();

				// Encode the string combination into a url to send to the php page
				String data = URLEncoder.encode("availablity_Value", "UTF-8") + "="
						+ URLEncoder.encode(availablity_Value, "UTF-8") + "&"
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
				schMinimalDetails(buff);
				wr.close();
				rd.close();

			} catch (Exception ex) {
				Log.i("Connection", "Exception: " + ex);
				ex.printStackTrace();
				// System.out.println(ex.toString());
			}

		}

		int DBnumOfRowsObt;
		String[] SlotID = new String[100];
		String[] DocName = new String[100];
		String[] Date = new String[100];
		String[] Slot = new String[100];
		StringBuffer showAvailableScheduleList = new StringBuffer();

		public void schMinimalDetails(String bufferVal) {
			System.out.println(" bufferVal is $$$$$$$$$$ " + bufferVal);
			String bufferValCopy = bufferVal.substring(bufferVal.indexOf("SLOT"));
			System.out.println("bufferValCopy ###### " + bufferValCopy);

			String[] splitBuffer = bufferValCopy.split(":");

			System.out.println("splitBuffer length:" + splitBuffer.length);
			DBnumOfRowsObt = (int) (splitBuffer.length) / 4;

			StringTokenizer st = new StringTokenizer(bufferValCopy, ":");
			for (int j = 0; j < DBnumOfRowsObt; j++)

			{
				while (st.hasMoreTokens())

				{
					SlotID[j] = st.nextToken();
					DocName[j] = st.nextToken();
					Date[j] = st.nextToken();
					Slot[j] = st.nextToken();
					showAvailableScheduleList.append(SlotID[j] + ","+ DocName[j] + "," + Date[j] + ","
							+ Slot[j] +".");

				}
			}
		}

		// Display patients List in Screen

		public void displayAvailableSchedule() {

			final Context context = PatientDetails.this;


			ListView ls2 = new ListView(context);

			// clear previous results in the LV
			ls2.setAdapter(null);
			// populate
			ArrayList m_Devices = new ArrayList();
			Device device;

			int i = 0;
			StringTokenizer stTemp = new StringTokenizer(
					showAvailableScheduleList.toString(), ".");

			while (stTemp.hasMoreTokens()) {
				StringTokenizer st = new StringTokenizer(stTemp.nextToken(), ",");
				while (st.hasMoreTokens()) {
					SlotID[i] = st.nextToken();
					DocName[i] = st.nextToken();
					Date[i] = st.nextToken();
					Slot[i] = st.nextToken();

					device = new Device(SlotID[i], DocName[i] + ", " + Date[i]
							+ ", " + Slot[i], 0, 0, 100);
					m_Devices.add(device);
				}
				i++;
			}

			final CustomAdapter lvAdapter = new CustomAdapter(context, m_Devices);
			ls2.setAdapter(lvAdapter);
			ls2.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
	
					String result =PatientDetails
						.updateScheduleDetails(SlotID[arg2]);
					if (result == "success") {
						Toast.makeText(PatientDetails.this,
								"Appointment Scheduled Successfully!",
								Toast.LENGTH_SHORT).show();
						Log.i("DisplayAvailableSchedule",
								"Appointment Scheduled successfully... ");
						
						
						

					} else {
						// Toast.makeText(PatientDetails_Doctor.this,"Problem creating patient record.. Please check that all fields are filled in.",
						// Toast.LENGTH_SHORT).show();
						Log.i("AppointmentScheduled",
								"Failed to schedule appointment..");
					}



				}
			});

		
			setContentView(ls2);
			
		
		
		}

		
		public void fetchMyAppiontments() {

			String cmd = "fetchMyAppiontments";
			Log.i("Connection", "Trying to retrieve the list of Appointments");
			URLConnection urlConnection = null;
			String output = "";
			try {

				// System.out.println("My ID IS :::: " + login.ID_Cache);
				urlConnection = Connection.connect();

				// Encode the string combination into a url to send to the php page
				String data = URLEncoder.encode("myID", "UTF-8") + "="
						+ URLEncoder.encode(Patient_Login.ID_Cache, "UTF-8") + "&"
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
		String[] MyDocName = new String[100];
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
					MyDocName[j] = st.nextToken();
					MyDate[j] = st.nextToken();
					MySlot[j] = st.nextToken();
					showMYAppointments.append(MySlotID[j] + ","+ MyDocName[j] + "," + MyDate[j] + ","
							+ MySlot[j] +".");

				}
			}
		}

		// Display patients List in Screen

		public void displayMYAppointments() {

			final Context context = PatientDetails.this;


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
					MyDocName[i] = st.nextToken();
					MyDate[i] = st.nextToken();
					MySlot[i] = st.nextToken();

					device = new Device(MySlotID[i], MyDocName[i] + ", " + MyDate[i]
							+ ", " + MySlot[i], 0, 0, 100);
					m_Devices.add(device);
				}
				i++;
			}

			final CustomAdapter lvAdapter = new CustomAdapter(context, m_Devices);
			ls2.setAdapter(lvAdapter);
			setContentView(ls2);
			
		
		
		}




		public static String updateScheduleDetails( String SLOTID ) {

			String cmd = "updateScheduleDetails";
			String output = "";
			Log.i("Connection", "Trying to update the scheduler");
			URLConnection urlConnection = null;
			
			try {
				
				System.out.println("Slot ID is :::: " + SLOTID);
				urlConnection = Connection.connect();

				// Encode the string combination into a url to send to the php page
				String data = URLEncoder.encode("myID", "UTF-8") + "="
						+ URLEncoder.encode(Patient_Login.ID_Cache, "UTF-8") + "&"
						+ URLEncoder.encode("SlotID", "UTF-8") + "="
						+ URLEncoder.encode(SLOTID, "UTF-8") + "&"
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
				
				while ((buffer = rd.readLine()) != null) {Log.i("Connection", "Retrieving message buffer is " + buffer);
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