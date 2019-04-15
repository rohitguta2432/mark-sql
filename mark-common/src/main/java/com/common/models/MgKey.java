package com.common.models;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the mg_keys database table.
 * 
 */
@Entity
@Table(name="mg_keys")
@NamedQuery(name="MgKey.findAll", query="SELECT m FROM MgKey m")
public class MgKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="date_created")
	private Timestamp dateCreated;

	@Column(name="ignore_limits")
	private byte ignoreLimits;

	@Lob
	@Column(name="ip_addresses")
	private String ipAddresses;

	@Column(name="is_private_key")
	private byte isPrivateKey;

	private String key;

	private int level;

	@Column(name="user_id")
	private int userId;

	public MgKey() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public byte getIgnoreLimits() {
		return this.ignoreLimits;
	}

	public void setIgnoreLimits(byte ignoreLimits) {
		this.ignoreLimits = ignoreLimits;
	}

	public String getIpAddresses() {
		return this.ipAddresses;
	}

	public void setIpAddresses(String ipAddresses) {
		this.ipAddresses = ipAddresses;
	}

	public byte getIsPrivateKey() {
		return this.isPrivateKey;
	}

	public void setIsPrivateKey(byte isPrivateKey) {
		this.isPrivateKey = isPrivateKey;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}