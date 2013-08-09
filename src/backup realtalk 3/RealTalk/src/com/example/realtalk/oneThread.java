package com.example.realtalk;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.widget.ListView;
import android.widget.Toast;

public class oneThread extends Activity{
	public static String numinfo = "";
	public static String cnameinfo = "";
	
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
	    	 String no = cur.getString(2);
			 cnameinfo = cur.getString(2);
	    	if(cur.getString(cur.getColumnIndexOrThrow("type")).equalsIgnoreCase("1") ){
	    		

				//Resolving the contact name from the contacts.
                Uri lookupUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(no ));
                Cursor c = getContentResolver().query(lookupUri, new String[]{ContactsContract.Data.DISPLAY_NAME},null,null,null);
                try {
                    c.moveToFirst();
                String  displayName = c.getString(0);
                String ContactName = displayName;   
                cnameinfo = ContactName;
             		                
                } catch (Exception e) {
                    // TODO: handle exception
                }finally{
                    c.close();
                }
       
			   		
	    		  itemDetails.setAddress(cnameinfo);
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
