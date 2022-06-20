package com.medialog.uplussave.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 유효성검사 에러 VO
 * @filename ValidationError.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Getter
@Setter
public class ValidationError {

    /**
     * 코드
     */
    private String code;

    /**
     * 필드 id
     */
    private String field;

    /**
     * 에러 메시지
     */
    private String message;

    /**
     * 추가 정보 배열
     */
    private String[] extra;

    @Builder
    public ValidationError(String code, String field, String message, String[] extra) {
        this.code = code;
        this.field = field;
        this.message = message;
        this.extra = extra != null ? Arrays.copyOf(extra, extra.length) : null;
    }

    @Override
    public String toString() {
        List<String> list = new ArrayList<>();
        list.add(code);
        list.add(field);
        list.add(message);

        if (extra != null) {
            for (String ex : extra) {
                list.add(ex);
            }
        }

        return Joiner.on("|").useForNull("").join(list);
    }

    public static ValidationError toObject(String str) {
        ValidationError ve = ValidationError.builder().build();

        Iterable<String> values = Splitter.on("|").split(str);
        int i = 0;
        List<String> extra = new ArrayList<>();
        for (String value : values) {
            if (i == 0) {
                ve.setCode(value);
            } else if (i == 1) {
                ve.setField(value);
            } else if (i == 2) {
                ve.setMessage(value);
            } else {
                extra.add(value);
            }

            i++;
        }

        if (extra.size() > 0) {
            ve.setExtra(extra.toArray(new String[extra.size()]));
        }

        return ve;
    }

    public static void main(String[] args) {
        ValidationError.builder().code("1").field("id").message("123213").build();
    }
}