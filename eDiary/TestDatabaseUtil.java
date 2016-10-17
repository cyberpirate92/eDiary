package eDiary;

public class TestDatabaseUtil {
	public static void main(String[] args) throws Exception {
		DatabaseUtil dbUtil = new DatabaseUtil("localhost", 3306, "root", "", "ediary");
		System.out.println("Test 1: " + dbUtil.validateLogin("theja", "theja"));
		System.out.println("Test 2: " + dbUtil.validateLogin("cyberpirate", "cyberpirate"));
	}
}
