package com.droid.activities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.droid.utilities.ConstantsFile;

public class ApolloMainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apollo_main);
        
        Button doctor_login = (Button) findViewById(R.id.doctor_button);
		Button patient_login = (Button) findViewById(R.id.patient_button);
		Button admin_login = (Button) findViewById(R.id.admin_button);
		doctor_login.setOnClickListener(new OnClickListener() {

			public void onClick(View viewParam) {
				Intent intent = new Intent(ApolloMainActivity.this, Doctor_Login.class);
				startActivity(intent);
			
			}
		});
		
		patient_login.setOnClickListener(new OnClickListener() {

			public void onClick(View viewParam) {
				Intent intent = new Intent(ApolloMainActivity.this, Patient_Login.class);
				startActivity(intent);
			
			}
		});
		
		admin_login.setOnClickListener(new OnClickListener() {

			public void onClick(View viewParam) {
				Intent intent = new Intent(ApolloMainActivity.this, Admin_login.class);
				startActivity(intent);
			
			}
		});
    }


}
