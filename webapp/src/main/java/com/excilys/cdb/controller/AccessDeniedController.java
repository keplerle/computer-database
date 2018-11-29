package com.excilys.cdb.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("accessDenied")
public class AccessDeniedController {
	Logger logger = LoggerFactory.getLogger(AccessDeniedController.class);

	@GetMapping
	public String getAccessDenied(ModelMap model) {
		return "403";
	}

}
