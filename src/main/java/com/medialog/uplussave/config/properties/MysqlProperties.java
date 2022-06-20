package com.medialog.uplussave.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

/**
 * Mysql전용 프로퍼티 클래스
 * @filename MysqlProperties.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Data
@ConfigurationProperties(prefix = MysqlProperties.PREFIX)
public class MysqlProperties implements DatabaseProperties {
	public static final String PREFIX = "spring.mysql.datasource";

	public static final boolean DEFAULT_INITIALIZE = false;

	private boolean initialize = DEFAULT_INITIALIZE;

	private String driverClassName;

	private String url;

	private String username;

	private String password;

	private int initialSize;

	private int maxActive;

	private int maxIdle;

	private int minIdle;

	private int maxWait;
}
