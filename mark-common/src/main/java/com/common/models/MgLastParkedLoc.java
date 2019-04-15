package com.common.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the mg_last_parked_loc database table.
 * 
 */
@Entity
@Table(name="mg_last_parked_loc")
@NamedQuery(name="MgLastParkedLoc.findAll", query="SELECT m FROM MgLastParkedLoc m")
public class MgLastParkedLoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="created_on")
	private Timestamp createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_on")
	private Date modifiedOn;

	@Temporal(TemporalType.DATE)
	@Column(name="parked_date")
	private Date parkedDate;

	@Column(name="parked_loc")
	private String parkedLoc;

	@Column(name="user_id")
	private int userId;

	public MgLastParkedLoc() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Date getModifiedOn() {
		return this.modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Date getParkedDate() {
		return this.parkedDate;
	}

	public void setParkedDate(Date parkedDate) {
		this.parkedDate = parkedDate;
	}

	public String getParkedLoc() {
		return this.parkedLoc;
	}

	public void setParkedLoc(String parkedLoc) {
		this.parkedLoc = parkedLoc;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}