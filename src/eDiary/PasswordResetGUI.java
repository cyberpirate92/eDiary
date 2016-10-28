package eDiary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

class PasswordResetGUI extends JFrame {
	
	private User currentUser;
	private JPanel centerPanel, bottomPanel;
	private JPasswordField passwordField, repasswordField;
	private JButton submitButton, cancelButton;
	private DatabaseUtil dbUtil;
	
	PasswordResetGUI(User user, DatabaseUtil dbUtil) {
		
		super("Reset Password : " + user.getUsername());
		currentUser = user;
		this.dbUtil = dbUtil;
		
		centerPanel = new JPanel();
		bottomPanel = new JPanel();
		
		passwordField = new JPasswordField();
		repasswordField = new JPasswordField();
		
		submitButton = new JButton("Change Password");
		cancelButton = new JButton("Cancel");
		
		registerEventListeners();
		initializeLayout();
	}
	
	private void initializeLayout() {
		
		centerPanel.setLayout(new GridLayout(2,2,5,5));
		centerPanel.add(ResourceUtil.getCenteredLabel("Password"));
		centerPanel.add(passwordField);
		centerPanel.add(ResourceUtil.getCenteredLabel("Re-Password"));
		centerPanel.add(repasswordField);
		
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		bottomPanel.add(submitButton);
		bottomPanel.add(cancelButton);
		
		this.setLayout(new BorderLayout());
		this.add(ResourceUtil.getCenteredLabel("Enter your new password in the fields below"), BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		this.setSize(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void registerEventListeners() {
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password, repassword;
				password = new String(passwordField.getPassword());
				repassword = new String(repasswordField.getPassword());
				if(password != null && password.length() >= 6 && password.equals(repassword)) {
					if(dbUtil != null) {
						try {
							currentUser.setPassword(password);
							dbUtil.updateUser(currentUser);
							JOptionPane.showMessageDialog(null, "Password changed successfully!");
						}
						catch(Exception exc) {
							exc.printStackTrace();
							JOptionPane.showMessageDialog(null, "Sorry, something went wrong, Please try again");
						}
						finally {
							PasswordResetGUI.this.navigateToLoginWindow();
						}
					}
					else {
						System.out.println("ERROR: dbUtil NULL");
						JOptionPane.showMessageDialog(null, "Sorry, something went wrong, Please try again");
						PasswordResetGUI.this.navigateToLoginWindow();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter a valid password of minimum length 6");
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PasswordResetGUI.this.navigateToLoginWindow();
			}
		});
	}
	
	private void navigateToLoginWindow() {
		PasswordResetGUI.this.dispose();
		new DiaryLoginGUI();
	}
}
