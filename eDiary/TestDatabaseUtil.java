package eDiary;

import java.util.Calendar;

public class TestDatabaseUtil {
	public static void main(String[] args) throws Exception {
		DatabaseUtil dbUtil = new DatabaseUtil("localhost", 3306, "root", "", "ediary");
		System.out.println("Test 1: " + dbUtil.validateLogin("theja", "theja"));
		System.out.println("Test 2: " + dbUtil.validateLogin("cyberpirate", "cyberpirate"));
		
		Calendar temp = Calendar.getInstance();
		dbUtil.saveJournalEntry("theja", "shortexample", temp);
		JournalEntry entry = dbUtil.getJournalEntry("theja", temp);
		if(entry.getEntry() != null)
			System.out.println("Entry says '" + entry.getEntry() +"'");
		else
			System.out.println("Entry null");
	}
}
