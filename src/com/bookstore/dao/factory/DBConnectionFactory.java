package com.bookstore.dao.factory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBConnectionFactory {
	private static Connection connection = null;
	private DBConnectionFactory() {

	}
	public static Connection getConnection() {	
		//read the property file
		InputStream inputStream = DBConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = properties.getProperty("jdbc.url");
		String driverName = properties.getProperty("jdbc.driverName");
		String username = properties.getProperty("jdbc.username");
		String password = properties.getProperty("jdbc.password");
		try {
			Class.forName(driverName);
			System.out.println("Driver Loaded");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Connected");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
}
