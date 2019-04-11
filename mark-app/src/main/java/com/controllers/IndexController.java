package com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@GetMapping("")
	public String index() {

		return "redirect:/dashboard";
		// return "index";
	}

	/*
	 * @GetMapping("/login") public String signin() { return "signin"; }
	 */

}