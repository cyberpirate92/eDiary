package eDiary;

class User {
	private String username, password, question, answer, encryptionKey;
	
	User(String username, String password, String question, String answer, String encryptionKey) {
		super();
		this.username = username;
		this.password = password;
		this.question = question;
		this.answer = answer;
		this.encryptionKey = encryptionKey;
	}

	String getUsername() {
		return username;
	}

	void setUsername(String username) {
		this.username = username;
	}

	String getPassword() {
		return password;
	}

	void setPassword(String password) {
		this.password = password;
	}

	String getQuestion() {
		return question;
	}

	void setQuestion(String question) {
		this.question = question;
	}

	String getAnswer() {
		return answer;
	}

	void setAnswer(String answer) {
		this.answer = answer;
	}

	String getEncryptionKey() {
		return encryptionKey;
	}

	void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}
}
