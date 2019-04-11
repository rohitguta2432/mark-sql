package com.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.models.AuthenticationToken;

public interface AuthenticationTokenDao extends JpaRepository<AuthenticationToken, String> {

	List<AuthenticationToken> findAllByUserId(Integer userId);	
	
	void deleteByUserId(Integer userId);
}
