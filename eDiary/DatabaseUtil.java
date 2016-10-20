package eDiary;

import java.sql.*;
import java.util.Calendar;

class DatabaseUtil {
	
	private final String DB_NAME;
	private final String DB_USERNAME;
	private final String DB_PASSWORD;
	private final String DB_HOST;
	private final int DB_PORT;
	
	private final String LOGIN_TABLE;
	private final String ENTRIES_TABLE;
	
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	
	public DatabaseUtil(String host, int port, String username, String password, String dbName) {
		this.DB_HOST = host;
		this.DB_PORT = port;
		this.DB_USERNAME = username;
		this.DB_PASSWORD = password;
		this.DB_NAME = dbName;
		this.LOGIN_TABLE = "users";
		this.ENTRIES_TABLE = "entries";
		this.resultSet = null;
	}
	
	private Connection getConnection() throws SQLException { 
		return DriverManager.getConnection(getConnectionString(), DB_USERNAME, DB_PASSWORD);
	}
	
	private String getConnectionString() {
		return "jdbc:mysql://"+DB_HOST+":"+DB_PORT+"/"+DB_NAME;
	}
	
	private void runSQLQuery(String sql) throws SQLException {
		try {
			closeConnection();
			Class.forName("com.mysql.jdbc.Driver");
			connection = this.getConnection();
			statement = connection.createStatement();
			if(sql.startsWith("SELECT") || sql.startsWith("select"))
				resultSet = statement.executeQuery(sql);
			else {
				statement.executeUpdate(sql);
				closeConnection();
			}
		}
		catch(Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
			closeConnection();
		}
	}
	
	private void closeConnection() throws SQLException{
		if(connection != null)
			connection.close();
		if(statement != null)
			statement.close();
		if(resultSet != null)
			resultSet.close();
	}
	
	boolean validateLogin(String username, String password) throws SQLException {
		boolean loginSuccess = false;
		runSQLQuery("SELECT COUNT(*) FROM users WHERE username='"+username+"' AND password='"+password+"'");
		if(resultSet != null) {
			while(resultSet.next())
				if(resultSet.getInt(1) > 0)
					loginSuccess = true;
			closeConnection();
		}
		return loginSuccess;
	}
	
	User getUser(String username) throws SQLException {
		User user = null;
		String sql = "SELECT username, password, question, answer, enckey FROM "+LOGIN_TABLE+" WHERE username='" + username + "'";
		runSQLQuery(sql);
		while(resultSet.next()) {
			user = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
		}
		return user;
	}
	
	private String getEncryptionKey(String username) throws SQLException {
		String encryptionKey = null;
		String sql = "SELECT enckey FROM "+LOGIN_TABLE+" WHERE username='"+username+"'";
		runSQLQuery(sql);
		if(resultSet != null) {
			while(resultSet.next()) {
				encryptionKey = resultSet.getString(1);
			}
			closeConnection();
			System.out.println("Encryption Key: " + encryptionKey);
		}
		return encryptionKey;
	}
	
	void saveJournalEntry(String username, String entry, Calendar entryDate) throws SQLException {
		
		// retrieving the encryption key of the user
		String encryptionKey = getEncryptionKey(username);
		
		
		if(encryptionKey != null) {
			// encrypting the journal entry
			String cipherText = CryptUtil.encrypt(encryptionKey, entry);
			long currentTime = System.currentTimeMillis(), entryTime = entryDate.getTimeInMillis();
			
			// pushing cipher to database
			String sql = "INSERT INTO "+ENTRIES_TABLE+" (username, entry, entry_date, last_edited) "
					+ "VALUES ('"+username+"', '"+cipherText+"', "+entryTime+", "+currentTime+")";
			runSQLQuery(sql);
			closeConnection();
		}
	}
	
	JournalEntry getJournalEntry(String username, Calendar entryDate) throws Exception {
		
		JournalEntry entry = null;
		
		// retrieving the encryption key of the user
		String encryptionKey = getEncryptionKey(username);
		
		if(encryptionKey != null) {
			String cipherText = null;
			Calendar lastEdited = Calendar.getInstance();
			
			// retrieving the cipher text from table
			String sql = "SELECT entry, last_edited FROM entries "
					+ "WHERE username='"+username+"' AND entry_date="+entryDate.getTimeInMillis();
			runSQLQuery(sql);
			if(resultSet != null) {
				while(resultSet.next()) {
					cipherText = resultSet.getString(1);
					lastEdited.setTimeInMillis(resultSet.getLong(2));
				}
				if(cipherText != null) { 
					String plainText = CryptUtil.decrypt(encryptionKey, cipherText);
					entry = new JournalEntry(plainText, entryDate, lastEdited);
					System.out.println("Plain Text : "+ plainText);
				}
			}
		}
		return entry;
	}
	
	void addNewUser(User newUser) throws SQLException {
		String u, p, q, a, k;
		
		u = newUser.getUsername();
		p = newUser.getPassword();
		q = newUser.getQuestion();
		a = newUser.getAnswer();
		k = newUser.getEncryptionKey();
		
		String sql = "INSERT INTO "+this.LOGIN_TABLE+
				" (username, password, question, answer, enckey) VALUES" +
				"('"+u+"', '"+p+"', '"+q+"', '"+a+"', '"+k+"')";
		
		runSQLQuery(sql);
	}
}
