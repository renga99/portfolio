/*
 * Copyright (c) 2015 lee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of lee, Inc.
 * Use is subject to license terms.
 */
package lee.ysl.api.beans;

public class Bill {
	
	private String id;
	private String payee;
	private String dueAmount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPayee() {
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
	}
	public String getDueAmount() {
		return dueAmount;
	}
	public void setDueAmount(String dueAmount) {
		this.dueAmount = dueAmount;
	}
}
