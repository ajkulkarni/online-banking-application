package org.thothlab.devilsvault.db.model;

import java.sql.Date;

public class Request {
	private int id;
	private int requesterid;
	private String request_type;
	private String current_value;
	private String requested_value;
	private String status;
	private String approver;
	private String description;
	private String role;
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	private Date timestamp_created;
	private Date timestamp_updated;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getRequesterid() {
		return requesterid;
	}
	
	
	public void setRequesterid(int requesterid) {
		this.requesterid = requesterid;
	}

	public String getRequest_type() {
		return request_type;
	}
	public void setRequest_type(String request_type) {
		this.request_type = request_type;
	}
	public String getCurrent_value() {
		return current_value;
	}
	public void setCurrent_value(String current_value) {
		this.current_value = current_value;
	}
	public String getRequested_value() {
		return requested_value;
	}
	public void setRequested_value(String requested_value) {
		this.requested_value = requested_value;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getTimestamp_created() {
		return timestamp_created;
	}
	public void setTimestamp_created(Date timestamp_created) {
		this.timestamp_created = timestamp_created;
	}
	public Date getTimestamp_updated() {
		return timestamp_updated;
	}
	public void setTimestamp_updated(Date timestamp_updated) {
		this.timestamp_updated = timestamp_updated;
	}
	
}
