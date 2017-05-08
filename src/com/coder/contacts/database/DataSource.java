package com.coder.contacts.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DataSource {

	private static Connection connection = null;
	private static DataSource instance;
	private final static String path =System.getProperty("user.dir")
			+File.separator+"datasource"+File.separator+"tables"
			+File.separator+"contact.db";
	public static DataSource getInstance() {
		
		if(instance == null) {
			instance = new DataSource();
		}
		return instance;
	}
	
	public static Connection getConnection() {
		if(connection == null) {
			try {
					Class.forName("org.sqlite.JDBC");
					connection = DriverManager.getConnection("jdbc:sqlite:" + path);
			} catch (ClassNotFoundException e) {JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (SQLException e) {JOptionPane.showMessageDialog(null, "SQLException!");} 
		}
		
		return connection;
	}
}
