/*
 * Copyright (c) 2015 lee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of lee, Inc.
 * Use is subject to license terms.
 */

package lee.ysl.api.apps.aggregation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import lee.ysl.api.apps.yaas.LoginApp;
import lee.ysl.api.beans.Provider;
import lee.ysl.api.beans.Providers;
import lee.ysl.api.io.HTTP;
import lee.ysl.api.parsers.GSONParser;
import lee.ysl.api.util.Console;

public class SiteApp {
	private static final String fqcn = SiteApp.class.getName();

	public static void searchSite(String searchString) throws IOException,
			URISyntaxException {
		String mn = "searchSite(searchString " + searchString + " )";
		System.out.println(fqcn + " :: " + mn);
		String searchSiteURL = LoginApp.localURLVer1 + "provider/v1?name="
				+ searchString;
		String jsonResponse = HTTP.doGet(searchSiteURL, LoginApp.loginTokens);
		System.out.println(jsonResponse);
		List<Providers> providerList = (List<Providers>) GSONParser.handleJson(
				jsonResponse, lee.ysl.api.beans.Providers.class);
		for (Providers provider : providerList) {
			System.out.println("Provider Id >> " + provider.getProviderId()
					+ " >> Name  >>" + provider.getProviderName());

		}
	}

	public static Provider getSiteLoginForm(String sitId) throws IOException,
			URISyntaxException {
		String mn = "searchSite(site Id " + sitId + " )";
		System.out.println(fqcn + " :: " + mn);
		String getSiteURL = LoginApp.localURLVer1 + "provider/v1/" + sitId;
		String jsonResponse = HTTP.doGet(getSiteURL, LoginApp.loginTokens);
		Provider site = (Provider) GSONParser.handleJson(jsonResponse,
				lee.ysl.api.beans.Provider.class);
		System.out.println(site.getProvider()[0].getName());
		return site;
		/*site.getProvider()[0].getLoginForm().getRow()[0].getField()[0]
				.setValue("hello");
		site.toString();*/
	}

	public static void main(String[] args) throws IOException,
			URISyntaxException {
		System.out.println("SiteAPP - TEST - START");
		LoginApp.doLogin("lee_10000004", "lee123", "ysluser2", "TEST@123");
		Console con = new Console();
		if (con != null) {
			String searchString = con
					.readLine("Enter the site you want to search : ");
			searchSite(searchString);
		}
		if (con != null) {
			String site = con.readLine("Enter the site Id : ");
			getSiteLoginForm(site);
		}
	}
}