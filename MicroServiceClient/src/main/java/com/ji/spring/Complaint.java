package com.ji.spring;

import java.util.Date;

public class Complaint {

    private int complaintId;

    private String description;

    private java.sql.Date date;

    private ComplaintHandler complaintHandler;

    private ComplaintStatus status = ComplaintStatus.OPEN;

    public Complaint() {
    	
    }
    
	public Complaint(int complaintId,String description, java.sql.Date date, ComplaintHandler complaintHandler, ComplaintStatus status) {
		this.complaintId = complaintId;
		this.description = description;
		this.date = date;
		this.complaintHandler = complaintHandler;
		this.status = status;
	}

	public int getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(int complaintId) {
		this.complaintId = complaintId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	public ComplaintHandler getComplaintHandler() {
		return complaintHandler;
	}

	public void setComplaintHandler(ComplaintHandler complaintHandler) {
		this.complaintHandler = complaintHandler;
	}

	public ComplaintStatus getStatus() {
		return status;
	}

	public void setStatus(ComplaintStatus status) {
		this.status = status;
	}

    
    
}
