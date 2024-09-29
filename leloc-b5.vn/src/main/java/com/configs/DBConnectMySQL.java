package com.configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectMySQL {
	   private static String USERNAME = "root";
	   private static String PASSWORD = "leloc123";
	   private static String DRIVER = "com.mysql.cj.jdbc.Driver";
	   private static String URL = "jdbc:mysql://localhost:3306/users";

	   public static Connection getDatabaseConnection() throws SQLException, Exception {
	       Class.forName(DRIVER);
			return DriverManager.getConnection(URL,USERNAME,PASSWORD);
	   }
	   public static void main(String[] args)
	   {
		   new DBConnectMySQL();
		   try {
			System.out.println(DBConnectMySQL.getDatabaseConnection());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	   
	}
	
	

