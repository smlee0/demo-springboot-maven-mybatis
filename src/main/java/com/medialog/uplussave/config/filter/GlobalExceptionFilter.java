package com.medialog.uplussave.config.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.thymeleaf.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 글로벌 필터
 * @filename GlobalExceptionFilter.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Slf4j
@Component
public class GlobalExceptionFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(req, res);
        }
        catch (Exception ex) {
            HttpServletRequest request = (HttpServletRequest) req;
            String ipAddress = (StringUtils.isEmpty(request.getHeader("X-Forwarded-For")) ? request
                    .getRemoteAddr() : request.getHeader("X-Forwarded-For"));

            if(ex.toString().indexOf(" java.io.IOException: Broken pipe") < 0) {
                log.warn("[GlobalExceptionFilter] ipAddress:{} / userAgent:{}, requestUrl:{}", ipAddress, request
                        .getHeader(HttpHeaders.USER_AGENT), request.getRequestURL());
            }
        }
    }
}
