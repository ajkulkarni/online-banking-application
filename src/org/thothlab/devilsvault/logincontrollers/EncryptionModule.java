package org.thothlab.devilsvault.logincontrollers;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

public class EncryptionModule {

	
	//To generate the Key and store it in the Keystore
	private static void generatekey()
	{
		
		try {
			KeyStore keyStore = KeyStore.getInstance("JCEKS");
			keyStore.load(null, null);
			
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			Key key = keyGen.generateKey();
			//Need to change the password
			keyStore.setKeyEntry("secret", key, "password".toCharArray(),null);
			keyStore.store(new FileOutputStream("output.jceks"), "password".toCharArray());
			System.out.println("KeyStore Fuc "+key.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	     
	}
	
	public String Dec(String encryptedText)
	{
		Key key = null;
		
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
		byte[] plainByte = null;
		
		try
		{
			 KeyStore keyStore = KeyStore.getInstance("JCEKS");
			 
			 //Need to change the password
			 keyStore.load(new FileInputStream("output.jceks"), "password".toCharArray());
			     
			 key = keyStore.getKey("secret", "password".toCharArray());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			cipher.init(Cipher.DECRYPT_MODE,key, cipher.getParameters());
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			plainByte = cipher.doFinal(encryptedTextByte);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println(plainByte.toString());
	    return plainByte.toString();
	}
	
	public String Enc(String args)
	{
		byte[] iv = new byte[16];
		SecureRandom random = new SecureRandom();
		random.nextBytes(iv);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
		Key key = null;
		byte[] cipherBytes = null;
		try
		{
			 KeyStore keyStore = KeyStore.getInstance("JCEKS");
			 
			 //Need to change the password
			 keyStore.load(new FileInputStream("output.jceks"), "password".toCharArray());
			     
			 key = keyStore.getKey("secret", "password".toCharArray());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
			Cipher cipher = null;
			try {
				cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  // Transformation of the algorithm
	        try {
				cipher.init(Cipher.ENCRYPT_MODE, key);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				cipherBytes = cipher.doFinal(args.getBytes());
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //System.out.println(new String(cipherBytes));
		
	        
		//System.out.println("Cipher "+cipherBytes);
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(cipherBytes);
		System.out.println(encryptedText);
		Dec(encryptedText);
		return encryptedText;
		
		
		//getClass(Dec(cipherBytes.toString());
	}
	public static void  main(String args[])
	{
		
		/*byte[] plainBytes = "HELLO JCE".getBytes();
        
        // Generate the key first
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);  // Key size
        Key key = keyGen.generateKey();
         
        // Create Cipher instance and initialize it to encrytion mode
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  // Transformation of the algorithm
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherBytes = cipher.doFinal(plainBytes);
        //System.out.println(new String(cipherBytes));
         
        // Reinitialize the Cipher to decryption mode
        cipher.init(Cipher.DECRYPT_MODE,key, cipher.getParameters());
        byte[] plainBytesDecrypted = cipher.doFinal(cipherBytes);
         
        System.out.println("DECRUPTED DATA : "+new String(plainBytesDecrypted));    */
		EncryptionModule E = new EncryptionModule();
		//E.generatekey();
		String s = E.Enc("aditya");
		//System.out.println(s);
		
			
	}
}
