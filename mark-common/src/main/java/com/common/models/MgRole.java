package com.common.models;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mg_roles database table.
 * 
 */
@Entity
@Table(name="mg_roles")
@NamedQuery(name="MgRole.findAll", query="SELECT m FROM MgRole m")
public class MgRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="role_id")
	private int roleId;

	@Lob
	private String description;

	private String destination;

	@Column(name="role_name")
	private String roleName;

	public MgRole() {
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDestination() {
		return this.destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}