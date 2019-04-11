package com.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
@Service
public class AuthServiceImpl implements AuthService {

	@Override
	public boolean logout(HttpServletRequest request) {

		try {
			HttpSession session = request.getSession(false);
			session.invalidate();

			// delete token here

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
