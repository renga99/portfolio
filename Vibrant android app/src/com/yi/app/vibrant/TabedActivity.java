package com.yi.app.vibrant;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class TabedActivity extends TabActivity {

	public static String DEVICE_IMEI="";
	public static String DEVICE_ANDROID_ID="";
	public static String DEVICE_ANDROID_MAC_ADDRESS="";
	public static String DEVICE_ANDROID_IP_ADDRESS="";
	public static final String REGISTRATION_ID = "regisId";
	private static final String APPLICATION_VERSION = "applicationVersion";
	public static String REG_ID="";
	static String R_ID = "";
	GoogleCloudMessaging gcm;
	Context appContext;
	String regisId,id;
	YodleeSecureServer appUtil;
	AsyncTask<Void, Void, String> shareRegidTask;
	NotificationManager mNotificationManager;

	TabHost mTabHost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_activity);
		mTabHost=getTabHost();

		TabSpec notificationSpec=mTabHost.newTabSpec("Fisrt");
		notificationSpec.setIndicator("Notifications", null);
		Intent notificationSpecintent=new Intent(this,NotificationActivity.class);
		notificationSpec.setContent(notificationSpecintent);

		TabSpec serviceReqSpec=mTabHost.newTabSpec("Second");
		serviceReqSpec.setIndicator("Service Req", null);
		Intent serviceReqSpecintent=new Intent(this,ServiceRequestActivity.class);
		serviceReqSpec.setContent(serviceReqSpecintent);


		TabSpec refreshLogSpec=mTabHost.newTabSpec("Third");
		refreshLogSpec.setIndicator("Refresh logs", null);
		Intent refreshLogSpecintent=new Intent(this,RefreshLogActivity.class);
		refreshLogSpec.setContent(refreshLogSpecintent);

		mTabHost.addTab(serviceReqSpec);
		mTabHost.addTab(notificationSpec);
		mTabHost.addTab(refreshLogSpec);



		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		try{
			mNotificationManager.cancel(NotificationIntentServiceGCM.NOTIFICATION_ID);
		}catch(Exception e){
			Log.d("mainactivity", "first time launch");
		}

		//setContentView(R.layout.main_activity);
		final ProgressDialog dialog = new ProgressDialog(this,ProgressDialog.THEME_HOLO_DARK);
	    dialog.setTitle("Registering...");
	    dialog.setMessage("Please wait, registering device to secure servers..");
	    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    dialog.setIndeterminate(true);
	    dialog.setCancelable(false);
	    dialog.show();

	    long delayInMillis = 5000;
	    Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	        @Override
	        public void run() {
	            dialog.dismiss();
	        }
	    }, delayInMillis);

		appContext = getApplicationContext();

		Toast.makeText(getApplicationContext(),
				"Please wait, we are registering your phone to yodlee secure servers...",
				Toast.LENGTH_LONG).show();

		//Collecting handset parameters
		TelephonyManager m_tmgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		DEVICE_IMEI=m_tmgr.getDeviceId();
		WifiManager m_wmgr= (WifiManager) getSystemService(WIFI_SERVICE);
		DEVICE_ANDROID_MAC_ADDRESS=m_wmgr.getConnectionInfo().getMacAddress();
		int ip_adress=m_wmgr.getConnectionInfo().getIpAddress();
		DEVICE_ANDROID_IP_ADDRESS=Integer.toString(ip_adress);

		if (TextUtils.isEmpty(regisId)) {
			regisId = registerToGCM();
			MainActivity.R_ID = regisId;
			Log.d("MainActivity", "GCM Registration : " + regisId);
		} else {
			//			Toast.makeText(getApplicationContext(),
			//					"Already Registered with GCM Server!",
			//					Toast.LENGTH_LONG).show();
			Log.d("MainActivity", "Already Registered with GCM Server");
		}

		if (TextUtils.isEmpty(regisId)) {
			//			Toast.makeText(getApplicationContext(), "Registration id is empty",
			//					Toast.LENGTH_LONG).show();
			Log.d("MainActivity", "Registration id is empty");
		} else {
			Intent i = new Intent(getApplicationContext(),
					MainActivity.class);
			i.putExtra("regId", regisId);
			Log.d("MainActivity",
					"before startActivity");
			startActivity(i);
			finish();
			Log.d("MainActivity", "after startActivity");
			Toast.makeText(getApplicationContext(),
					"Registration Completed !",
					Toast.LENGTH_LONG).show();
		}


		appUtil = new YodleeSecureServer();

		id = getIntent().getStringExtra("regisId");
		R_ID = regisId;
		Log.d("MainActivity", "regisId: " + regisId);

		final Context context = this;
		shareRegidTask = new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String result = appUtil.shareRegIdWithAppServer(context, regisId);
				return result;
			}

			@Override
			protected void onPostExecute(String result) {
				shareRegidTask = null;
				Toast.makeText(getApplicationContext(), result,
						Toast.LENGTH_LONG).show();
			}

		};
		shareRegidTask.execute(null, null, null);
		//dialog.dismiss();
	}

	public String registerToGCM() {

		gcm = GoogleCloudMessaging.getInstance(this);
		regisId = getRegistrationId(appContext);

		if (TextUtils.isEmpty(regisId)) {

			registerInBackground();

			Log.d("MainActivity",
					"registerToGCM - successfully registered with GCM server - regisId: "
							+ regisId);
		} else {
			Toast.makeText(getApplicationContext(),
					"RegistrationId already available. RegId: " + regisId,
					Toast.LENGTH_LONG).show();
			Log.d("MainActivity", "Registration id : "+regisId+" already available");

		}
		return regisId;
	}

	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		String registrationId = prefs.getString("regisId", "");
		if (registrationId.isEmpty()) {
			Log.i("regisId", "Registration not found.");
			return "";
		}
		int registeredVersion = prefs.getInt(APPLICATION_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i("Main Activity", "App version got changed.");
			return "";
		}
		return registrationId;
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			Log.d("RegisterActivity",
					"I never expected this! Going down, going down!" + e);
			throw new RuntimeException(e);
		}
	}

	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(appContext);
					}
					regisId = gcm.register(Config.GOOGLE_PROJECT_ID);
					Log.d("RegisterActivity", "registerInBackground - regId: "
							+ regisId);
					msg = "Device registered, registration ID=" + regisId;

					storeRegistrationId(appContext, regisId);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					Log.d("RegisterActivity", "Error: " + msg);
				}
				Log.d("RegisterActivity", "AsyncTask completed: " + msg);
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				//				Toast.makeText(getApplicationContext(),
				//						"Registered with GCM Server." + msg, Toast.LENGTH_LONG)
				//						.show();
				Log.d("onPostExecute","Registered with GCM Server." + msg);
			}
		}.execute(null, null, null);
	}

	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		int appVersion = getAppVersion(context);
		Log.i("regisId", "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(regisId, regId);
		editor.putInt(APPLICATION_VERSION, appVersion);
		editor.commit();
	}


	//	@Override
	//		protected void onResume() {
	//			// TODO Auto-generated method stub
	//			super.onResume();
	//			// Set an EditText view to get user input
	//			final EditText input = new EditText(this);
	//			new AlertDialog.Builder(MainActivity.this)
	//		    .setTitle("Refreshing account..")
	//		    .setMessage("Enter OTP")
	//		    .setView(input)
	//		    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	//		        public void onClick(DialogInterface dialog, int whichButton) {
	//		            Editable value = input.getText();
	//		        }
	//		    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	//		        public void onClick(DialogInterface dialog, int whichButton) {
	//		            // Do nothing.
	//		        }
	//		    }).show();
	//		}

}


