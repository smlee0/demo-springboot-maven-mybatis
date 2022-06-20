package com.medialog.uplussave.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 공통 인터셉터
 * @filename CommonInterceptor.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Slf4j
@Component
public class CommonInterceptor extends HandlerInterceptorAdapter{

    private String mobileWebUrl;

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object Handler) throws Exception{

        if(request.getMethod().toUpperCase().equals("GET")&&request.getRequestURI().indexOf('.')==-1&&!"/".equals(request.getRequestURI())){

            Device device=DeviceUtils.getCurrentDevice(request);

            response.setHeader("referer", request.getRequestURI());
            log.debug("device {} context : {} uri : {} remote : {}  referer : {}",device,request.getContextPath(),request.getRequestURI(),request.getRemoteAddr(),request.getHeader("referer"));
        }


        //return true;
        return super.preHandle(request,response,Handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,HttpServletResponse response,Object obj,ModelAndView mav) throws Exception{

        DeviceUtils.getCurrentDevice(request);

    }

}
