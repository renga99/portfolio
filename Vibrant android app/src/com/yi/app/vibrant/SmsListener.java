package com.yi.app.vibrant;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View.OnCreateContextMenuListener;
import android.widget.Toast;


public class SmsListener extends BroadcastReceiver {

	final SmsManager smsManager = SmsManager.getDefault();
	AsyncTask<Void, Void, String> shareRegidTask;

	public static String REG_ID = "";
	public static String OTP = "";

	public void onReceive(Context context, Intent intent) {

		final Bundle bundle = intent.getExtras();

		try {

			if (bundle != null) {

				final Object[] pdusObj = (Object[]) bundle.get("pdus");

				for (int i = 0; i < pdusObj.length; i++) {

					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage.getDisplayOriginatingAddress();

					String senderNumber = phoneNumber;

					final String message = currentMessage.getDisplayMessageBody();

					String mobileClientXmlToSend="<MobileOtpClientRequest deviceId:"+MainActivity.DEVICE_IMEI+">";

					mobileClientXmlToSend=mobileClientXmlToSend+"<MobileOtpClientIpAdd>"+MainActivity.DEVICE_ANDROID_IP_ADDRESS+
							"</MobileOtpClientIpAdd>";

					mobileClientXmlToSend=mobileClientXmlToSend+"<MobileSmsRecived>"+
							message+"</MobileSmsRecived>";

					final String MobileClientXmlToSendFinal=mobileClientXmlToSend;

					Log.i("SmsReceiver", "senderNum: "+ senderNumber + "; message: " + message);

					if(message.toLowerCase().contains("otp")){
					int duration = Toast.LENGTH_LONG;
					Toast toast = Toast.makeText(context, "senderNum: "+ senderNumber + ", message: " + message+"IPADRESS"+MainActivity.DEVICE_ANDROID_IP_ADDRESS, duration);
					toast.show();
					OTP = message.substring(message.indexOf(':')+1);
					//postOTP(message.substring(message.indexOf(':')));

					Intent in = new Intent(context,PostThread.class);
					in.putExtra("message",message.substring(message.indexOf(':')+1));
					context.startService(in);




//					shareRegidTask = new AsyncTask<Void, Void, String>() {
//
//
//						@Override
//						protected String doInBackground(Void... params) {
//							String result = postOTP(message.substring(message.indexOf(':')));
//							return result;
//						}
//
//						@Override
//						protected void onPostExecute(String result) {
//							shareRegidTask = null;
////							Toast.makeText(context, result,
////								Toast.LENGTH_LONG).show();
//						}
//
//					};
//					shareRegidTask.execute(null, null, null);

					}
				}
			}

		} catch (Exception e) {
			Log.e("SmsReceiver", "Exception smsReceiver" +e);

		}
	}

	public String postOTP(String message){
		String result = "";
		try {
			URL serverUrl = null;
			try {
				serverUrl = new URL(Config.REMOTE_SERVER_URL);
			} catch (MalformedURLException e) {
				Log.e("sms", "URL Connection Error: "
						+ Config.APP_SERVER_URL, e);
				result = "Invalid URL: " + Config.APP_SERVER_URL;
			}

			HttpURLConnection httpCon = null;
			try {
				HttpURLConnection con = (HttpURLConnection) ( serverUrl).openConnection();
				con.setRequestMethod("POST");
				con.setDoInput(true);
				con.setDoOutput(true);
				con.connect();
				con.getOutputStream().write( ("otp=" + message).getBytes());
				con.getOutputStream().write(("rid="+MainActivity.R_ID.trim()).getBytes());
				int status = con.getResponseCode();
				if (status == 200) {
					result = "Otp upload to yodlee secure servers ";
				} else {
					result = "Post Failure." + " Status: " + status;
				}
			} finally {
				if (httpCon != null) {
					httpCon.disconnect();
				}
			}

		} catch (IOException e) {
			result = "Post Failure. Error in sharing with App Server.";
			Log.e("AppUtil", "Error in sharing with App Server: " + e);
		}
		return result;
	}




}
