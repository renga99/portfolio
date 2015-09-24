package com.yi.app.vibrant;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class PostThread extends  Service {

	private Thread serviceThread = null;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		String message = intent.getStringExtra("message");
		//String result = postOTP("12345");
		//		if(result.contains("Otp upload")){
		//			return 0;
		//		}else{
		//			return 1;
		//		}
		if(!Config.REMOTE_SERVER_URL.equals("")){
			serviceThread = new ServiceThread();
			serviceThread.start();

		}
		return 0;

	}

	private class ServiceThread extends Thread {
		@Override
		public void run() {
			if(SmsListener.OTP == "" || SmsListener.OTP == null || SmsListener.OTP == " " || SmsListener.OTP.equals(null) || SmsListener.OTP.equals("") || SmsListener.OTP.equals(" ")){
				System.out.println("calling otp");
				uploadGPSData(OTP.OTP);
			}else{
				System.out.println("calling sms");
				uploadGPSData(SmsListener.OTP);
			}
		}
	};

	public void uploadGPSData(String message) {

		HttpResponse response = null;

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("otp", message));
		nameValuePairs.add(new BasicNameValuePair("rid", MainActivity.REG_ID));

		try {
			HttpParams httpParameters = new BasicHttpParams();
			int timeoutConnection = 3000000;
			HttpConnectionParams.setConnectionTimeout(httpParameters,timeoutConnection);
			int timeoutSocket = 5000000; // in milliseconds which is the timeout
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			HttpClient httpclient = new DefaultHttpClient(httpParameters);
			HttpPost method = new HttpPost(Config.REMOTE_SERVER_URL);
			// method.setHeader("Content-Type","text/xml");

			method.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = httpclient.execute(method);
			System.out.println("==response==" + response);
			if (response != null) {
				Log.i("login",""+ response.getEntity().getContentLength());
			} else {
				Log.i("login", "got a null response");
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Could not connect to server. Please Try again",
					Toast.LENGTH_SHORT).show();
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
	}
	//return response;



	public String postOTP(String message){
		String result = "";

		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("rid", MainActivity.R_ID.trim());
		paramsMap.put("otp", message);

		try {
			URL serverUrl = null;
			try {
				serverUrl = new URL(Config.REMOTE_SERVER_URL);
			} catch (MalformedURLException e) {
				Log.e("sms", "URL Connection Error: "
						+ Config.APP_SERVER_URL, e);
				result = "Invalid URL: " + Config.REMOTE_SERVER_URL;
			}

			StringBuilder postBody = new StringBuilder();
			Iterator<Entry<String, String>> iterator = paramsMap.entrySet()
					.iterator();

			while (iterator.hasNext()) {
				Entry<String, String> param = iterator.next();
				postBody.append(param.getKey()).append('=')
				.append(param.getValue());
				if (iterator.hasNext()) {
					postBody.append('&');
				}
			}
			String body = postBody.toString();
			byte[] bytes = body.getBytes();
			HttpURLConnection httpCon = null;
			try {
				httpCon = (HttpURLConnection) serverUrl.openConnection();
				httpCon.setDoOutput(true);
				httpCon.setUseCaches(false);
				httpCon.setFixedLengthStreamingMode(bytes.length);
				httpCon.setRequestMethod("POST");
				httpCon.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded;charset=UTF-8");
				httpCon.connect();
				OutputStream out = httpCon.getOutputStream();
				out.write(bytes);
				out.close();

				int status = httpCon.getResponseCode();
				if (status == 200) {
					result = "Otp shared with Application Server. RegId: "
							+ message;
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
