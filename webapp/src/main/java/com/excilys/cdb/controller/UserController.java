package com.excilys.cdb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.cdb.dto.UserDTO;

@Controller
@RequestMapping("user")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping
	@PreAuthorize("permitAll")
	public String getLogin(ModelMap model) {
		model.addAttribute("userDto", new UserDTO());

		return "loginPage";
	}
}
