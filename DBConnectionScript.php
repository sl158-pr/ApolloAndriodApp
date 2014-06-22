<?php

echo "1st line of PHP";

#print "1st line of PHP";


#print "User: " . $_POST[username] . "\n";

#print "Pass: " . $_POST[password] . "\n";


#print "Cmd: " . $_POST[cmd] . "\n";

#print "\n Welcome to Apollo! \n";



$connection = oci_connect('gxv7734','9N4=Ehz9', '(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP) (HOST = omega.uta.edu)(PORT = 1521)) (CONNECT_DATA = (SERVER = DEDICATED) (SERVICE_NAME = CSE1.OMEGA.UTA.EDU)) )');

if($connection) 
	#print "\n Connection Made to oracle!  \n";
else
{
	$err = oci_error();
	echo "Oracle Connect Error " . $err['text'];
}

if (!$connection) {
	echo "\n Couldn't make a connection!";

	exit;
}

if($_POST[cmd] == "login") {
	#print "inside login " . "\n";

	$sql = "SELECT * FROM LOGIN WHERE ID='" . $_POST[username] . "' and PASSWORD='".$_POST[password]."'";

	$sql_statement = oci_parse($connection, $sql);

	$user_results = oci_execute($sql_statement);

	#print $sql . "\n";

	$nrows = oci_fetch_all($sql_statement, $res);

	#print "Number of rows:" . $nrows . "\n";



while (($row = oci_fetch_array($sql_statement, OCI_NUM))) {
	#print "\n Fname on fetch:" . $row[0];
	#print "\n Password on fetch:" . $row[1] . "\n";

}


        if($nrows != 0 && $_POST[password] != "") {
                        print "Pass.";
        } else {
                        print "Fail.";
        }


}

elseif($_POST[cmd] == "fetchMyPatientsList") {

#print "inside viewMyPatientsList in PHP script". "\n";


$sql = "SELECT PID,PNAME,AGE,SEX FROM details_patient WHERE PRIMARY_DOCTOR_ID='" . $_POST[myID] . "' or SECONDARY_DOCTOR_ID='". $_POST[myID] . "'";

#print $sql ."\n";

$sql_statement = oci_parse($connection, $sql);

$user_results = oci_execute($sql_statement);

#$nrows = oci_fetch_all($sql_statement, $res);
#echo "$nrows rows obtained \n" . "\n";

 $num_columns = OCINumCols($sql_statement);

   # format results by row
       while (OCIFetch($sql_statement)) {
	#print "inside while fetch";
               for ($i = 1; $i <= $num_columns; $i++) {

                       $column_value1 = OCIResult($sql_statement,$i);
                       #$column_value2 = OCIResult($sql_statement,$i+1);
                       //echo $i;
				#print "results \n";
                               echo "$column_value1" ."\n";
                               #echo "$column_value2" ."\n";
               }

       }

       #echo "Finished";

       // free resources and close connection
       OCIFreeStatement($sql_statement);
 #      OCILogoff($connection);

}



elseif($_POST[cmd] == "fetchMyPatientsMedList") {

#print "Inside fetchMyPatientsMedList";

#print "inside viewMyPatientsList in PHP script". "\n";


$sql = " SELECT  P.ID,M.MEDICINE,M.DOSE,M.DIAGNOSIS_NO,M.REMARKS FROM MEDICATION M JOIN DIAGNOSIS_LAB_REPORTS P ON M.DIAGNOSIS_NO = P.DIAGNOSIS_NO WHERE P.ID = '" . $_POST[patientID] . "'";

#print $sql ."\n";

$sql_statement = oci_parse($connection, $sql);

$user_results = oci_execute($sql_statement);

#$nrows = oci_fetch_all($sql_statement, $res);
#echo "$nrows rows obtained \n" . "\n";

 $num_columns = OCINumCols($sql_statement);

   # format results by row
       while (OCIFetch($sql_statement)) {
	#print "inside while fetch";
               for ($i = 1; $i <= $num_columns; $i++) {

                       $column_value1 = OCIResult($sql_statement,$i);
                       #$column_value2 = OCIResult($sql_statement,$i+1);
                       //echo $i;
				#print "results \n";
                               echo "$column_value1" ."\n";
                               #echo "$column_value2" ."\n";
               }

       }

       #echo "Finished";

       // free resources and close connection
       OCIFreeStatement($sql_statement);
 #      OCILogoff($connection);

}



elseif($_POST[cmd] == "fetchMyPatientsLabResults") {

#print "Inside fetchMyPatientsLabResult";

#print "inside viewMyPatientsList in PHP script". "\n";


$sql = "SELECT ID,DIAGNOSIS_NO,REPORT_DATE,TEST,RESULT FROM DIAGNOSIS_LAB_REPORTS WHERE ID='" . $_POST[patientID] . "'";

#print $sql ."\n";

$sql_statement = oci_parse($connection, $sql);

$user_results = oci_execute($sql_statement);

#$nrows = oci_fetch_all($sql_statement, $res);
#echo "$nrows rows obtained \n" . "\n";

 $num_columns = OCINumCols($sql_statement);

   # format results by row
       while (OCIFetch($sql_statement)) {
	#print "inside while fetch";

               for ($i = 1; $i <= $num_columns; $i++) {

                       $column_value1 = OCIResult($sql_statement,$i);
                       #$column_value2 = OCIResult($sql_statement,$i+1);
                       //echo $i;
				#print "results \n";

                               echo "$column_value1" ."\n";
                               #echo "$column_value2" ."\n";
               }

       }

       #echo "Finished";

       // free resources and close connection
       OCIFreeStatement($sql_statement);
 #      OCILogoff($connection);

}


elseif($_POST[cmd] == "fetchMyPatMedVHist") {

#print "Inside fetchMyPatMedVHist";

#print "inside fetchMyPatMedVHist in PHP script". "\n";


$sql = "SELECT PATIENT_ID,DATE_VISITED,DETAILS_VITALS from VISIT_HISTORY WHERE PATIENT_ID ='" . $_POST[patientID] . "' AND ROWNUM <= ". $_POST[MedHistNumOfRecordsToFetch] . " ORDER BY DATE_VISITED";

#print $sql ."\n";

$sql_statement = oci_parse($connection, $sql);

$user_results = oci_execute($sql_statement);

#$nrows = oci_fetch_all($sql_statement, $res);
#echo "$nrows rows obtained \n" . "\n";

 $num_columns = OCINumCols($sql_statement);

   # format results by row
       while (OCIFetch($sql_statement)) {
	#print "inside while fetch";

               for ($i = 1; $i <= $num_columns; $i++) {

                       $column_value1 = OCIResult($sql_statement,$i);
                       #$column_value2 = OCIResult($sql_statement,$i+1);
                       //echo $i;
				#print "results \n";

                               echo "$column_value1" ."\n";
                               #echo "$column_value2" ."\n";
               }

       }

       #echo "Finished";

       // free resources and close connection
       OCIFreeStatement($sql_statement);
 #      OCILogoff($connection);

}

elseif($_POST[cmd] == "fetchDoctorsList") {

#print "inside FETCHDOCTORS LIST in PHP script". "\n";


$sql = "SELECT DID,DNAME,DETAILS FROM details_doctor";

#print $sql ."\n";

$sql_statement = oci_parse($connection, $sql);

$user_results = oci_execute($sql_statement);

#$nrows = oci_fetch_all($sql_statement, $res);
#echo "$nrows rows obtained \n" . "\n";

 $num_columns = OCINumCols($sql_statement);

   # format results by row
       while (OCIFetch($sql_statement)) {
	#print "inside while fetch";
               for ($i = 1; $i <= $num_columns; $i++) {

                       $column_value1 = OCIResult($sql_statement,$i);
                       #$column_value2 = OCIResult($sql_statement,$i+1);
                       //echo $i;
				#print "results \n";
                               echo "$column_value1" ."\n";
                               #echo "$column_value2" ."\n";
               }

       }

       #echo "Finished";

       // free resources and close connection
       OCIFreeStatement($sql_statement);
 #      OCILogoff($connection);

}

elseif($_POST[cmd] == "fetchPatientsList") {

#print "inside viewPatientsList in PHP script". "\n";


$sql = "SELECT PID,PNAME,AGE,SEX FROM details_patient";

#print $sql ."\n";

$sql_statement = oci_parse($connection, $sql);

$user_results = oci_execute($sql_statement);

#$nrows = oci_fetch_all($sql_statement, $res);
#echo "$nrows rows obtained \n" . "\n";

 $num_columns = OCINumCols($sql_statement);

   # format results by row
       while (OCIFetch($sql_statement)) {
	#print "inside while fetch";
               for ($i = 1; $i <= $num_columns; $i++) {

                       $column_value1 = OCIResult($sql_statement,$i);
                       #$column_value2 = OCIResult($sql_statement,$i+1);
                       //echo $i;
				#print "results \n";
                               echo "$column_value1" ."\n";
                               #echo "$column_value2" ."\n";
               }

       }

       #echo "Finished";

       // free resources and close connection
       OCIFreeStatement($sql_statement);
 #      OCILogoff($connection);

}


elseif ($_POST[cmd] == "verifyAuthorization")
{

$sql_rights = "SELECT COUNT(*) AS NUMBER_OF_ROWS FROM RIGHTS WHERE PATIENT_ID = '" . $_POST[PatientID] . "' AND DOCTOR_ADMIN_ID = '" . $_POST[DOCTOR_ID]."'";

$stmt= oci_parse($connection, $sql_rights);

oci_define_by_name($stmt, 'NUMBER_OF_ROWS', $number_of_rows);

oci_execute($stmt);

oci_fetch($stmt);

if ($number_of_rows == 1)


	print "Pass.";


else
	print "Fail.";

       // free resources and close connection
       OCIFreeStatement($sql_statement);


}

elseif($_POST[cmd] == "fetchPatientDetails") {

#print "inside fetchPatientDetails in PHP script". "\n";



  $sql = "SELECT PID,PNAME,EMAIL,AGE,BLOOD_TYPE,SEX,B_P,ALLERGIES,DIAGNOSIS,PRIMARY_DOCTOR_ID,SECONDARY_DOCTOR_ID FROM details_patient WHERE PID='" . $_POST[PatientID] . "'";
  
  #print $sql ."\n";
  
  $sql_statement = oci_parse($connection, $sql);
  
  $user_results = oci_execute($sql_statement);
  
  #$nrows = oci_fetch_all($sql_statement, $res);
  #echo "$nrows rows obtained \n" . "\n";
  
   $num_columns = OCINumCols($sql_statement);
  
     # format results by row
         while (OCIFetch($sql_statement)) {
  #	print "inside while fetch";
                 for ($i = 1; $i <= $num_columns; $i++) {
  
                         $column_value1 = OCIResult($sql_statement,$i);
                         #$column_value2 = OCIResult($sql_statement,$i+1);
                         //echo $i;
  				#print "results \n";
                                 echo "$column_value1" ."\n";
                                 #echo "$column_value2" ."\n";
                 }
  
         }
         

  
         #echo "Finished";
  
         // free resources and close connection
         OCIFreeStatement($sql_statement);
   #      OCILogoff($connection);


}

elseif($_POST[cmd] == "fetchDoctorDetails") {

#print "inside fetchDoctorDetails in PHP script". "\n";


$sql = "SELECT DID,DNAME,ADDRESS,OFFICE_HOURS,EMAIL,DETAILS,DOCTOR_TYPE FROM DETAILS_DOCTOR WHERE DID='" . $_POST[DID] . "'";

#print $sql ."\n";

$sql_statement = oci_parse($connection, $sql);

$user_results = oci_execute($sql_statement);

#$nrows = oci_fetch_all($sql_statement, $res);
#echo "$nrows rows obtained \n" . "\n";

 $num_columns = OCINumCols($sql_statement);

   # format results by row
       while (OCIFetch($sql_statement)) {
#	print "inside while fetch";
               for ($i = 1; $i <= $num_columns; $i++) {

                       $column_value1 = OCIResult($sql_statement,$i);
                       #$column_value2 = OCIResult($sql_statement,$i+1);
                       //echo $i;
				#print "results \n";
                               echo "$column_value1" ."\n";
                               #echo "$column_value2" ."\n";
               }

       }

       #echo "Finished";

       // free resources and close connection
       OCIFreeStatement($sql_statement);
 #      OCILogoff($connection);

}
elseif($_POST[cmd] == "fetchMedicationDetails") {

#print "inside fetchMedicationDetails in PHP script". "\n";


$sql = "SELECT  DIAGNOSIS_NO,DIAGNOSIS_DESC,MEDICINE,DOSE,REMARKS FROM MEDICATION WHERE DIAGNOSIS_NO='" . $_POST[DIAGID] . "'";

#print $sql ."\n";

$sql_statement = oci_parse($connection, $sql);

$user_results = oci_execute($sql_statement);

#$nrows = oci_fetch_all($sql_statement, $res);
#echo "$nrows rows obtained \n" . "\n";

 $num_columns = OCINumCols($sql_statement);

   # format results by row
       while (OCIFetch($sql_statement)) {
#	print "inside while fetch";
               for ($i = 1; $i <= $num_columns; $i++) {

                       $column_value1 = OCIResult($sql_statement,$i);
                       #$column_value2 = OCIResult($sql_statement,$i+1);
                       //echo $i;
				#print "results \n";
                               echo "$column_value1" ."\n";
                               #echo "$column_value2" ."\n";
               }

       }

       #echo "Finished";

       // free resources and close connection
       OCIFreeStatement($sql_statement);
 #      OCILogoff($connection);

}
elseif($_POST[cmd] == "fetchReportsDetails") {

#print "inside fetchMedicationDetails in PHP script". "\n";


$sql = "SELECT  DIAGNOSIS_NO,ID,REPORT_DATE,TEST ,Result,REMARKS FROM DIAGNOSIS_LAB_REPORTS WHERE DIAGNOSIS_NO='" . $_POST[DIAGID] . "'";

#print $sql ."\n";

$sql_statement = oci_parse($connection, $sql);

$user_results = oci_execute($sql_statement);

#$nrows = oci_fetch_all($sql_statement, $res);
#echo "$nrows rows obtained \n" . "\n";

 $num_columns = OCINumCols($sql_statement);

   # format results by row
       while (OCIFetch($sql_statement)) {
#	print "inside while fetch";
               for ($i = 1; $i <= $num_columns; $i++) {

                       $column_value1 = OCIResult($sql_statement,$i);
                       #$column_value2 = OCIResult($sql_statement,$i+1);
                       //echo $i;
				#print "results \n";
                               echo "$column_value1" ."\n";
                               #echo "$column_value2" ."\n";
               }

       }

       #echo "Finished";

       // free resources and close connection
       OCIFreeStatement($sql_statement);
 #      OCILogoff($connection);

}

elseif($_POST[cmd] == "searchpatient") {

#print "inside searchpatient in PHP script". "\n";


$sql = "SELECT PID,PNAME,SEX,AGE FROM details_patient WHERE PID='" . $_POST[PID] . "' or PNAME='". $_POST[PID] . "'";

#print $sql ."\n";

$sql_statement = oci_parse($connection, $sql);

$user_results = oci_execute($sql_statement);

#$nrows = oci_fetch_all($sql_statement, $res);
#echo "$nrows rows obtained \n" . "\n";

 $num_columns = OCINumCols($sql_statement);

   # format results by row
       while (OCIFetch($sql_statement)) {
	#print "inside while fetch";
               for ($i = 1; $i <= $num_columns; $i++) {

                       $column_value1 = OCIResult($sql_statement,$i);
                       #$column_value2 = OCIResult($sql_statement,$i+1);
                       //echo $i;
				#print "results \n";
                               echo "$column_value1" ."\n";
                               #echo "$column_value2" ."\n";
               }

       }

       #echo "Finished";

       // free resources and close connection
       OCIFreeStatement($sql_statement);
 #      OCILogoff($connection);

}


elseif($_POST[cmd] == "searchdoctor") {

#print "inside searchdoctor in PHP script". "\n";


$sql = "SELECT DID,DNAME,DOCTOR_TYPE,EMAIL FROM details_doctor WHERE DID='" . $_POST[DID] . "' or DNAME='". $_POST[DID] . "'";

#print $sql ."\n";

$sql_statement = oci_parse($connection, $sql);

$user_results = oci_execute($sql_statement);

#$nrows = oci_fetch_all($sql_statement, $res);
#echo "$nrows rows obtained \n" . "\n";

 $num_columns = OCINumCols($sql_statement);

   # format results by row
       while (OCIFetch($sql_statement)) {
	#print "inside while fetch";
               for ($i = 1; $i <= $num_columns; $i++) {

                       $column_value1 = OCIResult($sql_statement,$i);
                       #$column_value2 = OCIResult($sql_statement,$i+1);
                       //echo $i;
				#print "results \n";
                               echo "$column_value1" ."\n";
                               #echo "$column_value2" ."\n";
               }

       }

       #echo "Finished";

       // free resources and close connection
       OCIFreeStatement($sql_statement);
 #      OCILogoff($connection);

}




elseif($_POST[cmd] == "createPatientRecord")
{
        $sql = "SELECT ID_SEQ_PAT.NEXTVAL from dual";
	  $sql_statement = oci_parse($connection, $sql);
	  $user_results = oci_execute($sql_statement);

	while ($row = oci_fetch_array($sql_statement, OCI_NUM))
	{
		$current_id = $row[0];

	}

	print "\n New patient ID created:" . $current_id;

	print $sql . "\n";


	$sql_loginInsert = "INSERT INTO LOGIN (ID, PASSWORD, HINT_QUESTION1, HINT_ANSWER1, HINT_QUESTION2, HINT_ANSWER2, AUTHENTICATION_LEVEL, QUESTIONS_SET, CREATED_BY, CREATED_ON) VALUES ('PAT'||$current_id, DBMS_RANDOM.STRING('A',5),'Which is the first letter in the alphabet?','A','Which is the last letter in the alphabet?','Z','PATIENT','0','".$_POST[primaryDoc]."',SYSDATE)";

	print $sql_loginInsert . "\n";


	  $sql_statement_login_insert = oci_parse($connection, $sql_loginInsert);
	  $user_results_login_insert = oci_execute($sql_statement_login_insert);


	print "Login User result:" .$user_results_login_insert . "\n";



	$sql_detailsInsert = "INSERT INTO DETAILS_PATIENT(PID,PNAME,EMAIL,AGE,BLOOD_TYPE,SEX,B_P,ALLERGIES,DIAGNOSIS,PRIMARY_DOCTOR_ID, SECONDARY_DOCTOR_ID) VALUES ('PAT'||$current_id,'".$_POST[patientName]."','".$_POST[email]."','".$_POST[age]."','".$_POST[bloodType]."','".$_POST[gender]."','".$_POST[BP]."','".$_POST[allergies]."','".$_POST[diagnosis]."','".$_POST[primaryDoc]."','".$_POST[secondaryDoc]."')";


	print $sql_detailsInsert . "\n";

       $sql_statement_details_insert = oci_parse($connection, $sql_detailsInsert);
       $user_results_details_insert = oci_execute($sql_statement_details_insert);

	print "patient details User result:" .$user_results_details_insert . "\n";



       if($user_results_login_insert && $user_results_details_insert)
       {
         print "New patient record inserted in login and details_patient tables .." . "\n";
         print "Pass.";
       }
           else
       {

	 	print "Fail.";
       }


}


elseif($_POST[cmd] == "createDoctorRecord")
{
        $sql = "SELECT ID_SEQ_DOC.NEXTVAL from dual";
	  $sql_statement = oci_parse($connection, $sql);
	  $user_results = oci_execute($sql_statement);

	while ($row = oci_fetch_array($sql_statement, OCI_NUM))
	{
		$current_id = $row[0];

	}

	print "\n New doctor ID created:" . $current_id;

	print $sql . "\n";


	$sql_loginInsert = "INSERT INTO LOGIN (ID, PASSWORD, HINT_QUESTION1, HINT_ANSWER1, HINT_QUESTION2, HINT_ANSWER2,
				AUTHENTICATION_LEVEL, QUESTIONS_SET, CREATED_BY, CREATED_ON) VALUES ('DOC'||$current_id,
				DBMS_RANDOM.STRING('A',5),'Which is the first letter in the alphabet?','A',
				'Which is the last letter in the alphabet?','Z','DOCTOR','0','ADMIN',SYSDATE)";

	print $sql_loginInsert . "\n";


	  $sql_statement_login_insert = oci_parse($connection, $sql_loginInsert);
	  $user_results_login_insert = oci_execute($sql_statement_login_insert);


	print "Login User result:" .$user_results_login_insert . "\n";



	$sql_detailsInsert = "INSERT INTO DETAILS_DOCTOR(DID,DNAME,ADDRESS,OFFICE_HOURS,EMAIL,DETAILS,DOCTOR_TYPE) VALUES
				('DOC'||$current_id,'".$_POST[DoctorName]."','".$_POST[address]."','".$_POST[OfficeHrs]."',
				'".$_POST[Email]."','".$_POST[Details]."','".$_POST[DoctorType]."')";


	print $sql_detailsInsert . "\n";

       $sql_statement_details_insert = oci_parse($connection, $sql_detailsInsert);
       $user_results_details_insert = oci_execute($sql_statement_details_insert);

	print "Doctor details User result:" .$user_results_details_insert . "\n";



       if($user_results_login_insert && $user_results_details_insert)
       {
         print "New doctor record inserted in login and details_doctor tables .." . "\n";
         print "Pass.";
       }
           else
       {

	 	print "Fail.";
       }


}

elseif($_POST[cmd] == "CreateLabRecord")
{
        $sql = "SELECT ID_SEQ_LABREPORTS.NEXTVAL from dual";
	  $sql_statement = oci_parse($connection, $sql);
	  $user_results = oci_execute($sql_statement);

	while ($row = oci_fetch_array($sql_statement, OCI_NUM))
	{
		$current_id = $row[0];

	}

	print "\n New Lab Record created:" . $current_id;

	print $sql . "\n";




	$sql_labsInsert = "INSERT INTO diagnosis_lab_reports(DIAGNOSIS_NO, ID, REPORT_DATE, TEST, RESULT, REMARKS) VALUES
				('DIAG'||$current_id,'".$_POST[PatientId]."',SYSTIMESTAMP,'".$_POST[Test]."',
				'".$_POST[Result]."','".$_POST[TestRemarks]."')";


	print $sql_labsInsert. "\n";

       $sql_statement_details_insert = oci_parse($connection, $sql_labsInsert);
       $user_results_details_insert = oci_execute($sql_statement_details_insert);

	print "Lab details User result:" .$user_results_details_insert . "\n";



       if($user_results_details_insert)
       {
         print "New Lab record inserted .." .$current_id. "\n";
         print "DIAG".$current_id;
       }
           else
       {

	 	print "Fail.";
       }


}

elseif($_POST[cmd] == "CreateMedicationRecord")
{
       
	

	$sql_medicaitonInsert = "INSERT INTO MEDICATION(MEDICATION_DATE, DIAGNOSIS_NO, DIAGNOSIS_DESC, MEDICINE, DOSE, REMARKS) VALUES
				(SYSTIMESTAMP,'".$_POST[DiagnosisNo]."','".$_POST[DiagnosisDesc]."',
				'".$_POST[Medicine]."','".$_POST[Dosage]."','".$_POST[MedicRemarks]."')";


	print $sql_detailsInsert . "\n";

       $sql_statement_details_insert = oci_parse($connection, $sql_medicaitonInsert);
       $user_results_details_insert = oci_execute($sql_statement_details_insert);

	print "Lab details User result:" .$user_results_details_insert . "\n";



       if($user_results_details_insert)
       {
         print "New medication record inserted .." . "\n";
         print "Pass.";
       }
           else
       {

	 	print "Fail.";
       }


}




elseif($_POST[cmd] == "updatePatientRecord")
{


	$sql_detailsUpdate = "UPDATE DETAILS_PATIENT
SET PNAME = '" . $_POST[patientName] . "', EMAIL = '" . $_POST[email] . "', AGE = '" . $_POST[age] . "', BLOOD_TYPE = '" . $_POST[bloodType] . "', SEX = '" . $_POST[gender] . "' , B_P = '" . $_POST[BP] . "',ALLERGIES = '" . $_POST[allergies]."',DIAGNOSIS = '" . $_POST[diagnosis] . "',PRIMARY_DOCTOR_ID = '" . $_POST[primaryDoc] . "', SECONDARY_DOCTOR_ID = '" . $_POST[secondaryDoc] . "' where PID = '".$_POST[patientID]."'";




	print $sql_detailsUpdate . "\n";

       $sql_statement_details_Update = oci_parse($connection, $sql_detailsUpdate);
       $user_results_details_update = oci_execute($sql_statement_details_Update);

oci_commit($connection);

	print "patient details User result:" .$user_results_details_update . "\n";



       if($user_results_details_update)
       {
         print "New patient record updated in  details_patient tables .." . "\n";
         print "Pass.";
       }
           else
       {

	 	print "Fail.";
       }


}
elseif($_POST[cmd] == "updatePassword")
{


	$sql_detailsUpdate = "UPDATE LOGIN SET PASSWORD = '" . $_POST[password] . "' where ID = '".$_POST[myID]."'";




	print $sql_detailsUpdate . "\n";

       $sql_statement_details_Update = oci_parse($connection, $sql_detailsUpdate);
       $user_results_details_update = oci_execute($sql_statement_details_Update);

oci_commit($connection);

	print "Login User result:" .$user_results_details_update . "\n";



       if($user_results_details_update)
       {
         print "Login record updated in  details_patient tables .." . "\n";
         print "Pass.";
       }
           else
       {

	 	print "Fail.";
       }


}

elseif($_POST[cmd] == "updateDoctorRecord")
{


	$sql_detailsUpdate = "UPDATE DETAILS_DOCTOR
SET DNAME = '" . $_POST[DoctorName] . "', EMAIL = '" . $_POST[Email] . "', ADDRESS = '" . $_POST[address] . "', OFFICE_HOURS = '" . $_POST[OfficeHrs] . "', DETAILS = '" . $_POST[Details] . "' , DOCTOR_TYPE = '" . $_POST[DoctorType] . "' where DID = '".$_POST[DID]."'";




	print $sql_detailsUpdate . "\n";

       $sql_statement_details_Update = oci_parse($connection, $sql_detailsUpdate);
       $user_results_details_update = oci_execute($sql_statement_details_Update);

oci_commit($connection);

	print "doctor details User result:" .$user_results_details_update . "\n";



       if($user_results_details_update)
       {
         print "Doctor record updated in  details_doctor tables .." . "\n";
         print "Pass.";
       }
           else
       {

	 	print "Fail.";
       }


}
elseif($_POST[cmd] == "updateMedicationRecord")
{


	$sql_detailsUpdate = "UPDATE MEDICATION
SET MEDICATION_DATE= SYSTIMESTAMP, DIAGNOSIS_DESC = '" . $_POST[DiagnosisDesc] . "', MEDICINE = '" . $_POST[Medicine] . "', DOSE = '" . $_POST[Dosage] . "', REMARKS = '" . $_POST[MedicRemarks] . "' where DIAGNOSIS_NO = '".$_POST[DiagnosisNo]."'";




	print $sql_detailsUpdate . "\n";

       $sql_statement_details_Update = oci_parse($connection, $sql_detailsUpdate);
       $user_results_details_update = oci_execute($sql_statement_details_Update);

oci_commit($connection);

	print "MEDICATION details User result:" .$user_results_details_update . "\n";



       if($user_results_details_update)
       {
         print "MEDICATION record updated in  medication tables .." . "\n";
         print "Pass.";
       }
           else
       {

	 	print "Fail.";
       }


}
elseif($_POST[cmd] == "updateReportRecord")
{


	$sql_detailsUpdate = "UPDATE DIAGNOSIS_LAB_REPORTS
SET  TEST = '" . $_POST[Test] . "', RESULT = '" . $_POST[Result] . "', REMARKS = '" . $_POST[TestRemarks] . "' where DIAGNOSIS_NO = '".$_POST[DiagnosisNo]."'";




	print $sql_detailsUpdate . "\n";

       $sql_statement_details_Update = oci_parse($connection, $sql_detailsUpdate);
       $user_results_details_update = oci_execute($sql_statement_details_Update);

oci_commit($connection);

	print "Report details User result:" .$user_results_details_update . "\n";



       if($user_results_details_update)
       {
         print "Report record updated in  report tables .." . "\n";
         print "Pass.";
       }
           else
       {

	 	print "Fail.";
       }


}
elseif($_POST[cmd] == "insertRight")
{






	if($_POST[docID1] != "")


	{
		$sql_rightsInsert1 = "INSERT INTO RIGHTS(PATIENT_ID,DOCTOR_ADMIN_ID) VALUES
				('".$_POST[patientID]."','".$_POST[docID1]."')";


		print $sql_rightsInsert1 . "\n";


	       $sql_statement_rights_Insert1 = oci_parse($connection, $sql_rightsInsert1);
	       $user_results_rights_insert1 = oci_execute($sql_statement_rights_Insert1);



		print "doctor rights User result1:" .$user_results_rights_insert1 . "\n";

	 	if($user_results_rights_insert1)
       		{
        		print "Doctor1 rights inserted in  rights table .." . "\n";
         		print "Pass.";
       		}


		else
       		{

		 	print "Fail.";
       		}



	}

	if($_POST[docID2] != "")

	{

		$sql_rightsInsert2 = "INSERT INTO RIGHTS(PATIENT_ID,DOCTOR_ADMIN_ID) VALUES
				('".$_POST[patientID]."','".$_POST[docID2]."')";



		print $sql_rightsInsert2 . "\n";


       		$sql_statement_rights_Insert2 = oci_parse($connection, $sql_rightsInsert2);
	        $user_results_rights_insert2 = oci_execute($sql_statement_rights_Insert2);

		print "doctor rights User result2:" .$user_results_rights_insert2 . "\n";

		if($user_results_rights_insert2)
       		{
		         print "Doctor2 rights inserted in  rights table .." . "\n";
		         print "Pass.";

	        }
		else
       		{

		 	print "Fail.";
       		}


	}

	



	oci_commit($connection);

 


}

elseif($_POST[cmd] == "deleteRight")
{


	if($_POST[docID1] != "")


	{
		$sql_rightsDelete1 = "DELETE FROM RIGHTS WHERE PATIENT_ID = '".$_POST[patientID]."' AND DOCTOR_ADMIN_ID = '".$_POST[docID1]."'";


		print $sql_rightsDelete1 . "\n";


	       $sql_statement_rights_delete1 = oci_parse($connection, $sql_rightsDelete1);
	       $user_results_rights_delete1 = oci_execute($sql_statement_rights_delete1);



		print "doctor rights User result1:" .$user_results_rights_delete1 . "\n";

	 	if($user_results_rights_delete1)
       		{
        		print "Doctor1 rights deleted from rights table .." . "\n";
         		print "Pass.";
       		}


		else
       		{

		 	print "Fail.";
       		}



	}

	if($_POST[docID2] != "")

	{

		$sql_rightsDelete2 = "DELETE FROM RIGHTS WHERE PATIENT_ID = '".$_POST[patientID]."' AND DOCTOR_ADMIN_ID = '".$_POST[docID2]."'";


		print $sql_rightsDelete2 . "\n";


	       $sql_statement_rights_delete2 = oci_parse($connection, $sql_rightsDelete2);
	       $user_results_rights_delete2 = oci_execute($sql_statement_rights_delete2);



		print "doctor rights User result2:" .$user_results_rights_delete2 . "\n";

	 	if($user_results_rights_delete2)
       		{
        		print "Doctor2 rights deleted from rights table .." . "\n";
         		print "Pass.";
       		}
		else
       		{

		 	print "Fail.";
       		}


	}

	



	oci_commit($connection);

 


}

elseif($_POST[cmd] == "rights") {

print "inside searchpatient in PHP script". "\n";
#$connection = oci_connect('axr9605','Mahytq1234', '(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP) (HOST = omega.uta.edu)(PORT = 1521)) (CONNECT_DATA = (SERVER = DEDICATED) (SERVICE_NAME = CSE1.OMEGA.UTA.EDU)) )');

$sql = "SELECT DOCTOR_ADMIN_ID FROM RIGHTS WHERE PATIENT_ID='" . $_POST[myID] . "'";

#print $sql ."\n";

$sql_statement = oci_parse($connection, $sql);

$user_results = oci_execute($sql_statement);

#$nrows = oci_fetch_all($sql_statement, $res);
#echo "$nrows rows obtained \n" . "\n";

 $num_columns = OCINumCols($sql_statement); 

   # format results by row 
       while (OCIFetch($sql_statement)) { 
	#print "inside while fetch";
               for ($i = 1; $i <= $num_columns; $i++) { 

                       $column_value1 = OCIResult($sql_statement,$i); 
                       #$column_value2 = OCIResult($sql_statement,$i+1); 
                       //echo $i; 
				#print "results \n";
                               echo "$column_value1" ."\n"; 
                               #echo "$column_value2" ."\n"; 
               } 
              
       } 

       #echo "Finished"; 

       // free resources and close connection 
       OCIFreeStatement($sql_statement); 
 #      OCILogoff($connection); 

}






else
{
        print "No arguments given. Try again";

}

?>