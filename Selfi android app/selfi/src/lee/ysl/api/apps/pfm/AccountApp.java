/*
* Copyright (c) 2015 lee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of lee, Inc.
* Use is subject to license terms.
*/
package lee.ysl.api.apps.pfm;

import java.io.IOException;
import java.net.URISyntaxException;

import lee.ysl.api.apps.yaas.LoginApp;
import lee.ysl.api.beans.Accounts;
import lee.ysl.api.io.HTTP;
import lee.ysl.api.parsers.GSONParser;
import lee.ysl.api.util.Console;


/**
 *  The AccountApp class provides authentication and authorization services. 
 * 
 *  
 *   Steps to Use this App: 
 *   i) doCoBrandLogin(String coBrandUserName, String coBrandPassword)
 *   ii) doMemberLogin(String userName, String userPassword)
 *   
 *   Browse all Accounts for member profile: 
 *   Accounts getAccounts() 
 *   
 * @author vshetty
 *
 */

public class AccountApp {
	private static final String fqcn = AccountApp.class.getName();

	public static Accounts getAccounts() throws IOException,
			URISyntaxException {
		String mn = "getAccounts()";
		System.out.println(fqcn + " :: " + mn);
		String accountSummaryURL = LoginApp.localURLVer1 + "accounts/v1/";
		String jsonResponse = HTTP.doGet(accountSummaryURL,
				LoginApp.loginTokens);
		System.out.println(jsonResponse);
		Accounts accounts =(Accounts) GSONParser.handleJson(
					jsonResponse, lee.ysl.api.beans.Accounts.class);
		return accounts;
	}

	public static void main(String a[]) throws IOException, URISyntaxException {
		System.out.println("AccountApp - TEST - START");
		LoginApp.doLogin("yslsandbox10","lee@123", "vpandi", "lee@123");
		Console con = new Console();
		if (con != null) {
			String searchString = con
					.readLine("To Fetch All accounts enter 1 : ");
		Accounts accounts = AccountApp.getAccounts();
		System.out.println(accounts.toString());
		}
	}
}
