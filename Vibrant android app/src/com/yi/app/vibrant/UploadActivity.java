package com.yi.app.vibrant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UploadActivity extends Activity {

	private MenuItem item;
	private String url = Config.REMOTE_SERVER_URL;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_upload);
		String message = "12345";
//		final EditText edtTxt1 = (EditText) findViewById(R.id.editTextUpl1);
//		final EditText edtTxt2 = (EditText) findViewById(R.id.editTextUpl2);
//		Button btnUpl = (Button) findViewById(R.id.upload);
				SendHttpRequestTask t = new SendHttpRequestTask();

				String[] params = new String[]{url, message};
				t.execute(params);
			}


private class SendHttpRequestTask extends AsyncTask<String, Void, String> {


		@Override
		protected String doInBackground(String... params) {
			String url = params[0];
			String name = params[1];

			String data = sendHttpRequest(url, name);
			//System.out.println("Data ["+data+"]");
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
//			edtResp.setText(result);
//			item.setActionView(null);

		}



	}

	private String sendHttpRequest(String url, String name) {
		StringBuffer buffer = new StringBuffer();
		try {
			System.out.println("URL ["+url+"] - Name ["+name+"]");

			HttpURLConnection con = (HttpURLConnection) ( new URL(url)).openConnection();
			con.setRequestMethod("POST");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
			con.getOutputStream().write( ("otp=" + name).getBytes());
			con.getOutputStream().write( ("rid=" + name).getBytes());

			InputStream is = con.getInputStream();
			byte[] b = new byte[1024];

			while ( is.read(b) != -1)
				buffer.append(new String(b));

			con.disconnect();
		}
		catch(Throwable t) {
			t.printStackTrace();
		}

		return buffer.toString();
	}
}
