package eDiary;

class CryptUtil {
	static String encrypt(String key, String plainText) {
		String cipherText = "";
		int count = 0;
		int[][] keyMat = new int[2][2];
		int[][][] plainTextMat = new int[plainText.length()/2][2][1];
		
		key = key.toLowerCase();
		plainText = plainText.toLowerCase();
		
		//populating key Matrix
		for(int i=0; i<keyMat.length; i++)
			for(int j=0; j<keyMat[i].length; j++, count++)
				keyMat[i][j] = key.charAt(count) - 'a';
		
		//populating plainText Matrix
		count = 0;
		for(int i=0; i<plainTextMat.length; i++)
			for(int j=0; j<plainTextMat[i].length; j++)
				for(int k=0; k<plainTextMat[i][j].length; k++, count++)
					plainTextMat[i][j][k] = plainText.charAt(count) - 'a';
		
		// multiplying each column vector of plainText with key matrix
		for(int i=0; i<plainTextMat.length; i++) {
			int[][] resultant = multiply(keyMat, plainTextMat[i]);
			System.out.println("Key");
			display(keyMat);
			System.out.println("Column Vector : "+i);
			display(plainTextMat[i]);
			System.out.println("Resultant Matrix, ");
			display(resultant);
			for(int j=0; j<resultant.length; j++)
				for(int k=0; k<resultant[j].length; k++)
					cipherText += (char)(resultant[j][k] + 'a') + "";
		}
		
		return cipherText;
	}
	
	static String decrypt(String key, String cipherText) {
		String plainText = "";
		return plainText;
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
					if(sum < 0) {
						result[i][j] = 26 - (Math.abs(sum) % 26);
					}
					else
						result[i][j] = sum%26;
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
}
