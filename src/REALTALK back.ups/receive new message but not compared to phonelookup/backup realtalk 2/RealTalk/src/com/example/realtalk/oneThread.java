package com.example.realtalk;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class oneThread extends Activity{
	
	ArrayList<message> myListData;
	RealTalkAdapter realAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewmessagelist);
		String value=null;
		Bundle extras = getIntent().getExtras();
		   if (extras != null) 
		   {
		     value = extras.getString("value");
		   }
		   Toast.makeText(this, value, Toast.LENGTH_LONG).show();
		   
		   final ListView lvList = (ListView) findViewById(R.id.conversationList);
		   
	}
}
