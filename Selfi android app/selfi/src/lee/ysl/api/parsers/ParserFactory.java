/*
* Copyright (c) 2015 lee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of lee, Inc.
* Use is subject to license terms.
*/
package lee.ysl.api.parsers;

public class ParserFactory 
{
	private static final String fqcn=ParserFactory.class.getName();
	
	public static Parser getParser(Class<?> T)
	{
		String mn = "getParser(" + T.getName()+ ")";
		System.out.println(fqcn + " :: " + mn);
		Parser parser = null;
		if(T.getCanonicalName().equals("lee.ysl.api.beans.CobrandContext"))
		{
			parser = (CobrandContextParser) new CobrandContextParser();
		}
		if(T.getCanonicalName().equals("lee.ysl.api.beans.UserContext"))
		{
			parser = (UserContextParser) new UserContextParser();
		}
		if(T.getCanonicalName().equals("lee.ysl.api.beans.Accounts"))
		{
			parser = (AccountsParser) new AccountsParser();
		}
		if(T.getCanonicalName().equals("lee.ysl.api.beans.Transactions"))
		{
			parser = (TransactionsParser) new TransactionsParser();
		}
		/*if(T.getCanonicalName().equals("lee.ysl.api.beans.Bills"))
		{
			parser = (BillsParser) new BillsParser();
		}*/
		if(T.getCanonicalName().equals("lee.ysl.api.beans.Holdings"))
		{
			parser = (HoldingsParser) new HoldingsParser();
		}
		if(T.getCanonicalName().equals("lee.ysl.api.beans.Providers"))
		{
			parser = (ProvidersParser) new ProvidersParser();
		}
		if(T.getCanonicalName().equals("lee.ysl.api.beans.Provider"))
		{
			parser = (SiteParser) new SiteParser();
		}
		System.out.println(fqcn + " :: " + mn+": Created Parser : " + parser.getClass().getName());
		return parser;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
