package com.api.services;

import java.util.Map;

import com.common.models.AuthenticationToken;
import com.common.models.User;

public interface AuthenticationTokenService  extends MarkService<AuthenticationToken> {

	AuthenticationToken getAuthTokenByUserId(Integer userId);

	Map<String, Object> generateAuthToken(User user);

	void deleteAuthenticationToken(Integer userId);

	void updateLastAccessedTime(AuthenticationToken existingAuthenticationToken);

}
