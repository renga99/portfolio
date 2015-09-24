/*
* Copyright (c) 2015 lee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of lee, Inc.
* Use is subject to license terms.
*/
package lee.ysl.api.io;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class HTTPS {

	private static final String fqcn = HTTP.class.getName();
	private static final String userAgent = "Mozilla/5.0";
	private static final String contentTypeURLENCODED="application/x-www-form-urlencoded";
	private static final String contentTypeJSON="application/json";
	
	public static String doPost(String url,String requestBody) throws IOException
	{
		String mn = "doIO(POST : " + url + ", " + requestBody+" )";
		System.out.println(fqcn + " :: " + mn);
		URL restURL = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("User-Agent", userAgent);
		conn.setRequestProperty("Content-Type", contentTypeURLENCODED);
		conn.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(requestBody);
		wr.flush();
		wr.close();
		int responseCode = conn.getResponseCode();
		System.out.println(fqcn + " :: " + mn + " : " + "Sending 'HTTP POST' request");
		System.out.println(fqcn + " :: " + mn + " : "+ "Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuilder jsonResponse = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			jsonResponse.append(inputLine);
		}
		in.close();
		System.out.println(fqcn + " :: " + mn + " : "+ jsonResponse.toString());
		return new String(jsonResponse);
	}
	
	public static String doPostUser(String url, Map<String,String> sessionTokens, String requestBody) throws IOException
	{
		String mn = "doIO(POST : " + url + ", " + requestBody+ "sessionTokens : " + sessionTokens  + " )";
		System.out.println(fqcn + " :: " + mn);
		URL restURL = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("User-Agent", userAgent);
		conn.setRequestProperty("Content-Type", contentTypeURLENCODED);
		conn.setRequestProperty("Authorization", sessionTokens.toString() );
		conn.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(requestBody);
		wr.flush();
		wr.close();
		int responseCode = conn.getResponseCode();
		System.out.println(fqcn + " :: " + mn + " : " + "Sending 'HTTP POST' request");
		System.out.println(fqcn + " :: " + mn + " : "+ "Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuilder jsonResponse = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			jsonResponse.append(inputLine);
		}
		in.close();
		System.out.println(fqcn + " :: " + mn + " : "+ jsonResponse.toString());
		return new String(jsonResponse);
	}
	
	public static String doGet(String url, Map<String,String> sessionTokens) throws IOException, URISyntaxException
	{
		String mn = "doIO(GET :" + url+ ", sessionTokens =  " + sessionTokens.toString() +" )";
		System.out.println(fqcn + " :: " + mn);
		URL myURL = new URL(url);
		System.out.println(fqcn + " :: " + mn + ": Request URL=" + url.toString());
		HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
		//conn.setRequestMethod("GET");
		conn.setRequestProperty("User-Agent", userAgent);
		//conn.setRequestProperty("Content-Type", contentTypeJSON);
		//conn.setRequestProperty("Accept",);
		conn.setRequestProperty("Authorization", sessionTokens.toString() );
		conn.setDoOutput(true);
		System.out.println(fqcn + " :: " + mn + " : " + "Sending 'HTTP GET' request");
		int responseCode = conn.getResponseCode();
		System.out.println(fqcn + " :: " + mn + " : "+ "Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(conn.getInputStream()));
		String inputLine;	
		StringBuilder jsonResponse = new StringBuilder();
		while ((inputLine = in.readLine()) != null) 
		{
			System.out.println(inputLine);
			jsonResponse.append(inputLine);
		}
		in.close();
		System.out.println(fqcn + " :: " + mn + " : "+ jsonResponse.toString());
		return new String(jsonResponse);
	}
}
