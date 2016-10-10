package org.thothlab.devilsvault.logincontrollers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Password {
	
	public static String generateHash(String input) {
		StringBuilder hash = new StringBuilder();

		try {
			MessageDigest sha = MessageDigest.getInstance("HMAC-SHA-256");
			byte[] hashedBytes = sha.digest(input.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'a', 'b', 'c', 'd', 'e', 'f' };
			for (int idx = 0; idx < hashedBytes.length; ++idx) {
				byte b = hashedBytes[idx];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch (NoSuchAlgorithmException e) {
			// handle error here.
		}

		return hash.toString();
	}

	public void signup(String username, String password) {
		
		
		SecureRandom randomIV = new SecureRandom();  //Secure Random generator in order to generate 128-bit key randomly
        byte tempiv[] = new byte[4];
        byte[][] initIV = new byte[4][4];   
        for(int i=0;i<4;i++)
        {
         randomIV.nextBytes(tempiv);
         for(int j=0;j<4;j++)
         {
            initIV[i][j] = tempiv[j];
            
         }
        }
        
        String SALT = initIV.toString();
		String saltedPassword = SALT + password;
		String hashedPassword = generateHash(saltedPassword);
		System.out.println(hashedPassword);
		//DB.put(username, hashedPassword);
	}
	
	public Boolean login(String username, String password) {
		Boolean isAuthenticated = false;
		String SALT=""; // fetch the salt from the DB
		// remember to use the same SALT value use used while storing password
		// for the first time.
		String storedPasswordHash ="";//fetch stored hashpassword from db
		String saltedPassword = SALT + password;
		String hashedPassword = generateHash(saltedPassword);

		/*String storedPasswordHash = DB.get(username);
		if(hashedPassword.equals(storedPasswordHash)){
			isAuthenticated = true;
		}else{
			isAuthenticated = false;
		}*/
		return isAuthenticated;
	}
}
