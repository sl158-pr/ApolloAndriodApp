/*package com.droid.activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Emergency extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_emergency, menu);
        return true;
    }
}

*/

package com.droid.activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import java.io.IOException;
import java.text.AttributedString;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class Emergency extends Activity {
	double pLong;
	double pLat;
	TextView textLat;
	TextView textLong; 
	TextView Adlat;
	TextView Adlong;
	TextView Address1;
	double xlat;
	double xlong;
	 int x=1;
	//  Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
	  
		Geocoder gcd = new Geocoder(this, Locale.getDefault());
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
       // setContentView(R.layout.);
        
     //   textLat=(TextView) findViewById(R.id.Latitude);
      // 	textLong = (TextView) findViewById(R.id.Longitude);
      //  Adlat=(TextView) findViewById(R.id.AdLatitude);
       // Adlong=(TextView) findViewById(R.id.AdLongitude);
        //Address1=(TextView) findViewById(R.id.Adress);
        
       try{
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener ll = new myLocationListener();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0, ll);
       } catch (Exception e) {
    	   e.printStackTrace();
       }
       
    //    textLat.setText(Double.toString());
        
        
        //
       
        /*
   //     try {
        	  List<Address> addresses = geocoder.getFromLocation(37.422006,-122.284095, 1);
        	
  // List<Address> addresses = geocoder.getFromLocation(pLat, pLong, 1);
  
   if(addresses != null) {
    Address returnedAddress = addresses.get(0);
    StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
    for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
     strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
    }
   Address.setText(strReturnedAddress.toString());
   }
   else{
	   Address.setText("No Address returned!");
   }
  } catch (IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
   Address.setText("Canont get Address!");
  }
      */  
        
    }
        class myLocationListener implements LocationListener {
        	
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			if(location!=null)
			{
			
				pLong=location.getLongitude();
			
				pLat =location.getLatitude();
				 //textLat.setText(Double.toString(location.getLatitude()));
				//	textLong.setText(Double.toString(pLong));
					/*
					
					  try {
						  List<Address> addresses1 = geocoder.getFromLocation(pLat,pLong, 1);
			        	Adlat.setText(Double.toString(location.getLatitude()));
			  // List<Address> addresses = geocoder.getFromLocation(pLat, pLong, 1);
			  
			   if(addresses1 != null) {
			    Address returnedAddress = addresses1.get(0);
			    StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
			    for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
			     strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
			    }
			   Address1.setText(strReturnedAddress.toString());
			   }
			   else{
				   Address1.setText("No Address returned!");
			   }
			  } catch (IOException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
			   Address1.setText("Canont get Address!");
			  }
			  
			  */
					
					
				
			    	List<Address> addresses = null;
			    	String position = "";
			    	
					try {
						addresses = gcd.getFromLocation(pLat, pLong, 1);
					} catch (IOException e) {
						e.printStackTrace();
					}
					/*
			    	if (addresses.size() > 0) {
			    	    position = addresses.get(0).;
			    	}
			    	
			    	
			    	Address1.setText(position);
			  */
					
					 if(addresses != null) {
						    Address returnedAddress = addresses.get(0);
						    StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
						    for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
						     strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
						    }
						//   Address1.setText(strReturnedAddress.toString());
						   
						   
						   
					 // email
						   
						    
						       try{
						if (x<=1){
						   Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);  
						   String aEmailList[] = { "prudhvi.gnt65@gmail.com","prudhvi.sagar@gmail.com" };  
						 //  String aEmailCCList[] = { "user3@fakehost.com","user4@fakehost.com"};  
					//	   String aEmailBCCList[] = { "user5@fakehost.com" };  
					   emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);  
					//	   emailIntent.putExtra(android.content.Intent.EXTRA_CC, aEmailCCList);  
						//   emailIntent.putExtra(android.content.Intent.EXTRA_BCC, aEmailBCCList);  
						   emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My subject");  
						   emailIntent.setType("plain/text");  
						   emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, strReturnedAddress.toString());  
						   startActivity(emailIntent);  
						   
						  
						   x++;
						   }
						
						       } catch (Exception e) {
						    	   e.printStackTrace();
						       }
						   //end email				 
					 }
						   else{
				//			   Address1.setText("No Address returned!");
						   }
								 
			}
			
		}
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
        	
        }
       
            
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_emergency, menu);
        return true;
    }
}
