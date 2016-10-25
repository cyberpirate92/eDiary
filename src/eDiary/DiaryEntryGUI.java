package eDiary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class DiaryEntryGUI extends JFrame implements ActionListener {
	
	private JPanel leftPanel, centerPanel, rightPanel;
	private User currentUser;
	private JTextArea entryArea;
	private DatabaseUtil dbUtil;
	private JButton saveButton, clearButton, logoutButton;
	private JButton boldButton, italicsButton, underlineButton;
	private JDatePickerImpl datePicker;
	private JLabel currentDateLabel;
	
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
		logoutButton = new JButton("Logout");
		currentDateLabel = ResourceUtil.getCenteredLabel("");
		datePicker = new JDatePickerImpl(new JDatePanelImpl(getUtilDateModel(), new Properties()), null);
		
		registerEventListeners();
		initializeLayout();
		updateCurrentDateLabel();
		
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
		logoutButton.addActionListener(this);
		datePicker.getModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				try {
					DiaryEntryGUI.this.updateOnDateChanged();
				}
				catch(Exception exc) {
					System.out.println("Exception : ");
					exc.printStackTrace();
				}
			}
		});
	}
	
	private void initializeLayout() {
		
		// LEFT PANEL
		
		JPanel calendarPanel = new JPanel();
		calendarPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		calendarPanel.add(datePicker);
		
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(ResourceUtil.getCenteredLabel("Welcome, "+currentUser.getUsername()), BorderLayout.NORTH);
		leftPanel.add(calendarPanel, BorderLayout.CENTER);
		leftPanel.add(logoutButton, BorderLayout.SOUTH);
		
		// CENTER PANEL
		
		JPanel buttonPanel = new JPanel();
		JPanel formattingPanel = new JPanel();
		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(saveButton);
		buttonPanel.add(clearButton);
		
		formattingPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		formattingPanel.add(currentDateLabel);
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
		
		this.setSize(700, 400);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void updateOnDateChanged() throws Exception {
		updateCurrentDateLabel();
		
		JournalEntry entry = dbUtil.getJournalEntry(currentUser.getUsername(), getCurrentEntryDate());
		if(entry != null) 
			entryArea.setText(entry.getEntry());
		else
			entryArea.setText("");
	}
	
	private void updateCurrentDateLabel() {
		currentDateLabel.setText(this.getCurrentEntryDateString());
	}
	
	private UtilDateModel getUtilDateModel() {
		UtilDateModel model = new UtilDateModel();
		Calendar c = Calendar.getInstance();
		model.setDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
		model.setSelected(true);
		return model;
	}
	
	private Calendar getCurrentEntryDate() {
		Calendar c = Calendar.getInstance();
		c.setTime((Date)(datePicker.getModel().getValue()));
		return c ;
	}
	
	private String getCurrentEntryDateString() {
		Calendar c = getCurrentEntryDate();
		String currentDate = c.get(Calendar.DAY_OF_MONTH) + " ";
		SimpleDateFormat monthName = new SimpleDateFormat("MMMM");
		currentDate += monthName.format(c.getTime()) + " ";
		currentDate += c.get(Calendar.YEAR);
		return currentDate;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton srcBtn = (JButton) e.getSource();
		// int start = entryArea.getSelectionStart(), end = entryArea.getSelectionEnd();
		
		if(srcBtn.equals(saveButton)) {
			saveJournal();
		}
		else if(srcBtn.equals(clearButton)) {
			
		}
		else if(srcBtn.equals(boldButton)) {
			
		}
		else if(srcBtn.equals(italicsButton)) {
			
		}
		else if(srcBtn.equals(underlineButton)) {
			
		}
		else if(srcBtn.equals(logoutButton)) {
			int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout ?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION) {
				result = JOptionPane.showConfirmDialog(null, "Save Entry ?", "Confirm Save", JOptionPane.YES_NO_OPTION);
				if(result != JOptionPane.CANCEL_OPTION) {
					if(result == JOptionPane.YES_OPTION)
						saveJournal();
					this.dispose();
					new DiaryLoginGUI();
				}
			}
		}
	}
	
	private void saveJournal() {
		String entry = entryArea.getText().trim();
		if(entry != null && entry.length() > 0) {
			try {
				dbUtil.saveJournalEntry(currentUser.getUsername(), entry, getCurrentEntryDate());
			}
			catch(SQLException ex) {
				JOptionPane.showMessageDialog(null, "Could not save entry, please try again!");
				ex.printStackTrace();
			}
		}
	}
}
	