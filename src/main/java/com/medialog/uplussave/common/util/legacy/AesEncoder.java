package com.medialog.uplussave.common.util.legacy;

import java.math.BigInteger;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class AesEncoder {

	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding"; // algorithm/mode/padding

	public static byte[] encrypt(byte[] plainText, byte[] key) {
		byte[] iv = new byte[16];
		return encrypt(plainText, key, iv);
	}

	public static byte[] decrypt(byte[] cipherText, byte[] key) {
		byte[] iv = new byte[16];
		return decrypt(cipherText, key, iv);
	}

	public static String encryptBase64(String PT, byte[] key) {
		byte[] iv = new byte[16];
		return encryptBase64(PT, key, iv);
	}

	public static String decryptBase64(String b64CT, byte[] key) {
		byte[] iv = new byte[16];
		return decryptBase64(b64CT, key, iv);
	}

	public static byte[] encrypt(byte[] plainText, byte[] key, byte[] iv) {
		try {
			Cipher c = Cipher.getInstance(TRANSFORMATION);
			SecretKeySpec k = new SecretKeySpec(key, ALGORITHM);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			c.init(Cipher.ENCRYPT_MODE, k, ivSpec);
			return c.doFinal(plainText);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] decrypt(byte[] cipherText, byte[] key, byte[] iv) {
		try {
			Cipher c = Cipher.getInstance(TRANSFORMATION);
			SecretKeySpec k = new SecretKeySpec(key, ALGORITHM);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			c.init(Cipher.DECRYPT_MODE, k, ivSpec);
			return c.doFinal(cipherText);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String encryptBase64(String PT, byte[] key, byte[] iv) {
		byte[] enc = encrypt(PT.getBytes(), key, iv);
		return new String(Base64.encodeBase64(enc));
	}

	public static String encryptBase64(String PT, String key, String iv) {
		return AesEncoder.encryptBase64(PT, key.getBytes(), iv.getBytes());
	}

	public static String decryptBase64(String b64CT, byte[] key, byte[] iv) {
		byte[] enc = Base64.decodeBase64(b64CT.getBytes());
		return new String(decrypt(enc, key, iv));
	}

	public static String decryptBase64(String b64CT, String key, String iv) {
		return AesEncoder.decryptBase64(b64CT, key.getBytes(), iv.getBytes());
	}

	public static String encryptHex(String PT, byte[] key, byte[] iv) {
		byte[] enc = encrypt(PT.getBytes(), key, iv);
		return new BigInteger(enc).toString(16);
	}

	public static String encryptHex(String PT, String key, String iv) {
		return AesEncoder.encryptHex(PT, key.getBytes(), iv.getBytes());
	}

	public static String decryptHex(String PT, byte[] key, byte[] iv) throws DecoderException {
		byte[] dec = decrypt(Hex.decodeHex(PT.toCharArray()), key, iv);
		return new String(dec);
	}

	public static String decryptHex(String PT, String key, String iv) throws DecoderException {
		return AesEncoder.decryptHex(PT, key.getBytes(), Hex.decodeHex(iv.toCharArray()));
	}

}
