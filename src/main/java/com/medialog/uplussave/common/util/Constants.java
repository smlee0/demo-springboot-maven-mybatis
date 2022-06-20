package com.medialog.uplussave.common.util;

import java.util.Locale;

/**
 * 상수 및 Enum 관리
 * @filename Constants.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
public class Constants {

    // 에러 페이지 관리
    public static final String ERROR_VIEW_404 = "/error/404"; // 404 에러 뷰
    public static final String ERROR_VIEW_500 = "/error/500"; // 500 에러 뷰
    public static final String ERROR_VIEW_403 = "/error/403"; // 404 에러 뷰

    // PC/모바일 체크
    public static final String VIEW_PATH_PC = "/pc"; // 사용자 뷰 경로
    public static final String VIEW_PATH_MOBILE = "/mo"; // 사용자 뷰 경로

    // KEY
    public static final String KEY_RESULT = "result";
    public static final String KEY_ITEM = "item";
    public static final String KEY_ITEMS = "items";
    public static final String KEY_PARAMS = "params";
    public static final String KEY_DATA = "data";
    public static final String KEY_COUNT = "count";

    // 파일관리
    public static final String ATTACH_PC = "P";
    public static final String ATTACH_MOBILE = "M";
    public static final String ATTACH_VIDEO = "V";

    // file max size
    long FILE_MAX_SIZE = 5 * (1024 * 1024);

    // 페이징 옵션
    public static final Integer DEFAULT_CURRENT_PAGE_NO = 1; // 현재 페이지 번호 디폴트 값
    public static final Integer DEFAULT_RECORD_COUNT_PER_PAGE = 10; // 한 페이지당 게시되는 게시물 건 수 디폴트 값
    public static final Integer DEFAULT_PAGE_SIZE = 5; // 페이징의 페이지 번호 개수 디폴트 값

    //정규식
    public static final String REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    // 기타
    public static final String YYYYMMDD = "yyyy-MM-dd"; // 기본 날짜 포맷
    public static final Locale DEFAULT_LOCALE = Locale.KOREA;

//    public static final String SESSION_KEY_AUTHNUM = "AUTHNUM";
//    public static final String SESSION_KEY_CELLULAR = "CELLULAR";
//    public static final String SESSION_KEY_USER = "USER";

    public static final String ERROR_PAGE = "redirect:/";

}
