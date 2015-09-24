package com.yi.app.vibrant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class LoginActivity extends Activity {



	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

		Button btListe = (Button)findViewById(R.id.login);
	    btListe.setOnClickListener(new OnClickListener()
	    {    public void onClick(View v)
	        {
	            Intent intent = new Intent(LoginActivity.this,TabedActivity.class );
	            startActivity(intent);
	            finish();
	        }
	    });


	}
}
