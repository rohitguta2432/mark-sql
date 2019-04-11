package com.common.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

import com.common.constants.Constants;

import lombok.Data;


/**
 * @author Rohit Raj
 *
 */
@Data
@Entity
@Table(name="users")
public class User extends AbstractEntity { 

  private static final long serialVersionUID = 1L;	
	
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;
  
  private String firstName;
  private String lastName;
  private String fullName;
  private String email;
  private int status = Constants.STATUS_ACTIVE;
  private String username;
  private String parentId = Constants.ADMIN;
  private String password;
  private String phone;
  private String authorities;
  private String crn = "super_admin";
  private String hashedUserImage;
  private String dob;
  private double yearExperience;
  private String availability;
  private String employeeId;
  
 @Transient
  private String error;
  
  @Transient
  private int errorStatus=1;
  
  @Transient
  private String companyName;

  @Transient
  private String parentName;
  
  @Transient
  private String parentType;
}
