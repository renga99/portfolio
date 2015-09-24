/*
* Copyright (c) 2015 lee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of lee, Inc.
* Use is subject to license terms.
*/
package lee.ysl.api.parsers;

import java.io.IOException;


public class GSONParser 
{
	private static final String fqcn = GSONParser.class.getName();
	
	public static <T> Object handleJson(String json, Class<?> T) throws IOException
	{
		String mn = "handleJson(" + json + ", " + T.getCanonicalName()+" )";
		System.out.println(fqcn + " :: " + mn );
		return ParserFactory.getParser(T).parseJSON(json);
	}
	
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

	}

}
