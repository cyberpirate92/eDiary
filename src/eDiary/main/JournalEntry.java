package eDiary.main;

import java.util.Calendar;

public class JournalEntry {

	private String entry;
	private Calendar entryDate, lastEdited;

	public JournalEntry(String entry, long entryDateMillis, long lastEditedMillis) {
		this.entry = entry;
		this.entryDate = Calendar.getInstance();
		this.lastEdited = Calendar.getInstance();
		this.entryDate.setTimeInMillis(entryDateMillis);
		this.lastEdited.setTimeInMillis(lastEditedMillis);
	}

	public JournalEntry(String entry, Calendar entryDate, Calendar lastEdited) {
		this.entry = entry;
		this.entryDate = entryDate;
		this.lastEdited = lastEdited;
	}

	public String getEntry() {
		return entry;
	}

	void setEntry(String entry) {
		this.entry = entry;
	}

	Calendar getEntryDate() {
		return entryDate;
	}

	void setEntryDate(Calendar entryDate) {
		this.entryDate = entryDate;
	}

	Calendar getLastEdited() {
		return lastEdited;
	}

	void setLastEdited(Calendar lastEdited) {
		this.lastEdited = lastEdited;
	}
}
