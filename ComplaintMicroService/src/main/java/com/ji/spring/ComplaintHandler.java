package com.ji.spring;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "complainthandler")
public class ComplaintHandler {

    @Id
    @Column(name = "complaint_handler_id")
    @Positive(message = "Complaint handler id must be a positive number greater than zero")
    private int complaintHandlerId;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name is a mandatory field")
    private String name;
    
    public ComplaintHandler() {
    	
    }
    
	public ComplaintHandler(
			@Positive(message = "Complaint handler id must be a positive number greater than zero") int complaintHandlerId,
			@NotBlank(message = "Name is a mandatory field") String name) {
		this.complaintHandlerId = complaintHandlerId;
		this.name = name;
	}

	public int getComplaintHandlerId() {
		return complaintHandlerId;
	}

	public void setComplaintHandlerId(int complaintHandlerId) {
		this.complaintHandlerId = complaintHandlerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    
    
}