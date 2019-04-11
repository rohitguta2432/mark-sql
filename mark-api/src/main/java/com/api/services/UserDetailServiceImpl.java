package com.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.common.constants.Constants;
import com.common.models.MarkUser;
import com.common.models.User;
import com.api.dao.UserDao;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Rohit Raj
 *
 */

@Slf4j
@Service("userDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String userName) {

		User user = userDao.findOneByUsernameAndStatus(userName,Constants.STATUS_ACTIVE);

		if (user == null) {
			log.info("No user found with username.");
			throw new UsernameNotFoundException("No user found with username.");
		}

		return new MarkUser(user.getId(), user.getUsername(), Constants.P_STRING,
				AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities()),
				user.getEmail(), user.getStatus() > 0, user.getParentId(),user.getCrn(),user.getHashedUserImage());
	}
	
	public UserDetails loadUserByUsernameAndPassword(String userName, String password) {

		User user = userDao.findOneByUsernameAndPasswordAndStatus(userName,password,Constants.STATUS_ACTIVE);
		if (user == null) {
			log.info("No user found with username.");
			throw new UsernameNotFoundException("No user found with username.");
		}
		return new MarkUser(user.getId(), user.getUsername(), Constants.P_STRING,
				AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities()),
				user.getEmail(), user.getStatus() > 0,user.getParentId(),user.getCrn(),user.getHashedUserImage());
	}
}
