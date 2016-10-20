package eDiary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DiaryRegistrationGUI extends JFrame implements ActionListener, FocusListener {
	
	private JPanel topPanel, centerPanel, bottomPanel;
	private JTextField usernameField, questionField;
	private JPasswordField passwordField, answerField;
	private JButton registerButton, cancelButton;
	
	public DiaryRegistrationGUI() {
		
		topPanel = new JPanel();
		centerPanel = new JPanel();
		bottomPanel = new JPanel();
		
		usernameField = new JTextField();
		questionField = new JTextField();
		
		passwordField = new JPasswordField();
		answerField = new JPasswordField();
		
		registerButton = new JButton("Register");
		cancelButton = new JButton("Cancel");
		
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
		
		usernameField.setBorder(ResourceUtil.getDefaultBorder());
		passwordField.setBorder(ResourceUtil.getDefaultBorder());
		questionField.setBorder(ResourceUtil.getDefaultBorder());
		answerField.setBorder(ResourceUtil.getDefaultBorder());
	}
	
	private void initializeLayout() {
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(new JLabel("Fill the following fields to register"));
		
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
		
		this.setSize(400,250);
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
			
		}
		else if(srcBtn.equals(cancelButton)) {
			
		}
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
