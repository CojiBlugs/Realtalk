package com.example.realtalk;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.net.Uri;
import android.content.ContentValues;


public class ComposeActivity extends Activity {
	String phone;
	String body ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		
		
		Button back = (Button) findViewById(R.id.backBtn);
		EditText add = (EditText) findViewById(R.id.editText1);
		EditText bod = (EditText) findViewById(R.id.editText2);
		phone = add.getText().toString();
		body = bod.getText().toString();
		
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				//ContentValues values = new ContentValues(); 
	              
			    //values.put("address", add.getText().toString()); 
			              
			    //values.put("body", bod.getText().toString()); 
			              
			    //getContentResolver().insert(Uri.parse("content://sms/sent"), values);
				//SmsManager sms = SmsManager.getDefault();
			      // sms.sendTextMessage(phone, null, body, null, null);
			    //SmsManager smsManager = SmsManager.getDefault();
			    //ArrayList<String> parts = smsManager.divideMessage(bod.getText().toString()); 
			    //smsManager.sendMultipartTextMessage(add.getText().toString(), null, parts, null, null);
				// TODO Auto-generated method stub
				//Intent intent = new Intent(ComposeActivity.this, MainActivity.class);
			//startActivity(intent);
			
			}
		});
	}
	
	
	

}