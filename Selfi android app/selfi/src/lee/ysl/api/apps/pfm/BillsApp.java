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
import lee.ysl.api.beans.Bills;
import lee.ysl.api.io.HTTP;
import lee.ysl.api.parsers.GSONParser;
import lee.ysl.api.util.Console;

/**
 * 	The BillsApp class provides all bills for a member account, which are the aggregated from a Billing Account.
 * 
 *  
 *   Steps to Use this App: 
 *   i) doCoBrandLogin(String coBrandUserName, String coBrandPassword)
 *   ii) doMemberLogin(String userName, String userPassword)
 * @author vshetty
 *
 */
public class BillsApp 
{
	private static final String fqcn = BillsApp.class.getName();

	/**
	 * Return all the Bills of a member Account.
	 * 
	 * @return lee.ysl.api.beans.Bills.java
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static Bills getBills() throws IOException,
			URISyntaxException {
		String mn = "getBills()";
		System.out.println(fqcn + " :: " + mn);
		String BillsURL = LoginApp.localURLVer1 + "bills/v1/";
		String jsonResponse = HTTP.doGet(BillsURL,
				LoginApp.loginTokens);
		System.out.println(jsonResponse);
		Bills bills =(Bills) GSONParser.handleJson(
					jsonResponse, lee.ysl.api.beans.Bills.class);
		return bills;
	}
	
	public static void main(String a[]) throws IOException, URISyntaxException {
		System.out.println("BillsApp - TEST - START");
		LoginApp.doLogin("lee_10000004", "lee123", "ysluser2", "TEST@123");
		Console con = new Console();
		if (con != null) {
			String searchString = con
					.readLine("To Fetch All bills enter 1 : ");
		Bills bills = BillsApp.getBills();
		System.out.println(bills.toString());
		}
	}
}
