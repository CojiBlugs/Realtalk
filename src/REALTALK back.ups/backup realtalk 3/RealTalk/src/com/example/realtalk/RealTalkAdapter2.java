package com.example.realtalk;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RealTalkAdapter2 extends BaseAdapter {
	private LayoutInflater myinflater;
	private List<message> ITEMS = new ArrayList<message>();

	public RealTalkAdapter2(Context context, ArrayList<message> items) {
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
			convertView = myinflater.inflate(R.layout.listdesign2, null);
			holder = new ViewHolder();
			holder.txt_address = (TextView) convertView
					.findViewById(R.id.addressID2);
			holder.txt_body = (TextView) convertView.findViewById(R.id.bodyID2);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txt_address.setText(ITEMS.get(position).getAddress());
		holder.txt_body.setText(ITEMS.get(position).getBody());
		
		return convertView;
	}

	static class ViewHolder {
		TextView txt_address;
		TextView txt_body;
	}

}
