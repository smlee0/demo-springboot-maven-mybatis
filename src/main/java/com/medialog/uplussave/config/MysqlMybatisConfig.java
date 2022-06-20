package com.medialog.uplussave.config;

import javax.sql.DataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MySql과 Mybatis 설정 관리
 * @filename MysqlMybatisConfig.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Configuration
@MapperScan(basePackages = MybatisConfig.BASE_PACKAGE, annotationClass = Mapper.class, sqlSessionFactoryRef = "mysqlSessionFactory")
public class MysqlMybatisConfig extends MybatisConfig {
	@Bean
    public SqlSessionFactory mysqlSessionFactory(@Qualifier("mysqlDataSource") DataSource mysqlDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        configureSqlSessionFactory(sessionFactoryBean, mysqlDataSource);
        return sessionFactoryBean.getObject();
    }
}
