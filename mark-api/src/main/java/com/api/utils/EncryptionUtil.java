package com.api.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */

@Component
public class EncryptionUtil {

	private MessageDigest messageDigest;

	@PostConstruct
	public void init() throws NoSuchAlgorithmException {
		messageDigest = MessageDigest.getInstance("SHA");
	}

	public String encode(String plainText) {
		messageDigest.update(plainText.getBytes());
		String encodePassword = byteArrayToHexString(messageDigest.digest());
		messageDigest.reset();
		return encodePassword;
	}

	public boolean matchPassword(String encodedPassword, String plainPassword) {
		return encodedPassword.equals(encode(plainPassword));
	}

	private String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();
	}

}
