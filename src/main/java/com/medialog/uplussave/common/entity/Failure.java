package com.medialog.uplussave.common.entity;

import java.util.List;
import java.util.Map;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 실패 결과
 * @filename Failure.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Failure extends SFResult {

    /**
     * 성공 여부
     */
    private boolean success = false;

    /**
     * bean 유효성 에러 메시지 리스트
     */
    private List<BeanError> errors;

    /**
     * 결과 코드
     */
    private String code;

    /**
     * 결과 메시지
     */
    private String message;

    /**
     * 필드
     */
    private String fieldId;

    /**
     * 이동 페이지
     */
    private String redirectUrl;

    /**
     * 결과 데이터
     */
    private Map<String, Object> data;

    @Builder
    public Failure(boolean success, List<BeanError> errors, String code, String message, String fieldId, Map<String, Object> data, String redirectUrl) {
        this.success = success;
        this.errors = errors != null ? Lists.newArrayList(errors) : null;
        this.code = code;
        this.message = message;
        this.fieldId = fieldId;
        this.redirectUrl = redirectUrl;
        this.data = data != null ? Maps.newHashMap(data) : null;
    }
}
