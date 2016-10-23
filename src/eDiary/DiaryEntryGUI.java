package eDiary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DiaryEntryGUI extends JFrame implements ActionListener {
	
	private JPanel leftPanel, centerPanel, rightPanel;
	private User currentUser;
	private JTextArea entryArea;
	private DatabaseUtil dbUtil;
	private JButton saveButton, clearButton;
	private JButton boldButton, italicsButton, underlineButton;
	
	DiaryEntryGUI(User user) {
		
		super(user.getUsername() + "'s Diary");
		
		currentUser = user;
		
		leftPanel = new JPanel();
		centerPanel = new JPanel();
		rightPanel = new JPanel();
		dbUtil = new DatabaseUtil("localhost", 3306, "root", "", "ediary");
		entryArea = new JTextArea();
		saveButton = new JButton("save");
		clearButton = new JButton("clear");
		boldButton = new JButton("<html><b>B</b></html>");
		italicsButton = new JButton("<html><i>I</i></html>");
		underlineButton = new JButton("<html><u>U</u></html>");
		
		registerEventListeners();
		initializeLayout();
		
		try {
			JournalEntry entry = dbUtil.getJournalEntry(currentUser.getUsername(), Calendar.getInstance());
			if(entry != null)
				entryArea.setText(entry.getEntry());
			else 
				System.out.println("No journal entry found for today's date");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void registerEventListeners() {
		
		saveButton.addActionListener(this);
		clearButton.addActionListener(this);
		boldButton.addActionListener(this);
		italicsButton.addActionListener(this);
		underlineButton.addActionListener(this);
	}
	
	private void initializeLayout() {
		
		JPanel buttonPanel = new JPanel();
		JPanel formattingPanel = new JPanel();
		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(saveButton);
		buttonPanel.add(clearButton);
		
		formattingPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		formattingPanel.add(boldButton);
		formattingPanel.add(italicsButton);
		formattingPanel.add(underlineButton);
		
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(formattingPanel, BorderLayout.NORTH);
		centerPanel.add(entryArea, BorderLayout.CENTER);
		centerPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		this.add(leftPanel, BorderLayout.WEST);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(rightPanel, BorderLayout.EAST);
		
		this.setSize(400, 400);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton srcBtn = (JButton) e.getSource();
		int start = entryArea.getSelectionStart(), end = entryArea.getSelectionEnd();
		
		if(srcBtn.equals(saveButton)) {
			String entry = entryArea.getText().trim();
			if(entry != null && entry.length() > 0) {
				try {
					dbUtil.saveJournalEntry(currentUser.getUsername(), entry, Calendar.getInstance());
				}
				catch(SQLException ex) {
					JOptionPane.showMessageDialog(null, "Could not save entry, please try again!");
					ex.printStackTrace();
				}
			}
		}
		else if(srcBtn.equals(clearButton)) {
			
		}
		else if(srcBtn.equals(boldButton)) {
			
		}
		else if(srcBtn.equals(italicsButton)) {
			
		}
		else if(srcBtn.equals(underlineButton)) {
			
		}
	}
}
	