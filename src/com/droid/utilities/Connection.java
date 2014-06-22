package com.droid.utilities;


import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;


public class Connection
{
	/**
	 * Makes the connection to the php page
	 * @return the url connection to php page
	 */
    public static URLConnection connect()
    {
    	URL url;
    	URLConnection urlConnection = null;
    	//Establish a connection to the database's php page
    	try
    	{

	    	url = new URL("http://omega.uta.edu/~gxv7734/DBConnectionScript.php");
    		
			urlConnection = url.openConnection();
			//Tell the php page we're sending "POST" messages
			((HttpURLConnection)urlConnection).setRequestMethod("POST");
			urlConnection.setDoInput(true);
	        urlConnection.setDoOutput(true);
	        Log.i("Connection", "Connection Made");
    	}
	    catch (Exception ex)
    	{
    		Log.i("Connection", "Failed to connect");
    		//System.out.println("Exception caught:\n" + ex.toString());
    	}
	    return urlConnection;
    }

}