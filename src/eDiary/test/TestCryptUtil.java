package eDiary.test;

import eDiary.main.CryptUtil;

public class TestCryptUtil {
	public static void main(String[] args) throws Exception {
		String key = "hill", message = "somecrap";
		String cipherText = CryptUtil.encrypt(key, message);
		System.out.println("Cipher Text: " + cipherText);
		String plainText = CryptUtil.decrypt(key, cipherText);
		System.out.println("Plain Text: " + plainText);
	}
}
