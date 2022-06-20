package com.medialog.uplussave.config;

import java.io.IOException;
import javax.sql.DataSource;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Mybatis 설정에 필요한 세팅정보 관리
 * @filename MybatisConfig.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
public abstract class MybatisConfig {
	public static final String BASE_PACKAGE = "com.medialog.uplussave";
//    public static final String TYPE_ALIASES_PACKAGE = "medialog.mhp.**.vo";
    public static final String TYPE_ALIASES_PACKAGE = "com.medialog.uplussave.**.dto";
    //public static final String CONFIG_LOCATION_PATH = "classpath:META-INF/mybatis/mybatis-config.xml";
    public static final String MAPPER_LOCATIONS_PATH = "classpath*:com/medialog/uplussave/**/mapper/*.xml";

    protected void configureSqlSessionFactory(SqlSessionFactoryBean sessionFactoryBean, DataSource dataSource) throws IOException {
        PathMatchingResourcePatternResolver pathResolver = new PathMatchingResourcePatternResolver();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
        //sessionFactoryBean.setConfigLocation(pathResolver.getResource(CONFIG_LOCATION_PATH));
        sessionFactoryBean.setMapperLocations(pathResolver.getResources(MAPPER_LOCATIONS_PATH));
        sessionFactoryBean.setConfiguration( getSqlSessionConfig() );
    }


    private Configuration getSqlSessionConfig() {
        Configuration sqlSessionConfig = new Configuration();
        sqlSessionConfig.setMapUnderscoreToCamelCase(true);
        sqlSessionConfig.setCacheEnabled(false);
        sqlSessionConfig.setDefaultStatementTimeout(10000);
        return sqlSessionConfig;
    }

}
