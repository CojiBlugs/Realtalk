package com.example.realtalk;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static String numinfo = "";
	public static String cnameinfo = "";
	String msgBody, msgNumber;

	IntentFilter intentFilter;

	private BroadcastReceiver intentReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			msgBody = (intent.getExtras().getString("sms"));
			msgNumber = (intent.getExtras().getString("num"));
			
			message item = new message();
			item.setAddress(msgNumber);
			item.setBody(msgBody);
			System.out.println(msgNumber);
			myListData.add(item);
			realAdapter.notifyDataSetChanged();
			Toast.makeText(context, "new MSG Receive", Toast.LENGTH_LONG).show();
		}
	};

	
	ArrayList<message> myListData;
	RealTalkAdapter realAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myListData = new ArrayList<message>();
		//myListData = GetItems();		
		myListData = GetConversations();
		
		realAdapter = new RealTalkAdapter(this, myListData); 
		
		final ListView lvList = (ListView) findViewById(R.id.conversationList);
		lvList.setAdapter(realAdapter);
		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				
				Object o = lvList.getItemAtPosition(position);
            	message obj_itemDetails = (message)o;
        		//Toast.makeText(MainActivity.this, "You have chosen : " + " " + obj_itemDetails.getThreadID(), Toast.LENGTH_LONG).show();
				String threadId = obj_itemDetails.getThreadID();
				Intent intent = new Intent(MainActivity.this, oneThread.class);
				Bundle bundle = new Bundle();   
                bundle.putString( "value",threadId);  
                intent.putExtras(bundle);   
                startActivity(intent);
			}
		});
		intentFilter = new IntentFilter();
		intentFilter.addAction("SMS_RECEIVED_ACTION");
	
	}
	
	private ArrayList<message> GetConversations(){
		ArrayList<message> result = new ArrayList<message>();
		message itemlist;
		Uri uriSMSURI = Uri.parse("content://sms/conversations/");
		Uri uri = Uri.parse("content://sms/");
		Cursor cursor = getContentResolver().query(uri, null, null, null,null);
	    Cursor cur = getContentResolver().query(uriSMSURI, null, null, null,"date DESC");
	    String idC, idSMS;
	    while(cur.moveToNext()){
	    	idC = cur.getString(cur.getColumnIndexOrThrow("thread_id")).toString();
	    	System.out.println("con : "+idC);
	    	
	    	itemlist = new message();
	    	while(cursor.moveToNext()){
	    		idSMS = cursor.getString(cursor.getColumnIndexOrThrow("thread_id")).toString();
	    		System.out.println("sms : "+idSMS);
	    		if(idC.matches(idSMS)){
	    			itemlist.setThreadID(cursor.getString(cursor.getColumnIndexOrThrow("thread_id")).toString());
	    			
	    			 String no = cursor.getString(2);
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
			    				    			
	    			itemlist.setAddress(cnameinfo);
	    			break;
	    		}
	    	}
	    		cursor.moveToFirst();
	    		itemlist.setCountMsg(cur.getString(cur.getColumnIndexOrThrow("msg_count")).toString());
	    		itemlist.setSnippet(cur.getString(cur.getColumnIndexOrThrow("snippet")).toString());
	    		
	    	result.add(itemlist);
	    }
		return result;
	}

	private ArrayList<message> GetItems(){
		ArrayList<message> results = new ArrayList<message>();
		message itemDetails;
		Uri uriSMSURI = Uri.parse("content://sms/");
	    Cursor cur = getContentResolver().query(uriSMSURI, null, null, null,"date ASC");
	    String adr, val;
	    while (cur.moveToNext()) {
	    	itemDetails = new message();
	    	  if(cur.getString(cur.getColumnIndexOrThrow("type")).equalsIgnoreCase("1") ){
	    		  
	    		  
	    		    		  
	    		  adr = cur.getString(cur.getColumnIndexOrThrow("address")).toString();
	    		  val = getContactName(adr);
	    		  if(val !=null || val!=""){
	    			  
	    			  itemDetails.setAddress(val);
	    		  
	    		  }
	    		  if(val==null || val==""){ itemDetails.setAddress(cur.getString(cur.getColumnIndexOrThrow("address")).toString());}
	    		  itemDetails.setBody(cur.getString(cur.getColumnIndexOrThrow("body")).toString());
	    		  results.add(itemDetails);
	    		  
	    	  }else{	    		  
	    		  itemDetails.setAddress("From: Me");
	    		  itemDetails.setBody(cur.getString(cur.getColumnIndexOrThrow("body")).toString());
	    		  results.add(itemDetails);
	    	  }
	    }
		
		return results;
	}

	
	public String getContactName(String number) {
	    Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
	    String name = "";
	    
	    Cursor contactLookup = getContentResolver().query(uri, new String[] {
	            ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);

	    try {
	        if (contactLookup != null && contactLookup.getCount() > 0) {
	            contactLookup.moveToNext();
	            name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
	        }
	    } finally {
	        if (contactLookup != null) {
	            contactLookup.close();
	        }
	    }

	    return name;
	}
	protected void onResume() {
		registerReceiver(intentReceiver, intentFilter);
		super.onResume();
	}

	protected void onPause() {
		unregisterReceiver(intentReceiver);
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
