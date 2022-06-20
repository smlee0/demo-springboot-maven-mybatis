package com.medialog.uplussave.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.medialog.uplussave.config.properties.MysqlProperties;

/**
 * Mysql전용 데이터베이스 설정 관리
 * @filename MysqlDatabaseConfig.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties(MysqlProperties.class)
public class MysqlDatabaseConfig extends DatabaseConfig {

    @Autowired
    private MysqlProperties mysqlProperties;

    @Override
    @Bean(name = "mysqlDataSource", destroyMethod = "close")
    public DataSource dataSource() {
        org.apache.tomcat.jdbc.pool.DataSource mysqlDataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        configureDataSource(mysqlDataSource, mysqlProperties);
        return mysqlDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("mysqlDataSource") DataSource mysqlDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(mysqlDataSource);
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }
}