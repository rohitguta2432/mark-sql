package com.services;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

	public boolean logout(HttpServletRequest request);
	
}
