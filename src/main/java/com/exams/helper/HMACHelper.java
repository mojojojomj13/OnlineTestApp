package com.exams.helper;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.exams.exceptions.ServiceException;

/**
 * this is a utility class for HMAC SHA-256 encryption /decryption
 *
 * @author Prithvish Mukherjee
 */
public class HMACHelper {

	/**
	 * this method gets the HMAC-SHA256 encrypted text for a given Text
	 *
	 * @param key
	 *            the key to be used for hmac encryption
	 * @param message
	 *            the plain text / message to be hmac encrypted
	 * @return the HMAC-SHA256 encrypted Text value after encryption
	 * @throws ServiceException
	 *             Thrown in case of any error in encrypting the message
	 */
	public static String hashMessage(String key, String message) throws ServiceException {
		String hashedMessage = null;
		Mac sha256HMAC;
		try {
			sha256HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
			sha256HMAC.init(secretKey);
			hashedMessage = Base64.getEncoder().encodeToString(sha256HMAC.doFinal(message.getBytes()));
		} catch (NoSuchAlgorithmException | InvalidKeyException | IllegalStateException e) {
			throw new ServiceException("Hashing failed :: " + e, e);
		}
		return hashedMessage;
	}
}
