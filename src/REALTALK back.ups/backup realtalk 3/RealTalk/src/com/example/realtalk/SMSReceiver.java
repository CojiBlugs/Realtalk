package com.example.realtalk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

	
	@Override
	public void onReceive(Context context, Intent intent) {	
		Bundle bundle = intent.getExtras();
		SmsMessage[] messages=null;
		String str = "";
		String number = "";
		if(bundle !=null){
			Object[] pdus =  (Object[]) bundle.get("pdus");
			messages = new SmsMessage[pdus.length];
			
			for(int i=0; i<messages.length; i++)
			{
				messages[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
				//str += "From: " +messages[i].getOriginatingAddress()+"\n";
				str = messages[i].getDisplayMessageBody().toString();
				number =messages[i].getOriginatingAddress();
				
			}
			
			//Toast.makeText(context, str, Toast.LENGTH_LONG).show();
			Intent broadcastIntent = new Intent();
			broadcastIntent.setAction("SMS_RECEIVED_ACTION");
			broadcastIntent.putExtra("sms", str);
			broadcastIntent.putExtra("num", number);
			context.sendBroadcast(broadcastIntent);
		}	
	}
	
}
