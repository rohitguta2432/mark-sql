package com.api.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.common.constants.Constants;
import com.common.models.MarkUser;

public class RoleUtil {

	public static boolean isAdmin(MarkUser hitechUser) {

		for (GrantedAuthority authorities : hitechUser.getAuthorities()) {
			if ((authorities.getAuthority()).equals(Constants.ROLE_ADMIN)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isCurrentUserHasAdminRole() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MarkUser hitechUser = (MarkUser) auth.getPrincipal();
		for (GrantedAuthority authorities : hitechUser.getAuthorities()) {
			if ((authorities.getAuthority()).equals(Constants.ROLE_ADMIN)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isCurrentUserHasSuperAdminRole() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MarkUser hitechUser = (MarkUser) auth.getPrincipal();
		for (GrantedAuthority authorities : hitechUser.getAuthorities()) {
			if ((authorities.getAuthority()).equals(Constants.ROLE_SUPER_ADMIN)) {
				return true;
			}
		}

		return false;
	}

	public static MarkUser getCurrentUseInfo() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		return (MarkUser) auth.getPrincipal();
	}
}

