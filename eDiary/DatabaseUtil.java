package eDiary;

import java.sql.*;

public class DatabaseUtil {
	
	private final String DB_NAME;
	private final String DB_USERNAME;
	private final String DB_PASSWORD;
	private final String DB_HOST;
	private final int DB_PORT;
	
	public DatabaseUtil(String host, int port, String username, String password, String dbName) {
		this.DB_HOST = host;
		this.DB_PORT = port;
		this.DB_USERNAME = username;
		this.DB_PASSWORD = password;
		this.DB_NAME = dbName;
	}
	
	private Connection getConnection() throws SQLException { 
		return DriverManager.getConnection(getConnectionString(), DB_USERNAME, DB_PASSWORD);
	}
	
	private String getConnectionString() {
		return "jdbc:mysql://"+DB_HOST+":"+DB_PORT+"/"+DB_NAME;
	}
	
	private ResultSet runSQL(String sql) {
		ResultSet resultSet = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = this.getConnection();
			Statement statement = con.createStatement();
			resultSet = statement.executeQuery(sql);
			con.close();
		}
		catch(Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
		return resultSet;
	}
	
	public void debug() throws SQLException {
		ResultSet resultSet = runSQL("SELECT * from test");
		if(resultSet != null)
			while(resultSet.next()) {
				System.out.println(resultSet.getInt(1));
		}
	}
}
