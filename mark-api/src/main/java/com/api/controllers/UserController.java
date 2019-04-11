package com.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.common.models.User;
import com.api.services.UserService;
import com.api.dto.Filter;
import com.api.dto.Response;
import com.api.utils.QueryParser;


@RestController
@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "{userId}")
	public ResponseEntity<Response<User>> getUserById(@PathVariable("userId") Integer userId) {
		return new ResponseEntity<>(
				new Response<>(HttpStatus.OK.value(), "User fetched successfully by userId.", userService.get(userId)),
				HttpStatus.OK);
	}

	@RequestMapping(value = "username/{userName}")
	public ResponseEntity<Response<User>> getUserByUserName(@PathVariable("userName") String userName) {
		return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(), "User fetched successfully by username.",
				userService.getUserByUserName(userName)), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<User>> save(@RequestBody User user) {
		return new ResponseEntity<>(
				new Response<>(HttpStatus.OK.value(), "User saved successfully.", userService.save(user)),
				HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
	@RequestMapping(value = "all", method = RequestMethod.GET)
	public ResponseEntity<Response<List<User>>> getAllUsers(@RequestParam(value = "q", required = false) String query,
			Pageable pageable) {
		List<Filter> filters = QueryParser.parse(query);

		return new ResponseEntity<>(
				new Response<>(HttpStatus.OK.value(), "Users fetched successfully",
						userService.getAllUsers(pageable, filters), userService.getAllUsers(null, filters).size()),
				HttpStatus.OK);
	}

	@RequestMapping(value = "delete/{id}/{updatedBy}", method = RequestMethod.DELETE)
	public ResponseEntity<Response<Boolean>> delete(@PathVariable("id") String id,
			@PathVariable("updatedBy") String updatedBy) {

		return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(), "User deleted successfully.", true),
				HttpStatus.OK);
	}

	@RequestMapping(value = "saveAll",method = RequestMethod.POST)
	public ResponseEntity<Response<List<User>>> uploadBulkVehicle(@RequestBody List<User> userList) {
		return new ResponseEntity<Response<List<User>>>(new Response<List<User>>(HttpStatus.OK.value(),
				"User upload successfully", userService.uploadBulkUser(userList)), HttpStatus.OK);
	}

}
