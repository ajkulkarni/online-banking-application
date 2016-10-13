package org.thothlab.devilsvault.logincontrollers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Mac;

public class Password {
	
	public static String generateHash(String input) {
		StringBuilder hash = new StringBuilder();

		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
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
	 public static StringBuffer toHexString(byte[][] array)
	    {
	        char[] hexarray = "0123456789ABCDEF".toCharArray();
	        StringBuffer hexstring = new StringBuffer();
	        for ( int j = 0; j < array.length; j++ )
	        {
	            for(int k=0;k<array.length;k++)
	            {
	                int v = array[k][j] & 0xFF;
	                hexstring.append(hexarray[v >>> 4]);
	                hexstring.append(hexarray[v & 0x0F]);                
	            }
	        }
	        return hexstring;
	    }
	public String generatesalt()
	{
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
        //password = "aditya";
        //System.out.println(password);
        String SALT = toHexString(initIV).toString();
        return SALT;
	}
	public String signup( String password, String SALT) {
		
		
		
        //System.out.println(SALT);
		String saltedPassword = SALT + password;
		//System.out.println(saltedPassword);
		String hashedPassword = generateHash(saltedPassword);
		//System.out.println(hashedPassword.length());
		//System.out.println(hashedPassword);
		return hashedPassword;
		//DB.put(username, hashedPassword);
	}
	
	public String login(String salt, String password) {
		//Boolean isAuthenticated = false;
		//String SALT=""; // fetch the salt from the DB
		// remember to use the same SALT value use used while storing password
		// for the first time.
		//String storedPasswordHash ="";//fetch stored hashpassword from db
		String saltedPassword = salt + password;
		String hashedPassword = generateHash(saltedPassword);
		return hashedPassword;

		
	}
	
}
