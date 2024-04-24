package com.ji.spring;

public class ComplaintHandler {

    private int complaintHandlerId;
    private String name;
    
    public ComplaintHandler() {
    	
    }
    
	public ComplaintHandler(int complaintHandlerId, String name) {
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