
package com.medialog.uplussave.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medialog.uplussave.common.util.Constants;
import com.medialog.uplussave.common.util.OSValidator;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

/**
 * Web Config
 * @filename WebConfig.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Configuration
@EnableAspectJAutoProxy
//@MapperScan(value= {""})
@PropertySources({
    @PropertySource("/config/custom/test.properties")
})
public class WebConfig implements WebMvcConfigurer {

	@Value("${env.file.path}")
	private String prefix_upload_path;

    @Value("${env.file.editor.path}")
    private String prefix_editor_upload_path;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 삭제예정 : 로컬에서 퍼블보는 용도.
        //registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //registry.addResourceHandler("/html/**").addResourceLocations("classpath:/html/");

    	String attachFilePath = "file:///"+prefix_editor_upload_path;

    	if( !OSValidator.isWindows() ) {
			attachFilePath = "file:"+prefix_editor_upload_path;
		}

    	registry.addResourceHandler("/attach/**").addResourceLocations( attachFilePath );


    	///POPUP 관련
      	String popupFilePath = "file:///"+prefix_upload_path;

    	if( !OSValidator.isWindows() ) {
    		popupFilePath = "file:"+prefix_upload_path;
		}

    	registry.addResourceHandler("/popup/**").addResourceLocations( popupFilePath + "/" );

    }

    @Autowired
    private CommonInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(deviceResolverHandlerInterceptor());

        registry.addInterceptor(interceptor).addPathPatterns("/**")
        		.excludePathPatterns("/upload/**").excludePathPatterns("/static/**")
        		.excludePathPatterns("/error").excludePathPatterns("/cgi-bin/**");

    }

//    @Bean
//    public ServletWebServerFactory factory() {
//        return new TomcatServletWebServerFactory() {
//            @Override
//            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
//
//            	String docBase = new File("C://temp").getAbsolutePath();
//
//            	System.out
//						.println("WebConfig.factory().new TomcatServletWebServerFactory() {...}.getTomcatWebServer() :: " + docBase);
//
//                tomcat.addContext("/aaab", docBase  );
//
//                return super.getTomcatWebServer(tomcat);
//            }
//        };
//    }

    @Bean
    public ObjectMapper getBeanObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper;
    }

    @Bean
    public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
        return new DeviceResolverHandlerInterceptor();
    }

    @Bean
    // 메세지 엑세서 MessageSource 사용해도 되는데, 메소드가 더 많음
    public MessageSourceAccessor getMessageSourceAccessor(){
    	return new MessageSourceAccessor(messageSource());
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        //WEB-INF 밑에 해당 폴더에서 properties를 찾는다.
        messageSource.setBasenames("messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(10); // reload messages every 10 seconds
        return messageSource;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    /**
     * lucy-xss-filter
     *
     * @return filterRegistration
     */
    @Bean
    public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() {
        FilterRegistrationBean<XssEscapeServletFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new XssEscapeServletFilter());
        filterRegistration.setOrder(1);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

    /**
     * jsonView 설정
     *
     * @return view
     */
    @Bean
    public View jsonView() {
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setPrettyPrint(true);

        return view;
    }

    /**
     * 에러페이지 필터 설정
     *
     * @return
     */
    @Bean
    public ErrorPageFilter errorPageFilter() {
        return new ErrorPageFilter();
    }

    /**
     * 에러페이지 설정
     *
     * @param filter
     * @return
     */
    @Bean
    public FilterRegistrationBean errorPageFilterRegistrationBean(ErrorPageFilter filter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(filter);

        filter.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, Constants.ERROR_VIEW_403));
        filter.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, Constants.ERROR_VIEW_404));
        filter.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_VIEW_500));
        filter.addErrorPages(new ErrorPage(Exception.class, Constants.ERROR_VIEW_404));
        filter.addErrorPages(new ErrorPage(Constants.ERROR_VIEW_404));

        return filterRegistrationBean;
    }

}
