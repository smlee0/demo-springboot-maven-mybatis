package com.medialog.uplussave.common.service;

/**
 * 공통 서비스 인터페이스
 * @filename CommonService.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
public interface CommonService {

    /**
     * 오브젝트를 JSON 문자열로 변환
     * @param data
     * @return
     * @author Lee Se Min
     * @date 2022-06-10
     */
    String toJSON(final Object data);

    /**
     * 사용자 IP주소 조회
     * @return
     * @author Lee Se Min
     * @date 2022-06-10
     */
    String getRemoteAddress();

    /**
     * 사용자 접속환경이 모바일기기인지 여부 조회
     * @return
     * @author Lee Se Min
     * @date 2022-06-10
     */
    Boolean isMobileDevice();

    /**
     * AJAX 요청인지 여부 조회
     * @return
     * @author Lee Se Min
     * @date 2022-06-10
     */
    Boolean isAjaxRequest();

    /**
     * 문자열 암호화(AES256)
     * @param plainText
     * @return
     * @author Lee Se Min
     * @date 2022-06-10
     */
    String encrypt(String plainText);

    /**
     * 암호화된 문자열 복호화(AES256)
     * @param cipherText
     * @return
     * @author Lee Se Min
     * @date 2022-06-10
     */
    String decrypt(String cipherText);
}
