/*
* Copyright (c) 2015 lee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of lee, Inc.
* Use is subject to license terms.
*/
package lee.ysl.api.io;

import java.util.HashMap;
import java.util.Map;

public class leeHTTPDefaults {

	private static Map<String,String> leeHTTPDefaults = new HashMap<String,String>();
	
	public static void initleeHTTPDefaultsMap()
	{
		leeHTTPDefaults.put("User-Agent", "Mozilla/5.0");
		leeHTTPDefaults.put("Content-Type","application/x-www-form-urlencoded");
		leeHTTPDefaults.put("Accept","application/json");
		
	}
	
	public static Map<String,String> getleeHTTPDefaultsMap()
	{
		return leeHTTPDefaults;
	}

}
