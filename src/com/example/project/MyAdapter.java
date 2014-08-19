package com.example.project;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	ArrayList<Item> data = new ArrayList<Item>();
	Context context;

	public MyAdapter(Context context, ArrayList<Item> arr) {
		if (arr != null) {
			data = arr;
		}
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int num) {
		// TODO Auto-generated method stub
		return data.get(num);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int i, View someView, ViewGroup arg2) {
		LayoutInflater inflater = LayoutInflater.from(context);
		if (someView == null) {
			someView = inflater.inflate(R.layout.listview_item, arg2, false);
		}

		TextView title = (TextView) someView.findViewById(R.id.title);
		TextView pubDate = (TextView) someView.findViewById(R.id.pubDate);

		title.setText(data.get(i).title);
//		link.setText(data.get(i).link);
		pubDate.setText("Дата публикации: " + data.get(i).pubDate);
//		descr.setText(data.get(i).description);

		return someView;
	}

}
