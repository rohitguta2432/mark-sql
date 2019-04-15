package com.common.models;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mg_role_permissions database table.
 * 
 */
@Entity
@Table(name="mg_role_permissions")
@NamedQuery(name="MgRolePermission.findAll", query="SELECT m FROM MgRolePermission m")
public class MgRolePermission implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;

	@Column(name="permission_id")
	private int permissionId;

	@Column(name="role_id")
	private int roleId;

	public MgRolePermission() {
	}

	public int getPermissionId() {
		return this.permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

}