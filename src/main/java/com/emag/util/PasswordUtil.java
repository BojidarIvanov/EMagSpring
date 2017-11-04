package com.emag.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Base64;

public class PasswordUtil {

	// This code uses SHA-256.
	public static String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.reset();
		md.update(password.getBytes());
		byte[] mdArray = md.digest();
		StringBuilder sb = new StringBuilder(mdArray.length * 2);
		for (byte b : mdArray) {
			int v = b & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString();
	}

	public static String getSalt() {
		Random r = new SecureRandom();
		byte[] saltBytes = new byte[32];
		r.nextBytes(saltBytes);
		return Base64.getEncoder().encodeToString(saltBytes);
	}

	/*
	 * public static String hashAndSaltPassword(String password) throws
	 * NoSuchAlgorithmException { String salt = getSalt(); return
	 * hashPassword(password + salt); }
	 */

	public static String checkPasswordStrength(String password) {
		if (password == null || password.trim().isEmpty()) {
			return "Password cannot be an empty string.";
		} else if (password.length() < 8) {
			return "Password is to short. Must be at least 8 characters long.";
		}
		return "Password is ok";
	}

	public static boolean validatePassword(String password) {

		String error = checkPasswordStrength(password);
		if (!error.equals("Password is ok")) {
			return false;
		}
		return true;
	}

	/*
	 * This code tests the functionality of this class.
	 */
	public static void main(String[] args) {
		try {
			System.out.println("Hash for 'sesame':\n" + hashPassword("5555555555"));
			System.out.println("Random salt:\n" + getSalt());
			/*
			 * System.out.println("Salted hash for 'sesame':\n" +
			 * hashAndSaltPassword("sesame"));
			 */
		} catch (NoSuchAlgorithmException ex) {
			System.out.println(ex);
		}

		if (checkPasswordStrength("sesame1776").equals("Password is ok")) {
			System.out.println("Password is valid.");
		} else {
			System.out.println("Password is not valid.");
		}

	}
}