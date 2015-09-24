/*
* Copyright (c) 2015 lee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of lee, Inc.
* Use is subject to license terms.
*/

package lee.ysl.api.apps.yaas;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lee.ysl.api.beans.CobrandContext;
import lee.ysl.api.beans.UserContext;
import lee.ysl.api.io.HTTP;
import lee.ysl.api.parsers.GSONParser;
import lee.ysl.api.util.Console;

public class LoginApp 
{
	private static final String fqcn = LoginApp.class.getName();
	public static final String localURLVer1 = "https://stage.api.lee.com/ysl/private-yslsandbox10/"; //"http://192.168.57.9:8980/ysl/lee/";//"https://rest.developer.lee.com/services/srest/restserver/v1.0/";
	public static Map<String,String> loginTokens = new HashMap<String,String>();
	
	public static void doCoBrandLogin(String coBrandUserName, String coBrandPassword) throws IOException
	{
		String mn = "doCoBrandLogin(coBrandUserName " + coBrandUserName + ", coBrandPassword " + coBrandPassword + " )";
		System.out.println(fqcn + " :: " + mn);
		final String requestBody="cobrandLogin="+coBrandUserName+"&cobrandPassword="+coBrandPassword;
		String coBrandLoginURL = localURLVer1 + "cobrand/v1/login";
		//HTTP.createConnection(coBrandLoginURL);
		String jsonResponse = HTTP.doPost(coBrandLoginURL, requestBody);
		CobrandContext coBrand = (CobrandContext) GSONParser.handleJson(jsonResponse, lee.ysl.api.beans.CobrandContext.class);
		System.out.println(coBrand.getSession());
		loginTokens.put("cobSession", coBrand.getSession());
	}
	
	public static void doMemberLogin(String userName, String userPassword) throws IOException
	{
		String mn = "doMemberLogin(userLogin=" +userName+ ", userPassword = " + userPassword + ", coBrandSessionCredential =" + loginTokens.get("cobSession") + " )";
		System.out.println(fqcn + " :: " + mn);
		final String requestBody="coBrandSessionCredential="+ loginTokens.get("cobSession")+"&userLogin=" + userName + "&userPassword="+ userPassword;
		String userLoginURL = localURLVer1 + "user/v1/login";
		//HTTP.addHeaders("Authorization" , loginTokens.get("cobSession"));
		String jsonResponse = HTTP.doPostUser(userLoginURL,loginTokens, requestBody);
		UserContext member = (UserContext) GSONParser.handleJson(jsonResponse, lee.ysl.api.beans.UserContext.class);
		System.out.println(member.getSession());
		loginTokens.put("userSession", member.getSession());
	}
	
	public static void doLogin(String coBrandUserName, String coBrandPassword,String userName, String userPassword) throws IOException
	{
		doCoBrandLogin(coBrandUserName,coBrandPassword);
		doMemberLogin(userName,userPassword);
	}
    public static void main( String[] args ) throws IOException
    {
        System.out.println( "LoginAPP - TEST - START" );
      int MAX_LOGINS = 1;
        Console con = new Console();
        boolean auth = false;
        if (con != null)
        {
          int count = 0;
          do
          {
            /*String coBrandUserName = con.readLine("Enter your coBrandUserName: ");
            String coBrandPassword = con.readLine("Enter " + coBrandUserName + "'s password: ");*/
            LoginApp.doCoBrandLogin("yslsandbox10","lee@123");
            String userName = con.readLine("Enter your userName: ");
            String userPassword = con.readLine("Enter " + userName + "'s password: ");
            LoginApp.doMemberLogin(userName,userPassword);
            //String userName = con.readLine("Enter your userName: ");
            //String userPassword = con.readLine("Enter " + userName + "'s password");
            //LoginApp.doLogin(coBrandUserName, new String(coBrandPassword), coBrandUserName, new String(userPassword));
            //Arrays.fill(pwd, ' '); // delete password from memory
            //con.writer().write("\n\n");  // output a couple of newlines
          } while (!auth && ++count < MAX_LOGINS);
        }
    		//LoginApp.doLogin("lee_10000004", "lee123", "ysltest15", "TEST@123");
			System.out.println(LoginApp.loginTokens.toString());
        }
}
