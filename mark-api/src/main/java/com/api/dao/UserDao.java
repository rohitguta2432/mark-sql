package com.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.common.models.User;

@Repository
@Transactional
public interface UserDao extends JpaRepository<User, String> {

	User findOneByEmailAndStatus(String email, int status);
	User findOneByUsernameAndStatus(String username, int status);
	User findOneByUsernameAndPasswordAndStatus(String username, String password, int status);
	
	User findOneById(String id);
	User findOneByIdAndStatus(Integer id, int status);
	User findOneByIdAndCrnAndStatus(String id, String crn, int status);
	
	List<User> findAllByParentIdAndAuthoritiesAndCrnAndStatus(String parentId, String authority,String crn,int status);
	
	List<User> findAllByParentIdAndAuthoritiesAndStatus(String parentId, String authority,int status);
	
	List<User> findAllByAuthorities(String authority);
	
	List<User> findAllByAuthoritiesAndCrn(String authority,String crn);
	
	List<User> findAllByAuthoritiesAndParentIdAndStatus(String authority,String parentId,int status);
	
	User findOneByEmployeeIdAndStatus(String employeeId, int status);
	
	List<User> findAllByCrnAndAuthoritiesAndStatus(String crn, String authority, int status);
	
	
}
