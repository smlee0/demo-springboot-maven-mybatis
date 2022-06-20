package com.medialog.uplussave.common.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.StringUtils;
import com.google.common.hash.Hashing;

/**
 * 암호화 클래스
 * @filename Encrypt.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
public class Encrypt {

    /**
     * AES256키
     */
    private static final String enKey = "__KB_ASSET_MNG__";
    private static final String IV = "0000000000000000";

    /**
     * sha256 암호화
     * @param plaintext
     * @return
     * @author Lee Se Min
     * @date 2022-06-10
     */
    public static String sha256Hex(String plaintext) {
        String sha256hex = Hashing.sha256().hashString(plaintext, StandardCharsets.UTF_8).toString();

        return sha256hex;
    }

    /**
     * AES256암호화
     * @param plainText
     * @return
     * @throws Exception
     * @author Lee Se Min
     * @date 2022-06-10
     */
    public static String encryptAES256(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(enKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        String cipherText = Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes("UTF-8")));

        return cipherText;
    }

    /**
     * AES256복호화
     * @param cipherText
     * @return
     * @throws Exception
     * @author Lee Se Min
     * @date 2022-06-10
     */
    public static String decryptAES256(String cipherText) throws Exception {
        byte[] decodeCipher = Base64.getDecoder().decode(cipherText);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(enKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));

        return new String(cipher.doFinal(decodeCipher), "UTF-8");
    }

    /**
     * URL에 안전하게 문자열 치환
     * @param str
     * @return
     * @author Lee Se Min
     * @date 2022-06-10
     */
    public static String safeUrl(String str) {
        return str.replaceAll("\\+", "\\-").replaceAll("\\/", "\\_").replaceAll("=", "");
    }

    /**
     * safeUrl함수에 의해 치환된 문자열을 취소
     * @param safeUrl
     * @return
     * @author Lee Se Min
     * @date 2022-06-10
     */
    public static String safeUrlCancel(String safeUrl) {
        int safeUrl4x = safeUrl.length() % 4;
        StringBuffer sb = new StringBuffer();

        sb.append(safeUrl.replaceAll("\\-", "\\+").replaceAll("\\_", "\\/"));

        if (safeUrl4x % 4 > 0) {
            sb.append(StringUtils.repeat("=", 4 - safeUrl4x));
        }

        return sb.toString();
    }

}
