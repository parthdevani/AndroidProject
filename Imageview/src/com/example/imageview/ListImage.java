package com.example.imageview;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.parth.database.DBhelper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListImage extends Activity {
	DBhelper dbhelper;
	ListView lv;
	String[] values;
	Map<String, byte[]> map;
	MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two);
		lv = (ListView) findViewById(R.id.listView1);
		// db helper need to call for fatch the data
		dbhelper = new DBhelper(getApplicationContext());
		map = dbhelper.getAlldata();
		adapter = new MyAdapter(this, map);
		lv.setAdapter(adapter);

	}

}
