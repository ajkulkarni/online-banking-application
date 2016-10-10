package org.thothlab.devilsvault.logincontrollers;


import java.io.*;
import java.lang.*;
import java.lang.reflect.UndeclaredThrowableException;
import java.security.*;
import java.util.Date;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;



public class OneTimePassword {
	OneTimePassword()
	{}
	private static final int[] doubleDigits = { 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 };
	
	
	private static int calcChecksum(long num, int digits)
	{
		boolean doubleDigit = true;
		int total = 0;
		while(0 < digits--)
		{
			int digit = (int)(num%10);
			num /= 10;
			if(doubleDigit)
			{
				digit = doubleDigits[digit];
			}
			total += digit;
			doubleDigit = !doubleDigit;
		}
		int result = total % 10;
        if (result > 0) {
            result = 10 - result;
        }
        return result;
		
	}
	public static byte[] hmac_sha1(byte[] keyBytes, byte[] text) throws NoSuchAlgorithmException, InvalidKeyException
	{
		try
		{
			Mac hmacSha1;
			try
			{
				hmacSha1 = Mac.getInstance("HmacSHA256");
				
			} 
			catch (NoSuchAlgorithmException nsae)
			{
				hmacSha1 = Mac.getInstance("HMAC-SHA-256");
			}
			SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
			hmacSha1.init(macKey);
			return hmacSha1.doFinal(text);
					
		}
		catch (GeneralSecurityException gse)
		{
			throw new UndeclaredThrowableException(gse);
			
		}
	}
	
	private static final int[] DIGITS_POWER = {1,10,100,1000,10000,100000,1000000,10000000,100000000};
	
	static public String generateOTP(byte[] secret, long movingFactor, int codeDigits,boolean addChecksum,int truncationOffset)
            throws NoSuchAlgorithmException, InvalidKeyException
	{
        
        String result = null;
        int digits = addChecksum ? (codeDigits + 1) : codeDigits;
        byte[] text = new byte[8];
        for (int i = text.length - 1; i >= 0; i--) {
            text[i] = (byte) (movingFactor & 0xff);
            movingFactor >>= 8;
        }

        //System.out.println(secret);
        byte[] hash = hmac_sha1(secret, text);

        
        int offset = hash[hash.length - 1] & 0xf;
        if ((0 <= truncationOffset) && (truncationOffset < (hash.length - 4)))
        {
            offset = truncationOffset;
        }
        int binary = ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16) | ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);

        int otp = binary % DIGITS_POWER[codeDigits];
        if (addChecksum) {
            otp = (otp * 10) + calcChecksum(otp, codeDigits);
        }
        result = Integer.toString(otp);
        while (result.length() < digits) {
            result = "0" + result;
        }
        return result;
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
	public static String getotp() {
		// TODO Auto-generated method stub
		
		String result = null;
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
        //System.out.println("INIT " + toHexString(initIV).length());
      
		
		
        String seed32 = toHexString(initIV).toString();
		 String pass = "12345678901234567890";
		 byte[] code = seed32.getBytes();
		 //System.out.println(code);
		 try
		 {
			Date dateobj = new Date();
			long s1 = dateobj.getTime();
			result = OneTimePassword.generateOTP(code, 20, 6, false, 20);
			System.out.println(result);
			return result;
			/*System.out.println("Enter the passcode");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String inputString = new String(br.readLine());
			Date dateobj1 = new Date();
			long s2 = dateobj1.getTime();
			if((s2-s1)/1000<=30 && result.compareTo(inputString)==0)
				System.out.println((s2-s1)/1000);
			else
				System.out.println("Blah" + (s2-s1)/1000);*/
			
		 }
		 catch (Exception e)
		 {
		  	System.out.println("Exception = " + e);
		 }
		return result;
		 

	}

}
