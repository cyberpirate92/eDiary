package eDiary;

public class TestCryptUtil {

	public static void main(String[] args) {
		String key = "hill", message = "shortexample";
		String cipherText = CryptUtil.encrypt(key, message);
		System.out.println(cipherText);
	}

}
