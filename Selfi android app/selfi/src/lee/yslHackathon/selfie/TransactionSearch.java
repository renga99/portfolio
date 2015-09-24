package lee.yslHackathon.selfie;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import lee.ysl.api.apps.aggregation.SiteApp;
import lee.ysl.api.apps.yaas.LoginApp;
import lee.ysl.api.beans.CobrandContext;
import lee.ysl.api.beans.Provider;
import lee.ysl.api.beans.Transactions;
import lee.ysl.api.beans.UserContext;
import lee.ysl.api.io.HTTP;
import lee.ysl.api.io.HTTPS;
import lee.ysl.api.parsers.GSONParser;
import lee.ysl.api.util.Console;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TransactionSearch {
	
	private static final String fqcn = TransactionSearch.class.getName();
	public static final String localURLVer1 = "https://stage.api.lee.com/ysl/private-yslsandbox10/user/v1/login"; //"http://192.168.57.9:8980/ysl/lee/";//"https://rest.developer.lee.com/services/srest/restserver/v1.0/";
	public static Map<String,String> loginTokens = new HashMap<String,String>();
	
	
	public static void doCoBrandLogin(String coBrandUserName, String coBrandPassword) throws IOException
	{
		String mn = "doCoBrandLogin(coBrandUserName " + coBrandUserName + ", coBrandPassword " + coBrandPassword + " )";
		System.out.println(fqcn + " :: " + mn);
		final String requestBody="cobrandLogin="+coBrandUserName+"&cobrandPassword="+coBrandPassword;
		String coBrandLoginURL = localURLVer1 + "cobrand/v1/login";
		String jsonResponse = HTTP.doPost(coBrandLoginURL, requestBody);
		CobrandContext coBrand = (CobrandContext) GSONParser.handleJson(jsonResponse, lee.ysl.api.beans.CobrandContext.class);
		System.out.println(coBrand.getSession());
		loginTokens.put("cobSession", coBrand.getSession());
	}

	
	public static void registerUser(String userJSon) throws IOException
	{/*
		
		
		HTTP.addHeaders("Authorization" , loginTokens.get("cobSession"));
		final String requestBody="coBrandSessionCredential="+ loginTokens.get("cobSession")+"&loginName="+userName+"&password="+pwd+"&email="+email+"&firstName="+firstName+"&lastName="+lastName+"&Address="+address
				+"&Street="+street+"&State="+state+"&City="+city+"&Country="+country+"&currency_format="+currency+"&date_format="+dateformate;
		String jsonResponse = HTTPS.doPostUser(userRegURL, loginTokens,userJSon );
		System.out.println(jsonResponse);
		CobrandContext coBrand = (CobrandContext) GSONParser.handleJson(jsonResponse, lee.ysl.api.beans.UserContext.class);
		System.out.println(coBrand.getSession());
		loginTokens.put("cobSession", coBrand.getSession());
	*/}
	
	public static void doMemberLogin(String userName, String userPassword) throws IOException
	{
		String mn = "doMemberLogin(userLogin=" +userName+ ", userPassword = " + userPassword + ", coBrandSessionCredential =" + loginTokens.get("cobSession") + " )";
		System.out.println(fqcn + " :: " + mn);
		final String requestBody="coBrandSessionCredential="+ loginTokens.get("cobSession")+"&userLogin=" + userName + "&userPassword="+ userPassword;
		String userLoginURL = localURLVer1 + "user/v1/login";
		//HTTP.addHeaders("Authorization" , loginTokens.get("cobSession"));
		String jsonResponse = HTTP.doPostUser(userLoginURL,loginTokens, requestBody);
		UserContext member = (UserContext) GSONParser.handleJson(jsonResponse, lee.ysl.api.beans.UserContext.class);
		System.out.println(member.getSession());
		loginTokens.put("userSession", member.getSession());
	}
	
	public static String addSiteAccount(Provider loginForm) throws IOException, URISyntaxException
	{
		String mn = "addSiteAccount( " + loginForm.toString()+ " )";
		System.out.println(fqcn + " :: " + mn);
		Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();
		String loginFormJson = gson.toJson(loginForm);
		String addSiteURL = localURLVer1 + "provider/v1/"+ loginForm.getProvider()[0].getId();
		String jsonResponse = HTTP.doPut(addSiteURL,loginFormJson,loginTokens);
		return jsonResponse;
		//System.out.println(jsonResponse);
	}
	
	
	public static UserDAO getInputForResgisteraion()
	{
		  Console con = new Console();
		  UserDAO user=new UserDAO();
		  String userName = con.readLine("Enter your userName: ");
		  user.setLoginName(userName);
          String userPassword = con.readLine("Enter " + userName + "'s password: ");
          user.setPassword(userPassword);
          return user;
	}
	
	public static Transactions getTransactions() throws IOException,URISyntaxException {
		String mn = "getTransactions()";
		System.out.println(fqcn + " :: " + mn);
		String transactionsURL = localURLVer1 + "transactions/v1/";
		String jsonResponse = HTTP.doGet(transactionsURL,loginTokens);
		System.out.println(jsonResponse);
		Transactions transactions =(Transactions) GSONParser.handleJson(jsonResponse, lee.ysl.api.beans.Transactions.class);
		return transactions;
		}
	
	public static void main(String a[]) throws Exception
	{
		Provider provider=null;
		UserDAO user=new UserDAO();
		user.setLoginName("vpandi");
		user.setPassword("lee@123");
		user.setEmail("vpandi@lee.com");
		user.setFirstName("Vetri");
		user.setLastName("Pandi");
		user.setAddress("Bangalore");
		user.setStreet("Bangalore");
		user.setState("KA");
		user.setCity("Bangalore");
		user.setCountry("India");
		user.setCurrency_format("USD");
		user.setDate_format("MM/dd/yyyy");
		
		doCoBrandLogin("yslsandbox10","lee@123");
		Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();
		String userJson = gson.toJson(user);
		registerUser(userJson);
		//doMemberLogin("vpandi","lee@123");
		//LoginApp.doLogin("yslsandbox10","lee@123", "vpandi", "lee@123");
		Console con = new Console();
		if (con != null) {
			String searchString = con
					.readLine("Enter the site you want to search : ");
			SiteApp.searchSite(searchString);;
		}
		if (con != null) {
			String site = con.readLine("Enter the site Id : ");
			provider = SiteApp.getSiteLoginForm(site);
		}
		if (con != null)
		{
			System.out.println(" Add Site Account ");
			String userName = con.readLine("Enter site userName : ");
			provider.getProvider()[0].getLoginForm().getRow()[0].getField()[0]
					.setValue(userName);
		}
		if (con != null)
		{
			String password = con.readLine("Enter site password : ");
			provider.getProvider()[0].getLoginForm().getRow()[1].getField()[0]
					.setValue(password);
			addSiteAccount(provider);
		}
		
		if (con != null) {
			String searchString = con.readLine("To Fetch all Transactions press 1 : ");
			Transactions transactions = getTransactions();
			System.out.println(transactions.toString());
		}

	}
}
