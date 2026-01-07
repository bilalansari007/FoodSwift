package com.diw.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	static final String URL="jdbc:mysql://localhost:3306/fooddeliveryapp";
	static final String USERNAME="root";
	static final String PASSWORD="root";
	
	
	
	public static final Connection connection()
	{
		
		Connection connection=null;
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			 connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return connection;
		
		
		
	}
	
	
}
