package com.api.controllers;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.api.dto.Response;
import com.common.models.AuthenticationToken;
import com.common.models.User;
import com.common.models.MarkUser;
import com.api.services.AuthenticationTokenService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Rohit Raj
 *
 */
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AuthenticationController {

	@Autowired
	private AuthenticationTokenService authenticationTokenService;

	@Value("${mark.token.inactivity.time}")
	private int inactivityTime;

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Map<String, Object>>> generateAuthentication(@RequestBody User user,
			HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {

		Map<String, Object> dataMap = authenticationTokenService.generateAuthToken(user);
		
		return ResponseEntity
				.ok(new Response<Map<String, Object>>(HttpStatus.OK.value(), "Token generated successfully", dataMap));
	}

	@RequestMapping(value = "logout/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Response<Boolean>> logout(@PathVariable(value = "userId") long userId) {

		try {
			//authenticationTokenService.deleteAuthenticationToken(userId);
		} catch (Exception e) {
			log.error("Error while deleting auth token for user id : ", userId, e);
			throw e;
		}

		return ResponseEntity
				.ok(new Response<Boolean>(HttpStatus.OK.value(), "User auth token deleted successfully", Boolean.TRUE));
	}

	@RequestMapping(value = "logout/token", method = RequestMethod.GET)
	public ResponseEntity<Response<Boolean>> deleteAuthToken() {

		long userId = 0;
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			MarkUser markUser = (MarkUser) authentication.getPrincipal();
			/*userId = vedantaUser.getId();
			authenticationTokenService.deleteAuthenticationToken(vedantaUser.getId());*/
		} catch (Exception e) {
			log.error("Error while deleting auth token for user id." + userId, e);
			throw e;
		}

		return ResponseEntity
				.ok(new Response<Boolean>(HttpStatus.OK.value(), "User auth token deleted successfully", Boolean.TRUE));
	}

	private String persistAuthToken(Long userId, String token, boolean isAdminRequest, String deviceToken,
			Long businessUnitId) throws JsonParseException, JsonMappingException, IOException {

		/*AuthenticationToken existingAuthToken = authenticationTokenService.getAuthTokenByUserId(userId);

		if (existingAuthToken != null) {
			if (!existingAuthToken.isAdminToken() && isTokenValid(existingAuthToken)) {
				if (!StringUtils.isEmpty(existingAuthToken.getDeviceToken())
						&& existingAuthToken.getDeviceToken().equals(deviceToken)) {
					log.info("Authentication token exists for user,", userId, " hence using it.");
					authenticationTokenService.updateLastAccessedTime(existingAuthToken.getId());
					return existingAuthToken.getAuthToken();
				} else {
					log.info("Authentication token exists for user,", userId, " hence blocking it.");
					throw new DuplicateLoginException("Duplicate login is not allowed.");
				}
			} else {
				authenticationTokenService.deleteAuthenticationToken(userId);
				log.info("Persisting auth token to DB.");
				authenticationTokenService
						.save(new AuthenticationToken(userId, token, isAdminRequest, deviceToken, businessUnitId));
				return token;
			}
		} else {
			log.info("Persisting auth token to DB.");
			authenticationTokenService
					.save(new AuthenticationToken(userId, token, isAdminRequest, deviceToken, businessUnitId));
			return token;
		}*/
		return null;
	}

	private String persistAuthToken(Long userId, String token, boolean isAdminRequest, Long businessUnitId)
			throws JsonParseException, JsonMappingException, IOException {

		/*AuthenticationToken existingAuthToken = authenticationTokenService.getAuthTokenByUserId(userId);

		if (existingAuthToken != null) {
			if (!existingAuthToken.isAdminToken() && isTokenValid(existingAuthToken)) {

				log.info("Authentication token exists for user,", userId, " hence blocking it.");
				throw new DuplicateLoginException("Duplicate login is not allowed.");
			} else {
				authenticationTokenService.deleteAuthenticationToken(userId);
				log.info("Persisting auth token to DB.");
				authenticationTokenService.save(new AuthenticationToken(userId, token, isAdminRequest, businessUnitId));
				return token;
			}
		} else {
			log.info("Persisting auth token to DB.");
			authenticationTokenService.save(new AuthenticationToken(userId, token, isAdminRequest, businessUnitId));
			return token;
		}*/
		return null;
	}

	private MarkUser getUserDetails(String userName, JSONObject ldapResponse, Long businessUnitId) {
		/*try {
			return (MarkUser) this.userDetailsService.loadUserByUsername(userName);
		} catch (UsernameNotFoundException e) {
			User user = userDetailsService.saveLDAPUser(createUser(userName, ldapResponse, businessUnitId));

			return new MarkUser(user.getId(), user.getName(), Constants.P_STRING, user.getLoginId(),
					AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities()), user.getEmail(),
					user.getEmployeeId(), user.getStatus() > 0, user.getIsWithoutAdAllowed(), user.getBusinessUnitId(),
					user.getPlantCode(), user.getPlantId());
		}*/
		return null;
	}

	private MarkUser getUserDetails(String userName, Long businessUnitId) {

		/*MarkUser userDetails = (MarkUser) this.userDetailsService.loadUserByUsernameAndBuId(userName,
				businessUnitId);

		if (userDetails == null) {
			log.error("No user found with login Id : ", userName);
			throw new UsernameNotFoundException("No user found with login Id : " + userName);
		}*/
		return null;
	}

	private boolean isTokenValid(AuthenticationToken existingAuthToken) {
		long timeDIff = System.currentTimeMillis() - existingAuthToken.getLastAccessed();
		return TimeUnit.MILLISECONDS.toMinutes(timeDIff) < inactivityTime;
	}
}
