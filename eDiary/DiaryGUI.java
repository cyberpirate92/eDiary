package eDiary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DiaryGUI extends JFrame implements ActionListener {

	JPanel topPanel, centerPanel, bottomPanel;
	JTextField usernameField;
	JPasswordField passwordField;
	JButton submitBtn, forgotPasswordBtn;
	
	public DiaryGUI() {
		topPanel = new JPanel();
		centerPanel = new JPanel();
		bottomPanel = new JPanel();
		
		usernameField = new JTextField(10);
		passwordField = new JPasswordField(10);
		
		submitBtn = new JButton("LOGIN");
		forgotPasswordBtn = new JButton("FORGOT PASSWORD");
		
		initializeLayout();
		registerEventListeners();
	}
	
	private void initializeLayout() {
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(new JLabel("Login"));
		
		JPanel formPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		formPanel.setLayout(new GridLayout(2,2,5,5));
		formPanel.add(new JLabel("username"));
		formPanel.add(usernameField);
		formPanel.add(new JLabel("password"));
		formPanel.add(passwordField);
		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.add(submitBtn);
		buttonPanel.add(forgotPasswordBtn);
		
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(buttonPanel, BorderLayout.SOUTH);
		centerPanel.add(formPanel, BorderLayout.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
	}
	
	private void registerEventListeners() {
		submitBtn.addActionListener(this);
		forgotPasswordBtn.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton sourceBtn = (JButton) e.getSource();
		if(sourceBtn.equals(submitBtn)) {
			
		}
		else if(sourceBtn.equals(forgotPasswordBtn)) {
			
		}

	}

}
