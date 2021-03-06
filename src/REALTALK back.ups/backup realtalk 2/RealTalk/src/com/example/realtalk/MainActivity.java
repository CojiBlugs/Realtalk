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
	 String name = "";
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
			Toast.makeText(context, "Mensahi bago ", Toast.LENGTH_SHORT).show();
			 // onAddItem2ListClicked();
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
	    		
	    		 String no = cursor.getString(2);
	    		 cnameinfo = cursor.getString(2);
	    		if(idC.matches(idSMS)){
	    			itemlist.setThreadID(cursor.getString(cursor.getColumnIndexOrThrow("thread_id")).toString());
	    			
	    			
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
	    		itemlist.setThreadID(cur.getString(cur.getColumnIndexOrThrow("thread_id")).toString());
	    		
	    	
	    		
	    			
	    	result.add(itemlist);
	    	
	    	
	    	//Toast.makeText(getBaseContext(), "New Message ", Toast.LENGTH_LONG).show();
		
	    	
		
	    		}
	    		
		return result;
	
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
