package com.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.common.models.User;

import com.services.UserService;
import com.util.UserRoleUtil;

import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping(value = "user")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;


	@GetMapping("create")
	public String userCreate(Model model) {
		log.info("fetching list of all user");
		model.addAttribute("title", "Create");
		return "user/create";
	}

	@GetMapping("")
	public String usersList(Model model) {
		log.info("fetching list of all companies");
		
		model.addAttribute("userListActive", true);
		model.addAttribute("setting", true);
		model.addAttribute("title" , "title");
		return "user/view";
	}

	@PostMapping(value = "save")
	public String saveUser(@ModelAttribute("user") User user, Model model, RedirectAttributes redirectAttributes) {
		log.info("saving user details");

		boolean flag = user.getId().equals("") ? false : true;
		user.setId(user.getId().equals("") ? null : user.getId());
		user = userService.saveUser(user);
		redirectAttributes.addFlashAttribute("msg", flag ? "User updated successfully" : "User saved successfully");
		
		redirectAttributes.addFlashAttribute("title", "title");
		return "redirect:/user/userDetails/"+user.getParentId();
	}

	@PostMapping(value = "uploadProfile")
	public @ResponseBody String uploadProfileUser(@ModelAttribute("user") User user, Model model) {
		log.info("saving user details");
		model.addAttribute("user", userService.saveUser(user));
		return "user/create";
	}

	@GetMapping("get-user/{userName}")
	public @ResponseBody Map<String, Object> getUserByUserName(@PathVariable("userName") String userName) {
		log.info("fetching user by user name using ajax.");
		return userService.isUserExistByUserName(userName);
	}

	@GetMapping("userDetails/{userId}")
	public String getUserEmployeeDetailsById(@PathVariable("userId") String userId,
			@RequestParam( value="title" , required = false ) String title,
			Model model) {
		log.info("fetching user details by user id");
		model.addAttribute("users", userService.getUserEmployeeDetailsById(userId));
		if(!ObjectUtils.isEmpty(title))
			model.addAttribute("title" , title);
		return "user/view";
	}

	@GetMapping(value = "update/{id}")
	public String updateDevice(@PathVariable("id") String id, Model model) {

		model.addAttribute("disabledAuthorities", true);
		model.addAttribute("title", "Update");
		return "user/create";
	}

	@GetMapping("getProfileById")
	public @ResponseBody User getProfileById() {
		log.info("fetching user by user id");
		Integer userId = UserRoleUtil.getCurrentUserId();
		return userService.getUserById(userId);
	}

	@PostMapping(value = "updateUserProfile")
	public String updateUserProfile(@ModelAttribute("user") User user, Model model,
			RedirectAttributes redirectAttributes) {
		log.info("saving user details");

		user.setId(UserRoleUtil.getCurrentUserId());
		boolean flag = user.getId().equals("") ? false : true;
		user.setId(user.getId().equals("") ? null : user.getId());
		user = userService.saveUser(user);
		redirectAttributes.addFlashAttribute("msg", flag ? "User updated successfully" : "User saved successfully");

		return "redirect:/user";
	}
}
