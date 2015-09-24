/*
* Copyright (c) 2015 lee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of lee, Inc.
* Use is subject to license terms.
*/
package lee.ysl.api.parsers;
import java.io.IOException;
import lee.ysl.api.beans.Provider;
import com.google.gson.Gson;
public class SiteParser implements Parser
{
	private String fqcn = this.getClass().getName();
	public Provider parseJSON(String json) throws IOException 
	{
		String mn = "parseJSON(" + json + ")";
		System.out.println(fqcn + " :: " + mn);
		Gson gson = new Gson();
		return (Provider)gson.fromJson(json, Provider.class);	}
}
