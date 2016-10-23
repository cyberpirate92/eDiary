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
	private static Color defaultBackground = Color.WHITE;
	private static Color highlightBackground = new Color(255,255,255,200);
	private static Color errorBackground = new Color(255,0,0,100);
	
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
	
	static Color getDefaultBackground() {
		return defaultBackground;
	}

	static Color getHighlightBackground() {
		return highlightBackground;
	}
	
	static Color getErrorBackground() {
		return errorBackground;
	}
}
