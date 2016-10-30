package eDiary.test;

import javax.swing.SwingUtilities;
import eDiary.main.DiaryLoginGUI;

public class TestDiaryLoginGUI {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new DiaryLoginGUI();
			}
		});

	}

}
