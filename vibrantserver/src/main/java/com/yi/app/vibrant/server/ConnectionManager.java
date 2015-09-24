package com.yi.app.vibrant.server;

import java.io.IOException;


import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public final class ConnectionManager {

	public static DataSource getDbCon() throws IOException {

		//Properties prop = Utilities.getProperties();
		MysqlDataSource dataSource = null;


		dataSource = new MysqlDataSource();
		//dataSource.setURL("jdbc:mysql://"+prop.getProperty("remoteDB")+":3306/at");
		dataSource.setURL("jdbc:mysql://127.0.0.1:3306/vibrant");
		dataSource.setUser("root");
		dataSource.setPassword("admin");


		return dataSource;
	}

//	public static DataSource getOfflineDbCon() throws IOException {
//
//		//Properties prop = Utilities.getProperties();
//		MysqlDataSource dataSource = null;
//
//
//		dataSource = new MysqlDataSource();
//		//dataSource.setURL("jdbc:mysql://"+prop.getProperty("offlineDbIp")+":3306/bugs"); // offline
//		//dataSource.setURL("jdbc:mysql://"+prop.getProperty("onlineDbIp")+":3306/bugs"); // online
//		dataSource.setUser("readonly");
//		dataSource.setPassword("readonly");
//
//
//		return dataSource;
//	}


}
