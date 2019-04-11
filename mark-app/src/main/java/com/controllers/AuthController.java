package com.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.config.Layout;
import com.services.AuthService;
import com.services.UserService;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
@Controller
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@GetMapping("/login")
	@Layout(value = "layouts/noLayout")
	public String signin(Model model) {
		return "signin";
	}

	@GetMapping("/index")
	public String index(Model model) {
	return "index";
	}
	
	@GetMapping("/logout")
	@Layout(value = "layouts/noLayout")
	public String logout(HttpServletRequest request) {

		authService.logout(request);
		return "signin";
	}

	@GetMapping("/access-denied")
	public String accessDenied(Model model) {

		return "access-denied";
	}
	
	@GetMapping("/error")
	public String error(Model model) {

		return "error";
	}
}
