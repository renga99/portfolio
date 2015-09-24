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
import lee.ysl.api.beans.Holdings;
import lee.ysl.api.io.HTTP;
import lee.ysl.api.parsers.GSONParser;
import lee.ysl.api.util.Console;

/**
 *  The HoldingApp class provides holdings for a member account. 
 *  Holdings are Investment accounts which a member has aggregated using Aggregation Apps.
 * 
 *  
 *   Steps to Use this App: 
 *   i) doCoBrandLogin(String coBrandUserName, String coBrandPassword)
 *   ii) doMemberLogin(String userName, String userPassword)
 *   
 *   Browse all Accounts for member profile: 
 *   Accounts getHoldings() 
 *   
 * @author vshetty
 *
 */

public class HoldingApp {

	private static final String fqcn = HoldingApp.class.getName();

	public static Holdings getHoldings() throws IOException,
			URISyntaxException {
		String mn = "getHoldings()";
		System.out.println(fqcn + " :: " + mn);
		String holdingsURL = LoginApp.localURLVer1 + "holdings/v1/";
		String jsonResponse = HTTP.doGet(holdingsURL,
				LoginApp.loginTokens);
		System.out.println(jsonResponse);
		Holdings holdings =(Holdings) GSONParser.handleJson(
					jsonResponse, lee.ysl.api.beans.Holdings.class);
		return holdings;
	}

	public static void main(String a[]) throws IOException, URISyntaxException {
		System.out.println("HoldingApp - TEST - START");
		LoginApp.doLogin("lee_10000004", "lee123", "ysluser2", "TEST@123");
		Console con = new Console();
		if (con != null) {
			String searchString = con
					.readLine("To Fetch All Holdings enter 1 : ");
		Holdings holdings = HoldingApp.getHoldings();
		System.out.println(holdings.toString());
		}
	}
}
