package com.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
		private static Connection conn;
		
		public static Connection getDBconn()
		{
			String usernameDb="root";
			String passwordDb="";
			String urlDb= "jdbc:mysql://localhost:3306/banking_db";
			String driverName="com.mysql.jdbc.Driver";
			
			try {
				Class.forName(driverName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			try {
				conn=DriverManager.getConnection(urlDb,usernameDb,passwordDb);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return conn;
		}
		
		public static void DBclose()
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
}
