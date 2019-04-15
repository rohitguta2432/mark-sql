package com.common.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the mg_users database table.
 * 
 */
@Entity
@Table(name="mg_users")
@NamedQuery(name="MgUser.findAll", query="SELECT m FROM MgUser m")
public class MgUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="add_home")
	private String addHome;

	@Column(name="add_work")
	private String addWork;

	private String city;

	@Column(name="created_on")
	private Timestamp createdOn;

	private String email;

	private String gender;

	private String image;

	private String mobile;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_on")
	private Date modifiedOn;

	private String name;

	private String password;

	@Column(name="reset_hash")
	private int resetHash;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reset_time")
	private Date resetTime;

	@Column(name="role_id")
	private int roleId;

	private String status;

	public MgUser() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddHome() {
		return this.addHome;
	}

	public void setAddHome(String addHome) {
		this.addHome = addHome;
	}

	public String getAddWork() {
		return this.addWork;
	}

	public void setAddWork(String addWork) {
		this.addWork = addWork;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getModifiedOn() {
		return this.modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getResetHash() {
		return this.resetHash;
	}

	public void setResetHash(int resetHash) {
		this.resetHash = resetHash;
	}

	public Date getResetTime() {
		return this.resetTime;
	}

	public void setResetTime(Date resetTime) {
		this.resetTime = resetTime;
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}