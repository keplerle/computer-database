package com.excilys.cdb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

	@PostMapping
	public String postLogin(@ModelAttribute("userDto") UserDTO userDto, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			return "500";
		}
				return null;

	}
}
