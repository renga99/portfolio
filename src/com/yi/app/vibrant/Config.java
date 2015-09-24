package com.yi.app.vibrant;


public interface Config {

	// Remote Server Url
	 //static final String APP_SERVER_URL = "http://www.rengaindustries.com/VibrantServer/NotifyRemoteServer?shareRegId=1";

	// internal server
	// static final String APP_SERVER_URL = "http://IN-L0761.corp.yodlee.com:8080/VibrantServer/NotifyRemoteServer?shareRegId=1";
	 //static final String APP_SERVER_URL = "http://192.168.7.134:8080/GCM-App-Server/GCMNotification?shareRegId=1";

	static final String APP_SERVER_URL = "http://192.168.63.206:8080/vibrantserver-1.0/GCMNotification?shareRegId=1";
	static final String REMOTE_SERVER_URL = "http://192.168.63.206:8080/vibrantserver-1.0/UploadServlet?shareRegId=1";

	//static final String REMOTE_SERVER_URL = "https://vibrant.ngrok.com/vibrantserver-1.0/UploadServlet?shareRegId=1";
	//static final String APP_SERVER_URL = "https://vibrant.ngrok.com/vibrantserver-1.0/GCMNotification?shareRegId=1";
	// Google Project Number
	static final String GOOGLE_PROJECT_ID = "65931490685";
	static final String MESSAGE_KEY = "message";

}

