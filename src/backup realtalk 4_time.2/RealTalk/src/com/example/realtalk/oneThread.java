package com.example.realtalk;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class oneThread extends Activity{
	
	ArrayList<message> myLData;
	RealTalkAdapter2 realAdapter;
	String value;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewmessagelist);
		
		Bundle extras = getIntent().getExtras();
		   if (extras != null) 
		   {
		     this.value = extras.getString("value");
		   }
		   Toast.makeText(this, value, Toast.LENGTH_LONG).show();
		   System.out.println(""+value);
		   
		myLData = new ArrayList<message>();
		myLData = GetItems();	
		realAdapter = new RealTalkAdapter2(this, myLData); 
		
		final ListView mlist = (ListView) findViewById(R.id.msgThread);
		mlist.setAdapter(realAdapter);
		
		
	}
	private ArrayList<message> GetItems(){
		ArrayList<message> results = new ArrayList<message>();
		message itemDetails;
		String where = "thread_id="+ value;
		Uri uriSMSURI = Uri.parse("content://sms/");
	   // Cursor cur = getContentResolver().query(uriSMSURI, null, null, null,"date ASC");
		Cursor cur = getContentResolver().query(uriSMSURI, null, where, null,"date ASC");
	    while (cur.moveToNext()) {
	    	System.out.println("hello ");
	    	itemDetails = new message();
	    	  //if(cur.getString(cur.getColumnIndexOrThrow("thread_id")).equalsIgnoreCase(value)){
	    	 
	    	if(cur.getString(cur.getColumnIndexOrThrow("type")).equalsIgnoreCase("1") ){
	    		  itemDetails.setAddress(cur.getString(cur.getColumnIndexOrThrow("address")).toString());
	    		  itemDetails.setBody(cur.getString(cur.getColumnIndexOrThrow("body")).toString());
	    		  results.add(itemDetails);
	    		  
	    	  }else{	    		  
	    		  itemDetails.setAddress("From: Me");
	    		  itemDetails.setBody(cur.getString(cur.getColumnIndexOrThrow("body")).toString());
	    		  results.add(itemDetails);
	    	 // }
	    	  }
	    }
		
		return results;
	}
}
