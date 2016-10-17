package eDiary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

class DiaryGUI extends JFrame implements ActionListener, MouseListener, FocusListener {

	JPanel topPanel, centerPanel, bottomPanel;
	JTextField usernameField;
	JPasswordField passwordField;
	JButton submitBtn, forgotPasswordBtn, registerBtn;
	Border defaultBorder, highlightedBorder;
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
		forgotPasswordBtn = new JButton("<HTML><U>Forgot Password</U></HTML>");
		registerBtn = new JButton("Register");
		
		registerEventListeners();
		setFonts();
		setStyles();
		initializeLayout();
	}
	
	private void setStyles() {
		defaultBorder = BorderFactory.createLineBorder(new Color(0,0,255,50), 2);
		highlightedBorder = BorderFactory.createEtchedBorder(new Color(0,0,255,50), Color.BLACK);
		forgotPasswordBtn.setBorder(null);
		forgotPasswordBtn.setForeground(Color.BLUE);
		forgotPasswordBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		usernameField.setBorder(defaultBorder);
		passwordField.setBorder(defaultBorder);
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
		JPanel buttonTopPanel = new JPanel(), buttonBottomPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		JPanel imagePanel;
		JLabel imageLabel = null;
		
		formPanel.setLayout(new GridLayout(2,2,5,5));
		formPanel.add(this.getCenteredLabel("Username"));
		formPanel.add(usernameField);
		formPanel.add(this.getCenteredLabel("Password"));
		formPanel.add(passwordField);
		
		buttonTopPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonTopPanel.add(forgotPasswordBtn);
		
		buttonBottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonBottomPanel.add(submitBtn);
		buttonBottomPanel.add(registerBtn);
		
		buttonPanel.setLayout(new GridLayout(2,1,5,5));
		buttonPanel.add(buttonBottomPanel);
		buttonPanel.add(buttonTopPanel);
		
		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(new JPanel(), BorderLayout.NORTH);
		rightPanel.add(formPanel, BorderLayout.CENTER);
		rightPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		try {
			imageLabel = new JLabel(getImageIcon("images/splash.jpg"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		centerPanel.setLayout(new GridLayout(1,2,5,5));
		if(imageLabel != null) {
			imagePanel = new JPanel();
			imagePanel.setLayout(new BorderLayout());
			imagePanel.add(imageLabel, BorderLayout.CENTER);
			centerPanel.add(imagePanel);
		}
		centerPanel.add(rightPanel);
		
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		this.setSize(650, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private ImageIcon getImageIcon(String imagePath) throws IOException {
		ImageIcon image = null;
		BufferedImage bufImg = ImageIO.read(new File(imagePath));
		image = new ImageIcon(bufImg);	
		return image;
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
		forgotPasswordBtn.addMouseListener(this);
		registerBtn.addActionListener(this);
		
		usernameField.addFocusListener(this);
		passwordField.addFocusListener(this);
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
	
	@Override
	public void mouseEntered(MouseEvent e) {
		JButton button = (JButton) e.getSource();
		if(button != null) {
			button.setForeground(Color.RED);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JButton button = (JButton) e.getSource();
		if(button != null) {
			button.setForeground(Color.BLUE);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		JTextField field = (JTextField) e.getSource();
		if(field != null) {
			field.setBorder(highlightedBorder);
			field.setBackground(new Color(255,255,255,200));
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		JTextField field = (JTextField) e.getSource();
		if(field != null) { 
			field.setBorder(defaultBorder);
			field.setBackground(Color.WHITE);
		}
	}

}
