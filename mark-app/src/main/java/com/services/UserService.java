package com.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import com.common.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.AuthorizationUtil;
import com.util.Constants;
import com.util.MarkConstant;
import com.util.RestServiceUtil;
import com.util.URLConstants;
import com.util.UserRoleUtil;
import com.web.MarkWebException;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Rohit Raj
 *
 */
@Component
@Slf4j
public class UserService {

	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Autowired
	private RestServiceUtil restServiceUtil;

	@Autowired
	private FileUtilityService fileUtilityService;

	//@Value("${awss3.profileFolder}")
	private String profileFolder;

	@Autowired
	private AuthorizationUtil authorizationUtil;

	public List<User> getAllUsers() {
		int status;
		try {
			JsonNode response = restServiceUtil.makeRequest(URLConstants.GET_USERS, null, null, HttpMethod.GET);

			status = response.get(MarkConstant.STATUS_CODE).intValue();
			String errorMsg = response.get(MarkConstant.MESSAGE).asText();
			if (status != 200) {
				throw new MarkWebException(errorMsg, status);
			}

			try {
				if (!ObjectUtils.isEmpty(response.get(MarkConstant.DATA))) {
					String data = response.get(MarkConstant.DATA).toString();

					return OBJECT_MAPPER.readValue(data, new TypeReference<List<User>>() {
					});
				}
				return new ArrayList<>();
			} catch (IOException ex) {
				throw new MarkWebException("Response Converson error for users on web.", 500);
			}
		} catch (MarkWebException e) {

			e.printStackTrace();
			String errorCode = e.getMessage();
			if (errorCode.equals("401")) {
				log.error("Token Expired.");
				throw new MarkWebException("Error while fetching users", 401);
			}

			if (errorCode.equals("403")) {
				log.error("Access Denied this api.");
				throw new MarkWebException("Access Denied this api.", 403);
			}

			if (e.code == 500) {
				log.error(e.getMessage());
				throw new MarkWebException(e.getMessage(), e.code);
			}

			if (e.code == 404) {
				log.error(e.getMessage());
				throw new MarkWebException(e.getMessage(), e.code);
			}

			log.error("Error while fetching users");
			throw new MarkWebException("Error while fetching users");
		}
	}

	public User saveUser(User user) {
		if (ObjectUtils.isEmpty(user)) {
			throw new MarkWebException("user info cannot be empty");
		}
		try {
			/*if(ObjectUtils.isEmpty(user.getId())) {
				user.setCrn(UserRoleUtil.getCurrentUserCrn());
			}*/
			user.setFullName(user.getFirstName());
			if (!StringUtils.isEmpty(user.getLastName())) {
				user.setFullName(user.getFirstName() + " " + user.getLastName());
			}
			if (!StringUtils.isEmpty(user.getId())) {
				user.setUpdatedBy(UserRoleUtil.getCurrentUser());
			} else {
				user.setCreatedBy(UserRoleUtil.getCurrentUser());
			}
		} catch (NullPointerException ex) {
			log.info("Last name null.");
		}
		int status;
		try {

			user.setHashedUserImage("");

			if(ObjectUtils.isEmpty(user.getId())){
				
				if(UserRoleUtil.isCurrentUserSuperAdmin()){
					if(!ObjectUtils.isEmpty(user.getAuthorities()) &&
							com.common.constants.Constants.ROLE_ADMIN.equals(user.getAuthorities())){
						//user.setParentId(UserRoleUtil.getCurrentUserId());
					}
				}
				
				/*if(!ObjectUtils.isEmpty(user.getAuthorities()) &&
						com.common.constants.Constants.ROLE_FLEET_MANAGER.equals(user.getAuthorities())){
					
					if(UserRoleUtil.isCurrentUserSuperAdmin()){
						User companyOwner = this.getCompanyOwner(user.getCrn());
						if(!ObjectUtils.isEmpty(companyOwner) && !ObjectUtils.isEmpty(companyOwner.getId())){
							user.setParentId(companyOwner.getId());
						}
					}*/
					
					if(UserRoleUtil.isCurrentUserAdmin()){
						user.setCrn(UserRoleUtil.getCurrentUserCrn());
					}
				}
			
			
			JsonNode response = restServiceUtil.makeRequest(URLConstants.SAVE_USER_DETAILS, user, null,
					HttpMethod.POST);
			status = response.get(MarkConstant.STATUS_CODE).intValue();
			String errorMsg = response.get(MarkConstant.MESSAGE).asText();
			if (status != 200) {
				throw new MarkWebException(errorMsg, status);
			}

			try {

				if (!ObjectUtils.isEmpty(response.get(MarkConstant.DATA))) {
					String data = response.get(MarkConstant.DATA).toString();
					return OBJECT_MAPPER.readValue(data, new TypeReference<User>() {
					});
				}

				return new User();
			} catch (IOException ex) {
				throw new MarkWebException("Response Converson error for users on web.", 500);
			}

		} catch (MarkWebException e) {
			String errorCode = e.getMessage();
			if (errorCode.equals("401")) {
				log.error("Token Expired.");
				throw new MarkWebException("Error while saving user information", 401);
			}

			if (errorCode.equals("403")) {
				log.error("Access Denied this api.");
				throw new MarkWebException("Access Denied this api.", 403);
			}

			if (e.code == 500) {
				log.error(e.getMessage());
				throw new MarkWebException(e.getMessage(), e.code);
			}

			if (e.code == 404) {
				log.error(e.getMessage());
				throw new MarkWebException(e.getMessage(), e.code);
			}

			log.error("Error while saving user information");
			throw new MarkWebException("Error while saving user information");
		}
	}

	public Map<String, Object> isUserExistByUserName(String userName) {

		Map<String, Object> data = new HashMap<>();
		User user = this.getUserByUserName(userName);
		if (ObjectUtils.isEmpty(user.getId())) {
			data.put(Constants.CODE, 404);
		} else {
			data.put(Constants.CODE, 200);
			data.put(Constants.USER, user);
		}

		return data;
	}

	public User getCompanyOwner(String crn) {
		int status;
		String url = String.format(URLConstants.GET_COMPANY_OWNER, crn);
		try {

			JsonNode response = authorizationUtil.makeRequestGet(url);

			status = response.get(MarkConstant.STATUS_CODE).intValue();
			String errorMsg = response.get(MarkConstant.MESSAGE).asText();
			if (status != 200) {
				throw new MarkWebException(errorMsg, status);
			}

			try {
				if (!ObjectUtils.isEmpty(response.get(MarkConstant.DATA))) {
					String data = response.get(MarkConstant.DATA).toString();
					return OBJECT_MAPPER.readValue(data, new TypeReference<User>() {
					});
				}
				return new User();
			} catch (IOException ex) {
				throw new MarkWebException("Response Converson error for users on web.", 500);
			}

		} catch (MarkWebException e) {

			String errorCode = e.getMessage();
			if (errorCode.equals("401")) {
				log.error("Token Expired.");
				throw new MarkWebException("Error while fetching form user by username", 401);
			}

			if (errorCode.equals("403")) {
				log.error("Access Denied this api.");
				throw new MarkWebException("Access Denied this api.", 403);
			}

			if (e.code == 500) {
				log.error(e.getMessage());
				throw new MarkWebException(e.getMessage(), e.code);
			}

			if (e.code == 404) {
				log.error(e.getMessage());
				throw new MarkWebException(e.getMessage(), e.code);
			}

			log.error("Error while fetching user by username.");
			throw new MarkWebException("Error while fetching user by username.");
		}
	}
	
	public User getUserByUserName(String username) {
		int status;
		String url = String.format(URLConstants.GET_USER_BY_USER_NAME, username);
		try {

			JsonNode response = authorizationUtil.makeRequestGet(url);

			status = response.get(MarkConstant.STATUS_CODE).intValue();
			String errorMsg = response.get(MarkConstant.MESSAGE).asText();
			if (status != 200) {
				throw new MarkWebException(errorMsg, status);
			}

			try {
				if (!ObjectUtils.isEmpty(response.get(MarkConstant.DATA))) {
					String data = response.get(MarkConstant.DATA).toString();
					return OBJECT_MAPPER.readValue(data, new TypeReference<User>() {
					});
				}
				return new User();
			} catch (IOException ex) {
				throw new MarkWebException("Response Converson error for users on web.", 500);
			}

		} catch (MarkWebException e) {

			String errorCode = e.getMessage();
			if (errorCode.equals("401")) {
				log.error("Token Expired.");
				throw new MarkWebException("Error while fetching form user by username", 401);
			}

			if (errorCode.equals("403")) {
				log.error("Access Denied this api.");
				throw new MarkWebException("Access Denied this api.", 403);
			}

			if (e.code == 500) {
				log.error(e.getMessage());
				throw new MarkWebException(e.getMessage(), e.code);
			}

			if (e.code == 404) {
				log.error(e.getMessage());
				throw new MarkWebException(e.getMessage(), e.code);
			}

			log.error("Error while fetching user by username.");
			throw new MarkWebException("Error while fetching user by username.");
		}
	}

	private void validateUser(User user) {

		if (ObjectUtils.isEmpty(user)) {
			log.info("user canot be null occur on web validation while saving.");
			throw new MarkWebException("user cannot empty while saving");
		}

		try {

			User existingUser = this.getUserByUserName(user.getUsername());
			if (!ObjectUtils.isEmpty(existingUser) && (ObjectUtils.isEmpty(existingUser.getHashedUserImage())
					|| !existingUser.getHashedUserImage().equals(user.getHashedUserImage()))) {
				if (!ObjectUtils.isEmpty(user.getUsername())) {
					/*user.setHashedUserImage(
							fileUtilityService.getFileName(user.getUserProfileFileUpload().getOriginalFilename()));
					fileUtilityService.saveFile(user.getUserProfileFileUpload(), user.getUsername(), "profile",
							user.getHashedUserImage());*/
				}
			}

		} catch (MarkWebException e) {
			log.info("error occur on uploading profile while user saving.");
			throw new MarkWebException("user cannot empty while saving", e);
		}
	}

	public User getUserById(Integer userId) {

		int status;
		String url = String.format(URLConstants.GET_USER_DETAIL_BY_ID, userId);
		try {
			JsonNode response = restServiceUtil.makeRequest(url, null, null, HttpMethod.GET);

			status = response.get(MarkConstant.STATUS_CODE).intValue();
			String errorMsg = response.get(MarkConstant.MESSAGE).asText();
			if (status != 200) {
				throw new MarkWebException(errorMsg, status);
			}

			try {
				if (!ObjectUtils.isEmpty(response.get(MarkConstant.DATA))) {
					String data = response.get(MarkConstant.DATA).toString();
					return OBJECT_MAPPER.readValue(data, new TypeReference<User>() {
					});
				}
				return new User();
			} catch (IOException ex) {
				throw new MarkWebException("Response Converson error for User on web.", 500);
			}
		} catch (MarkWebException e) {

			String errorCode = e.getMessage();
			if (errorCode.equals("401")) {
				log.error("Token Expired.");
				throw new MarkWebException("Error while fetching User by id", 401);
			}

			if (errorCode.equals("403")) {
				log.error("Access Denied this api.");
				throw new MarkWebException("Access Denied this api.", 403);
			}

			if (e.code == 500) {
				log.error(e.getMessage());
				throw new MarkWebException(e.getMessage(), e.code);
			}

			if (e.code == 404) {
				log.error(e.getMessage());
				throw new MarkWebException(e.getMessage(), e.code);
			}
			log.error("Error while fetching User by id");
			throw new MarkWebException("Error while fetching User by id");
		}
	}

	public List<User> getUserEmployeeDetailsById(String userId) {

		int status;
		String url = String.format(URLConstants.GET_USER_EMPLOYEE_DETAIL_BY_ID, userId);
		try {
			JsonNode response = restServiceUtil.makeRequest(url, null, null, HttpMethod.GET);

			status = response.get(MarkConstant.STATUS_CODE).intValue();
			String errorMsg = response.get(MarkConstant.MESSAGE).asText();
			if (status != 200) {
				throw new MarkWebException(errorMsg, status);
			}

			try {
				if (!ObjectUtils.isEmpty(response.get(MarkConstant.DATA))) {
					String data = response.get(MarkConstant.DATA).toString();
					return OBJECT_MAPPER.readValue(data, new TypeReference<List<User>>() {
					});
				}
				return new ArrayList<User>();
			} catch (IOException ex) {
				throw new MarkWebException("Response Converson error for User on web.", 500);
			}
		} catch (MarkWebException e) {

			String errorCode = e.getMessage();
			if (errorCode.equals("401")) {
				log.error("Token Expired.");
				throw new MarkWebException("Error while fetching User by id", 401);
			}

			if (errorCode.equals("403")) {
				log.error("Access Denied this api.");
				throw new MarkWebException("Access Denied this api.", 403);
			}

			if (e.code == 500) {
				log.error(e.getMessage());
				throw new MarkWebException(e.getMessage(), e.code);
			}

			if (e.code == 404) {
				log.error(e.getMessage());
				throw new MarkWebException(e.getMessage(), e.code);
			}
			log.error("Error while fetching User details by id");
			throw new MarkWebException("Error while fetching User details by id");
		}
	}
	
}
