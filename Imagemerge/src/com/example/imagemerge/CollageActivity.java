package com.example.imagemerge;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.parth.util.TouchImageView;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

public class CollageActivity extends Activity implements OnClickListener {
	// ImageView main_view;
	TouchImageView left, right, middle;
	Button select_image, share;
	Bitmap[] bitmap;
	String emp_path = "/storage/emulated/0/devani.png";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bitmap = new Bitmap[3];
		// main_view = (ImageView) findViewById(R.id.imageView1);

		left = (TouchImageView) findViewById(R.id.imageView4);
		right = (TouchImageView) findViewById(R.id.imageView3);
		middle = (TouchImageView) findViewById(R.id.imageView2);
		select_image = (Button) findViewById(R.id.button1);

		share = (Button) findViewById(R.id.button2);
		select_image.setOnClickListener(this);
		share.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.button1) {
			Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
			galleryIntent.setType("image/*");
			// galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
			galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
			galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

			// Intent cameraIntent = new
			// Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

			// Intent chooser = new Intent(Intent.ACTION_CHOOSER);
			// chooser.putExtra(Intent.EXTRA_INTENT, galleryIntent);
			// chooser.putExtra(Intent.EXTRA_TITLE, "Select Source");

			// Intent[] intentArray = { cameraIntent };
			// chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
			startActivityForResult(galleryIntent, 100);

		} else if (v.getId() == R.id.button2) {

			TableLayout content = (TableLayout) findViewById(R.id.tableLayout1);
			content.setDrawingCacheEnabled(true);
			Bitmap bitmap = content.getDrawingCache();

			File file = new File("/storage/emulated/0/" + "devani" + ".png");

			try {
				if (!file.exists()) {
					file.createNewFile();
				} else {
					file.delete();
					file.createNewFile();
				}
				FileOutputStream ostream = new FileOutputStream(file);

				bitmap.compress(CompressFormat.PNG, 10, ostream);

				/**/
				share_social();
				ostream.close();

				content.invalidate();
			} catch (Exception e) {
				e.printStackTrace();
				// Log.e("parth", "exception to save file" + e);
			} finally {
				content.setDrawingCacheEnabled(false);

			}

		}
	}

	private void share_social() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();

		intent.setAction(Intent.ACTION_SEND);
		intent.setType("image/*");

		// intent.putExtra(Intent.EXTRA_TEXT, "testparth");
		// intent.putExtra(Intent.EXTRA_TITLE, "testparth");
		// intent.putExtra(Intent.EXTRA_SUBJECT, "testsubject");
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(emp_path)));

		Intent openInChooser = new Intent(intent);
		openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intent);
		startActivity(openInChooser);

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
			Log.e("parth", "inside request code" + requestCode + "result: " + resultCode);

			if (null != data) {
				Log.e("parth", "inside getdata");
				try {
					if (bitmap[0] != null) {
						bitmap[0].recycle();
					}

					if (bitmap[1] != null) {
						bitmap[1].recycle();
					}
					if (bitmap[2] != null) {
						bitmap[2].recycle();
					}

					ClipData clip = data.getClipData();
					if (clip.getItemCount() == 3) {
						Log.e("parth", "selected image ==3");
						for (int i = 0; i < clip.getItemCount(); i++) {
							ClipData.Item item = clip.getItemAt(i);
							Uri uri = item.getUri();
							Log.e("parth", "image path" + uri);
							bitmap[i] = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

							left.setImageBitmap(bitmap[0]);
							right.setImageBitmap(bitmap[2]);
							middle.setImageBitmap(bitmap[1]);

						}
					} else {
						Toast.makeText(getApplicationContext(), "Select only 3 photo", Toast.LENGTH_SHORT).show();
					}

				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "Select 3 photo", Toast.LENGTH_SHORT).show();
					Log.e("parth", "EXCEPTION oCCURES" + e);
					System.out.println("exception " + e);
				}

			} else {
				Log.e("parth", "data is null");
			}

		}
	}
}