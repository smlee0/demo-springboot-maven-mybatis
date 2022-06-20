package com.medialog.uplussave.config.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.View;
import com.medialog.uplussave.common.service.CommonService;
import lombok.extern.slf4j.Slf4j;

/**
 * 글로벌 익셉션 핸들러
 * @filename GlobalExceptionHandler.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private static CommonService commonService;

    @Autowired
    private View jsonView;



    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,

            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,

            ConversionNotSupportedException.class,
            TypeMismatchException.class,

            MethodArgumentNotValidException.class,
            MissingServletRequestPartException.class,
            BindException.class,
            NoHandlerFoundException.class
    })
    public ModelAndView pageNotFoundErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception
    {
        final String requestUrl = request.getRequestURL().toString();
        log.error(String.format("[pageNotFoundErrorHandler] requestURL:\"%s\"", requestUrl), ex);


        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null)
        {
            throw ex;
        }
        else if (commonService.isAjaxRequest())
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            ModelAndView mav = new ModelAndView(jsonView);
            mav.addObject("code", HttpServletResponse.SC_NOT_FOUND);
            mav.addObject("url", requestUrl);

            return mav;
        }
        else
        {
            String viewName = "redirect:/";

            ModelAndView mav = new ModelAndView(viewName);

            return mav;
        }
    }


//    @ExceptionHandler(ApiRequestException.class)
//    public ModelAndView apiRequestExceptionHandler(HttpServletRequest request, HttpServletResponse response, ApiRequestException ex) throws Exception
//    {
//        final String requestUrl = request.getRequestURL().toString();
//
//        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null)
//        {
//            throw ex;
//        }
//        else
//        {
//            response.setStatus(ex.getErrorType().getCode());
//
//            ModelAndView mav = new ModelAndView(jsonView);
//            mav.addObject("code", ex.getErrorType().getCode());
//            mav.addObject("url", requestUrl);
//            mav.addObject("error", ex.getMessage());
//
//            return mav;
//        }
//    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public ResponseEntity<String> fileSizeLimitExceeded(Exception e) {
        log.info(HttpStatus.INTERNAL_SERVER_ERROR.value() + " : " + e.getMessage());
        String result =  e.getMessage();
        ResponseEntity<String> responseEntity = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR); // 500 반환

//        model.addAttribute("errorTitle", "Uploaded Resource Size Exceeds Limit.");
//        model.addAttribute("errorDescription", "Uploaded resource's size exceeds limit. Please decrease request size.");
        return responseEntity;
    }


    @ExceptionHandler(Exception.class)
    public ModelAndView systemErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        final String requestUrl = request.getRequestURL().toString();
        log.error(String.format("[systemErrorHandler] requestURL:\"%s\"", requestUrl), ex);


        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null) {
            throw ex;
        }
        else if (commonService.isAjaxRequest()) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            ModelAndView mav = new ModelAndView(jsonView);
            mav.addObject("code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mav.addObject("url", requestUrl);
            mav.addObject("error", ex.getMessage());

            return mav;
        }
        else {
            String viewName = "redirect:/";

            ModelAndView mav = new ModelAndView(viewName);
            mav.addObject("url", requestUrl);

            return mav;
        }
    }
}
