package eDiary;

import javax.swing.SwingUtilities;

public class TestDiaryGUI {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new DiaryGUI();
			}
		});

	}

}
