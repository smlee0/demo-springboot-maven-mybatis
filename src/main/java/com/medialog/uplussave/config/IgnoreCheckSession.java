package com.medialog.uplussave.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 세션 제외 대상 체크(?)
 * @filename IgnoreCheckSession.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreCheckSession {
	String type() default "";
}
