package eDiary.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DiaryRegistrationGUI extends JFrame implements ActionListener, FocusListener {

	private JPanel topPanel, centerPanel, bottomPanel;
	private JTextField usernameField, questionField;
	private JPasswordField passwordField, answerField;
	private JButton registerButton, cancelButton;
	private JLabel descriptionLabel;
	private DatabaseUtil dbUtil;

	public DiaryRegistrationGUI() {

		super("Register new user");

		topPanel = new JPanel();
		centerPanel = new JPanel();
		bottomPanel = new JPanel();

		usernameField = new JTextField();
		questionField = new JTextField();

		passwordField = new JPasswordField();
		answerField = new JPasswordField();

		descriptionLabel = new JLabel("Fill the following fields to register");

		registerButton = new JButton("Register");
		cancelButton = new JButton("Cancel");

		dbUtil = new DatabaseUtil("localhost", 3306, "root", "", "ediary");

		registerEventListeners();
		setStyles();
		initializeLayout();
	}

	private void registerEventListeners() {
		registerButton.addActionListener(this);
		cancelButton.addActionListener(this);

		usernameField.addFocusListener(this);
		passwordField.addFocusListener(this);
		questionField.addFocusListener(this);
		answerField.addFocusListener(this);
	}

	private void setStyles() {
		usernameField.setFont(ResourceUtil.getDefaultFont());
		questionField.setFont(ResourceUtil.getDefaultFont());
		registerButton.setFont(ResourceUtil.getDefaultFont());
		cancelButton.setFont(ResourceUtil.getDefaultFont());

		usernameField.setBorder(ResourceUtil.getDefaultBorder());
		passwordField.setBorder(ResourceUtil.getDefaultBorder());
		questionField.setBorder(ResourceUtil.getDefaultBorder());
		answerField.setBorder(ResourceUtil.getDefaultBorder());
	}

	private void initializeLayout() {

		descriptionLabel.setFont(ResourceUtil.getDefaultFont());

		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(descriptionLabel);

		centerPanel.setLayout(new GridLayout(4,3,5,5));
		centerPanel.add(ResourceUtil.getCenteredLabel("Username"));
		centerPanel.add(usernameField);
		centerPanel.add(ResourceUtil.getCenteredLabel("Password"));
		centerPanel.add(passwordField);
		centerPanel.add(ResourceUtil.getCenteredLabel("Question"));
		centerPanel.add(questionField);
		centerPanel.add(ResourceUtil.getCenteredLabel("Answer"));
		centerPanel.add(answerField);

		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		bottomPanel.add(registerButton);
		bottomPanel.add(cancelButton);

		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);

		this.setSize(400,450);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton srcBtn = (JButton) e.getSource();
		if(srcBtn.equals(registerButton)) {

			String username, password, question, answer;
			Color color = ResourceUtil.getErrorBackground();
			ArrayList<String> errors = new ArrayList<>();

			username = usernameField.getText().trim();
			password = new String(passwordField.getPassword());
			question = questionField.getText().trim();
			answer = new String(answerField.getPassword()).trim();

			if(username == null || username.length() < 6) {
				usernameField.setBackground(color);
				errors.add("Username must be atleast 6 characters");
			}
			if(password == null || password.length() < 6) {
				passwordField.setBackground(color);
				errors.add("Password must be atleast 6 characters");
			}
			if(question == null || question.length() == 0) {
				questionField.setBackground(color);
				errors.add("Question cannot be blank");
			}
			if(answer == null || answer.length() == 0) {
				answerField.setBackground(color);
				errors.add("Answer cannot be blank");
			}

			if(errors.size() > 0) {
				String errorMessage = "Please correct the following errors, <br> <ol>";
				for(String s : errors)
					errorMessage += "<li>" + s + "</li>";
				descriptionLabel.setText("<html>"+errorMessage+"</ol></html>");
			}
			else {
				try {
					String encKey = generateEncryptionKey();
					User newUser = new User(username, password, question, answer, encKey);
					dbUtil.addNewUser(newUser);
					JOptionPane.showMessageDialog(null, "Account created, you can now login");
					this.dispose();
					new DiaryLoginGUI();
				}
				catch(Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error occured, please try again");
				}
			}
		}
		else if(srcBtn.equals(cancelButton)) {
			this.dispose();
			new DiaryLoginGUI();
		}
	}

	private String generateEncryptionKey() {
		// TODO: implement a random 4-letter string generation algorithm
		return "hill";  // for now :P
	}

	@Override
	public void focusGained(FocusEvent e) {
		JTextField field = (JTextField) e.getSource();
		if(field != null) {
			field.setBorder(ResourceUtil.getHighlightedBorder());
			field.setBackground(new Color(255,255,255,200));
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		JTextField field = (JTextField) e.getSource();
		if(field != null) {
			field.setBorder(ResourceUtil.getDefaultBorder());
			field.setBackground(Color.WHITE);
		}
	}
}
