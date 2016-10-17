package eDiary;

import java.sql.*;

class DatabaseUtil {
	
	private final String DB_NAME;
	private final String DB_USERNAME;
	private final String DB_PASSWORD;
	private final String DB_HOST;
	private final int DB_PORT;
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	
	public DatabaseUtil(String host, int port, String username, String password, String dbName) {
		this.DB_HOST = host;
		this.DB_PORT = port;
		this.DB_USERNAME = username;
		this.DB_PASSWORD = password;
		this.DB_NAME = dbName;
		this.resultSet = null;
	}
	
	private Connection getConnection() throws SQLException { 
		return DriverManager.getConnection(getConnectionString(), DB_USERNAME, DB_PASSWORD);
	}
	
	private String getConnectionString() {
		return "jdbc:mysql://"+DB_HOST+":"+DB_PORT+"/"+DB_NAME;
	}
	
	private ResultSet runSQL(String sql) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = this.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
		}
		catch(Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
		return resultSet;
	}
	
	private void closeConnection() throws SQLException{
		if(connection != null)
			connection.close();
		if(statement != null)
			statement.close();
		if(resultSet != null)
			resultSet.close();
	}
	
	public void debug() throws SQLException {
		ResultSet resultSet = runSQL("SELECT * from test");
		if(resultSet != null)
			while(resultSet.next()) {
				System.out.println(resultSet.getString(2));
		}
		closeConnection();
	}
}
