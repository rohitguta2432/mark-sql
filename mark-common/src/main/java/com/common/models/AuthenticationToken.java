package com.common.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



import lombok.Data;

@Data
@Entity
@Table(name="auth_token")
public class AuthenticationToken implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private Integer userId;
	private String authToken;
	private Long createDate;
	private Long lastAccessed;
	private boolean isAdminToken;
	private String deviceToken;
	
	public AuthenticationToken() {
		
	}
	
	public AuthenticationToken(Integer userId, String authToken, boolean isAdminToken, String deviceToken) {

		this.userId = userId;
		this.authToken = authToken;
		this.isAdminToken = isAdminToken;
		this.deviceToken = deviceToken;
		this.onCreate();
	}

	/**
	 * <b>Instantiates a new authentication token</b>
	 *
	 * @param userId User id as Long
	 * @param authToken Auth token as String
	 * @param isAdminToken Is admin token as Boolean
	 * @param businessUnitId Business unit id as Long
	 */
	public AuthenticationToken(Integer userId, String authToken, boolean isAdminToken) {
		this.userId = userId;
		this.authToken = authToken;
		this.isAdminToken = isAdminToken;
		this.onCreate();
	}

	protected void onCreate() {
		createDate = new Date().getTime();
		lastAccessed = new Date().getTime();
	}

}
