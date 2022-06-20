package com.medialog.uplussave.config;


import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import com.medialog.uplussave.config.properties.DatabaseProperties;

/**
 * 데이터베이스 셋팅
 * @filename DatabaseConfig.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
public abstract class DatabaseConfig {
	@Bean
    public abstract DataSource dataSource();

    protected void configureDataSource(org.apache.tomcat.jdbc.pool.DataSource dataSource, DatabaseProperties databaseProperties) {
        //TODO dataSource settings
        dataSource.setDriverClassName(databaseProperties.getDriverClassName());
        dataSource.setUrl(databaseProperties.getUrl());
        dataSource.setUsername(databaseProperties.getUsername());
        dataSource.setPassword(databaseProperties.getPassword());
        dataSource.setInitialSize(100);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(true);
        dataSource.setValidationQuery("select 1");
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(120000);
        dataSource.setRemoveAbandonedTimeout(60);
        dataSource.setRemoveAbandoned(true);
        dataSource.setLogAbandoned(true);

//        dataSource.setMaxActive(30);
//        dataSource.setMaxIdle(10);
//        dataSource.setMinIdle(databaseProperties.getMinIdle());
//        dataSource.setMaxWait(1000);
    }
}
