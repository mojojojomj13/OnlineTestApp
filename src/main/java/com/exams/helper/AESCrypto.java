package com.exams.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.exams.enums.STATUS;
import com.exams.exceptions.ServiceException;

public class AESCrypto {

	private static final String IV = "FILINTERNATIONAL"; // length has to be 16

	// public static String encryptionKey = "j3xGTko0hyBwG7DUDqxHr2dBiYXWrT6Y";
	public static byte[] encrypt(String plainText, String encryptionKey) throws ServiceException {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
			SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
			return cipher.doFinal(plainText.getBytes("UTF-8"));
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException
				| UnsupportedEncodingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
				| BadPaddingException e) {
			throw new ServiceException(STATUS.NOT_AUTHENTICATED, STATUS.NOT_AUTHENTICATED.getMessage(), e);
		}
	}

	public static String decrypt(byte[] cipherText, String encryptionKey) throws ServiceException {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
			SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
			return new String(cipher.doFinal(cipherText), "UTF-8");
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException
				| UnsupportedEncodingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
				| BadPaddingException e) {
			throw new ServiceException(STATUS.NOT_AUTHENTICATED, STATUS.NOT_AUTHENTICATED.getMessage(), e);
		}
	}

	public static void main(String[] args) throws Exception {
		byte[] encKey = Base64.getDecoder().decode("YnRxWUhSSkxrZEk1d3hNSGF0OXN6cDgzOGpyVVpYNFY=");
		byte[] bytes = encrypt("A315090", new String(encKey, "UTF-8"));// "ghBJExfho40aUso9yFUZNQguarVAayXG");
		String base64Encoded = Base64.getEncoder().encodeToString(bytes);
		System.out.println(URLEncoder.encode(base64Encoded, "UTF-8"));
	}
}
