package com.ji.spring;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.util.Date;

@Entity
@Table(name = "Complaint")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_id")
    private int complaintId;

    @Column(name = "description", nullable = false)
    @NotBlank(message = "Description is a mandatory field")
    private String description;

    @Column(name = "date", nullable = false)
    @PastOrPresent(message = "Date should be today or a past date")
    private java.sql.Date date;

    @ManyToOne
    @JoinColumn(name = "complaint_handler_id")
    private ComplaintHandler complaintHandler;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ComplaintStatus status = ComplaintStatus.OPEN;

    public Complaint() {
    	
    }
    
	public Complaint(int complaintId, @NotBlank(message = "Description is a mandatory field") String description,
			@PastOrPresent(message = "Date should be today or a past date") java.sql.Date date,
			ComplaintHandler complaintHandler, ComplaintStatus status) {
		super();
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

	public java.sql.Date getDate() {
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
