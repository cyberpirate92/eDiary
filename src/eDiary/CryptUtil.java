package eDiary;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

class CryptUtil {
	static String encrypt(String key, String plainText) {
		
		char appendChar;
		if(plainText.length() % 2 == 0)
			appendChar = '*';
		else
			appendChar = '-';
		String cipherText = "";
		int count = 0;
		int[][] keyMat = new int[2][2];
		int[][][] plainTextMat = new int[(int)(Math.ceil(plainText.length()/2F))][2][1];
		
		key = key.toLowerCase();
		plainText = plainText.toLowerCase();
		
		//populating key Matrix
		for(int i=0; i<keyMat.length; i++)
			for(int j=0; j<keyMat[i].length; j++, count++)
				keyMat[i][j] = key.charAt(count%key.length()) - 'a';
		
		//populating plainText Matrix
		count = 0;
		for(int i=0; i<plainTextMat.length; i++)
			for(int j=0; j<plainTextMat[i].length; j++)
				for(int k=0; k<plainTextMat[i][j].length; k++, count++) {
					if(count < plainText.length() && plainText.charAt(count) >= 'a' && plainText.charAt(count) <= 'z')
						plainTextMat[i][j][k] = plainText.charAt(count) - 'a';
					else
						plainTextMat[i][j][k] = ' ';
				}
		
		// multiplying each column vector of plainText with key matrix
		for(int i=0; i<plainTextMat.length; i++) {
			int[][] resultant = normalize(multiply(keyMat, plainTextMat[i]));
			
			/*System.out.println("Key");
			display(keyMat);
			System.out.println("Column Vector : "+i);
			display(plainTextMat[i]);
			System.out.println("Resultant Matrix, ");
			display(resultant);*/
			
			for(int j=0; j<resultant.length; j++)
				for(int k=0; k<resultant[j].length; k++)
					cipherText += (char)(resultant[j][k] + 'a') + "";
		}
		
		return cipherText + appendChar;
	}
	
	static String decrypt(String key, String cipherText) throws Exception {
		char appendChar = cipherText.charAt(cipherText.length()-1);
		cipherText = cipherText.substring(0, cipherText.length()-1);
		String plainText = "";
		int count = 0;
		int[][] keyMat = new int[2][2];
		int[][][] cipherTextMat = new int[(int)Math.ceil(cipherText.length()/2F)][2][1];
		
		// populating key matrix
		for(int i=0; i<keyMat.length; i++)
			for(int j=0; j<keyMat[i].length; j++, count++)
				keyMat[i][j] = key.charAt(count%key.length()) - 'a';
		
		// populating the cipherText matrix
		count = 0;
		for(int i=0; i<cipherTextMat.length; i++)
			for(int j=0; j<cipherTextMat[i].length; j++)
				for(int k=0; k<cipherTextMat[i][j].length; k++, count++) {
					if(count < cipherText.length())
						cipherTextMat[i][j][k] = cipherText.charAt(count) - 'a';
					else
						cipherTextMat[i][j][k] = 0;
				}
		
		// calculating the determinant of keyMat
		int det = normalize(calculateDeterminant(keyMat));
		
		// calculating inverse of determinant 
		int detInverse = calculateInverse(det);
		
		// calculating the adjugate matrix of keyMat
		int[][] keyMatAdjugate = normalize(calculateAdjugate(keyMat));
		
		// calculating the inverse Key Matrix
		int[][] keyMatInverse = normalize(scalarMultiply(detInverse, keyMatAdjugate));
		
		// multiplying Inverse Key Matrix with cipher text column vectors to obtain plain text
		for(int i=0; i<cipherTextMat.length; i++) {
			int[][] resultant = normalize(multiply(keyMatInverse, cipherTextMat[i]));
			for(int j=0; j<resultant.length; j++)
				for(int k=0; k<resultant[j].length; k++)
					plainText += (char)(resultant[j][k] + 'a') + "";
		}
		if(appendChar == '-')
			return plainText.substring(0, plainText.length()-1);
		else
			return plainText;
	}
	
	// utility function to normalize a integer value to the range 0-25
	static int normalize(int val) {
		return val > 0 ? val % 26 : 26 - (Math.abs(val) % 26);
	}
	
	// utility function to normalize a matrix to the range 0-25
	static int[][] normalize(int[][] mat) {
		for(int i=0; i<mat.length; i++)
			for(int j=0; j<mat[i].length; j++) 
				mat[i][j] = normalize(mat[i][j]);
		return mat;
	}
	
	// utility function to perform scalar multiplication on a matrix
	static int[][] scalarMultiply(int scalar, int[][] mat) {
		for(int i=0; i<mat.length; i++)
			for(int j=0; j<mat[i].length; j++)
				mat[i][j] *= scalar;
		return mat;
	}
	
	// utility function to calculate adjugate of a matrix (2x2)
	static int[][] calculateAdjugate(int[][] mat) throws Exception {
		int[][] adjugateMat = null;
		if(mat.length == mat[mat.length-1].length && mat.length == 2) {
			adjugateMat = new int[2][2];
			adjugateMat[0][0] = mat[1][1];
			adjugateMat[0][1] = mat[0][1] * -1;
			adjugateMat[1][0] = mat[1][0] * -1;
			adjugateMat[1][1] = mat[0][0];
		}
		else {
			throw new Exception("A 2x2 matrix is required for calculating adjugate");
		}
		return adjugateMat;
	}
	
	// utility function to calculate the multiplicative inverse of a number
	static int calculateInverse(int val) {
		int temp, current = 0;
		while(true) {
			int x = 26 * current + 1;
			if(x % val == 0) {
				temp = x / val;
				break;
			}
			current++;
		}
		return temp;
	}
	
	// utility function to calculate determinant value of a matrix
	static int calculateDeterminant(int[][] mat) throws Exception {
		int determinant = 0;
		if(mat.length == mat[mat.length-1].length && mat.length == 2) {
			determinant = (mat[0][0] * mat[1][1]) - (mat[0][1] * mat[1][0]);
		}
		else {
			throw new Exception("A 2x2 matrix is required to find determinant value");
		}
		return determinant;
	}
	
	// utility function for multiplying 2D matrices 
	static int[][] multiply(int[][] matA, int[][] matB) {
		
		int[][] result = null;
		//checking if it's possible to multiply
		if(matA[matA.length-1].length == matB.length) {
			result = new int[matA.length][matB[matB.length-1].length];
			for(int i=0; i<result.length; i++) 
				for(int j=0; j<result[i].length; j++) {
					int sum = 0;
					for(int k=0; k<matA[i].length; k++) {
						sum += matA[i][k] * matB[k][j];
					}
					result[i][j] = sum;
				}
		}
		return result;
	}
	
	//utility function to display a matrix to STDOUT
	static void display(int[][] mat) {
		System.out.println();
		for(int i=0; i<mat.length; i++) {
			for(int j=0; j<mat[i].length; j++)
				System.out.print(mat[i][j] + "\t");
			System.out.println();
		}
	}
	
	// SHA256 for passwords
	public static String sha256(String base) {
	    try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } 
	    catch(Exception ex) {
	       throw new RuntimeException(ex);
	    }
	}
}
