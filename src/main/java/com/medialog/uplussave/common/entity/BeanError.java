package com.medialog.uplussave.common.entity;

import java.io.Serializable;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 빈 에러 정보
 * @filename BeanError.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BeanError implements Serializable {

    private String code;
    private String field;
    private String message;
    private String[] extra;

    @Builder
    public BeanError(String code, String field, String message, String[] extra) {
        this.code = code;
        this.field = field;
        this.message = message;
        this.extra = extra != null ? Arrays.copyOf(extra, extra.length) : null;
    }
}
