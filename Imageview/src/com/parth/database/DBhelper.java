package com.parth.database;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;



import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceActivity.Header;
import android.support.v7.widget.ThemedSpinnerAdapter.Helper;
import android.util.Log;

public class DBhelper   {
	//DBhelper dbhelper;
	Helper helper;
	public DBhelper(Context context) {
	helper=new Helper(context);
	}
	
	public long insrtData(String name,byte[] image){
		SQLiteDatabase db=helper.getWritableDatabase();

		ContentValues contentvalue=new ContentValues();
		
		contentvalue.put(Helper.COLUMN_NAME, image);
		contentvalue.put(Helper.COLUMN_ID, name);
		long id= db.insert(Helper.TABLE_NAME, null, contentvalue);
		Log.e("databse","insertdata");
		db.close();
		return id;
	}
	public Map<String, byte[]> getAlldata(){
		Map<String, byte[]> map =new HashMap<String, byte[]>();
		SQLiteDatabase db=helper.getWritableDatabase();
		String[] clum={Helper.COLUMN_sequnce,Helper.COLUMN_ID,Helper.COLUMN_NAME};
		Cursor c= db.query(Helper.TABLE_NAME,clum, null, null, null,null, null);
	
		while(c.moveToNext()){
			int index=c.getColumnIndex(Helper.COLUMN_ID);
			// byte[] data = c.getBlob(1);
			Log.e("retrive", c.getString(0)+"key: "+c.getString(1)+" value : "+c.getBlob(2));
			
			map.put(c.getString(0)+" "+c.getString(1),c.getBlob(2));
		}
		db.close();
		return map;
		
	}
	
	static class Helper extends SQLiteOpenHelper{
		 private static final String TABLE_NAME = "images";
		  private static final String COLUMN_ID = "name";
		  public static final String COLUMN_NAME = "image";
		  public static final String COLUMN_sequnce="_id";

		  private static final String DATABASE_NAME = "imageresource.db";
		  private static final int DATABASE_VERSION = 9;

		  // Database creation sql statement
		  private static final String DATABASE_CREATE = "create table "
		      + TABLE_NAME + "(" + COLUMN_sequnce
		      + " integer primary key autoincrement, "+ COLUMN_ID
		      + " VARCHAR(255), " + COLUMN_NAME
		      + " VARCHAR(255));";

		  public Helper(Context context) {
		    super(context, DATABASE_NAME, null, DATABASE_VERSION);
			//SQLiteDatabase db=getWritableDatabase();
		   Log.e("databse","constructer");
		  }

		  @Override
		  public void onCreate(SQLiteDatabase database) {
			  Log.e("parth", "table oncreate");
			  try {
				database.execSQL(DATABASE_CREATE);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("parth", "table is not created");
			}
		  }

		  @Override
		  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		    Log.e("database",
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		    onCreate(db);
		  }
	}

	} 