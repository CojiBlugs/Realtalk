package com.example.realtalk;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.telephony.SmsManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ComposeActivity extends Activity {
	String phone;
	String body ;
	Button sendSMS;
	EditText msgTxt;
	EditText numTxt;
	IntentFilter intentFilter;
	TextView inTxt;
	TextView outTxt;
	String str = "";
	private static final int CONTACT_PICKER_RESULT = 1001;

	public void doLaunchContactPicker(View view) {
		Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
				Contacts.CONTENT_URI);
		startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
	}
	private BroadcastReceiver intentReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			// inTxt = (TextView) findViewById(R.id.textMsg);
			// inTxt.append(intent.getExtras().getString("sms"));
			// inTxt.setText(intent.getExtras().getString("sms"));

		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		
		intentFilter = new IntentFilter();
		intentFilter.addAction("SMS_RECEIVED_ACTION");
	
		EditText add = (EditText) findViewById(R.id.editText1);
		EditText bod = (EditText) findViewById(R.id.editText2);
		phone = add.getText().toString();
		body = bod.getText().toString();

		sendSMS = (Button) findViewById(R.id.sendBtn);
		numTxt = (EditText) findViewById(R.id.editText1);
		msgTxt = (EditText) findViewById(R.id.editText2);

		msgTxt.setText("Message");
		msgTxt.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub

				msgTxt.setText("");
				return false;
			}
		});

		sendSMS.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// inTxt = (TextView) findViewById(R.id.);
				// inTxt.append(intent.getExtras().getString("sms"));
				// inTxt.append("Me: "+msgTxt.getText()+"\n\n");

				String myMsg = msgTxt.getText().toString();
				String theNumber = numTxt.getText().toString();

				sendMsg(theNumber, myMsg);
				msgTxt.setText("Message");

				msgTxt.setClickable(true);
				msgTxt.getGravity();

			}
		});
	}

	protected void sendMsg(String theNumber, String myMsg) {
		String SENT = "Message Sent";
		String DELIVERED = "Message Delivered";
		//ArrayList<String> contactList = new ArrayList();
		
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(
				SENT), 0);
		PendingIntent deliveredtPI = PendingIntent.getBroadcast(this, 0,
				new Intent(DELIVERED), 0);

		registerReceiver(new BroadcastReceiver() {
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(ComposeActivity.this, "SMS sent",
							Toast.LENGTH_LONG).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "Generic Failure",
							Toast.LENGTH_LONG).show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(), "No Service",
							Toast.LENGTH_LONG).show();
					break;
				}
			}
		}, new IntentFilter(SENT));

		registerReceiver(new BroadcastReceiver() {
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(ComposeActivity.this, "SMS delivered",
							Toast.LENGTH_LONG).show();
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(), "SMS not delivered",
							Toast.LENGTH_LONG).show();
					break;
				}
			}
		}, new IntentFilter(DELIVERED));

		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(theNumber, null, myMsg, sentPI, deliveredtPI);

	}

	protected void onResume() {
		registerReceiver(intentReceiver, intentFilter);
		super.onResume();
	}
		
		
	

	@SuppressWarnings("deprecation")
	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);

		EditText contactName = (EditText) findViewById(R.id.editText1);
		switch (reqCode) {
		case (CONTACT_PICKER_RESULT):
			if (resultCode == Activity.RESULT_OK) {
				Uri contactData = data.getData();
				Cursor c = managedQuery(contactData, null, null, null, null);
				if (c.moveToFirst()) {
					String id = c
							.getString(c
									.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

					String hasPhone = c
							.getString(c
									.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

					if (hasPhone.equalsIgnoreCase("1")) {
						Cursor phones = getContentResolver()
								.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
										null,
										ContactsContract.CommonDataKinds.Phone.CONTACT_ID
												+ " = " + id, null, null);
						phones.moveToFirst();
						String cNumber = phones
								.getString(phones
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						Toast.makeText(getApplicationContext(), cNumber,
								Toast.LENGTH_SHORT).show();

						// String nameContact =
						// c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

						contactName.setText(cNumber);
					}
				}
			}
		}
	
	}

}