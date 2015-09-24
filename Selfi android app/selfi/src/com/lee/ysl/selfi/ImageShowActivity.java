package com.lee.ysl.selfi;

import pl.itraff.TestApi.ItraffApi.model.APIResponse;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

public class ImageShowActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("API", "Start");
		APIResponse response = (APIResponse) getIntent().getExtras().getSerializable("data");
		Log.v("API", response.toString());
		byte[] data = getIntent().getExtras().getByteArray("bitmap");;
		if (data != null)
			Log.v("API", data.length + "");
		else {
			Log.v("API", "data is null");
		}
		Log.v("API", "Start");
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		if (bitmap.getWidth() > bitmap.getHeight())
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		else
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		ExtendedSurfaceView v = new ExtendedSurfaceView(getApplicationContext(), bitmap, response);
		setContentView(v);
	}

}
