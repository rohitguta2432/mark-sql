package com.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.common.models.AuthenticationToken;

@Repository
@Transactional
public interface AuthenticationTokenDao extends JpaRepository<AuthenticationToken, String> {

	List<AuthenticationToken> findAllByUserId(Integer userId);	
	
	void deleteByUserId(Integer userId);
}
