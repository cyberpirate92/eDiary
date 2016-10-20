package eDiary;

import javax.swing.SwingUtilities;

public class TestDiaryLoginGUI {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new DiaryLoginGUI();
			}
		});

	}

}
