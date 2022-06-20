package com.medialog.uplussave.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * SNS공유 컨트롤러
 *
 * @filename ShareController.java
 * @author Shim Sung Jin
 * @since 2022-06-15
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Slf4j
@Controller
public class ShareController {

	@RequestMapping("/sample/share")
	private String shareSns(HttpServletRequest request, Model model) {

		return "/pc/sample/share/view";
	}

}
