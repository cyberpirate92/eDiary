package eDiary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class PasswordHelperGUI extends JFrame {
	
	private JPanel centerPanel, bottomPanel;
	private JLabel questionLabel;
	private JTextField answerField;
	private JButton resetButton, cancelButton;
	private User currentUser;
	
	PasswordHelperGUI(User user) {
		super("Password Reset");
		
		currentUser = user;
		
		centerPanel = new JPanel();
		bottomPanel = new JPanel();
		
		questionLabel = new JLabel(currentUser.getQuestion());
		answerField = new JTextField();
		
		resetButton = new JButton("Reset Password");
		cancelButton = new JButton("Cancel");
		
		setFonts();
		registerEventListeners();
		initializeLayout();
	}
	
	private void setFonts() {
		questionLabel.setFont(ResourceUtil.getDefaultFont());
		answerField.setFont(ResourceUtil.getDefaultFont());
		resetButton.setFont(ResourceUtil.getDefaultFont());
		cancelButton.setFont(ResourceUtil.getDefaultFont());
	}
	
	private void initializeLayout() {
		
		centerPanel.setLayout(new GridLayout(2,1,5,5));
		centerPanel.add(questionLabel);
		centerPanel.add(answerField);
		
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		bottomPanel.add(resetButton);
		bottomPanel.add(cancelButton);
		
		this.setLayout(new BorderLayout());
		this.add(ResourceUtil.getCenteredLabel("Please answer your secret question to continue"), BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		this.setSize(300,300);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void registerEventListeners() {
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String answer = answerField.getText();
				if(answer.equals(currentUser.getAnswer())) {
					//TODO: instantiate password reset class
					System.out.println("Not yet implemented!");
				}
				else
					JOptionPane.showMessageDialog(null, "Sorry, but that's not the right answer!\nPlease try again");
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PasswordHelperGUI.this.dispose();
				new DiaryLoginGUI();
			}
		});
	}
}
