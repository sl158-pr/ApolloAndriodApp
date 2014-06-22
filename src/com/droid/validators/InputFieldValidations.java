package com.droid.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputFieldValidations {
	 public static boolean checkIfNumber(String in) {
	        
	        try {

	        	 Long.parseLong(in);
	        
	        } catch (NumberFormatException ex) {
	            return false;
	        }
	        
	        return true;
	    }
	 public static boolean isContainsSpecialChar(String toCheck)
	 {
	         boolean isContainsSC = false;
	         if (toCheck != null && !toCheck.equals(""))
	         {
	                 Matcher m = Pattern.compile("[\\W]").matcher(toCheck);
	                 while (m.find())
	                 {
	                         isContainsSC = true;
	                         
	                 }
	         }
	         return isContainsSC;
	 }

	 public static boolean IsEmpty(String aTextField) {
	    if ((aTextField.length()==0) ||(aTextField==null)) {
	       return true;
	    }
	    else { return false; }
	 }	

	 public static String validatePatientDetails(String sPatientName,String sAge,String sPrimaryDoc,String sSecondaryDoc,String sEmail,String sGender,String sBloodType,String sBP,String sAllergies,String sDiagnosis) {
		 if (IsEmpty(sPatientName)|| IsEmpty(sAge)||
					IsEmpty(sPrimaryDoc)|| IsEmpty(sSecondaryDoc)||
					IsEmpty(sEmail)|| IsEmpty(sGender)||
					IsEmpty(sBloodType)|| IsEmpty(sBP)) {
				return "All the fields must be entered";
			}
			else if(!checkIfNumber(sAge)||sAge.length()>3){
				return"Value entered for Age must be a number and length > 0 and less than 3";
			}
			else if(sPatientName.length()>50){
				return"Value entered for name must be valid and length > 0 and < 50";
			}
			else if(sGender.length()>1){
				return"Value entered for Sex must be F or M";
			}
			else if(sBloodType.length()>3){
				return"Value entered for blood type must be of length less than 3.eg:o+";
			}
			else if(sBP.length()>8){
				return"Value entered for blood pressure must be of length less than 8.eg:80/120";
			}
			else if(sAllergies.length()>100){
				return"Value entered for blood pressure must be of length less than 100";
			}
			else if(sDiagnosis.length()>100){
				return"Value entered for blood pressure must be of length less than 100";
			}
			else if (!(EmailAddressValidator.isValidEmailAddress(sEmail))){
				return"enter proper email address";
			}
			else
				return "valid";
	 }	 
	 public static String validateDoctorDetails(String sDocName,String sAddress,String sOfficeHrs,String sEmail,String sDetails,String sDoctorType) {
		 if (InputFieldValidations.IsEmpty(sDocName)|| InputFieldValidations.IsEmpty(sAddress)||
					InputFieldValidations.IsEmpty(sOfficeHrs)|| InputFieldValidations.IsEmpty(sDetails)||
					InputFieldValidations.IsEmpty(sEmail)|| InputFieldValidations.IsEmpty(sDoctorType)) {
				return"All the fields must be entered";
			}
			else if (!(EmailAddressValidator.isValidEmailAddress(sEmail))){
				return"enter valid email address";
			}
			else if(sDocName.length()>50){
				return"Value entered for doctorname must be of length less than 50";
			}
			else if(sAddress.length()>30){
				return"Value entered for address must be of length less than 30";
			}
			else if(sOfficeHrs.length()>15){
				return"Value entered for office hours must be of length less than 15";
			}

			else if(sDetails.length()>75){
				return"Value entered for details must be of length less than 75";
			}

			else if (!(EmailAddressValidator.isValidEmailAddress(sEmail))){
				return"enter proper email address";
			}
			else
				return "valid";
	 }	 


}
