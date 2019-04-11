package com.util;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
public class UserRoleUtil {

	public static String getCurrentUserCrn(){
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false);
		String crn = session.getAttribute(Constants.CRN).toString();
		if(crn.length()>2){
			return crn.substring(1 , crn.length()-1);
		}
		return null;
	}
	
	public static Integer getCurrentUserId(){
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false);		
			
		Integer userId = (Integer)session.getAttribute(Constants.USER_ID);
		/*if(userId.length()>2){
			return userId;
		}*/
		return userId;
	}
	
	public static String getCurrentUser(){
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false);	
		
		return session.getAttribute(Constants.USER).toString();
	}
	
	public static boolean isCurrentUserSuperAdmin(){
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false);	
		String authoritiesString = session.getAttribute(Constants.AUTHORITIES).toString();
		if(authoritiesString.contains(Constants.ROLE_SUPER_ADMIN)){
			return true;
		}
		return false;
	}
	
	public static boolean isCurrentUserAdmin(){
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false);	
		System.out.println("session AUTHORITIES: "+session.getAttribute(Constants.AUTHORITIES));
		String authoritiesString = session.getAttribute(Constants.AUTHORITIES).toString();
		if(authoritiesString.contains(Constants.ROLE_ADMIN)){
			return true;
		}
		return false;
	}

	public static boolean isCurrentUserIsRoleAdmin(){
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false);	
		String authoritiesString = session.getAttribute(Constants.AUTHORITIES).toString();
		if(authoritiesString.contains(Constants.ROLE_ADMIN)){
			return true;
		}
		return false;
	}
}