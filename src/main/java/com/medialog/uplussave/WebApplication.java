package com.medialog.uplussave;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 스프링부트 실행 클래스
 * @filename WebApplication.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@SpringBootApplication
public class WebApplication extends ServletInitializer {

    /**
     * 실행 메인
     * @param args
     * @author Lee Se Min
     * @date 2022-06-10
     */
	public static void main(String[] args) {
		SpringApplication application = new SpringApplicationBuilder()
				.sources(WebApplication.class)
//				.listeners(new ApplicationPidFileWriter("./mono-front.pid"))
				.build();

		setDefaultProfile(application);

		application.run(args);

//		SpringApplication.run(WebApplication.class, args);
	}

	/**
	 * spring default profile setting - if null
	 * @param application
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static void setDefaultProfile(SpringApplication application) {
		String profile = System.getProperty("spring.profiles.active");
		if (StringUtils.isEmpty(profile)) {
			application.setAdditionalProfiles("local");
		}
	}

}
