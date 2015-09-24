package com.yi.app.vibrant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class OTP extends Activity{
	public static String OTP = "";
	EditText msgTextField;
	Button sendButton;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.popup);

		//		Button btListe = (Button)findViewById(R.id.submit);
		//
		//		btListe.setOnClickListener(new OnClickListener()
		//		{
		//			public void onClick(View v)
		//			{
		//				EditText mEdit   = (EditText)findViewById(R.id.otptext);
		//				Intent in = new Intent(OTP.this,PostThread.class);
		//				in.putExtra("message",mEdit.getText().toString());
		//				getApplicationContext().startService(in);
		//			}
		//		});

		//make message text field object
		msgTextField = (EditText) findViewById(R.id.otptext);
		//make button object
		sendButton = (Button) findViewById(R.id.submit);
	}

	public void send(View v)
	{
		String  msg = msgTextField.getText().toString();
		Log.d("OTP", "message is:"+msg);
		OTP = msg;
		Intent in = new Intent(getApplicationContext(),PostThread.class);
		in.putExtra("message",OTP);
		getApplicationContext().startService(in);
		finish();
	}

}
