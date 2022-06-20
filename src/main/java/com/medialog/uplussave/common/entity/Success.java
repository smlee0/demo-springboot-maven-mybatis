package com.medialog.uplussave.common.entity;

import java.util.List;
import java.util.Map;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 성공 결과
 * @filename Success.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Getter
@Setter
public class Success extends SFResult {

    /**
     * 성공 여부
     */
    private boolean success = true;

    /**
     * 데이터
     */
    private Map<String, Object> data;

    /**
     * 결과 코드
     */
    private String code;

    /**
     * 결과 메시지
     */
    private String message;

    /**
     * 이동 페이지
     */
    private String redirectUrl;

    /**
     * list 데이터
     */
    private List list;

    @Builder
    public Success(Map<String, Object> data, String message, String code, List list, String redirectUrl) {
        this.data = data != null ? Maps.newHashMap(data) : null;
        this.message = message;
        this.code = code;
        this.redirectUrl = redirectUrl;
        this.list = list != null ? Lists.newArrayList(list) : null;
    }
}
