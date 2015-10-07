package com.example.imageview;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import com.parth.database.DBhelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity implements OnClickListener {
Button b_selct_image,b_view_imaage;
int REQUEST_CODE=0;
ImageView imageView;
Bitmap bitmap;
byte[] image;
EditText title;
DBhelper dbhelper;
int number=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b_selct_image=(Button)findViewById(R.id.seclet);
		b_view_imaage=(Button)findViewById(R.id.button2);
		b_selct_image.setOnClickListener(this);
		b_view_imaage.setOnClickListener(this);
		title=(EditText)findViewById(R.id.editText1);
		imageView=(ImageView)findViewById(R.id.imageView1);
		dbhelper=new DBhelper(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Random rn = new Random();
		
		number =  rn.nextInt(10000) + 1;
		if(v.getId()==R.id.seclet)
		{/*Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 0);*/
			/*Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
			Intent gallIntent=new Intent(Intent.ACTION_GET_CONTENT);
			
		Intent camIntent = new Intent("android.media.action.IMAGE_CAPTURE");
			pickIntent.putExtra(Intent.EXTRA_INTENT, camIntent);
		gallIntent.setType("image/*"); 
			pickIntent.putExtra(Intent.EXTRA_INTENT,gallIntent);
			
			pickIntent.putExtra(Intent.EXTRA_TITLE, "Select Source");
			startActivityForResult(pickIntent, 1);
			*/
			Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT,null);
		    galleryIntent.setType("image/*");
		    galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);

		    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 


		    Intent chooser = new Intent(Intent.ACTION_CHOOSER);
		    chooser.putExtra(Intent.EXTRA_INTENT, galleryIntent);      
		    chooser.putExtra(Intent.EXTRA_TITLE, "Select Source");

		    Intent[] intentArray =  {cameraIntent}; 
		    chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
		    startActivityForResult(chooser,REQUEST_CODE);
			
		
		}else if(v.getId()==R.id.button2){
		{
			 Intent viewimage = new Intent(this,ListImage.class);
			startActivity(viewimage);
			
		}
		
		}
}
		 protected void onActivityResult(int requestCode, int resultCode, Intent data) 
		  { super.onActivityResult(requestCode, resultCode, data);
		      if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
		      {Log.e("parth", "inside request code"+requestCode+"result: "+resultCode);
		          if(data.getData()!=null)
		          {
		              try 
		              {
		              if (bitmap != null) 
		                  {
		                      bitmap.recycle();
		                  }

		              InputStream stream = getContentResolver().openInputStream(data.getData());
		              bitmap = BitmapFactory.decodeStream(stream);
		             
		            
		             // Bitmap b=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		              ByteArrayOutputStream bos=new ByteArrayOutputStream();
		           
		             // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);	             
		              bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
		              byte[] byteArray = bos.toByteArray();
		              long i;
		              if(title.getText()!=null){
		              i= dbhelper.insrtData(title.getText().toString(),byteArray);
		              Log.e("parth", "inside with title");
		              }else
		              {
		            	  i= dbhelper.insrtData("image"+number,byteArray); 
		            	  Log.e("parth", "inside without title");
		              }
		             
		             Log.e("parth", "insert data response :" +i);
		            // img =bitmap.to
		             // b.compress(Bitmap.CompressFormat.PNG, 100, bos);
		             // img=bos.toByteArray();
		              stream.close();
		              bos.close();
		              imageView.setImageBitmap(bitmap);
		              }

		          catch (FileNotFoundException e) 
		              {
		                  e.printStackTrace();
		              }

		          catch (IOException e) 
		              {
		                  e.printStackTrace();
		              }
		          } 

		          else 
		          {
		              bitmap=(Bitmap) data.getExtras().get("data"); 
		              ByteArrayOutputStream bos=new ByteArrayOutputStream();
			           
			             // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);	             
			              bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
			              byte[] byteArray = bos.toByteArray();
			              long i;
			           
			              i= dbhelper.insrtData(title.getText().toString(),byteArray);
		              imageView.setImageBitmap(bitmap);
		          }

		         
		      }
		  }
		}
	