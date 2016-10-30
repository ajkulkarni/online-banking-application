package org.thothlab.devilsvault.controllers.security;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.thothlab.devilsvault.dao.userauthentication.OTPDelivery;

public class Encryption {
	
	public static String Encode(String data) {
		try {
			KeyStore keyStore = KeyStore.getInstance("JCEKS");
			InputStream stream = OTPDelivery.class.getClassLoader().getResourceAsStream("mykeystore.jks");
			keyStore.load(stream, "password".toCharArray());
			Key key = keyStore.getKey("mykey", "password".toCharArray());

			return encryptWithAESKey(data, key.getEncoded());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "encoding error";
		}

	}

	public static String Decode(String encryptedData) {
		try {
			KeyStore keyStore = KeyStore.getInstance("JCEKS");
			InputStream stream = OTPDelivery.class.getClassLoader().getResourceAsStream("mykeystore.jks");
			keyStore.load(stream, "password".toCharArray());
			Key key = keyStore.getKey("mykey", "password".toCharArray());

			return decryptWithAESKey(encryptedData, key.getEncoded());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "decoding error";
		}

	}

	public static String encryptWithAESKey(String data, byte[] key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException {
		SecretKey secKey = new SecretKeySpec(key, "AES");

		Cipher cipher = Cipher.getInstance("AES");

		cipher.init(Cipher.ENCRYPT_MODE, secKey);
		byte[] newData = cipher.doFinal(data.getBytes());

		return Base64.encodeBase64String(newData);
	}

	public static String decryptWithAESKey(String inputData, byte[] key) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("AES");
		SecretKey secKey = new SecretKeySpec(key, "AES");

		cipher.init(Cipher.DECRYPT_MODE, secKey);
		byte[] newData = cipher.doFinal(Base64.decodeBase64(inputData.getBytes()));
		return new String(newData);

	}

}