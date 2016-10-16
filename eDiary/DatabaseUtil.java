package eDiary;

import java.sql.*;


public class DatabaseUtil {
	public DatabaseUtil() {
		
	}
	
	public void displayAll() {
		
	}
	
	public static void debug() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "root", "fCTeh8hqcO*:");
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * from test");
			while(resultSet.next()) {
				System.out.println(resultSet.getInt(1));
			}
			con.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
