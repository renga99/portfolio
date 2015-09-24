package com.lee.ysl.selfi;

import java.util.ArrayList;
import java.util.HashMap;

import lee.ysl.api.beans.Transaction;
import lee.yslHackathon.selfie.TransactionApp;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class CardListActivity extends Activity {

    private static final String TAG = "CardListActivity";
    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;
    private ArrayList<Transaction> list;
    private static final HashMap<String,String> map = new HashMap<String,String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        listView = (ListView) findViewById(R.id.card_listView);

        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));

        cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);

        map.put("Key", "Gasoline"); map.put("phone", "Uncategorized"); map.put("remo", "Telephone"); map.put("renga", "Salary");
        map.put("food", "Uncategorized"); map.put("vetri", "Gasoline");map.put("land", "Telephone"); map.put("debit", "ATM");
        map.put("water", "Gasoline");map.put("dust", "Uncategorized");
        
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message").replaceAll(".jpg", "");
        String value = map.get(message);
        System.out.println("object is :"+message);
        if(value==null || value == "" || value == " "){
        	value = "Uncategorized";
        }
        
        
        try {
        	startProgress( value);
        	Thread.sleep(5000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(list == null || list.isEmpty()){
        	Card card = new Card("No transactions", "found");
            cardArrayAdapter.add(card);
            listView.setAdapter(cardArrayAdapter);
        }else{
        	
            for (Transaction trans : list) {
                Card card = new Card("Trans Date : "+trans.getDate()+"\n"+"Desc : "+trans.getDescription(), "Amount : "+trans.getAmount()+"Category : "+trans.getCategory());
                cardArrayAdapter.add(card);
            }
            listView.setAdapter(cardArrayAdapter);
        }
    }
    
    public void startProgress(final String message) {
        // do something long
        Runnable runnable = new Runnable() {
          @Override
          public void run() {
        	  try {
        		  TransactionApp app = new TransactionApp();
        		  list = (ArrayList<Transaction>) app.getTransactions(message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          }
        };
        new Thread(runnable).start();
      }
    
}
