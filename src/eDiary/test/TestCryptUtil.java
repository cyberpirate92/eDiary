package eDiary.test;

import eDiary.main.CryptUtil;

public class TestCryptUtil {
	public static void main(String[] args) throws Exception {
		String key = "hill", message = "somecrap";
		String cipherText = CryptUtil.encrypt(key, message);
		System.out.println("Cipher Text: " + cipherText);
		String plainText = CryptUtil.decrypt(key, cipherText);
		System.out.println("Plain Text: " + plainText);
		
		// encryptString method
		String message1 = "A string with spaces and !@# special charsss.";
		cipherText = CryptUtil.encryptString(key, message1);
		System.out.println("Cipher Text: " + cipherText);
		plainText = CryptUtil.decryptString(key, cipherText);
		System.out.println("Plain Text: " + plainText);
	}
}
