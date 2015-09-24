/*
* Copyright (c) 2015 lee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of lee, Inc.
* Use is subject to license terms.
*/
package lee.ysl.api.beans;

public class Holding {
	
	 private String costBasis;
	 private String cusipNumber;
	 private String dailyChange;
	 private String description;
	 private String exchange;
	 private String holdingType;
	 private String isin;
	 private String price;
	 private String quantity;
	 private String symbol;
	 private String value;
	public String getCostBasis() {
		return costBasis;
	}
	public void setCostBasis(String costBasis) {
		this.costBasis = costBasis;
	}
	public String getCusipNumber() {
		return cusipNumber;
	}
	public void setCusipNumber(String cusipNumber) {
		this.cusipNumber = cusipNumber;
	}
	public String getDailyChange() {
		return dailyChange;
	}
	public void setDailyChange(String dailyChange) {
		this.dailyChange = dailyChange;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public String getHoldingType() {
		return holdingType;
	}
	public void setHoldingType(String holdingType) {
		this.holdingType = holdingType;
	}
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
