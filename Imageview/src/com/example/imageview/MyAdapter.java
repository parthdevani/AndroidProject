package com.example.imageview;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.ListViewAutoScrollHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	private Map<String, byte[]> data;
	private Activity activity;
	TextView title;
	ImageView thumb_image;
	String titalArray[];
	Set keys;
	Iterator itr;
	private static LayoutInflater inflater = null;

	public MyAdapter(Activity a, Map<String, byte[]> map) {
		activity = a;
		data = map;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		String key[] = new String[data.size()];
		Bitmap bitmap[] = new Bitmap[data.size()];
		if (convertView == null) {
			keys = data.keySet();
			itr = keys.iterator();
			int i = 0;
			byte[] value;
			while (itr.hasNext()) {
				key[i] = (String) itr.next();
				value = (byte[]) data.get(key[i]);
				ByteArrayInputStream imageStream = new ByteArrayInputStream(value);
				bitmap[i] = BitmapFactory.decodeStream(imageStream);
				i++;
			}
			vi = inflater.inflate(R.layout.custom_list, null);
		}
		title = (TextView) vi.findViewById(R.id.title); // title
		title.setText(key[position]);
		thumb_image = (ImageView) vi.findViewById(R.id.list_image);
		thumb_image.setImageBitmap(bitmap[position]);
		return vi;
	}

}
