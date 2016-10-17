package eDiary;

public class TestDatabaseUtil {
	public static void main(String[] args) throws Exception {
		DatabaseUtil dbUtil = new DatabaseUtil("localhost", 3306, "root", "", "sample");
		dbUtil.debug();
	}
}
