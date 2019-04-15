package com.common.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the mg_service_info database table.
 * 
 */
@Entity
@Table(name="mg_service_info")
@NamedQuery(name="MgServiceInfo.findAll", query="SELECT m FROM MgServiceInfo m")
public class MgServiceInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String distance;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_date")
	private Date lastDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="next_date")
	private Date nextDate;

	@Column(name="user_id")
	private int userId;

	public MgServiceInfo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDistance() {
		return this.distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public Date getLastDate() {
		return this.lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public Date getNextDate() {
		return this.nextDate;
	}

	public void setNextDate(Date nextDate) {
		this.nextDate = nextDate;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}