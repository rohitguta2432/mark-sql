package com.api.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.common.models.AuthenticationToken;
import com.common.models.MarkUser;
import com.common.models.User;
import com.api.services.helpers.TokenUtils;
import com.api.utils.RoleUtil;
import com.common.constants.Constants;
import com.api.dao.AuthenticationTokenDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("authenticationTokenService")
public class AuthenticationTokenServiceImpl implements AuthenticationTokenService {

	@Autowired
	private AuthenticationTokenDao authenticationTokenDao;

	/** The token expiration time. */
	@Value("${mark.token.expiration}")
	private long tokenExpirationTime;

	/** The inactivity time. */
	@Value("${mark.token.inactivity.time}")
	private int inactivityTime;

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private UserDetailServiceImpl userDetailsService;

	@Override
	// @Transactional(rollbackFor = Exception.class)
	public AuthenticationToken save(AuthenticationToken authenticationToken) {

		if (authenticationToken == null) {
			log.info("Authentication object cannot be null/empty");
			throw new IllegalArgumentException("Authentication object cannot be null/empty");
		}

		if (StringUtils.isEmpty(authenticationToken.getUserId())) {
			log.info("Invalid user id.");
			throw new IllegalArgumentException("Invalid user id.");
		}

		if (StringUtils.isEmpty(authenticationToken.getAuthToken())) {
			log.info("Authentication token cannot be null/empty");
			throw new IllegalArgumentException("Authentication token cannot be null/empty");
		}

		AuthenticationToken existingToken = this.getAuthTokenByUserId(authenticationToken.getUserId());
		if (existingToken != null) {
			this.deleteAuthenticationToken(authenticationToken.getUserId());
		}

		return authenticationTokenDao.save(authenticationToken);
	}

	@Override
	public Map<String, Object> generateAuthToken(User user) {
		Map<String, Object> dataMap = new HashMap<>();
		MarkUser hitechUser = (MarkUser) userDetailsService.loadUserByUsernameAndPassword(user.getUsername(),
				user.getPassword());

		AuthenticationToken authenticationToken = this.getAuthTokenByUserId(hitechUser.getId());
		String token = "";
		boolean isExpired = false;
		if (ObjectUtils.isEmpty(authenticationToken) || tokenUtils.isTokenExpired(authenticationToken.getAuthToken())) {
			isExpired = true;
		}
		if (isExpired) {
			token = tokenUtils.generateToken(hitechUser);
		} else {
			token = authenticationToken.getAuthToken();
		}
		boolean isAdmin = RoleUtil.isAdmin(hitechUser);
		AuthenticationToken newAuthenticationToken = new AuthenticationToken(hitechUser.getId(), token, isAdmin);

		newAuthenticationToken = this.save(newAuthenticationToken);

		if (StringUtils.isEmpty(newAuthenticationToken.getId())) {
			log.info("Error while saving generated token.");
			throw new IllegalArgumentException("Error while saving generated token.");
		}

		dataMap.put(Constants.AUTH_TOKEN, token);
		dataMap.put(Constants.USER, hitechUser);

		return dataMap;
	}

	@Override
	public AuthenticationToken getAuthTokenByUserId(Integer userId) {

		AuthenticationToken authenticationToken = null;
		if (StringUtils.isEmpty(userId)) {
			log.info("Invalid user id.");
			throw new IllegalArgumentException("Invalid user id.");
		}
		List<AuthenticationToken> authToken = authenticationTokenDao.findAllByUserId(userId);
		if (!ObjectUtils.isEmpty(authToken)) {
			authenticationToken = authToken.get(0);
		}

		return authenticationToken;
	}

	@Override
	public AuthenticationToken update(Integer id, AuthenticationToken entity) {

		return null;
	}

	@Override
	public AuthenticationToken get(Integer id) {

		return null;
	}

	@Override
	public void deleteAuthenticationToken(Integer userId) {

		if (StringUtils.isEmpty(userId)) {
			log.info("Invalid user id.");
			throw new IllegalArgumentException("Invalid user id.");
		}
		authenticationTokenDao.deleteByUserId(userId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateLastAccessedTime(AuthenticationToken existingAuthenticationToken) {

		if (ObjectUtils.isEmpty(existingAuthenticationToken)) {
			log.info("Invalid auth token id.");
			throw new IllegalArgumentException("Invalid auth token id.");
		}
		existingAuthenticationToken.setLastAccessed(new Date().getTime());
		authenticationTokenDao.save(existingAuthenticationToken);
	}
}
