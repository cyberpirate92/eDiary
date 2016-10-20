package eDiary;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class ResourceUtil {
	
	private static Font defaultFont = new Font("monaco", Font.PLAIN, 15);
	private static Border defaultBorder = BorderFactory.createLineBorder(new Color(0,0,255,50), 2);
	private static Border highlightedBorder = BorderFactory.createEtchedBorder(new Color(0,0,255,50), Color.BLACK);
	
	private ResourceUtil() {}
	
	static Font getDefaultFont() {
		return defaultFont;
	}
	
	static Border getDefaultBorder() {
		return defaultBorder;
	}
	
	static Border getHighlightedBorder() {
		return highlightedBorder;
	}
	
	static JLabel getCenteredLabel(String text) {
		JLabel label = new JLabel();
		label.setText(text);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(defaultFont);
		return label;
	}
}
