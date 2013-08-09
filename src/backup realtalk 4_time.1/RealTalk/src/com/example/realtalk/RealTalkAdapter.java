package com.example.realtalk;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class RealTalkAdapter extends BaseAdapter {
	
	private LayoutInflater myinflater;
	private List<message> ITEMS = new ArrayList<message>(); 
		
	public RealTalkAdapter(Context context, ArrayList<message> items){
		myinflater = LayoutInflater.from(context);
		ITEMS = items;
	}
	
	@Override
	public int getCount() {
		return ITEMS.size();
	}

	@Override
	public Object getItem(int position) {
		return ITEMS.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		
		  if (convertView == null) {
		   convertView = myinflater.inflate(R.layout.listdesign, null);
		   holder = new ViewHolder();
		   holder.txt_address = (TextView) convertView.findViewById(R.id.addTXT);
		   holder.txt_body = (TextView) convertView.findViewById(R.id.msgTXT);
		   holder.txt_date = (TextView) convertView.findViewById(R.id.dateTXT);
		   		 
		   convertView.setTag(holder);
		  } else {
		   holder = (ViewHolder) convertView.getTag();
		  }
		  
		  
		  holder.txt_address.setText(ITEMS.get(position).getAddress()+ " ("+ITEMS.get(position).getCountMsg()+")");
		  holder.txt_body.setText(ITEMS.get(position).getSnippet());
		  
		   holder.txt_date.setText(convertDate(ITEMS.get(position).getMsgDate().toString().trim(),"hh:mm a"));
		  
		  return convertView;
	}
	
	 static class ViewHolder {
		  TextView txt_address;
		  TextView txt_body;
		  TextView txt_date;
	}

	 public static String convertDate(String dateInMilliseconds,String dateFormat) {
		    return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
		}
}
