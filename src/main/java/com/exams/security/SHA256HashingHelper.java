package com.exams.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.exams.enums.STATUS;
import com.exams.exceptions.ServiceException;

/**
 * this is a utility class for HMAC SHA-256 encryption /decryption
 *
 * @author Prithvish Mukherjee
 */
public class SHA256HashingHelper {

	private static final String HMAC_SHA256 = "HmacSHA256";

	/**
	 * this method gets the HMAC-SHA256 encrypted text for a given Text
	 *
	 * @param secret
	 *            the key to be used for HMAC hashed
	 * @param message
	 *            the plain text / message to be HMAC hashed
	 * 
	 * @return the HMAC-SHA256 encrypted Text value after encryption
	 * @throws ServiceException
	 *             may throw {@link ServiceException} in case any Key length is
	 *             invalid or Hashing algorithm is incorrect
	 */
	public static String hashMessage(String secret, String message) throws ServiceException {
		String hashedMessage = null;
		try {
			Mac sha256HMAC = Mac.getInstance(HMAC_SHA256);
			SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), HMAC_SHA256);
			sha256HMAC.init(secretKey);
			hashedMessage = Base64.getEncoder().encodeToString(sha256HMAC.doFinal(message.getBytes()));
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new ServiceException(STATUS.NOT_AUTHENTICATED, "User in Request is not Authenticated", e);
		}
		return hashedMessage;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(hashMessage("student", "stud123"));
	}
}
