package com.medialog.uplussave.config.properties;

/**
 * 데이터베이스 프로퍼티 인터페이스
 * @filename DatabaseProperties.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
public interface DatabaseProperties {
	public String getDriverClassName();

	public String getUrl();

	public String getUsername();

	public String getPassword();

	public boolean isInitialize();

	public int getInitialSize();

	public int getMaxActive();

	public int getMaxIdle();

	public int getMinIdle();

	public int getMaxWait();
}
