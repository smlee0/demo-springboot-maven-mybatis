package com.medialog.uplussave.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 스프링 시큐리티 설정 관리
 * @filename SecurityConfig.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Slf4j
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
//
//	@Autowired
//	private LoginService homeService;
//
//	@Autowired
//    AuthProvider authProvider;
//
//	@Autowired
//    AuthFailureHandler authFailureHandler;
//
//    @Autowired
//    AuthSuccessHandler authSuccessHandler;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**인증되지 않은 요청중 AJAX요청일 경우 403으로 응답, AJAX요청이 아닐 경우 login으로 리다이렉트
     * @param url
     * @return
     */
    private AjaxAwareAuthenticationEntryPoint ajaxAwareAuthenticationEntryPoint(String url) {
        return new AjaxAwareAuthenticationEntryPoint(url);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(authProvider);
    }

	@Override
	public void configure(WebSecurity web) { // scr/main/resources/static 하위 폴더들 접근 가능하게 하기
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**" , "/images/**");
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/manage/**").authenticated()
                .antMatchers("/**").permitAll()
                .antMatchers("/manage/files/rename").permitAll()
            .anyRequest()
                .authenticated()
                .and().csrf().disable()
            .formLogin()
	            .loginPage("/login")
	            .loginProcessingUrl("/login-act")
//	            .failureUrl("/login/error")
//	            .successHandler(authSuccessHandler)
//	            .failureHandler(authFailureHandler)
	            .usernameParameter("username")
	            .passwordParameter("password").permitAll()
            .permitAll()
            .and()
                .logout()
                .logoutSuccessUrl("/login")
//	                .logoutSuccessHandler(authFailureHandler)
//	            .and()
//	                .exceptionHandling()
//	                .accessDeniedPage("/access-denied")
            .and().exceptionHandling()
                .authenticationEntryPoint(ajaxAwareAuthenticationEntryPoint("/login"))
                .accessDeniedPage("/access-denied")
            .and()
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/login")
                .sessionRegistry(sessionRegistry());

        http.csrf().disable();
	 }

 	// logout 후 login할 때 정상동작을 위함
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}