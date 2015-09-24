package com.lee.ysl.selfi;



import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

public class SplashActivity  extends Activity{

	// Splash screen timer
    private static int SPLASH_TIME_OUT = 5000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
 
        new Handler().postDelayed(new Runnable() {
 
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                //Intent i = new Intent(SplashActivity.this, MainActivity.class);
            	Intent i = new Intent(SplashActivity.this, CatActivity.class);
            	File f = new File(Environment.getExternalStorageDirectory(), "scan.jpg");
            	Uri uri = Uri.fromFile(f);
            	i.setAction(Intent.ACTION_SEND);
            	i.setType("image/*");
            	i.putExtra(Intent.EXTRA_STREAM,uri);
            	startActivity(i);
 
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
 
}
