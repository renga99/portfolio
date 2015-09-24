/*
* Copyright (c) 2015 lee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of lee, Inc.
* Use is subject to license terms.
*/
package lee.ysl.api.beans;

public class UserContext {

	private String cobrandId;
	private String applicationId;
	private Session session;
	
	
	private class Session
	{
		private String externalSessionID;
		
		public String getExternalSessionID() {
			return externalSessionID;
		}
	}
	
	
	
	private Session getCobrandConversationCredentials() {
		return session;
	}


	public String getSession()
	{
		return this.getCobrandConversationCredentials().getExternalSessionID();
	}
	
	
	public String getCobrandId() 
	{
		return cobrandId;
	}


	
	public String getApplicationId() {
		return applicationId;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder(this.cobrandId+this.applicationId);
		System.out.println("CoBrand : toString() = " + sb);
		return new String(sb);
	}

}
