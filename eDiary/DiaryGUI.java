package eDiary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

class DiaryGUI extends JFrame implements ActionListener {

	JPanel topPanel, centerPanel, bottomPanel;
	JTextField usernameField;
	JPasswordField passwordField;
	JButton submitBtn, forgotPasswordBtn, registerBtn;
	Font defaultFont;
	
	public DiaryGUI() {
		super("Diary");
		
		defaultFont = new Font("monaco", Font.PLAIN, 15);
		
		topPanel = new JPanel();
		centerPanel = new JPanel();
		bottomPanel = new JPanel();
		
		usernameField = new JTextField(10);
		passwordField = new JPasswordField(10);
		
		submitBtn = new JButton("Login");
		forgotPasswordBtn = new JButton("Forgot Password");
		registerBtn = new JButton("Register");
		
		registerEventListeners();
		setFonts();
		initializeLayout();
	}
	
	private void setFonts() {
		submitBtn.setFont(defaultFont);
		forgotPasswordBtn.setFont(defaultFont);
		registerBtn.setFont(defaultFont);
		
		usernameField.setFont(defaultFont);
		passwordField.setFont(defaultFont);
	}
	
	private void initializeLayout() {
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		//topPanel.add(new JLabel("Login"));
		
		JPanel formPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel dummyPanel = new JPanel();
		
		formPanel.setLayout(new GridLayout(2,2,5,5));
		formPanel.add(this.getCenteredLabel("username"));
		formPanel.add(usernameField);
		formPanel.add(this.getCenteredLabel("password"));
		formPanel.add(passwordField);
		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(submitBtn);
		buttonPanel.add(forgotPasswordBtn);
		buttonPanel.add(registerBtn);
		
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(buttonPanel, BorderLayout.SOUTH);
		centerPanel.add(formPanel, BorderLayout.CENTER);
		centerPanel.add(dummyPanel, BorderLayout.EAST);
		
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		this.setSize(500, 150);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private JLabel getCenteredLabel(String text) {
		JLabel label = new JLabel();
		label.setText(text);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(defaultFont);
		return label;
	}
	
	private void registerEventListeners() {
		submitBtn.addActionListener(this);
		forgotPasswordBtn.addActionListener(this);
		registerBtn.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton sourceBtn = (JButton) e.getSource();
		if(sourceBtn.equals(submitBtn)) {
			System.out.println("Submit Button");
		}
		else if(sourceBtn.equals(forgotPasswordBtn)) {
			System.out.println("Forgot Password Button");
		}
		else if(sourceBtn.equals(registerBtn)) {
			System.out.println("Register Button");
		}
	}

}
