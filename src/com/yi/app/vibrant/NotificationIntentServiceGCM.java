package com.yi.app.vibrant;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class NotificationIntentServiceGCM extends IntentService {

	public static final int NOTIFICATION_ID = 1;
	private NotificationManager notifyManager;
	NotificationCompat.Builder builder;
	public static final String BROADCAST = "PACKAGE_NAME.android.action.broadcast";

	public NotificationIntentServiceGCM() {
		super("IntentServiceForGCM");
	}

	public static final String TAG = "NotificationIntentServiceGCM";



	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification("Deleted messages on server: "
						+ extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {

				for (int i = 0; i < 3; i++) {
					Log.i(TAG,
							"Working... " + (i + 1) + "/5 @ "
									+ SystemClock.elapsedRealtime());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}

				}
				Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());

				sendNotification("Refreshing your yodlee account now..."
						+ extras.get(Config.MESSAGE_KEY));
				Log.i(TAG, "Received: " + extras.toString());
			}
		}
		BroadcastListenerGCM.completeWakefulIntent(intent);
	}

	private void sendNotification(String msg) {
		Log.d(TAG, "Preparing to send notification...: " + msg);
		notifyManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, OTP.class), 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.applauncher)
				.setContentTitle("Yodlee Scheduled Refresh Notification")
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentText(msg);

		mBuilder.setContentIntent(contentIntent);
		notifyManager.notify(NOTIFICATION_ID, mBuilder.build());
		Log.d(TAG, "Notification sent successfully.");
		Log.d(TAG, "Triggering main app");
//		Intent i = new Intent();
//        i.setClassName("com.yi.app.vibrant", "com.yi.app.vibrant.MainActivity");
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(i);

		if(msg == "rcode:1"){
		Intent i1 = new Intent();
		i1.setClassName("com.yi.app.vibrant", "com.yi.app.vibrant.SmsListener");
		sendBroadcast(i1);
		}
		else if(msg == "rcode:2"){
			Intent i1 = new Intent();
	        i1.setClassName("com.yi.app.vibrant", "com.yi.app.vibrant.OTP");
	        //i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        startActivity(i1);
		}

//		PendingIntent intent = PendingIntent.getActivity(this.getApplicationContext(),
//				0,new Intent("com.yi.app.vibrant.MainActivity").putExtra("fromnotification", true), 0);
        Log.d(TAG, "Triggered main app");
	}
}
