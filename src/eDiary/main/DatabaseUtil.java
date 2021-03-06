package eDiary.main;

import java.sql.*;
import java.util.Calendar;

public class DatabaseUtil {

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
			System.out.println("SQL> " + sql);
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

	public boolean validateLogin(String username, String password) throws SQLException {
		boolean loginSuccess = false;
		runSQLQuery("SELECT COUNT(*) FROM users WHERE username='"+username+"' AND password='"+CryptUtil.sha256(password)+"'");
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

	public void saveJournalEntry(String username, String entry, Calendar entryDate) throws SQLException {

		// retrieving the encryption key of the user
		String encryptionKey = getEncryptionKey(username);

		// checking if a entry exists for this user on the same day
		boolean entryExists = false;
		String sql = "SELECT COUNT(*) FROM " + this.ENTRIES_TABLE +
				" WHERE username = '"+username+"' AND entry_day='"+
				this.getSQLDateString(entryDate)+"'";
		runSQLQuery(sql);
		if(resultSet != null) {
			if(resultSet.next()) {
				entryExists = resultSet.getInt(1) != 0;
			}
		}
		closeConnection();
		if(encryptionKey != null) {
			// encrypting the journal entry
			String cipherText = CryptUtil.encrypt(encryptionKey, entry);
			long currentTime = System.currentTimeMillis(), entryTime = entryDate.getTimeInMillis();

			// pushing cipher to database
			String entryDay = this.getSQLDateString(entryDate);
			if(!entryExists) {
				sql = "INSERT INTO "+ENTRIES_TABLE+" (username, entry, entry_date, last_edited, entry_day) "
						+ "VALUES ('"+username+"', '"+cipherText+"', "+entryTime+", "+currentTime+", '"+entryDay+"')";
			}
			else {
				sql = "UPDATE "+ENTRIES_TABLE+
						" SET entry='"+cipherText+"', last_edited="+currentTime+
						" WHERE entry_day='"+entryDay+"' AND username='"+username+"'";
			}
			runSQLQuery(sql);
			closeConnection();
		}
	}

	public JournalEntry getJournalEntry(String username, Calendar entryDate) throws Exception {

		JournalEntry entry = null;

		// retrieving the encryption key of the user
		String encryptionKey = getEncryptionKey(username);

		if(encryptionKey != null) {
			String cipherText = null;
			Calendar lastEdited = Calendar.getInstance();

			// retrieving the cipher text from table
			String entryDay = entryDate.get(Calendar.YEAR) + "-" + entryDate.get(Calendar.MONTH) + "-" + entryDate.get(Calendar.DAY_OF_MONTH);
			String sql = "SELECT entry, last_edited FROM entries "
					+ "WHERE username='"+username+"' AND entry_day='"+entryDay+"'";
			runSQLQuery(sql);
			if(resultSet != null) {
				while(resultSet.next()) {
					cipherText = resultSet.getString(1);
					lastEdited.setTimeInMillis(resultSet.getLong(2));

					System.out.println("Cipher Text: "+cipherText);
				}
				if(cipherText != null) {
					String plainText = CryptUtil.decrypt(encryptionKey, cipherText);
					entry = new JournalEntry(plainText, entryDate, lastEdited);
					System.out.println("Plain Text : "+ plainText);
				}
			}
			else {
				System.out.println("Result set null");
			}
		}
		return entry;
	}

	void addNewUser(User newUser) throws SQLException {
		String u, p, q, a, k;

		u = newUser.getUsername();
		p = CryptUtil.sha256(newUser.getPassword());
		q = newUser.getQuestion();
		a = newUser.getAnswer();
		k = newUser.getEncryptionKey();

		String sql = "INSERT INTO "+this.LOGIN_TABLE+
				" (username, password, question, answer, enckey) VALUES" +
				"('"+u+"', '"+p+"', '"+q+"', '"+a+"', '"+k+"')";

		runSQLQuery(sql);
	}

	boolean usernameExists(String username) throws Exception {
		String sql = "SELECT * FROM "+this.LOGIN_TABLE+" WHERE username='"+username+"'";
		boolean exists = false;
		this.runSQLQuery(sql);
		if(this.resultSet != null && resultSet.next()) {
				exists = true;
		}
		closeConnection();
		return exists;
	}

	void updateUser(User user) throws SQLException {
		String u, p, q, a, k;

		u = user.getUsername();
		p = CryptUtil.sha256(user.getPassword());
		q = user.getQuestion();
		a = user.getAnswer();
		k = user.getEncryptionKey();

		String sql = "UPDATE " + this.LOGIN_TABLE +
				" SET password = '" + p + "', question = '" + q + "', answer = '" + a + "', enckey = '" + k +
				"' WHERE username = '" + u + "'";

		runSQLQuery(sql);
	}

	private String getSQLDateString(Calendar c) {
		return c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH);
	}
}
