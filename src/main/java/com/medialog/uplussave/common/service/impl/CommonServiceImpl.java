package com.medialog.uplussave.common.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medialog.uplussave.common.service.CommonService;
import lombok.extern.slf4j.Slf4j;

/**
 * 공통 서비스 구현부
 * @filename CommonServiceImpl.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Slf4j
@Service
public class CommonServiceImpl implements CommonService {
    private volatile Cipher encryptCipher;
    private volatile Cipher decryptCipher;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    public CommonServiceImpl(@Value("${uplus.securityKey}") String securityKey)
            throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        securityKey = String.format("%1$-16s", securityKey).replace(" ", "_");

        byte[] keyBytes = new byte[16];
        System.arraycopy(securityKey.getBytes("UTF-8"), 0, keyBytes, 0, 16);

        String securityIV = securityKey.substring(0, 16);
        Key securityKeySpec = new SecretKeySpec(keyBytes, "AES");

        this.encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        this.encryptCipher.init(Cipher.ENCRYPT_MODE, securityKeySpec, new IvParameterSpec(securityIV.getBytes()));

        this.decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        this.decryptCipher.init(Cipher.DECRYPT_MODE, securityKeySpec, new IvParameterSpec(securityIV.getBytes()));
    }

    /**
     * HttpServletRequest
     * @return
     * @author Lee Se Min
     * @date 2022-06-10
     */
    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();

        return requestAttributes.getRequest();
    }

    @Override
    public String toJSON(final Object data) {
        try
        {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        }
        catch (JsonProcessingException ex)
        {
            return data.toString();
        }
    }

    @Override
    public String getRemoteAddress() {
        HttpServletRequest request = this.getCurrentRequest();
        String remoteAddress = request.getHeader("X-Forwarded-For");

        return (StringUtils.isEmpty(remoteAddress) ? request.getRemoteAddr() : remoteAddress);
    }


    @Override
    public Boolean isMobileDevice() {
        HttpServletRequest request = this.getCurrentRequest();
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);

        return !StringUtils.isEmpty(userAgent) && Pattern
                .compile("(iphone|ipad|ipod|android)", Pattern.CASE_INSENSITIVE).matcher(userAgent).find();
    }


    @Override
    public Boolean isAjaxRequest() {
        HttpServletRequest request = this.getCurrentRequest();
        String requestedWithHeader = request.getHeader("X-Requested-With");

        return "XMLHttpRequest".equals(requestedWithHeader);
    }

    @Override
    public String encrypt(String plainText) {
        try {
            byte[] encrypted = this.encryptCipher.doFinal(plainText.getBytes("UTF-8"));

            return new String(Base64Utils.encode(encrypted));
        }
        catch (Exception ex) {
            log.error(String.format("[encrypt] plainText:\"%s\"", plainText));
        }

        return plainText;
    }


    @Override
    public String decrypt(String cipherText) {
        try {
            byte[] byteStr = Base64Utils.decode(cipherText.getBytes());

            return new String(this.decryptCipher.doFinal(byteStr), "UTF-8");
        }
        catch (Exception ex) {
            log.error(String.format("[decrypt] cipherText:\"%s\"", cipherText));
        }

        return cipherText;
    }

}
