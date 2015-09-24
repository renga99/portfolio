/*
 * Copyright (c) 2015 lee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of lee, Inc.
 * Use is subject to license terms.
 */

package lee.yslHackathon.selfie;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import lee.ysl.api.apps.yaas.LoginApp;
import lee.ysl.api.beans.Transaction;
import lee.ysl.api.beans.Transactions;
import lee.ysl.api.io.HTTP;
import lee.ysl.api.parsers.GSONParser;

public class TransactionApp {

	private static final String fqcn = TransactionApp.class.getName();



	public List<Transaction> getTransactions() throws IOException,URISyntaxException {
		List<Transaction> transactionList=new ArrayList<Transaction>();
		String mn = "getTransactions()";
		System.out.println(fqcn + " :: " + mn);
		String transactionsURL = LoginApp.localURLVer1 + "transactions/v1/?container=bank";
		String jsonResponse = HTTP.doGet(transactionsURL,		LoginApp.loginTokens);
		System.out.println("See the response :"+jsonResponse);
		Transactions transactions =(Transactions) GSONParser.handleJson(jsonResponse, lee.ysl.api.beans.Transactions.class);
		transactionList=transactions.getTransactionList();
		return transactionList;
	}

	public  List<Transaction> getTransactions(String category) throws Exception
	{
		LoginApp.doLogin("yslsandbox10","lee@123","vpandi","lee@123");
		List<Transaction> trans = getTransactions();
		List<Transaction> resultList=new ArrayList<Transaction>();
		for(Transaction transaction:trans)
		{
			String txncategory=transaction.getCategory();
			if(txncategory.toUpperCase().trim().contains(category.toUpperCase().trim()))
			{
				resultList.add(transaction);
			}

		}
		return resultList;
	}

	//	public static void main(String a[]) throws Exception {
	//		List<Transaction> list=new ArrayList<Transaction>();
	//		System.out.println("TransactionApp - TEST - START");
	//		LoginApp.doLogin("yslsandbox10","lee@123","vpandi","lee@123");
	//		list =TransactionApp.getTransactions("unc");
	//      for(Transaction trans:list)
	//      {
	//    	  System.out.println("Desc:"+trans.getDescription());
	//    	  System.out.println("Amount:"+trans.getAmount());
	//    	  System.out.println("Container:"+trans.getCONTAINER());
	//    	  System.out.println("Category:"+trans.getCategory());
	//      }
}


