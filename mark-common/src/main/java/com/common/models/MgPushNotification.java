package com.common.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the mg_push_notification database table.
 * 
 */
@Entity
@Table(name="mg_push_notification")
@NamedQuery(name="MgPushNotification.findAll", query="SELECT m FROM MgPushNotification m")
public class MgPushNotification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creation_date")
	private Date creationDate;

	@Lob
	private String message;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modification_date")
	private Date modificationDate;

	@Column(name="notified_by")
	private int notifiedBy;

	@Column(name="notified_to")
	private int notifiedTo;

	private String seen;

	private String title;

	private byte type;

	private String url;

	public MgPushNotification() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getModificationDate() {
		return this.modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public int getNotifiedBy() {
		return this.notifiedBy;
	}

	public void setNotifiedBy(int notifiedBy) {
		this.notifiedBy = notifiedBy;
	}

	public int getNotifiedTo() {
		return this.notifiedTo;
	}

	public void setNotifiedTo(int notifiedTo) {
		this.notifiedTo = notifiedTo;
	}

	public String getSeen() {
		return this.seen;
	}

	public void setSeen(String seen) {
		this.seen = seen;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte getType() {
		return this.type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}