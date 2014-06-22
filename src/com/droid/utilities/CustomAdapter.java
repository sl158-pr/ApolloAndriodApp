//File used for displaying the list view for patients

package com.droid.utilities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.BaseAdapter;
import java.util.List;


class CustomAdapterView extends LinearLayout {        
	public CustomAdapterView(Context context, Device device) 
	{
		super( context );		
		setId(device.getDeviceID());
		    
		//container is a horizontal layer
		setOrientation(LinearLayout.HORIZONTAL);
		setPadding(0, 6, 0, 6);
		
		//image:params
		LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		Params.setMargins(6, 0, 6, 0);
		
		//vertical layer for text
		Params = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		LinearLayout PanelV = new LinearLayout(context);
		PanelV.setOrientation(LinearLayout.VERTICAL);
		PanelV.setGravity(Gravity.BOTTOM);
		
		TextView textName = new TextView( context );
		textName.setTextSize(16);
		textName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		textName.setText( device.getDeviceName());
		PanelV.addView(textName);       
		
		TextView textAddress = new TextView( context );
		textAddress.setTextSize(16);
		textAddress.setText( device.getDeviceAddress());
		PanelV.addView(textAddress);    
		
		addView(PanelV, Params);
	}
}


public class CustomAdapter extends BaseAdapter  {
	
	
	public static final String LOG_TAG = "BI::CA";
    private Context context;
    private List<Device> deviceList;

    public CustomAdapter(Context context, List<Device> deviceList ) { 
        this.context = context;
        this.deviceList = deviceList;
    }

    public int getCount() {                        
        return deviceList.size();
    }

    public Object getItem(int position) {     
        return deviceList.get(position);
    }

    public long getItemId(int position) {  
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) 
    { 
        Device device = deviceList.get(position);
        View v = new CustomAdapterView(this.context, device );
        
        v.setBackgroundColor((position % 2) == 1 ? Color.GRAY : Color.BLACK);
        
        /*v.setOnClickListener(new OnItemClickListener(position));*/
        return v;
    }

   

}
