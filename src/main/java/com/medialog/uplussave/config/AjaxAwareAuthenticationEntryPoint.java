package com.medialog.uplussave.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;

/**
 * 인증되지 않은 요청이 AJAX일경우는 403으로 응답 그외엔 파라메터의 url로 전환.
 * @filename AjaxAwareAuthenticationEntryPoint.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Slf4j
public class AjaxAwareAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint{

    public AjaxAwareAuthenticationEntryPoint(String loginUrl) {
        super(loginUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

//        String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");
        String ajaxHeader = request.getHeader("AJAX");

        boolean isAjax = "true".equals(ajaxHeader);
        log.debug(">>> isAjax: " + isAjax);

        if (isAjax) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Ajax Request Denied (Session Expired)");
        } else {
            super.commence(request, response, authException);
        }

    }
}