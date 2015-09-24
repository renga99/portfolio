/*
* Copyright (c) 2015 lee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of lee, Inc.
* Use is subject to license terms.
*/
package lee.ysl.api.beans;

public class LoginFormRow {

	private String id;
	private String label;
	private LoginField[] field;
	private String fieldRowChoice;
	private String form;
	private String help;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public LoginField[] getField() {
		return field;
	}
	public void setField(LoginField[] field) {
		this.field = field;
	}
	public String getFieldRowChoice() {
		return fieldRowChoice;
	}
	public void setFieldRowChoice(String fieldRowChoice) {
		this.fieldRowChoice = fieldRowChoice;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public String getHelp() {
		return help;
	}
	public void setHelp(String help) {
		this.help = help;
	}
	
	
}
