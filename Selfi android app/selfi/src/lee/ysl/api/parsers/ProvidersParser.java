/*
* Copyright (c) 2015 lee, Inc. All Rights Reserved.
* This software is the confidential and proprietary information of lee, Inc.
* Use is subject to license terms.
*/package lee.ysl.api.parsers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lee.ysl.api.beans.Providers;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
public class ProvidersParser implements Parser
{
	private String fqcn = this.getClass().getName();
	public List<Providers> parseJSON(String json) throws IOException 
	{
		String mn = "parseJSON(" + json + ")";
		System.out.println(fqcn + " :: " + mn);
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
	    JsonArray jArray = parser.parse(json).getAsJsonArray();
	    ArrayList<Providers> lcs = new ArrayList<Providers>();
	    for(JsonElement obj : jArray )
	    {
	        Providers cse = gson.fromJson( obj , Providers.class);
	        lcs.add(cse);
	    }
		return lcs;
	}
}
