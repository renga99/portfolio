/*
* Copyright (c) 2015 lee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of lee, Inc.
* Use is subject to license terms.
*/
package lee.ysl.api.beans;

public class LoginForm {

	private String id;
	private String help;
	private String forgetPasswordURL;
	private LoginFormRow[] row;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHelp() {
		return help;
	}
	public void setHelp(String help) {
		this.help = help;
	}
	public String getForgetPasswordURL() {
		return forgetPasswordURL;
	}
	public void setForgetPasswordURL(String forgetPasswordURL) {
		this.forgetPasswordURL = forgetPasswordURL;
	}
	public LoginFormRow[] getRow() {
		return row;
	}
	public void setRow(LoginFormRow[] row) {
		this.row = row;
	}
	
	
}
