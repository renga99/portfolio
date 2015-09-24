/*
* Copyright (c) 2015 lee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of lee, Inc.
* Use is subject to license terms.
*/
package lee.ysl.api.beans;

import java.util.ArrayList;
import java.util.List;




public class Transactions {

	Transaction[] transaction;

	public Transaction[] getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction[] transaction) {
		this.transaction = transaction;
	}
/*
	public String toString()
	{
		System.out.println(" I am here");
		StringBuilder transactions = new StringBuilder("");
		for (int i = 0; i<transaction.length; i++)
		{
			transactions.append(transaction[i].getId()).append("=>").append("amount=").append(transaction[i].getAmount()).append("=>" + transaction[i].getBaseType()).append("\n");
		}
		return transactions.toString();
	}*/
	
	public  List<Transaction> getTransactionList()
	{
		List<Transaction> transList=new ArrayList<Transaction>();
		for (int i = 0; i<transaction.length; i++)
		{
			Transaction transactionobj =new Transaction();
			String id=transaction[i].getId();
			String container=transaction[i].getCONTAINER();
			String amount=transaction[i].getAmount();
			String baseType=transaction[i].getBaseType();
			String category=transaction[i].getCategory();
			String desc=transaction[i].getDescription();
			String orgDesc=transaction[i].getOriginalDescription();
			String isManual=transaction[i].getManual();
			String transDate=transaction[i].getDate();
			if(null!=id)
				transactionobj.setId(id);
			if(null!=id)
				transactionobj.setCONTAINER(container);
			if(null!=id)
				transactionobj.setAmount(amount);
			if(null!=id)
				transactionobj.setBaseType(baseType);
			if(null!=id)
				transactionobj.setCategory(category);
			if(null!=id)
				transactionobj.setDescription(desc);
			if(null!=id)
				transactionobj.setOriginalDescription(orgDesc);
			if(null!=id)
				transactionobj.setDate(transDate);
			transList.add(transactionobj);

		}
		return transList;
	}
	
}
