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
import lee.ysl.api.beans.Transactions;
import lee.ysl.api.io.HTTP;
import lee.ysl.api.parsers.GSONParser;
import lee.ysl.api.util.Console;

public class TransactionApp {

	private static final String fqcn = TransactionApp.class.getName();
	
	public static Transactions getTransactions() throws IOException,URISyntaxException {
		String mn = "getTransactions()";
		System.out.println(fqcn + " :: " + mn);
		String transactionsURL = LoginApp.localURLVer1 + "transactions/v1/";
		String jsonResponse = HTTP.doGet(transactionsURL,
		LoginApp.loginTokens);
		System.out.println("See the response :"+jsonResponse);
		//Transactions transactions =(Transactions) GSONParser.handleJson(jsonResponse, lee.ysl.api.beans.Transactions.class);
		return null;
		}

	public static void main(String a[]) throws IOException, URISyntaxException {
		System.out.println("TransactionApp - TEST - START");
		LoginApp.doLogin("yslsandbox10","lee@123","vpandi","lee@123");
		Console con = new Console();
		if (con != null) {
			String searchString = con.readLine("To Fetch all Transactions press 1 : ");
		Transactions transactions = TransactionApp.getTransactions();
		System.out.println("Done");
		}
	}
}
