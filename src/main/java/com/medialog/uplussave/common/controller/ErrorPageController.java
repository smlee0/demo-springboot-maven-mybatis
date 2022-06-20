package com.medialog.uplussave.common.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.medialog.uplussave.common.util.Constants;
import lombok.extern.slf4j.Slf4j;

/**
 * 에러페이지 컨트롤러
 * @filename ErrorPageController.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Slf4j
@Controller
public class ErrorPageController implements ErrorController {


    @RequestMapping(value = Constants.ERROR_VIEW_404)
    public String forbiddenErrorPage(HttpServletRequest request, Model model, Exception exception)
    {
        String viewName = Constants.ERROR_VIEW_404;

        return viewName;
    }

    @Override
    public String getErrorPath()
    {
        return Constants.ERROR_PAGE;
    }
}
