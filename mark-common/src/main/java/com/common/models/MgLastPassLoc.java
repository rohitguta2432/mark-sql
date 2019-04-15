package com.common.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the mg_last_pass_loc database table.
 * 
 */
@Entity
@Table(name="mg_last_pass_loc")
@NamedQuery(name="MgLastPassLoc.findAll", query="SELECT m FROM MgLastPassLoc m")
public class MgLastPassLoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="avg_speed")
	private String avgSpeed;

	@Column(name="created_on")
	private Timestamp createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_ride")
	private Date dateRide;

	@Column(name="end_fuel")
	private String endFuel;

	@Column(name="end_km")
	private String endKm;

	@Column(name="end_loc")
	private String endLoc;

	@Column(name="fuel_efficiency")
	private String fuelEfficiency;

	@Column(name="max_speed")
	private String maxSpeed;

	@Column(name="service_reminder")
	private String serviceReminder;

	@Column(name="start_fuel")
	private String startFuel;

	@Column(name="start_km")
	private String startKm;

	@Column(name="start_loc")
	private String startLoc;

	@Column(name="user_id")
	private int userId;

	public MgLastPassLoc() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAvgSpeed() {
		return this.avgSpeed;
	}

	public void setAvgSpeed(String avgSpeed) {
		this.avgSpeed = avgSpeed;
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Date getDateRide() {
		return this.dateRide;
	}

	public void setDateRide(Date dateRide) {
		this.dateRide = dateRide;
	}

	public String getEndFuel() {
		return this.endFuel;
	}

	public void setEndFuel(String endFuel) {
		this.endFuel = endFuel;
	}

	public String getEndKm() {
		return this.endKm;
	}

	public void setEndKm(String endKm) {
		this.endKm = endKm;
	}

	public String getEndLoc() {
		return this.endLoc;
	}

	public void setEndLoc(String endLoc) {
		this.endLoc = endLoc;
	}

	public String getFuelEfficiency() {
		return this.fuelEfficiency;
	}

	public void setFuelEfficiency(String fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
	}

	public String getMaxSpeed() {
		return this.maxSpeed;
	}

	public void setMaxSpeed(String maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public String getServiceReminder() {
		return this.serviceReminder;
	}

	public void setServiceReminder(String serviceReminder) {
		this.serviceReminder = serviceReminder;
	}

	public String getStartFuel() {
		return this.startFuel;
	}

	public void setStartFuel(String startFuel) {
		this.startFuel = startFuel;
	}

	public String getStartKm() {
		return this.startKm;
	}

	public void setStartKm(String startKm) {
		this.startKm = startKm;
	}

	public String getStartLoc() {
		return this.startLoc;
	}

	public void setStartLoc(String startLoc) {
		this.startLoc = startLoc;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}