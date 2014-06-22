package com.droid.utilities;

import android.content.Context;
import android.widget.Button;
import com.droid.activities.Admin_login;

import com.droid.activities.Admin;
import com.droid.activities.DocViewPatientDetails;
import com.droid.activities.AdminCreatePatientDetails;
import com.droid.activities.AdminUpdatePatientDetails;
import com.droid.activities.DocViewUpdatePatientDetails;
import com.droid.activities.Doctor;
import com.droid.activities.CreateDoctor;
import com.droid.activities.Doctor_Login;
import com.droid.activities.Password;
import com.droid.activities.Patient_Login;
import com.droid.activities.PatientDetails;
import com.droid.activities.ApolloMainActivity;
import com.droid.activities.UpdateDoctorDetails;
import com.droid.activities.Password_Doctor;

public class ConstantsFile {
	
	public static Button btnAdminBack;
	public static Button btnAdminView=null;
	public static Button btnAdminCreate=null;
	public static Button btnAdminCreateDoc=null;
	
	public static Context foreignContext;
	public static String packageName="com.droid.activities";
	public static String apollo_main="com.droid.activities.ApolloMainActivity";
	public static String doctor_login="com.droid.activities.Doctor_Login";
	public static String admin_login="com.droid.activities.Admin_login";
	public static String patient_login="com.droid.activities.Patient_Login";
	public static String createDoctor ="com.droid.activities.CreateDoctor";
	public static String adminCreatePatientDetails ="com.droid.activities.AdminCreatePatientDetails";
	public static String adminUpdatePatientDetails ="com.droid.activities.AdminUpdatePatientDetails";
	public static String updateDoctorDetails ="com.droid.activities.UpdateDoctorDetails";
	public static String doctor ="com.droid.activities.Doctor";
	public static String admin="com.droid.activities.Admin";
	public static String patientDetails ="com.droid.activities.PatientDetails";
	public static String docViewPatientDetails ="com.droid.activities.DocViewPatientDetails";
	public static String docViewUpdatePatientDetails ="com.droid.activities.DocViewUpdatePatientDetails";
	public static String password ="com.droid.activities.Password";
	public static String password_doctor = "com.droid.activities.Password_Doctor";
	public static Class<AdminUpdatePatientDetails> adminUpdatePatientDetailsClass;

	public static Class<Admin> adminClass;
	public static Class<ApolloMainActivity> apolloMainActivityClass;
	public static Class<DocViewPatientDetails> docViewPatientDetailsClass;
    public static Class<DocViewUpdatePatientDetails> docViewUpdatePatientDetailsClass; 
	public static Class<Doctor> doctorClass;
	public static Class<UpdateDoctorDetails> updateDoctorDetailsClass;
	public static Class<CreateDoctor> createDoctorClass;
	public static Class<AdminCreatePatientDetails> adminCreatePatientDetailsClass;
	public static Class<Doctor_Login> doctorLoginClass;
	public static Class<Admin_login> adminLoginClass;
	public static Class<Patient_Login> patientLoginClass;
	public static Class<PatientDetails> PatientDetailsClass;
	public static Class<Password> passwordClass;
	public static Class<Password_Doctor> passwordDoctorClass;
	
	
}
